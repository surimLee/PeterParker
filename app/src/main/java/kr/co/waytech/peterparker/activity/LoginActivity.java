package kr.co.waytech.peterparker.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import kr.co.waytech.peterparker.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>로그인</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(1);


        Button button_findID = findViewById(R.id.btn_find_id);
        button_findID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LoginActivity.this, FineIDActivity.class);
                startActivity(intent2);
            }
        });

        Button button_findPW = findViewById(R.id.btn_find_pw);
        button_findPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(LoginActivity.this, FinePWActivity.class);
                startActivity(intent3);
            }
        });

        Button button_signup = findViewById(R.id.btn_signup);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent4);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
