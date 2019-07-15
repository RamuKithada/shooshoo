package com.android.shooshoo.models;

public class PrizeModel implements PrizeBaseModel {

    String worth;
    String number;
    String name;

    @Override
    public String getPrizeAmount() {
        return getWorth();
    }

    @Override
    public String getType() {
        return "Product";
    }

    private String getWorth() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth = worth;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
