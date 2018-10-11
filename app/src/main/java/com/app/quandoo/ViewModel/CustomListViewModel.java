package com.app.quandoo.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.app.quandoo.Service.Generic.DataWrapper;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Repository.CustomerRepository;
import com.app.quandoo.View.Callback.OnSearchInSoftKeyboardListener;

import java.util.List;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class CustomListViewModel extends ViewModel implements OnSearchInSoftKeyboardListener
{
    private MutableLiveData<DataWrapper<List<Customer>>> customerListObservable;
    CustomerRepository repository;

    public CustomListViewModel()
    {

        repository = new CustomerRepository();
        customerListObservable = new MutableLiveData<>();
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
        List<Customer> customerList;
        if (searchText.toString().isEmpty())
        {
            customerList = repository.getAllCustomers();
        } else
        {
            customerList = repository.getCustomersMatchingName(searchText);
        }
        DataWrapper data = new DataWrapper();
        data.setData(customerList);
        customerListObservable.postValue(data);
    }
}
