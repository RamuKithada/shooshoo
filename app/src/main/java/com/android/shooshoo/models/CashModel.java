package com.android.shooshoo.models;

public class CashModel implements PrizeBaseModel{

    String amount="";
    String currency="";
    String number;

    @Override
    public double getPrizeAmount() {
        int value=0,number=1;
        try {
            value=Integer.valueOf(getAmount());
            number=Integer.valueOf(getNumber());
        }catch (Exception e){

        }
        double total=value*number;
        return total;
    }

    @Override
    public String getType() {
        return "Cash";
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
