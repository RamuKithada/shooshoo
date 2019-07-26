package com.android.shooshoo.models;

public class CashModel implements PrizeBaseModel{

    String amount="";
    String currency="";
    String number="";

    @Override
    public double getPrizeAmount() {
        int value=0,number=1;
        try {
            value=Integer.valueOf(getAmount());
            number=getQuantity();
        }catch (Exception e){

        }
        double total=value*number;
        return total;
    }

    @Override
    public String getType() {
        return "Cash";
    }

    @Override
    public int getQuantity() {
        int number=1;
        try {
            number=Integer.valueOf(getNumber());
        }catch (Exception e){

        }
        return number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
