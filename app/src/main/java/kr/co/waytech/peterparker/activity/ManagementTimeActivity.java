package kr.co.waytech.peterparker.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import kr.co.waytech.peterparker.R;

public class ManagementTimeActivity extends AppCompatActivity {

    int count_time_all, count_day, count_time_day;
    public static int tabbtn_tool = 1;
    public static int check_day;
    public static int[][] timechecker;
    public static ArrayList<CheckBox> Manage_checkBoxArrayList_each_day;
    public static ArrayList<CheckBox> Manage_checkBoxArrayList_day_time;
    public static ArrayList<CheckBox> Manage_checkBoxArrayList_all_time;
    public static ArrayList<TextView> Manage_checkBoxArrayList_each_day_text;
    public static ArrayList<TextView> Manage_textviewArrayList_day_text;
    public static ArrayList<TextView> Manage_textviewArrayList_all_time;
    Button show_all_btn, show_day_btn;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_time_setting);
        Manage_checkBoxArrayList_all_time= new ArrayList<>();
        Manage_textviewArrayList_all_time= new ArrayList<>();
        Manage_checkBoxArrayList_day_time= new ArrayList<>();
        Manage_textviewArrayList_day_text= new ArrayList<>();
        Manage_checkBoxArrayList_each_day = new ArrayList<>();
        Manage_checkBoxArrayList_each_day_text = new ArrayList<>();
        final CheckBox checkall = findViewById(R.id.checkbox_check_all_1);
        final CheckBox checkday = findViewById(R.id.checkbox_check_all_2);
        show_all_btn = findViewById(R.id.show_check_all_btn);
        show_day_btn = findViewById(R.id.show_check_day_btn);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>공유 시간 설정</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(1);
        final LinearLayout checking_all = findViewById(R.id.checking_all);
        final LinearLayout checking_day = findViewById(R.id.checking_day);
        show_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_all_btn.setBackgroundResource(R.drawable.btn_white);
                show_all_btn.setTextSize(18);
                show_all_btn.setTypeface(show_all_btn.getTypeface(), Typeface.BOLD);
                show_day_btn.setBackgroundResource(R.drawable.btn_gray);
                show_day_btn.setTextSize(15);
                show_day_btn.setTypeface(show_day_btn.getTypeface(), Typeface.NORMAL);
                checking_all.setVisibility(View.VISIBLE);
                checking_day.setVisibility(View.GONE);
                checkall.setVisibility(View.VISIBLE);
                checkday.setVisibility(View.GONE);
            }
        });
        show_day_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_day_btn.setBackgroundResource(R.drawable.btn_white);
                show_day_btn.setTextSize(18);
                show_day_btn.setTypeface(show_day_btn.getTypeface(), Typeface.BOLD);
                show_all_btn.setBackgroundResource(R.drawable.btn_gray);
                show_all_btn.setTextSize(15);
                show_all_btn.setTypeface(show_all_btn.getTypeface(), Typeface.NORMAL);
                checking_day.setVisibility(View.VISIBLE);
                checking_all.setVisibility(View.GONE);
                checkall.setVisibility(View.GONE);
                checkday.setVisibility(View.VISIBLE);
            }
        });
        for(count_day = 0; count_day < 7; count_day++) {
            final String checkboxid_day = "management_checkbox_day_" + (count_day + 1);
            String textviewid_day = "management_text_day_" + (count_day + 1);
            int cresID_day = getResources().getIdentifier(checkboxid_day, "id", getPackageName());
            int tresID_day = getResources().getIdentifier(textviewid_day, "id", getPackageName());
            Manage_checkBoxArrayList_each_day.add((CheckBox) findViewById(cresID_day));
            Manage_checkBoxArrayList_each_day_text.add((TextView) findViewById(tresID_day));

            Manage_checkBoxArrayList_each_day.get(count_day).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                int b = count_day;
                public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                    if (bool == true) {
                        compoundButton.setButtonDrawable(R.drawable.ic_booking_time_enable_small);
                        Manage_checkBoxArrayList_each_day_text.get(b).setTextColor(Color.parseColor("#ffffff"));
                    }
                    else{
                        compoundButton.setButtonDrawable(R.drawable.ic_booking_time_disable_small);
                        Manage_checkBoxArrayList_each_day_text.get(b).setTextColor(Color.parseColor("#000000"));
                    }
                }
            });
        }

        checkall.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                if(checkall.isChecked() && tabbtn_tool == 1){
                    for(int i = 0; i < 48; i++) {
                        Manage_checkBoxArrayList_all_time.get(i).setChecked(true);
                        Manage_checkBoxArrayList_all_time.get(i).setButtonDrawable(R.drawable.ic_booking_time_enable);
                        Manage_checkBoxArrayList_all_time.get(i).setTextColor(Color.parseColor("#ffffff"));
                        checkall.setChecked(true);
                    }
                }

                else if(!checkall.isChecked() && tabbtn_tool == 1){
                    for(int i = 0; i < 48; i++) {
                        Manage_checkBoxArrayList_all_time.get(i).setChecked(false);
                        Manage_checkBoxArrayList_all_time.get(i).setButtonDrawable(R.drawable.ic_booking_time_disable);
                        Manage_checkBoxArrayList_all_time.get(i).setTextColor(Color.parseColor("#000000"));
                        checkall.setChecked(false);
                    }
                }

            }
        });
        checkday.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                if(checkday.isChecked() && tabbtn_tool == 1){
                    for(int i = 0; i < 48; i++) {
                        Manage_checkBoxArrayList_day_time.get(i).setChecked(true);
                        Manage_checkBoxArrayList_day_time.get(i).setButtonDrawable(R.drawable.ic_booking_time_enable);
                        Manage_textviewArrayList_day_text.get(i).setTextColor(Color.parseColor("#ffffff"));
                        checkday.setChecked(true);
                    }
                }
                else if(!checkday.isChecked() && tabbtn_tool == 1){
                    for(int i = 0; i < 48; i++) {
                        Manage_checkBoxArrayList_day_time.get(i).setChecked(false);
                        Manage_checkBoxArrayList_day_time.get(i).setButtonDrawable(R.drawable.ic_booking_time_disable);
                        Manage_textviewArrayList_day_text.get(i).setTextColor(Color.parseColor("#000000"));
                        checkday.setChecked(false);
                    }
                }

            }
        });
        for (count_time_all = 0; count_time_all < 48; count_time_all++) {
            final String checkboxid_time = "management_checkbox_all_time_" + (count_time_all + 1);
            String textviewid_time = "management_checkbox_all_text_" + (count_time_all + 1);
            int cresID_time = getResources().getIdentifier(checkboxid_time, "id", getPackageName());
            int tresID_time = getResources().getIdentifier(textviewid_time, "id", getPackageName());
            Manage_checkBoxArrayList_all_time.add((CheckBox)findViewById(cresID_time));
            Manage_textviewArrayList_all_time.add((TextView)findViewById(tresID_time));
            Manage_checkBoxArrayList_all_time.get(count_time_all).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                int a = count_time_all;
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                    if (bool == true) {
                        compoundButton.setButtonDrawable(R.drawable.ic_booking_time_enable);
                        Manage_textviewArrayList_all_time.get(a).setTextColor(Color.parseColor("#ffffff"));
                    }
                    else{
                        compoundButton.setButtonDrawable(R.drawable.ic_booking_time_disable);
                        Manage_textviewArrayList_all_time.get(a).setTextColor(Color.parseColor("#000000"));
                        tabbtn_tool = 0;
                        checkall.setChecked(false);
                        tabbtn_tool = 1;
                    }
                }
            });

        }
        for (count_time_day = 0; count_time_day < 48; count_time_day++) {
            final String checkboxid_time = "management_checkbox_day_time_" + (count_time_day + 1);
            String textviewid_time = "management_checkbox_day_text_" + (count_time_day + 1);
            int cresID_time = getResources().getIdentifier(checkboxid_time, "id", getPackageName());
            int tresID_time = getResources().getIdentifier(textviewid_time, "id", getPackageName());
            Manage_checkBoxArrayList_day_time.add((CheckBox)findViewById(cresID_time));
            Manage_textviewArrayList_day_text.add((TextView)findViewById(tresID_time));
            Manage_checkBoxArrayList_day_time.get(count_time_day).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                int b = count_time_day;
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                    if (bool == true) {
                        compoundButton.setButtonDrawable(R.drawable.ic_booking_time_enable);
                        Manage_textviewArrayList_day_text.get(b).setTextColor(Color.parseColor("#ffffff"));
                    }
                    else{
                        compoundButton.setButtonDrawable(R.drawable.ic_booking_time_disable);
                        Manage_textviewArrayList_day_text.get(b).setTextColor(Color.parseColor("#000000"));
                        tabbtn_tool = 0;
                        checkday.setChecked(false);
                        tabbtn_tool = 1;
                    }
                }
            });

        }
    }
}
