package com.app.quandoo.Service.Model;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class TableInfo
{
    public boolean isBooked;
    public long lastBookedTime;

    public TableInfo(boolean isBooked)
    {
        this.isBooked = isBooked;
    }
}
