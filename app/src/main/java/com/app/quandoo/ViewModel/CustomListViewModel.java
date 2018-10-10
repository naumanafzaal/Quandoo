package com.app.quandoo.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Repository.CustomerRepository;

import java.util.List;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class CustomListViewModel extends ViewModel
{
    private MutableLiveData<List<Customer>> customerListObservable;
    CustomerRepository repository;

    public CustomListViewModel()
    {

        repository = new CustomerRepository();
        customerListObservable = new MutableLiveData<>();
    }

    /**
     * Expose the LiveData Customer query so the UI can observe it.
     */
    public MutableLiveData<List<Customer>> getCustomerListObservable()
    {
        customerListObservable = repository.getCustomersList();
        return customerListObservable;
    }

    public void onSearchTextChanged(CharSequence searchText)
    {
        List<Customer> customerList;
        if (searchText.toString().isEmpty())
        {
            customerList = repository.getAllCustomers();
        } else
        {
            customerList = repository.getCustomersMatchingName(searchText.toString());
        }
        customerListObservable.postValue(customerList);
    }
}
