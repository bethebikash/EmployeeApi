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

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etSalary, etAge;
    private Button btnRegisterEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etSalary = findViewById(R.id.etSalary);
        etAge = findViewById(R.id.etAge);
        btnRegisterEmp = findViewById(R.id.btnRegisterEmp);

        btnRegisterEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void Register() {
        String name = etName.getText().toString();
        Float salary = Float.parseFloat(etSalary.getText().toString());
        int age = Integer.parseInt(etAge.getText().toString());

        EmployeeCUD employeeCUD = new EmployeeCUD(name, salary, age);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);

        Call<Void> voidCall = employeeAPI.registerEmployee(employeeCUD);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Employee created successfully.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Msg", "onFailure:" + t.getLocalizedMessage());
                Toast.makeText(RegisterActivity.this, "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
