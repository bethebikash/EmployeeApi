package com.bhattaraibikash.api;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhattaraibikash.api.api.EmployeeAPI;
import com.bhattaraibikash.api.model.Employee;
import com.bhattaraibikash.api.model.EmployeeCUD;
import com.bhattaraibikash.api.url.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateDeleteActivity extends AppCompatActivity {
    private EditText etEmpNoUd, etNameUd, etSalaryUd, etAgeUd;
    private Button btnSearchUd, btnUpdate, btnDelete;
    EmployeeAPI employeeAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        etEmpNoUd = findViewById(R.id.etEmpNoUd);
        etNameUd = findViewById(R.id.etNameUd);
        etSalaryUd = findViewById(R.id.etSalaryUd);
        etAgeUd = findViewById(R.id.etAgeUd);
        btnSearchUd = findViewById(R.id.btnSearchUd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnSearchUd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateEmployee();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteEmployee();
            }
        });
    }

    private void CreateInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        employeeAPI = retrofit.create(EmployeeAPI.class);
    }

    private void LoadData() {

        CreateInstance();
        Call<Employee> employeeCall = employeeAPI.getEmployeeById(Integer.parseInt(etEmpNoUd.getText().toString()));

        employeeCall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(UpdateDeleteActivity.this, "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                } else {

                    etNameUd.setText(response.body().getEmployee_name());
                    etSalaryUd.setText(Float.toString(response.body().getEmployee_salary()));
                    etAgeUd.setText(Integer.toString(response.body().getEmployee_age()));
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.d("Msg", "onFailure:" + t.getLocalizedMessage());
                Toast.makeText(UpdateDeleteActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void UpdateEmployee() {
        CreateInstance();

        String name = etNameUd.getText().toString();
        Float salary = Float.parseFloat(etSalaryUd.getText().toString());
        int age = Integer.parseInt(etAgeUd.getText().toString());

        EmployeeCUD employeeCUD = new EmployeeCUD(name, salary, age);

        Call<Void> voidCall = employeeAPI.updateEmployee(Integer.parseInt(etEmpNoUd.getText().toString()), employeeCUD);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(UpdateDeleteActivity.this, "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateDeleteActivity.this, "Employee updated successfully.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Msg", "onFailure:" + t.getLocalizedMessage());
                Toast.makeText(UpdateDeleteActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void DeleteEmployee() {
        CreateInstance();

        Call<Void> voidCall = employeeAPI.deleteEmployee(Integer.parseInt(etEmpNoUd.getText().toString()));

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(UpdateDeleteActivity.this, "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateDeleteActivity.this, "Employee deleted successfully.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Msg", "onFailure:" + t.getLocalizedMessage());
                Toast.makeText(UpdateDeleteActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
