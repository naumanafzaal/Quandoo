package com.app.quandoo.Service.Repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.text.TextUtils;

import com.app.quandoo.Database.AppDatabase;
import com.app.quandoo.Database.DAO.CustomerDao;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepository
{
    private QuandooAppService quandooAppService;
    CustomerDao customerDao;

    public CustomerRepository()
    {
        this.quandooAppService = RetrofitClient.getInstance().create(QuandooAppService.class);
        customerDao = AppDatabase.getInstance().customerDao();
    }

    public MutableLiveData<List<Customer>> getCustomersList()
    {
        final MutableLiveData<List<Customer>> data = new MutableLiveData<>();
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

    public List<Customer> getCustomersMatchingName(String searchText)
    {
        String[] splitString = searchText.toString().split(" ");
        String query = "select * from customer where ";
        List<String> queries = new ArrayList<>();
        for (int i = 0; i < splitString.length; i++)
        {
            queries.add(String.format("(firstName LIKE '%%%1$s%%' or lastName LIKE '%%%2$s%%')", splitString[i], splitString[i]));
        }
        query = query + TextUtils.join(" AND ", queries);
        SimpleSQLiteQuery sqLiteQuery = new SimpleSQLiteQuery(query);
        return customerDao.searchUserWithName(sqLiteQuery);
    }

    public List<Customer> getAllCustomers()
    {
        return customerDao.loadAllCustomers();
    }
}
