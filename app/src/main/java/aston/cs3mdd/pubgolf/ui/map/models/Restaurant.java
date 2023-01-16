package aston.cs3mdd.pubgolf.ui.map.models;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private String name;
    private String address;
    private String rating;
    private String totalRating;
    private Double lat;
    private Double lng;
    private String isOpen;

    public Restaurant(String name, String address, String rating, String totalRating, Double lat, Double lng, String isOpen) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.totalRating = totalRating;
        this.lat = lat;
        this.lng = lng;
        this.isOpen = isOpen;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getRating() {
        return rating;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getIsOpen() {
        return isOpen;
    }
}
