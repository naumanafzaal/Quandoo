package com.app.quandoo.Service.Repository;

import com.app.quandoo.Service.Model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuandooAppService
{
    String API_BASE_URL = "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/";

    @GET("customer-list.json")
    Call<List<Customer>> getCustomersList();

    @GET("table-map.json")
    Call<List<Boolean>> getTablesInfo();
}
