package kr.co.waytech.peterparker;

import android.graphics.drawable.Drawable;

public class Data {

    private String Address;
    private String content_Price;
    private String content_time;
    private String Distance;
    private Drawable resId;

    public String getAddress() {
        return Address;
    }


    public void setDistance(String Distance) {
        this.Distance = Distance;
    }

    public String getDistance() { return Distance; }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getContent_Price() {
        return content_Price;
    }

    public void setContent_time(String content_time) {
        this.content_time = content_time;
    }

    public String getContent_time() {
        return content_time;
    }

    public void setContent_Price(String content_Price) {
        this.content_Price = content_Price;
    }

    public Drawable getResId() {
        return resId;
    }

    public void setResId(Drawable resId) {
        this.resId = resId;
    }
}