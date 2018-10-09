package com.app.quandoo.View.Adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.quandoo.R;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.View.Callback.CustomerClickCallback;
import com.app.quandoo.databinding.CustomerItemBinding;

import java.util.List;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class CustomerListAdapter extends RecyclerView.Adapter<CustomerViewHolder>
{
    List<Customer> customerList;
    @Nullable
    private final CustomerClickCallback customerClickCallback;

    public CustomerListAdapter(@Nullable CustomerClickCallback customerClickCallback)
    {
        this.customerClickCallback = customerClickCallback;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        CustomerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.customer_item,
                viewGroup, false);
        binding.setCallback(customerClickCallback);

        return new CustomerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder customerViewHolder, int i)
    {
        customerViewHolder.binding.setCustomer(customerList.get(i));
        customerViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount()
    {
        return customerList != null ? customerList.size() : 0;
    }

    public void setCustomerList(List<Customer> customerList)
    {
        this.customerList = customerList;
        notifyDataSetChanged();
    }
}
