package com.bhattaraibikash.api;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnShow, btnRegister, btnSearch, btnUpdateDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        btnUpdateDelete = findViewById(R.id.btnUpdateDelete);
        btnUpdateDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnShow:
                startActivity(new Intent(MainActivity.this, ShowActivity.class));
                break;
            case R.id.btnRegister:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;

            case R.id.btnSearch:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            case R.id.btnUpdateDelete:
                startActivity(new Intent(MainActivity.this, UpdateDeleteActivity.class));
                break;

            default:
                break;
        }
    }
}
