package kr.co.waytech.peterparker.model;

public class ParkingList {

    String parkinglotName;
    String parkinglotAddress;
    String parkinglotPrice;
    Integer imageUrl;

    public ParkingList(String parkinglotName, String parkinglotAddress, String parkinglotPrice, Integer imageUrl) {
        this.parkinglotName = parkinglotName;
        this.parkinglotAddress = parkinglotAddress;
        this.parkinglotPrice = parkinglotPrice;
        this.imageUrl = imageUrl;
    }

    public String getPLName() {
        return parkinglotName;
    }

    public void setPLName(String parkinglotName) {
        this.parkinglotName = parkinglotName;
    }

    public String getPLAddress() {
        return parkinglotAddress;
    }

    public void setPLAddress(String parkinglotAddress) { this.parkinglotAddress = parkinglotAddress; }

    public String getPLPrice() {
        return parkinglotPrice;
    }

    public void setPLPrice(String parkinglotPrice) { this.parkinglotPrice = parkinglotPrice; }

    public Integer getPLImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
