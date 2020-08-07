package kr.co.waytech.peterparker;

public class ListData {

    private String Address;
    private String content_Price;
    private String content_time;
    private String Distance;
    private int resId;


    public void setAddress(String Address) {
        this.Address = Address;
    }
    public String getAddress() { return Address;
    }


    public void setDistance(String Distance) {
        this.Distance = Distance;
    }
    public String getDistance() { return Distance; }


    public void setContent_Price(String content_Price) {
        this.content_Price = content_Price;
    }
    public String getContent_Price() {
        return content_Price;
    }
}
