package com.app.quandoo.Service.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class Customer
{
    @SerializedName("customerFirstName") public String firstName;
    @SerializedName("customerLastName")public String lastName;
    public int id;
}