package com.dsoccer1980.fuel.util;

import com.dsoccer1980.fuel.domain.FuelConsumption;

public class Utils {

    public static void setTotalPrice(FuelConsumption consumption) {
        consumption.setTotalPrice(Math.round(consumption.getPrice()*consumption.getVolume()*100)/100.0);
    }
}
