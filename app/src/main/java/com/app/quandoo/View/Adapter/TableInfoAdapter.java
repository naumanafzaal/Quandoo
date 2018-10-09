package com.app.quandoo.View.Adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.quandoo.R;
import com.app.quandoo.Service.Model.TableInfo;
import com.app.quandoo.View.Callback.TableClickCallback;
import com.app.quandoo.databinding.TableItemBinding;

import java.util.List;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class TableInfoAdapter extends RecyclerView.Adapter<TableViewHolder>
{
    List<TableInfo> tableInfoList;
    @Nullable
    private final TableClickCallback tableClickCallback;

    public TableInfoAdapter(@Nullable TableClickCallback tableClickCallback)
    {
        this.tableClickCallback = tableClickCallback;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        TableItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.table_item,
                viewGroup, false);
        binding.setTableCB(tableClickCallback);
        return new TableViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder customerViewHolder, int i)
    {
        customerViewHolder.binding.setTableInfo(tableInfoList.get(i));
        customerViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount()
    {
        return tableInfoList != null ? tableInfoList.size() : 0;
    }

    public void setTableInfoList(List<TableInfo> tableInfoList)
    {
        this.tableInfoList = tableInfoList;
        notifyDataSetChanged();
    }
}
