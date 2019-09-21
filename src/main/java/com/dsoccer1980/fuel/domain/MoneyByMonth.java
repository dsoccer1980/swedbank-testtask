package com.dsoccer1980.fuel.domain;

public class MoneyByMonth {

    private int month;
    private double sum;

    public MoneyByMonth(int month, double sum) {
        this.month = month;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "MoneyByMonth{" +
                "month=" + month +
                ", sum=" + sum +
                '}';
    }
}
