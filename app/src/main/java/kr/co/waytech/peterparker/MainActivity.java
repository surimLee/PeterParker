package kr.co.waytech.peterparker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;
import android.location.LocationManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.co.waytech.peterparker.fragment.BookingListFragment;
import kr.co.waytech.peterparker.fragment.MapFragment;
import kr.co.waytech.peterparker.fragment.ParkingFragment;
import kr.co.waytech.peterparker.fragment.ProfileFragment;

import static kr.co.waytech.peterparker.fragment.MapFragment.mMap;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private androidx.appcompat.widget.Toolbar toolbar;
    public static Location location;
    public static LocationManager lm;


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