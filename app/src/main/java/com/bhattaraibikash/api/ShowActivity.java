package com.bhattaraibikash.api;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhattaraibikash.api.adapter.EmployeeAdapter;
import com.bhattaraibikash.api.api.EmployeeAPI;
import com.bhattaraibikash.api.model.Employee;
import com.bhattaraibikash.api.url.URL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowActivity extends AppCompatActivity {

    private TextView tvList;
    private RecyclerView rvEmployeeList;
    private List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        rvEmployeeList = findViewById(R.id.rvEmployeeList);
        rvEmployeeList.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvEmployeeList.setLayoutManager(linearLayoutManager);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL.base_url).addConverterFactory(GsonConverterFactory.create()).build();

        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<List<Employee>> listCall = employeeAPI.getAllEmployees();

        //Asynchronous Call
        listCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ShowActivity.this, "Error code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    List<Employee> employeeList = response.body();

                    EmployeeAdapter employeeAdapter = new EmployeeAdapter(ShowActivity.this, employeeList);
                    rvEmployeeList.setAdapter(employeeAdapter);
                    rvEmployeeList.setLayoutManager(new LinearLayoutManager(ShowActivity.this));

                }

            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.d("Msg", "onFailure:"+ t.getLocalizedMessage());
                Toast.makeText(ShowActivity.this, "Error : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
