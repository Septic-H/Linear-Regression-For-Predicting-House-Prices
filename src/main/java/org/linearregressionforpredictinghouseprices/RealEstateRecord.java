package org.linearregressionforpredictinghouseprices;

public class RealEstateRecord {
    private String transactionDate;
    private double houseAge;
    private double distanceToMRT;
    private int numConvenienceStores;
    private double latitude;
    private double longitude;
    private double price;

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getHouseAge() {
        return houseAge;
    }

    public void setHouseAge(double houseAge) {
        this.houseAge = houseAge;
    }

    public double getDistanceToMRT() {
        return distanceToMRT;
    }

    public void setDistanceToMRT(double distanceToMRT) {
        this.distanceToMRT = distanceToMRT;
    }

    public int getNumConvenienceStores() {
        return numConvenienceStores;
    }

    public void setNumConvenienceStores(int numConvenienceStores) {
        this.numConvenienceStores = numConvenienceStores;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
