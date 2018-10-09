package com.app.quandoo.Service.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
@Entity
public class Customer implements Serializable
{
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    public int id;

    @SerializedName("customerFirstName")
    public String firstName;

    @SerializedName("customerLastName")
    public String lastName;
}