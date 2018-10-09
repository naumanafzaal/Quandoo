package com.app.quandoo.Service.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.app.quandoo.Database.AppDatabase;
import com.app.quandoo.Database.DAO.CustomerDao;
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
        CustomerDao customerDao = AppDatabase.getInstance().customerDao();
        quandooAppService.getCustomersList().enqueue(new Callback<List<Customer>>()
        {

            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response)
            {
                // Replace old cutomers data with new one.
                List<Customer> body = response.body();
                customerDao.deleteAll();
                customerDao.insertOrReplaceUsers(body);
                data.setValue(body);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t)
            {
                // Incase of exception load local customers.
                List<Customer> customers = customerDao.loadAllCustomers();
                data.setValue(customers);
            }
        });

        return data;
    }
}
