package com.app.quandoo.Service.Repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.text.TextUtils;

import com.app.quandoo.Database.AppDatabase;
import com.app.quandoo.Database.DAO.CustomerDao;
import com.app.quandoo.Service.Generic.DataWrapper;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepository
{
    private QuandooAppService quandooAppService;
    CustomerDao customerDao;
    ExecutorService executorService;

    public CustomerRepository()
    {
        this.quandooAppService = RetrofitClient.getInstance().create(QuandooAppService.class);
        customerDao = AppDatabase.getInstance().customerDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public MutableLiveData<DataWrapper<List<Customer>>> getCustomersList()
    {
        final MutableLiveData<DataWrapper<List<Customer>>> data = new MutableLiveData<>();
        final DataWrapper<List<Customer>> dataWrapper = new DataWrapper<>();
        quandooAppService.getCustomersList().enqueue(new Callback<List<Customer>>()
        {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response)
            {
                // Replace old customers data with new one.
                List<Customer> body = response.body();
                customerDao.deleteAll();
                customerDao.insertOrReplaceUsers(body);
                dataWrapper.setData(body);
                data.postValue(dataWrapper);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t)
            {
                // Incase of exception load local customers.
                List<Customer> customers = customerDao.loadAllCustomers();
                dataWrapper.setData(customers);
                if(t != null)
                {
                    dataWrapper.setApiException(new Exception("Unable to load data. Please try again later."));
                }
                data.postValue(dataWrapper);
            }
        });

        return data;
    }

    // Execute search from database in background thread.
    public void searchCustomersMatchingName(MutableLiveData<DataWrapper<List<Customer>>> liveData, String searchText)
    {
        executorService.execute(new Runnable()
        {
            @Override
            public void run()
            {
                DataWrapper wrapper = new DataWrapper();
                if(searchText.isEmpty())
                {
                    wrapper.setData(customerDao.loadAllCustomers());
                }
                else
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

                    wrapper.setData(customerDao.searchUserWithName(sqLiteQuery));
                }
                liveData.postValue(wrapper);
            }
        });

    }
}
