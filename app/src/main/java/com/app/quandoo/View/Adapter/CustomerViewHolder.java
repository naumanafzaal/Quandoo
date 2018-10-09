package com.app.quandoo.View.Adapter;

import android.support.v7.widget.RecyclerView;

import com.app.quandoo.databinding.CustomerItemBinding;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class CustomerViewHolder extends RecyclerView.ViewHolder
{
    CustomerItemBinding binding;

    public CustomerViewHolder(CustomerItemBinding binding)
    {
        super(binding.getRoot());
        this.binding = binding;
    }
}
