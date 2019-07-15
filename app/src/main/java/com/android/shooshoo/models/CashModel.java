package com.android.shooshoo.models;

public class CashModel implements PrizeBaseModel{

    String amount="";
    String currency="";

    @Override
    public String getPrizeAmount() {
        return getAmount();
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
}
