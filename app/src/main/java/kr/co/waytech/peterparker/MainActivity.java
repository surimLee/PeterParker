package kr.co.waytech.peterparker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.co.waytech.peterparker.adapter.RecentBookingAdapter;
import kr.co.waytech.peterparker.fragment.BookingListFragment;
import kr.co.waytech.peterparker.fragment.MapFragment;
import kr.co.waytech.peterparker.fragment.ParkingFragment;
import kr.co.waytech.peterparker.fragment.ProfileFragment;
import kr.co.waytech.peterparker.model.RecentBooking;

public class MainActivity extends AppCompatActivity {

    RecyclerView recentRecycler;
    RecentBookingAdapter recentBookingAdapter;

    private androidx.appcompat.widget.Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add dummy data in Booking class
        List<RecentBooking> recentBookingList = new ArrayList<>();
        recentBookingList.add(new RecentBooking("산기대 종합관 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "3,000원", "2020년 07월 20일(월) 12:00\n~2020년 07월 20일(월) 14:00", R.drawable.parkinglot4));
        recentBookingList.add(new RecentBooking("산기대 E동 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "2,000원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot5));
        recentBookingList.add(new RecentBooking("산기대 A동 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "2,000원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot5));
        recentBookingList.add(new RecentBooking("산기대 B동 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "2,000원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot5));
        recentBookingList.add(new RecentBooking("산기대 C동 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "2,000원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot5));
        recentBookingList.add(new RecentBooking("산기대 D동 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "2,000원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot5));
        recentBookingList.add(new RecentBooking("시흥비즈니스센터","사용완료","경기도 시흥시 정왕동 2121-1", "2,000원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot3));

        setRecentRecycler(recentBookingList);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.mainactivity_bottomnavigationview);
        bottomNavigationView.setSelectedItemId(R.id.action_map);
        getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new MapFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_map:
                                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new MapFragment()).commit();
                                return true;
                            case R.id.action_bookingList:
                                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new BookingListFragment()).commit();
                                return true;
                            case R.id.action_parking:
                                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new ParkingFragment()).commit();
                                return true;
                            case R.id.action_profile:
                                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new ProfileFragment()).commit();
                                return true;
                        }
                return false;
            }
        });
    }

    private  void setRecentRecycler(List<RecentBooking> recentBookingList){
        recentRecycler = findViewById(R.id.recent_recycler);

        //Use Linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);

        //setting Adapter
        recentBookingAdapter = new RecentBookingAdapter(this, recentBookingList);
        recentRecycler.setAdapter(recentBookingAdapter);
    }
}


