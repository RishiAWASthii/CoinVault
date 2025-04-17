package com.coinmanagnment;

import java.util.Date;

public class Coin {
   private int Coin_ID;
    private String Country;
    private int Denomination;
    private int Yom;
    private int Current_Value;
    private Date Acquried_date;


    public Coin() {
    }

    public Coin(String country, int denomination, int current_Value, int yom, Date acquried_date) {
        Country = country;
        Denomination = denomination;
        Current_Value = current_Value;
        Yom = yom;
        Acquried_date = acquried_date;
    }

    public int getCoin_ID() {
        return Coin_ID;
    }

    public void setCoin_ID(int coin_ID) {
        Coin_ID = coin_ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getDenomination() {
        return Denomination;
    }

    public void setDenomination(int denomination) {
        Denomination = denomination;
    }

    public int getYom() {
        return Yom;
    }

    public void setYom(int yom) {
        Yom = yom;
    }

    public int getCurrent_Value() {
        return Current_Value;
    }

    public void setCurrent_Value(int current_Value) {
        Current_Value = current_Value;
    }

    public java.sql.Date getAcquried_date() {
        return (java.sql.Date) Acquried_date;
    }

    public void setAcquried_date(Date acquried_date) {
        Acquried_date = acquried_date;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "Coin_ID=" + Coin_ID +
                ", Country='" + Country + '\'' +
                ", Denomination=" + Denomination +
                ", Yom=" + Yom +
                ", Current_Value=" + Current_Value +
                ", Acquried_date=" + Acquried_date +
                '}';
    }
}
