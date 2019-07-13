package com.github_rn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText = findViewById(R.id.editText);
        button = (Button)findViewById(R.id.login_btn);

    }

    @Override
    protected void onResume() {
        super.onResume();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString() != "") {
                    String user = editText.getText().toString();
                    if (user.equals("1")) {
                        Intent intent = new Intent(LoginActivity.this, RNContainerActivity.class);
                        intent.putExtra("appName","fengxiaoge");
                        startActivity(intent);
                    } else if (user.equals("2")) {
                        Intent intent = new Intent(LoginActivity.this, RNContainerActivity.class);
                        intent.putExtra("appName","fengxiaodai");
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
