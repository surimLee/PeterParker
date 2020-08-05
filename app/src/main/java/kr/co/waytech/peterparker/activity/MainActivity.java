package kr.co.waytech.peterparker.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.co.waytech.peterparker.R;
import kr.co.waytech.peterparker.adapter.BookingListAdapter;
import kr.co.waytech.peterparker.fragment.BookingListFragment;
import kr.co.waytech.peterparker.fragment.MapFragment;
import kr.co.waytech.peterparker.fragment.ParkingFragment;
import kr.co.waytech.peterparker.fragment.ProfileFragment;
import kr.co.waytech.peterparker.model.BookingList;

public class MainActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}


