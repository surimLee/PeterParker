package kr.co.waytech.peterparker.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kr.co.waytech.peterparker.OneDayDecorator;
import kr.co.waytech.peterparker.R;
import kr.co.waytech.peterparker.SaturdayDecorator;
import kr.co.waytech.peterparker.SundayDecorator;


public class CalendarActivity extends AppCompatActivity  {

    MaterialCalendarView materialCalendarView;
    long now = System.currentTimeMillis() - 1000;
    long MaximumDate = now + (1000*24*60*60*14);
    public static int mYear, mMonth, mDay;
    Date date_today = new Date(now);
    public Drawable drawable;
    String[] dateraw1, dateraw2;
    String[] splited_date1, splited_date2;
    Date date_max = new Date(MaximumDate);
    final List<CalendarDay> days = new ArrayList<>();
    Color color = new Color();
    TextView calendar_text1, calendar_text2;
    Button select_btn, booking_day_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_booking);
        calendar_text1 = findViewById(R.id.calendar_text1);
        materialCalendarView = findViewById(R.id.calendarView);
        select_btn = findViewById(R.id.calendar_btn);
        booking_day_text = findViewById(R.id.booking_Calendar_btn);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(now)
                .setMaximumDate(MaximumDate)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();
                mYear = Year;
                mMonth = Month;
                mDay = Day;
                calendar_text1.setText(Year + "년 " + Month + "월 " + Day + "일 ");
            }
        });
        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookingActivity.CalendarBtn.setText(mYear + "년 " + mMonth + "월 " + mDay + "일 ");
                finish();
            }
        });

        /*
        materialCalendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates)
            { // do stuff
                if (dates.get(0).isAfter(dates.get(1))) {
                    System.out.println("1");
                } else {
                    System.out.println(dates.get(0).toString() + ", " + dates.get(dates.size() - 1).toString());
                    dateraw1 = dates.get(0).toString().split("\\{");
                    dateraw2 = dates.get(0).toString().split("\\{");
                    splited_date1 = dateraw1[1].split("-");

                }

            }
        });

         */

    }

}
