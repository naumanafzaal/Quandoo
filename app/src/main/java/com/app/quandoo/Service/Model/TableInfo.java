package com.app.quandoo.Service.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
@Entity
public class TableInfo
{
    @PrimaryKey
    @NonNull
    public int id;

    public boolean isBooked;
    public long lastBookedTime;

    // Customer information is being returned from server. Once we have it we know for which customer this table is booked.
    public int customerId = -1;

    // Populate it when fetching tables information.
    @Ignore
    public Customer customer;

    final static  long FifteenMinsInMilli = TimeUnit.MINUTES.toMillis(15);

    public TableInfo(int id, boolean isBooked)
    {
        this.isBooked = isBooked;
        this.id = id;
        if(isBooked)
        {
            // If table is booked on server set its last book time to now as we are not getting it from server.
            lastBookedTime = System.currentTimeMillis();
        }
    }

    public void bookTable(Customer customer)
    {
        isBooked = true;
        lastBookedTime = System.currentTimeMillis();
        this.customerId = customer.id;
    }

    public void clearTable()
    {
        isBooked = false;
        lastBookedTime = 0;
        customerId = -1;
    }

    public boolean canBookTable()
    {
        if(lastBookedTime > 0)
        {
            return System.currentTimeMillis() - lastBookedTime > FifteenMinsInMilli;
        }
        return true;
    }
}
