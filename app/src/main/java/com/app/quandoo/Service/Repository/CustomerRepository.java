package com.app.quandoo.Service.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepository
{
    private QuandooAppService quandooAppService;

    public CustomerRepository()
    {
        this.quandooAppService = RetrofitClient.getInstance().create(QuandooAppService.class);
    }

    public LiveData<List<Customer>> getCustomersList()
    {
        final MutableLiveData<List<Customer>> data = new MutableLiveData<>();

        quandooAppService.getCustomersList().enqueue(new Callback<List<Customer>>()
        {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response)
            {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t)
            {
                data.setValue(null);
            }
        });

        return data;
    }
}
