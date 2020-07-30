package kr.co.waytech.peterparker;

import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;

public class FinePWActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>비밀번호 찾기</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(1);

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
