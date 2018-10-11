package com.app.quandoo.View.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.widget.Toast;

import com.app.quandoo.R;
import com.app.quandoo.Service.Generic.DataWrapper;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.View.Adapter.CustomerListAdapter;
import com.app.quandoo.View.Callback.CustomerClickCallback;
import com.app.quandoo.ViewModel.CustomListViewModel;
import com.app.quandoo.databinding.CustomerListBinding;

import java.util.ArrayList;
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
        binding.setViewModel(model);
        binding.setHandlers(model);

        setUpRecyclerView();
        observiewCustomerList();
    }

    private void observiewCustomerList()
    {
        model.getCustomerListObservable().observe(this, new Observer<DataWrapper<List<Customer>>>()
        {
            @Override
            public void onChanged(@Nullable DataWrapper<List<Customer>> dataWrapper)
            {
                binding.setIsLoading(false);
                List<Customer> customers = dataWrapper.getData();
                Exception exception = dataWrapper.getApiException();
                if (customers != null && !customers.isEmpty())
                {
                    customerListAdapter.setCustomerList(customers);
                } else if (exception != null)
                {
                    customerListAdapter.setCustomerList(new ArrayList<>());
                    Toast.makeText(CustomerListActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                } else
                {
                    customerListAdapter.setCustomerList(new ArrayList<>());
                }
            }
        });
    }


    private void setUpRecyclerView()
    {
        customerListAdapter = new CustomerListAdapter(this);
        binding.customerRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.customerRecycler.setAdapter(customerListAdapter);
        binding.setIsLoading(true);
    }

    @Override
    public void onClick(Customer customer)
    {
        TableInfoActivity.show(this, customer);
    }
}
