package kr.co.waytech.peterparker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.app.Fragment;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.viewpager.widget.ViewPager;

import kr.co.waytech.peterparker.MainActivity;
import kr.co.waytech.peterparker.ParkingItem;
import kr.co.waytech.peterparker.PostClass;
import kr.co.waytech.peterparker.R;

import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import kr.co.waytech.peterparker.MarkerItem;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;
import static kr.co.waytech.peterparker.PostClass.ParkingLat;
import static kr.co.waytech.peterparker.PostClass.ParkingLng;
import static kr.co.waytech.peterparker.PostClass.b;
import static kr.co.waytech.peterparker.PostClass.split_location;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final LatLng ABC = null;
    public static Float ZoomLevel;
    public static int Connect_Flag = 0;
    public static final LatLng target = null;
    Marker selectedMarker;
    View marker_root_view;
    TextView tv_marker;
    ImageButton search_btn;
    Button filter_btn;
    EditText search_edt;
    public static double lat, lng;
    public static double x1, y1, x2, y2;
    final PostClass Postc = new PostClass();
    String search_result;
    TabLayout tabLayout;
    ViewPager viewPager;
    public static GoogleMap mMap;
    private Context homeFragment;
    private MapView mapView = null;
    TabItem tab1, tab2, tab3;

    public MapFragment()
    {

        // required
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        mapView = (MapView)view.findViewById(R.id.map);
        mapView.getMapAsync(this);

        search_edt = (EditText)view.findViewById(R.id.search_edt);
        search_btn = (ImageButton)view.findViewById(R.id.search_btn);
        filter_btn = (Button)view.findViewById(R.id.filter_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                search_result = search_edt.getText().toString();
            }
        });
        tabLayout=(TabLayout)view.findViewById(R.id.tabs);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("현재 Pick"));
        tabLayout.addTab(tabLayout.newTab().setText("거리순"));
        tabLayout.addTab(tabLayout.newTab().setText("가격순"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
    private void changeView(int index) {

        switch (index) {
            case 0 :
                System.out.println("첫번째 탭");
                break ;
            case 1 :
                System.out.println("두번째 탭");
                break ;
            case 2 :
                System.out.println("세번째 탭");
                break ;

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng ABC = new LatLng(37.340917, 126.7336682);
        double ABC_LAT = 37.339917;
        double ABC_LNG = 126.7336682;
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        setCustomMarkerView();
        getMarkerItems();
        mMap.setMinZoomPreference((float) 7.5);
        mMap.addMarker(new MarkerOptions().position(ABC));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ABC, 14.0f));
        ClusterManager<ClusterItem> clusterManager = new ClusterManager<>(homeFragment, mMap);
        mMap.setOnCameraIdleListener(clusterManager);
        for(int i = 0; i < 10; i++){
            double lat = ABC_LAT + (i / 200d);
            double lng = ABC_LNG + (i / 200d);
            clusterManager.addItem(new ParkingItem(new LatLng(lat, lng), "Parking" + i));
        }
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                ZoomLevel = mMap.getCameraPosition().zoom;
                lat = mMap.getCameraPosition().target.latitude;
                lng = mMap.getCameraPosition().target.longitude;
                // 줌레벨이 11, 13, 15일때 통신
                if(setConnectBool() && Connect_Flag == 1 || (lng > y2 || lng < y1 || lat > x1 || lat < x2)){
                    System.out.println("통신---------------------------------------------------------");
                    x1 = (lat + 3*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                    y1 = (lng - 3*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                    x2 = (lat - 3*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                    y2 = (lng + 3*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                    Postc.send_Location(x1, y1, x2, y2);
                    System.out.println(ZoomLevel + "    (" + x1 + ", " + y1 + ")" + " (" + x2 + ", " + y2 + ")");
                }

                getMarkerItems();
            }
        });
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback(){
            @Override
            public void onMapLoaded() {
                LatLng latLng = new LatLng(37.340917, 126.7336682);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                getMarkerItems();
            }
        });

        mMap.setOnMarkerClickListener(clusterManager);
        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ClusterItem>() {
            @Override
            public boolean onClusterItemClick(ClusterItem item) {
                return false;
            }
        });
    }
    public boolean setConnectBool(){
        if((ZoomLevel >= 10.8000 && ZoomLevel <= 11.2000) || (ZoomLevel >= 12.8000 && ZoomLevel <= 13.2000) || (ZoomLevel >= 14.8000 && ZoomLevel <= 15.2000)){
            Connect_Flag++;
            return true;
            // 통신 시작

        }
        else {
            Connect_Flag = 0;
            return false;
        }
    }
    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(homeFragment).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }
    public void getMarkerItems() {
        ArrayList<MarkerItem> ParkingList = new ArrayList();

        for (MarkerItem markerItem : ParkingList) {
            addMarker(markerItem, false);
        }

    }
    private Marker addMarker(MarkerItem markerItem, boolean isSelectedMarker) {


        LatLng position = new LatLng(markerItem.getLat(), markerItem.getLon());
        int price = markerItem.getPrice();
        String formatted = NumberFormat.getCurrencyInstance().format((price));

        tv_marker.setText(formatted);

        if (isSelectedMarker) {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_phone_blue);
            tv_marker.setTextColor(Color.WHITE);
        } else {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_phone);
            tv_marker.setTextColor(Color.BLACK);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(Integer.toString(price));
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(homeFragment, marker_root_view)));


        return mMap.addMarker(markerOptions);

    }
    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }
    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        int price = Integer.parseInt(marker.getTitle());
        MarkerItem temp = new MarkerItem(lat, lon, price);
        return addMarker(temp, isSelectedMarker);

    }
    private void changeSelectedMarker(Marker marker) {
        // 선택했던 마커 되돌리기
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }


    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


}
