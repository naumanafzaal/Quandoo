package com.app.quandoo.View.Adapter;

import android.support.v7.widget.RecyclerView;

import com.app.quandoo.databinding.TableItemBinding;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class TableViewHolder extends RecyclerView.ViewHolder
{
    TableItemBinding binding;

    public TableViewHolder(TableItemBinding binding)
    {
        super(binding.getRoot());
        this.binding = binding;
    }
}
