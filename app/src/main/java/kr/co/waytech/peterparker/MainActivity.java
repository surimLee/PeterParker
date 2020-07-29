package kr.co.waytech.peterparker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.co.waytech.peterparker.fragment.BookingListFragment;
import kr.co.waytech.peterparker.fragment.HomeFragment;
import kr.co.waytech.peterparker.fragment.MapFragment;
import kr.co.waytech.peterparker.fragment.ParkingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.mainactivity_bottomnavigationview);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new HomeFragment()).commit();
                                return true;
                            case R.id.action_map:
                                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new MapFragment()).commit();
                                return true;
                            case R.id.action_bookingList:
                                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new BookingListFragment()).commit();
                                return true;
                            case R.id.action_parking:
                                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new ParkingFragment()).commit();
                                return true;
                        }
                return false;
            }
        });
    }
}


