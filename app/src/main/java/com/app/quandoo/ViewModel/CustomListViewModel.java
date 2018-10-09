package com.app.quandoo.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Repository.CustomerRepository;

import java.util.List;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class CustomListViewModel extends ViewModel
{
    private final LiveData<List<Customer>> customerListObservable;

    public CustomListViewModel() {
        CustomerRepository repository = new CustomerRepository();
        customerListObservable = repository.getCustomersList();
    }

    /**
     * Expose the LiveData Customer query so the UI can observe it.
     */
    public LiveData<List<Customer>> getCustomerListObservable() {
        return customerListObservable;
    }
}
