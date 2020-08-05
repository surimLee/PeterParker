package kr.co.waytech.peterparker.model;

public class RecentBooking {

    String parkinglotName;
    String status;
    String parkinglotAddress;
    String parkinglotPrice;
    String parkinglotSchedule;
    Integer imageUrl;




    public RecentBooking(String parkinglotName, String status, String parkinglotAddress, String parkinglotPrice, String parkinglotSchedule, Integer imageUrl) {
        this.parkinglotName = parkinglotName;
        this.status = status;
        this.parkinglotAddress = parkinglotAddress;
        this.parkinglotPrice = parkinglotPrice;
        this.parkinglotSchedule = parkinglotSchedule;
        this.imageUrl = imageUrl;
    }

    public String getParkinglotName() {
        return parkinglotName;
    }

    public void setParkinglotName(String parkinglotName) {
        this.parkinglotName = parkinglotName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParkinglotAddress() {
        return parkinglotAddress;
    }

    public void setParkinglotAddress(String parkinglotAddress) {
        this.parkinglotAddress = parkinglotAddress;
    }

    public String getParkinglotPrice() {
        return parkinglotPrice;
    }

    public void setParkinglotPrice(String parkinglotPrice) {
        this.parkinglotPrice = parkinglotPrice;
    }

    public String getParkinglotSchedule() {
        return parkinglotSchedule;
    }

    public void setParkinglotSchedule(String parkinglotSchedule) {
        this.parkinglotSchedule = parkinglotSchedule;
    }


    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
