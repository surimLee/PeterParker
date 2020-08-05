package kr.co.waytech.peterparker;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;
    private final int mPrice;

    public MyItem(String slat, String slng, String title, String sprice) {
        double pLat = Double.parseDouble(slat);
        double pLng = Double.parseDouble(slng);
        int price = Integer.parseInt(sprice);
        mPosition = new LatLng(pLat, pLng);
        mTitle = title;
        mPrice = price;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Nullable
    @Override
    public String getTitle() {
        return mTitle;
    }

    public int getPrice(){
        return mPrice;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return null;
    }
}