package com.app.quandoo.View.UI;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import com.app.quandoo.R;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Model.TableInfo;
import com.app.quandoo.Utils.SpacesItemDecoration;
import com.app.quandoo.View.Adapter.TableInfoAdapter;
import com.app.quandoo.View.Callback.TableClickCallback;
import com.app.quandoo.ViewModel.TableInfoViewModel;
import com.app.quandoo.databinding.TableInfoBinding;

import java.util.List;

public class TableInfoActivity extends AppCompatActivity implements TableClickCallback
{
    private TableInfoBinding binding;
    private TableInfoViewModel model;
    TableInfoAdapter tableInfoAdapter;

    public static void show(Activity activity, Customer customer)
    {
        Intent intent = new Intent(activity, TableInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("customer", customer);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle(R.string.tableInfo);

        binding = DataBindingUtil.setContentView(this, R.layout.table_info);
        model = ViewModelProviders.of(this).get(TableInfoViewModel.class);
        setUpRecyclerView();
        observeTableInfo();
    }

    private void setUpRecyclerView()
    {
        tableInfoAdapter = new TableInfoAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        binding.tablesRecycler.setLayoutManager(manager);
        int gridSpacing = getResources().getDimensionPixelSize(R.dimen.gridSpacing);
        binding.tablesRecycler.addItemDecoration(new SpacesItemDecoration(gridSpacing));
        binding.tablesRecycler.setAdapter(tableInfoAdapter);
        binding.setIsLoading(true);
    }

    private void observeTableInfo()
    {
        model.getTablesInfoObservable().observe(this, new Observer<List<TableInfo>>()
        {
            @Override
            public void onChanged(@Nullable List<TableInfo> tableInfos)
            {
                binding.setIsLoading(false);
                if (tableInfos != null && !tableInfos.isEmpty())
                {
                    tableInfoAdapter.setTableInfoList(tableInfos);
                } else
                {
                    Toast.makeText(TableInfoActivity.this, R.string.unableToLoadData, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onTableClicked(TableInfo tableInfo)
    {
        if(!model.bookTable(tableInfo, getCustomer()))
        {
            Toast.makeText(TableInfoActivity.this, R.string.tableAlreadyBooked, Toast.LENGTH_SHORT).show();
        }
    }

    private Customer getCustomer()
    {
        return (Customer) getIntent().getExtras().getSerializable("customer");
    }
}
