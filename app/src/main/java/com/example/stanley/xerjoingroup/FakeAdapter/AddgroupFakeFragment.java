package com.example.stanley.xerjoingroup.FakeAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.stanley.xerjoingroup.AddGroup;
import com.example.stanley.xerjoingroup.Mysql;
import com.example.stanley.xerjoingroup.Util.MySingleton;
import com.example.stanley.xerjoingroup.R;
import com.example.stanley.xerjoingroup.Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AddgroupFakeFragment extends Fragment {



    List<AddGroup> groupList = new ArrayList<>();
    Context context;
    @Bind(R.id.addgroup_rv) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.adapter_addgroup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //抓出成立團
        getGroup();
    }

    public static Fragment newInstance() {
        return new AddgroupFakeFragment();
    }

    private void getGroup() {

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, Values.READ_DATA_URL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("response");

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject group = array.getJSONObject(i);
                        groupList.add(new AddGroup(
                                group.getInt("gid"),
                                group.getString("name"),
                                group.getString("type"),
                                group.getString("place"),
                                group.getInt("uid"),
                                group.getString("date"),
                                group.getInt("number"),
                                group.getInt("remain")

                        ));


                    }

                    AddGroupAdapter addGroupAdapter = new AddGroupAdapter(groupList);
                    //要記得有layoutmanager
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
                    recyclerView.setAdapter(addGroupAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("error","do not get ");

            }


        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                Mysql mysql=new Mysql();
                String query=mysql.getGroup();
                params.put("query",query);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(this.getActivity()).addToRequestque(stringRequest);


    }

}
