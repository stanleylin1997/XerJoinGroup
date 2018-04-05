package com.example.stanley.xerjoingroup;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.stanley.xerjoingroup.Util.DatePickerFragment;
import com.example.stanley.xerjoingroup.Util.MySingleton;
import com.example.stanley.xerjoingroup.Util.TimePickerFragment;

import java.util.HashMap;
import java.util.Map;



public class CreateGroupActivity extends AppCompatActivity implements TimePickerFragment.DataCallBack{
    Button button;
    EditText Name,Num;
    TextView timeText;

    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        button = (Button) findViewById(R.id.buttonG);
        Name = (EditText) findViewById(R.id.editText3);
        Num = (EditText) findViewById(R.id.editText4);
        timeText = (TextView) findViewById(R.id.timeText);
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                //调用show方法弹出对话框
                // 第一个参数为FragmentManager对象
                // 第二个为调用该方法的fragment的标签
                datePickerFragment.show(getFragmentManager(), "date_picker");

            }
        });




        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        final String[] type = {"慢跑", "籃球", "排球", "重訓", "足球"};
        ArrayAdapter<String> typeList = new ArrayAdapter<>(CreateGroupActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                type);
        spinner.setAdapter(typeList);

        final Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        final String[] place = {"五期", "六期", "體育館", "游泳館2F", "操場"};
        ArrayAdapter<String> placeList = new ArrayAdapter<>(CreateGroupActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                place);
        spinner2.setAdapter(placeList);





        builder = new AlertDialog.Builder(CreateGroupActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name,type,place,time;
                final int num,remain, uid=123;
                name= Name.getText().toString();
                type = spinner.getSelectedItem().toString();
                place =spinner2.getSelectedItem().toString();
                num = Integer.parseInt(Num.getText().toString());
                remain = num-1;
                time = timeText.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Values.lOGIN_SERVER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                builder.setTitle("Server response");
                                builder.setMessage("Response:" +response);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Name.setText("");

                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(CreateGroupActivity.this,"Error!",Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        Map parmas = new HashMap();
                        Mysql mysql=new Mysql();
                        String query = mysql.createNewGroup(name,type,place,uid,time,num,remain);

                        parmas.put("query",query);

                        Log.d("query",query);


                        return parmas;
                    }
                };

                MySingleton.getmInstance(CreateGroupActivity.this).addToRequestque(stringRequest);


            }
        });

    }


    @Override
    public void getData(String data) {
        timeText.setText(data);
    }
}
