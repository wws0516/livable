package com.chuangshu.livable.service.search;

/**
 * @Author: wws
 * @Date: 2019-09-25 13:49
 */
public class RentSearch {

    private String city;
    private String priceBlock;
    private String acreageBlock;
    private String rentWay;
    private String feature;
    private String keywords;
    private String orderBy = "rent";
    private String orderDirection = "desc";
    private int start = 0;
    private int size = 5;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAcreageBlock() {
        return acreageBlock;
    }

    public void setAcreageBlock(String acreageBlock) {
        this.acreageBlock = acreageBlock;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getStart() {
        return start > 0 ? start : 0;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        if (this.size < 1) {
            return 5;
        } else if (this.size > 100) {
            return 100;
        } else {
            return this.size;
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPriceBlock() {
        return priceBlock;
    }

    public void setPriceBlock(String priceBlock) {
        this.priceBlock = priceBlock;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRentWay() {
        return rentWay;
    }

    public void setRentWay(String rentWay) {
        this.rentWay = rentWay;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    @Override
    public String toString() {
        return "RentSearch{" +
                "city='" + city + '\'' +
                ", priceBlock='" + priceBlock + '\'' +
                ", acreageBlock='" + acreageBlock + '\'' +
                ", rentWay='" + rentWay + '\'' +
                ", feature='" + feature + '\'' +
                ", keywords='" + keywords + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", start=" + start +
                ", size=" + size +
                '}';
    }
}
