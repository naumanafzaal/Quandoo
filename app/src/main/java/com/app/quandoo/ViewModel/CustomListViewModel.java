package com.app.quandoo.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.app.quandoo.Service.Generic.DataWrapper;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Repository.CustomerRepository;
import com.app.quandoo.View.Callback.OnSearchInSoftKeyboardListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class CustomListViewModel extends ViewModel implements OnSearchInSoftKeyboardListener
{
    private MutableLiveData<DataWrapper<List<Customer>>> customerListObservable;
    CustomerRepository repository;
    private ExecutorService executorService;

    public CustomListViewModel()
    {

        repository = new CustomerRepository();
        customerListObservable = new MutableLiveData<>();
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Expose the LiveData Customer query so the UI can observe it.
     */
    public MutableLiveData<DataWrapper<List<Customer>>> getCustomerListObservable()
    {
        customerListObservable = repository.getCustomersList();
        return customerListObservable;
    }

    public void onSearchPressed(String searchText)
    {
        executorService.execute(new Runnable()
        {
            @Override
            public void run()
            {
                DataWrapper dataWrapper = repository.searchCustomersMatchingName(searchText);
                customerListObservable.postValue(dataWrapper);
            }
        });

    }
}
