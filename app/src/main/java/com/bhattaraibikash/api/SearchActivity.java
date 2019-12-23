package com.bhattaraibikash.api;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhattaraibikash.api.api.EmployeeAPI;
import com.bhattaraibikash.api.model.Employee;
import com.bhattaraibikash.api.url.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private EditText etEmpNo;
    private TextView tvEmpDetail;
    private Button btnSearchEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etEmpNo = findViewById(R.id.etEmpNo);
        tvEmpDetail = findViewById(R.id.tvEmpDetail);
        btnSearchEmp = findViewById(R.id.btnSearchEmp);


        btnSearchEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL.base_url).addConverterFactory(GsonConverterFactory.create()).build();

        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<Employee> listCall = employeeAPI.getEmployeeById(Integer.parseInt(etEmpNo.getText().toString()));

        listCall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SearchActivity.this, "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    String data = "";
                    data += "Name is :" + response.body().getEmployee_name() + "\n";
                    data += "Salary is :" + response.body().getEmployee_salary() + "\n";
                    data += "Age is :" + response.body().getEmployee_age() + "\n";

                    tvEmpDetail.setText(data);

                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.d("Msg", "onFailure:"+ t.getLocalizedMessage());
                Toast.makeText(SearchActivity.this, "Error : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
