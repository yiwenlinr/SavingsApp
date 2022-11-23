package com.example.savingsapp.model;

public class Saving {
    String monto;
    public Saving(){}

    public Saving(String monto) {
        this.monto = monto;
    }
    public String getMonto(){
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

}
