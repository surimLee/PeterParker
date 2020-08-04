package kr.co.waytech.peterparker.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import kr.co.waytech.peterparker.MyAdapter;
import kr.co.waytech.peterparker.ParkingItem;
import kr.co.waytech.peterparker.PostClass;
import kr.co.waytech.peterparker.R;

import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.text.NumberFormat;
import java.util.ArrayList;

import kr.co.waytech.peterparker.MarkerItem;


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
    FrameLayout frameLayout;
    private ListView mListView;
    TabItem tab1, tab2, tab3;
    public static ArrayList<MarkerItem> ParkingList;

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
        frameLayout=(FrameLayout)view.findViewById(R.id.frame_view);
        //mListView = (ListView)view.findViewById(R.id.listView);
        tabLayout.addTab(tabLayout.newTab().setText("현재 Pick"));
        tabLayout.addTab(tabLayout.newTab().setText("거리순"));
        tabLayout.addTab(tabLayout.newTab().setText("가격순"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if (pos == 0){
                   // dataSetting();
                }
                else if (pos == 1){
                    System.out.println("거리순---------------------------------------------------------");
                    ZoomLevel = mMap.getCameraPosition().zoom;
                    lat = mMap.getCameraPosition().target.latitude;
                    lng = mMap.getCameraPosition().target.longitude;
                    // 줌레벨이 11, 13, 15일때 통신
                    System.out.println("통신---------------------------------------------------------");
                    x1 = (lat + 1*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                    y1 = (lng - 1*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                    x2 = (lat - 1*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                    y2 = (lng + 1*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                    Postc.send_Location(x1, y1, x2, y2);
                    System.out.println(ZoomLevel + " 위치 :  (" + x1 + ", " + y1 + ")" + " (" + x2 + ", " + y2 + ")");
                }
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
    private void dataSetting(){

        MyAdapter mMyAdapter = new MyAdapter();


        for (int i=0; i<10; i++) {
            mMyAdapter.addItem(ContextCompat.getDrawable(homeFragment.getApplicationContext(), R.drawable.home_icon), "name_" + i, "contents_" + i);
        }

        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter);
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
                x1 = (lat + 1*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                y1 = (lng - 1*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                x2 = (lat - 1*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                y2 = (lng + 1*(0.012 * (2^(int)(15.0 - ZoomLevel))));
                Postc.AddMarker(Postc.getcountnumber(), x1, x2, y1, y2);
                Postc.RemoveMarker(x1, x2, y1, y2);
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
<<<<<<< HEAD
        try {
            ArrayList<MarkerItem> ParkingList = new ArrayList();
            for (int i = 0; i < Postc.count; i++) {
                ParkingList.add(new MarkerItem(Postc.ParkingLat[i], Postc.ParkingLng[i], Postc.ParkingPrice[i]));
                System.out.println("핑" + Postc.ParkingLat[i] + ", " + Postc.ParkingLng[i]);
            }
            for (MarkerItem markerItem : ParkingList) {
                //addMarker(markerItem, false);
            }
=======
        ArrayList<MarkerItem> ParkingList = new ArrayList();

        for (MarkerItem markerItem : ParkingList) {
            addMarker(markerItem, false);
>>>>>>> 47e2962d38a165f9d347df6ad597f13cbd472068
        }
        catch (Exception e){
            System.out.println("리스트 추가 오류");
        }
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

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
    /*
    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        int price = Integer.parseInt(marker.getTitle());
        MarkerItem temp = new MarkerItem(lat, lon, price);
        return addMarker(temp, isSelectedMarker);

    }
     */
    /*
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

     */
    /*
    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
*/

}
