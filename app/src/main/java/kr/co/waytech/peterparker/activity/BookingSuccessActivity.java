package kr.co.waytech.peterparker.activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kr.co.waytech.peterparker.R;


public class BookingSuccessActivity extends AppCompatActivity {

    TextView success_list_text;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>주차장 예약</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(1);

        success_list_text = findViewById(R.id.success_list_text);
        SpannableString content = new SpannableString("내역");
        content.setSpan(new UnderlineSpan(), 0, content.length(),0);
        success_list_text.setText(content);
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
