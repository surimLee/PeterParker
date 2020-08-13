package kr.co.waytech.peterparker.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import kr.co.waytech.peterparker.R;

import static kr.co.waytech.peterparker.ListAdapter.address;
import static kr.co.waytech.peterparker.ListAdapter.phone;
import static kr.co.waytech.peterparker.ListAdapter.price;
import static kr.co.waytech.peterparker.ListAdapter.distance;

public class BookingActivity extends AppCompatActivity {
    public static int count;
    long now = System.currentTimeMillis() - 1000;
    Date date_today = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy년 MM월 dd일");
    String formatDate = sdfNow.format(date_today);
    public static double selectcount = 0;
    public static ArrayList<CheckBox> checkBoxArrayList;
    public static ArrayList<TextView> textviewArrayList;
    public static double checkedcount;
    public static TextView booking_parking_lot_address, booking_parking_lot_phone, booking_parking_lot_price, booking_parking_lot_distance;
    String textprice, texttime;
    public static Button btnbooking, CalendarBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinglist);
        checkBoxArrayList= new ArrayList<>();
        textviewArrayList= new ArrayList<>();
        btnbooking = (Button)findViewById(R.id.booking_btn);
        CalendarBtn = (Button)findViewById(R.id.booking_Calendar_btn);
        booking_parking_lot_address = findViewById(R.id.booking_parking_lot_address);
        booking_parking_lot_phone = findViewById(R.id.booking_parking_lot_phone);
        booking_parking_lot_price = findViewById(R.id.booking_parking_lot_price);
        booking_parking_lot_distance = findViewById(R.id.booking_parking_lot_distance);

        booking_parking_lot_address.setText(address);
        booking_parking_lot_price.setText("시간당 " + price);
        booking_parking_lot_distance.setText(distance);
        booking_parking_lot_phone.setText(phone);
        CalendarBtn.setText(formatDate);
        CalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        for(count = 0; count < 48; count++) {
            String checkboxid = "checkbox_time_" + (count + 1);
            String textviewid = "checkbox_text_" + (count + 1);
            int cresID = getResources().getIdentifier(checkboxid, "id", getPackageName());
            int tresID = getResources().getIdentifier(textviewid, "id", getPackageName());
            checkBoxArrayList.add((CheckBox) findViewById(cresID));
            textviewArrayList.add((TextView)findViewById(tresID));
            checkBoxArrayList.get(count).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            int a = count;
                    @Override

                    public void onCheckedChanged(CompoundButton compoundButton, boolean bool) {
                        if (bool == true) {
                            compoundButton.setButtonDrawable(R.drawable.ic_booking_time_enable);
                            textviewArrayList.get(a).setTextColor(Color.parseColor("#ffffff"));
                            selectcount++;
                            for (int b = 0; b < checkBoxArrayList.size(); b++) {
                                if (checkBoxArrayList.get(a).isChecked() && checkBoxArrayList.get(b).isChecked()) {
                                    if (b > a) {
                                        for (int c = a + 1; c < b; c++) {
                                            if (!checkBoxArrayList.get(c).isChecked()) {
                                                checkBoxArrayList.get(c).setChecked(true);
                                                checkBoxArrayList.get(c).setButtonDrawable(R.drawable.ic_booking_time_enable);
                                                textviewArrayList.get(c).setTextColor(Color.parseColor("#ffffff"));
                                                System.out.println("선택 : " + a + ", c = " + c);
                                            }
                                        }
                                    } else if (a > b) {
                                        for (int c = b; c < a; c++) {
                                            if (!checkBoxArrayList.get(c).isChecked()) {
                                                checkBoxArrayList.get(c).setChecked(true);
                                                checkBoxArrayList.get(c).setButtonDrawable(R.drawable.ic_booking_time_enable);
                                                textviewArrayList.get(c).setTextColor(Color.parseColor("#ffffff"));
                                                System.out.println("선택 : " + a + ", c = " + c);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            compoundButton.setButtonDrawable(R.drawable.ic_booking_time_disable);
                            textviewArrayList.get(a).setTextColor(Color.parseColor("#000000"));
                            selectcount++;
                            if (selectcount > 1) {
                                for (int b = 0; b < checkBoxArrayList.size(); b++) {
                                    if (checkBoxArrayList.get(b).isChecked()) {
                                        if (b < a) {
                                            if (checkBoxArrayList.get(b).isChecked()) {
                                                checkBoxArrayList.get(a).setChecked(false);
                                                checkBoxArrayList.get(a).setButtonDrawable(R.drawable.ic_booking_time_disable);
                                                textviewArrayList.get(a).setTextColor(Color.parseColor("#000000"));
                                                checkBoxArrayList.get(b).setChecked(false);
                                                checkBoxArrayList.get(b).setButtonDrawable(R.drawable.ic_booking_time_disable);
                                                textviewArrayList.get(b).setTextColor(Color.parseColor("#000000"));
                                                System.out.println("선택 : " + a + ", b 제거 " + b);
                                            }
                                        } else if (a < b) {
                                            if (checkBoxArrayList.get(b).isChecked()) {
                                                checkBoxArrayList.get(b).setChecked(false);
                                                checkBoxArrayList.get(b).setButtonDrawable(R.drawable.ic_booking_time_disable);
                                                textviewArrayList.get(b).setTextColor(Color.parseColor("#000000"));
                                                checkBoxArrayList.get(a).setChecked(false);
                                                checkBoxArrayList.get(a).setButtonDrawable(R.drawable.ic_booking_time_disable);
                                                textviewArrayList.get(a).setTextColor(Color.parseColor("#000000"));
                                                System.out.println("선택 : " + a + ", b 제거 " + b);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
            });

            btnbooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                    checkedcount = 0;
                    int g;
                    for (g = 0; g < checkBoxArrayList.size(); g++) {
                        if (checkBoxArrayList.get(g).isChecked()) {
                            checkedcount++;
                            textprice = ("결제 금액 : " + (int) (3000 * (checkedcount / 2)) + " 원");
                            System.out.println(checkedcount);
                        }
                        if(checkedcount == 0){
                            textprice = ("결제 금액 : " + "0 원");
                            System.out.println(checkedcount);
                        }
                    }
                    g = 0;
                    while (g < checkBoxArrayList.size()) {
                        if (checkBoxArrayList.get(g).isChecked()) {
                            if(checkedcount != 0) {
                                if(checkedcount == 48) {
                                    texttime = ("예약 시간 : " + textviewArrayList.get(g).getText() + " ~  23:59" );

                                }
                                else {
                                    texttime = ("예약 시간 : " + textviewArrayList.get(g).getText() + " ~ " + textviewArrayList.get(g + (int) checkedcount).getText());
                                    break;
                                }
                            }

                        }
                        if(checkedcount == 0) {
                            texttime = ("예약 시간 : " + "선택된 시간이 없습니다.");
                            System.out.println(checkedcount);
                            break;
                        }
                        g++;
                    }

                    builder.setTitle("주차장 예약을 하시겠습니까?").setMessage(textprice + "\n" + texttime).setIcon(R.drawable.booking_icon_enable);
                    builder.setPositiveButton("예약", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.println("예약함");
                            //post 함수
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.println("취소함");
                        }
                    });
                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                }
            });

        }

    }

}
