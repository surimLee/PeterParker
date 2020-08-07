package kr.co.waytech.peterparker.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import kr.co.waytech.peterparker.ParkingItem;
import kr.co.waytech.peterparker.activity.PostClass;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import kr.co.waytech.peterparker.Data;
import kr.co.waytech.peterparker.ListData;
import kr.co.waytech.peterparker.MainActivity;
import kr.co.waytech.peterparker.MyItem;
import kr.co.waytech.peterparker.OwnIconRendered;
import kr.co.waytech.peterparker.PostClass;
import kr.co.waytech.peterparker.activity.PostClass;
import kr.co.waytech.peterparker.R;
import kr.co.waytech.peterparker.RecyclerAdapter;
import kr.co.waytech.peterparker.ListAdapter;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static kr.co.waytech.peterparker.PostClass.All_Parkinglot;

import static kr.co.waytech.peterparker.activity.PostClass.All_Parkinglot;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1μ΄?
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5μ΄?
    private static final LatLng ABC = null;
    public static Float ZoomLevel;
    public static int Connect_Flag = 0;
    public static int count_ranged;
    public static String[][] Selected_Parking;
    public static final LatLng target = null;
    public static List<String> listAddress, listContent_Price, listContent_Time;
    public static List<Integer> listResId;
    Marker selectedMarker;
    View marker_root_view;
    TextView tv_marker;
    MainActivity mainActivity;

    public static double mlatitude, mlongitude;
    public static String ParkingID;
    public static LinearLayout tab1_layout;
    public static LinearLayout tab2_layout;
    public static TextView tab1_text;
    private ArrayList<ListData> array_parking_lot;
    private ListView mListView;
    ImageButton search_btn;
    Button filter_btn;
    EditText search_edt;
    public static MyItem ParkingItem;
    public static double lat, lng;
    public static int Plus_array;
    public static double x1, y1, x2, y2;
    final PostClass Postc = new PostClass();
    public static SlidingUpPanelLayout slidingUpPanelLayout;
    String search_result;
    TabLayout tabLayout;
    ViewPager viewPager;
    private GoogleApiClient mGoogleApiClient;
    public static List<Address> AddressList;
    public static GoogleMap mMap;
    private Context mapFragment;
    private MapView mapView = null;
    FrameLayout frameLayout;
    private ClusterManager<MyItem> clusterManager;
    public static Geocoder geocoder;
    TabItem tab1, tab2, tab3;
    private RecyclerAdapter adapter;
    private ListAdapter listadapter;
    private AppCompatActivity mActivity;


    LocationRequest locationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

    public MapFragment() {

        // required
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapFragment = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        final InputMethodManager imm = (InputMethodManager) mapFragment.getSystemService(INPUT_METHOD_SERVICE);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.getMapAsync(this);
        search_edt = (EditText) view.findViewById(R.id.search_edt);
        search_btn = (ImageButton) view.findViewById(R.id.search_btn);
        filter_btn = (Button) view.findViewById(R.id.filter_btn);
        tab1_text = (TextView) view.findViewById(R.id.tab1_text);
        geocoder = new Geocoder(mapFragment);


        //String provider = mainActivity.location.getProvider();
        //mlatitude = mainActivity.location.getLatitude();
        //mlongitude = mainActivity.location.getLongitude();
        mlatitude = 37;
        mlongitude = 128;

        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = search_edt.getText().toString();
                AddressList = null;

                try {
                    AddressList = geocoder.getFromLocationName(str, 10); // ?»?΄?¬ κ°μ κ°μ
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (AddressList != null) {
                    if (AddressList.size() == 0) {
                        System.out.println("μ£Όμ ?€λ₯?");
                    } else {
                        System.out.println(AddressList.get(0).toString());
                        String[] splitStr = AddressList.get(0).toString().split(",");
                        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // μ£Όμ
                        System.out.println(address);
                        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // ??
                        String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // κ²½λ
                        System.out.println(latitude);
                        System.out.println(longitude);
                        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        Address addr = AddressList.get(0);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16));
                    }
                }
                imm.hideSoftInputFromWindow(search_edt.getWindowToken(), 0);
                //search_result = search_edt.getText().toString();

                ZoomLevel = mMap.getCameraPosition().zoom;
                lat = mMap.getCameraPosition().target.latitude;
                lng = mMap.getCameraPosition().target.longitude;
                x1 = (lat + 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                y1 = (lng - 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                x2 = (lat - 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                y2 = (lng + 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
//                Postc.AddMarker(Postc.getcountnumber(), x1, x2, y1, y2);
//                Postc.RemoveMarker(x1, x2, y1, y2);
            }
        });


        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tab1_layout = (LinearLayout) view.findViewById(R.id.tab1_layout);
        tab2_layout = (LinearLayout) view.findViewById(R.id.tab2_layout);
        frameLayout = (FrameLayout) view.findViewById(R.id.frame_view);
        tabLayout.addTab(tabLayout.newTab().setText("??¬ Pick"));
        tabLayout.addTab(tabLayout.newTab().setText("κ±°λ¦¬?"));
        tabLayout.addTab(tabLayout.newTab().setText("κ°?κ²©μ"));
        slidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_main);
        slidingUpPanelLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if (pos == 0) {
                    System.out.println("???λ§μ»€---------------------------------------------------------");
                    ZoomLevel = mMap.getCameraPosition().zoom;
                    lat = mMap.getCameraPosition().target.latitude;
                    lng = mMap.getCameraPosition().target.longitude;
                    // μ€λ λ²¨μ΄ 11, 13, 15?Ό? ?΅? 
                    System.out.println("?΅? ---------------------------------------------------------");
                    x1 = (lat + 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    y1 = (lng - 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    x2 = (lat - 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    y2 = (lng + 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    Postc.send_Location(x1, y1, x2, y2);
                    System.out.println(ZoomLevel + " ?μΉ? :  (" + x1 + ", " + y1 + ")" + " (" + x2 + ", " + y2 + ")");
                    tab1_layout.setVisibility(View.VISIBLE);
                    tab2_layout.setVisibility(View.GONE);
                } else if (pos == 1) {

                    System.out.println("κ±°λ¦¬?---------------------------------------------------------");
                    ZoomLevel = mMap.getCameraPosition().zoom;
                    lat = mlatitude;
                    lng = mlongitude;
                    // μ€λ λ²¨μ΄ 11, 13, 15?Ό? ?΅? 
                    System.out.println("?΅? ---------------------------------------------------------");
                    x1 = (lat + 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    y1 = (lng - 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    x2 = (lat - 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    y2 = (lng + 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    Postc.send_Location(x1, y1, x2, y2);
                    System.out.println(ZoomLevel + " ?μΉ? :  (" + x1 + ", " + y1 + ")" + " (" + x2 + ", " + y2 + ")");


                    tab1_layout.setVisibility(View.GONE);
                    tab2_layout.setVisibility(View.GONE);
                    tab2_layout.setVisibility(View.VISIBLE);
                    getData_distance();
                }
                else if (pos == 2){
                    System.out.println("κ°?κ²©μ---------------------------------------------------------");
                    ZoomLevel = mMap.getCameraPosition().zoom;
                    lat = mlatitude;
                    lng = mlongitude;
                    // μ€λ λ²¨μ΄ 11, 13, 15?Ό? ?΅? 
                    System.out.println("?΅? ---------------------------------------------------------");
                    x1 = (lat + 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    y1 = (lng - 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    x2 = (lat - 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    y2 = (lng + 1 * (0.012 * (2 ^ (int) (15.0 - ZoomLevel))));
                    Postc.send_Location(x1, y1, x2, y2);
                    System.out.println(ZoomLevel + " ?μΉ? :  (" + x1 + ", " + y1 + ")" + " (" + x2 + ", " + y2 + ")");
                    tab1_layout.setVisibility(View.GONE);
                    tab2_layout.setVisibility(View.GONE);
                    tab2_layout.setVisibility(View.VISIBLE);
                    getData_Price();
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

    /*  μ£Όμλ³?? μ½λ
        for(int b = 0; b < Postc.count; b++) {
            List<Address> Adlist = null;
            try {

                double d1 = Double.parseDouble(All_Parkinglot[b][2]);
                double d2 = Double.parseDouble(All_Parkinglot[b][3]);
                Adlist = geocoder.getFromLocation(
                        d1, // ??
                        d2, // κ²½λ
                        5); // ?»?΄?¬ κ°μ κ°μ
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Adlist != null) {
                if (Adlist.size()==0) {

                } else {
                    String[] splitStr = Adlist.get(0).toString().split(",");
                    String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // μ£Όμ
                    System.out.println("μ£Όμ : "+ address);
                }
            }
                    }


         */
    private void getData_pick(String Address, int Price, double distance) {
        List<String> listAddress = Arrays.asList(Address);
        List<String> listContent_Price = Arrays.asList("μ£Όμ°¨?κΈ? : " + "?κ°λΉ " +Price + "?");
        List<String> listContent_Time = Arrays.asList("?΄??κ°? :00:00 ~ 12:00");
        List<String> listContent_Distance = Arrays.asList((int)distance + "m");
        List<Integer> listResId = Arrays.asList(R.drawable.parkinglot1);
        for (int i = 0; i < listAddress.size(); i++) {
            // κ°? List? κ°λ€? data κ°μ²΄? set ?΄μ€λ?€.
            Data data = new Data();
            data.setAddress(listAddress.get(i));
            data.setContent_Price(listContent_Price.get(i));
            data.setContent_time(listContent_Time.get(i));
            data.setResId(listResId.get(i));
            data.setDistance(listContent_Distance.get(i));

            // κ°? κ°μ΄ ?€?΄κ°? dataλ₯? adapter? μΆκ???©??€.
            adapter.addItem(data);
        }

        // adapter? κ°μ΄ λ³?κ²½λ??€? κ²μ ?? €μ€λ?€.
        adapter.notifyDataSetChanged();
    }

    private void getData_distance() {
        mListView = (ListView) getView().findViewById(R.id.list_parkinglot);
        listadapter = new ListAdapter();
        mListView.setAdapter(listadapter);
        count_ranged = 0;
        Plus_array = 0;
        for(int i = 0; i < All_Parkinglot.length; i++) {
            if ((int) getDistance(mlatitude, mlongitude, latlngConv(Double.parseDouble(All_Parkinglot[i][2]), Double.parseDouble(All_Parkinglot[i][3]))) < 6000) {
                count_ranged++;
            }
        }
        Selected_Parking = new String[count_ranged][4];
        for(int i = 0; i < All_Parkinglot.length; i++) {
            if((int) getDistance(mlatitude, mlongitude, latlngConv(Double.parseDouble(All_Parkinglot[i][2]), Double.parseDouble(All_Parkinglot[i][3]))) < 6000) {
                count_ranged++;
                Selected_Parking[Plus_array][0] = getAddress(Double.parseDouble(All_Parkinglot[i][2]), Double.parseDouble(All_Parkinglot[i][3]));
                Selected_Parking[Plus_array][1] = All_Parkinglot[i][1];
                Selected_Parking[Plus_array][2] = All_Parkinglot[i][2];
                Selected_Parking[Plus_array][3] = All_Parkinglot[i][3];
                Plus_array++;
            }
        }
        for (int i = 0; i < Selected_Parking.length; i++) {
            for (int j = i + 1; j < Selected_Parking.length; j++) {
                if ((int) getDistance(mlatitude, mlongitude, latlngConv(Double.parseDouble(Selected_Parking[i][2]), Double.parseDouble(Selected_Parking[i][3])))
                        > (int) getDistance(mlatitude, mlongitude, latlngConv(Double.parseDouble(Selected_Parking[j][2]), Double.parseDouble(Selected_Parking[j][3])))) {
                    String tempdis = Selected_Parking[i][0];
                    String tempprice = Selected_Parking[i][1];
                    String templat = Selected_Parking[i][2];
                    String templng = Selected_Parking[i][3];
                    Selected_Parking[i][0] = Selected_Parking[j][0];
                    Selected_Parking[i][1] = Selected_Parking[j][1];
                    Selected_Parking[i][2] = Selected_Parking[j][2];
                    Selected_Parking[i][3] = Selected_Parking[j][3];
                    Selected_Parking[j][0] = tempdis;
                    Selected_Parking[j][1] = tempprice;
                    Selected_Parking[j][2] = templat;
                    Selected_Parking[j][3] = templng;
                }
            }
        }

        for(int i = 0; i < Selected_Parking.length; i++) {
            if((int) getDistance(mlatitude, mlongitude, latlngConv(Double.parseDouble(Selected_Parking[i][2]), Double.parseDouble(Selected_Parking[i][3]))) < 6000) {
                listadapter.addItem(Selected_Parking[i][0], Selected_Parking[i][1] + " ?", (int) getDistance(mlatitude, mlongitude, latlngConv(Double.parseDouble(Selected_Parking[i][2]), Double.parseDouble(Selected_Parking[i][3]))) + "m");

               System.out.println(Selected_Parking[i][0] + ", " + Selected_Parking[i][1] + " ?"+ ", " + Selected_Parking[i][2]+ ", " +Selected_Parking[i][3]);
            }
        }
    }

    private void getData_Price() {
        mListView = (ListView) getView().findViewById(R.id.list_parkinglot);
        listadapter = new ListAdapter();
        mListView.setAdapter(listadapter);
        count_ranged = 0;
        Plus_array = 0;
        for(int i = 0; i < All_Parkinglot.length; i++) {
            if ((int) getDistance(mlatitude, mlongitude, latlngConv(Double.parseDouble(All_Parkinglot[i][2]), Double.parseDouble(All_Parkinglot[i][3]))) < 6000) {
                count_ranged++;
            }
        }
        Selected_Parking = new String[count_ranged][4];
        for(int i = 0; i < All_Parkinglot.length; i++) {
            if((int) getDistance(mlatitude, mlongitude, latlngConv(Double.parseDouble(All_Parkinglot[i][2]), Double.parseDouble(All_Parkinglot[i][3]))) < 6000) {
                count_ranged++;
                Selected_Parking[Plus_array][0] = getAddress(Double.parseDouble(All_Parkinglot[i][2]), Double.parseDouble(All_Parkinglot[i][3]));
                Selected_Parking[Plus_array][1] = All_Parkinglot[i][1];
                Selected_Parking[Plus_array][2] = All_Parkinglot[i][2];
                Selected_Parking[Plus_array][3] = All_Parkinglot[i][3];
                Plus_array++;
            }
        }
        for (int i = 0; i < Selected_Parking.length; i++) {
            for (int j = i + 1; j < Selected_Parking.length; j++) {
                if (Integer.parseInt(Selected_Parking[i][1]) > Integer.parseInt(Selected_Parking[j][1])) {
                    String tempdis = Selected_Parking[i][0];
                    String tempprice = Selected_Parking[i][1];
                    String templat = Selected_Parking[i][2];
                    String templng = Selected_Parking[i][3];
                    Selected_Parking[i][0] = Selected_Parking[j][0];
                    Selected_Parking[i][1] = Selected_Parking[j][1];
                    Selected_Parking[i][2] = Selected_Parking[j][2];
                    Selected_Parking[i][3] = Selected_Parking[j][3];
                    Selected_Parking[j][0] = tempdis;
                    Selected_Parking[j][1] = tempprice;
                    Selected_Parking[j][2] = templat;
                    Selected_Parking[j][3] = templng;
                }
            }
        }

        for(int i = 0; i < Selected_Parking.length; i++) {
            if((int) getDistance(mlatitude, mlongitude, latlngConv(Double.parseDouble(Selected_Parking[i][2]), Double.parseDouble(Selected_Parking[i][3]))) < 6000) {
                listadapter.addItem(Selected_Parking[i][0], Selected_Parking[i][1] + " ?", (int) getDistance(mlatitude, mlongitude, latlngConv(Double.parseDouble(Selected_Parking[i][2]), Double.parseDouble(Selected_Parking[i][3]))) + "m");

                System.out.println(Selected_Parking[i][0] + ", " + Selected_Parking[i][1] + " ?"+ ", " + Selected_Parking[i][2]+ ", " +Selected_Parking[i][3]);
            }
        }
    }

    private void init() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerview_main_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mapFragment);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
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

        //?‘?°λΉν°κ°? μ²μ ??±?  ? ?€??? ?¨?

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    public String getAddress(double lat, double lng){
        List<Address> Adlist = null;
        try {
            Adlist = geocoder.getFromLocation(
                    lat, // ??
                    lng, // κ²½λ
                    1); // ?»?΄?¬ κ°μ κ°μ
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Adlist != null) {
            if (Adlist.size()==0) {
                return null;
            } else {
                String[] splitStr = Adlist.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // μ£Όμ
                return address;
            }
        }
        return null;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng ABC = new LatLng(37.340917, 126.7336682);
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setMinZoomPreference((float) 7.5);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ABC, 14.0f));
        clusterManager = new ClusterManager<>(mapFragment, mMap);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLng latLng = new LatLng(37.340917, 126.7336682);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            }
        });
        clusterManager.setRenderer(new OwnIconRendered(mapFragment.getApplicationContext(), mMap, clusterManager));
        clusterManager.setAnimation(true);
        mMap.setOnCameraIdleListener(clusterManager);
        for (int i = 0; i < Postc.count; i++) {
            MyItem ParkingItem = new MyItem(All_Parkinglot[i][2], All_Parkinglot[i][3], All_Parkinglot[i][0], All_Parkinglot[i][1]);
            clusterManager.addItem(ParkingItem);
        }
        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                LatLng latLng = new LatLng(item.getPosition().latitude, item.getPosition().longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                tab1_layout.setVisibility(View.VISIBLE);
                tab1_text.setVisibility(View.GONE);
                ParkingID = MyItem.returnID();
                final String ssid = item.getTitle();
                System.out.println(ssid);
                System.out.println("μ£Όμ : "+ getAddress(item.getPosition().latitude, item.getPosition().longitude));
                init();
                getData_pick(getAddress(item.getPosition().latitude, item.getPosition().longitude),
                        item.getPrice(), getDistance(mlatitude, mlongitude, item.getPosition()));
                return false;
            }
        });
         mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
            }
        });

        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                LatLngBounds.Builder builder_c = LatLngBounds.builder();
                for (ClusterItem item : cluster.getItems()) {
                    builder_c.include(item.getPosition());
                }
                LatLngBounds bounds_c = builder_c.build();
                float zoom = mMap.getCameraPosition().zoom - 0.5f;
                mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
                LatLng latLng = new LatLng(cluster.getPosition().latitude, cluster.getPosition().longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                return false;
            }
        });

        /*
        ClusterManager<ClusterItem> clusterManager = new ClusterManager<>(homeFragment, mMap);
        mMap.setOnCameraIdleListener(clusterManager);
        for(int i = 0; i < 10; i++){
            double lat = ABC_LAT + (i / 200d);
            double lng = ABC_LNG + (i / 200d);
            clusterManager.addItem(new ParkingItem(new LatLng(lat, lng), "Parking" + i));
        }

         */
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

            }
        });
    }

    public boolean setConnectBool() {
        if ((ZoomLevel >= 10.8000 && ZoomLevel <= 11.2000) || (ZoomLevel >= 12.8000 && ZoomLevel <= 13.2000) || (ZoomLevel >= 14.8000 && ZoomLevel <= 15.2000)) {
            Connect_Flag++;
            return true;
            // ?΅?  ??

        } else {
            Connect_Flag = 0;
            return false;
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {

        tab1_text.setVisibility(View.VISIBLE);
    }
    public double getDistance(double mlat, double mlng , LatLng LatLng2) {
        double distance = 0;
        Location locationA = new Location("User");
        locationA.setLatitude(mlat);
        locationA.setLongitude(mlng);
        Location locationB = new Location("Parkinglot");
        locationB.setLatitude(LatLng2.latitude);
        locationB.setLongitude(LatLng2.longitude);
        distance = locationA.distanceTo(locationB);

        return distance;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
    public LatLng latlngConv(double a, double b){
        LatLng newLatlng = new LatLng(a, b);
        return newLatlng;
    }

}
