package com.app.quandoo.View.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.quandoo.R;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.View.Adapter.CustomerListAdapter;
import com.app.quandoo.View.Callback.CustomerClickCallback;
import com.app.quandoo.ViewModel.CustomListViewModel;
import com.app.quandoo.databinding.CustomerListBinding;

import java.util.List;


public class CustomerListActivity extends AppCompatActivity implements CustomerClickCallback
{
    CustomListViewModel model;
    CustomerListBinding binding;
    CustomerListAdapter customerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.customer_list);
        model = ViewModelProviders.of(this).get(CustomListViewModel.class);

        setUpRecyclerView();
        observiewCustomerList();
    }

    private void observiewCustomerList()
    {
        model.getCustomerListObservable().observe(this, new Observer<List<Customer>>()
        {
            @Override
            public void onChanged(@Nullable List<Customer> customers)
            {
                if(customers != null)
                {
                    binding.setIsLoading(false);
                    customerListAdapter.setCustomerList(customers);
                }
            }
        });
    }

    private void setUpRecyclerView()
    {
        customerListAdapter = new CustomerListAdapter(this);
        binding.customerRecycler.setAdapter(customerListAdapter);
        binding.setIsLoading(true);
    }

    @Override
    public void onClick(Customer customer)
    {
    }

}
