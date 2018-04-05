package com.example.stanley.xerjoingroup;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stanley.xerjoingroup.FakeAdapter.AddGroupAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddGroupActivity extends AppCompatActivity {



    List<AddGroup> groupList;
    Context context;

    //the recyclerview
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_addgroup);
        recyclerView =findViewById(R.id.addgroup_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groupList = new ArrayList<>();

        loadGroup();


    }

    private void loadGroup() {

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, Values.READ_DATA_URL, new Response.Listener<String>() {
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
                    recyclerView.setAdapter(addGroupAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                Mysql mysql=new Mysql();
                String query=mysql.getGroup();
                params.put("query",query);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }
}
