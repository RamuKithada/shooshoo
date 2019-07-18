package com.android.shooshoo.models;

public class PrizeModel implements PrizeBaseModel {

    String worth;
    String number;
    String name;

    @Override
    public double getPrizeAmount() {
        int value=0,number=1;
        try {
             value=Integer.valueOf(getWorth());
             number=Integer.valueOf(getNumber());
        }catch (Exception e){

        }


        double total=value*number;
        return total;
    }

    @Override
    public String getType() {
        return "Product";
    }

    public String getWorth() {
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
