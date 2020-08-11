package kr.co.waytech.peterparker.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;

import kr.co.waytech.peterparker.R;

public class AddParkinglotActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addparkinglot);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>내 주차장 등록</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(1);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
