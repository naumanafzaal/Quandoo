package com.app.quandoo.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Model.TableInfo;
import com.app.quandoo.Service.Repository.TableInfoRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class TableInfoViewModel extends ViewModel
{
    private MutableLiveData<List<TableInfo>> tableInfoObservable;

    TableInfoRepository repository;

    public TableInfoViewModel()
    {
        repository = new TableInfoRepository();
        fetchData();
        refreshData();
    }
    /**
     * Expose the LiveData Table Info query so the UI can observe it.
     */
    public MutableLiveData<List<TableInfo>> getTablesInfoObservable() {
        return tableInfoObservable;
    }

    public boolean bookTable(TableInfo tableInfo, Customer customer)
    {
        boolean canBookTable = tableInfo.canBookTable();
        if (canBookTable)
        {
            repository.bookTable(tableInfoObservable, tableInfo, customer);
        }
        return canBookTable;
    }

    // refresh data after 1 second to see if any table is freed now.
    private void refreshData()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                repository.refreshTablesInformation(tableInfoObservable);
                refreshData();
            }
        }, TimeUnit.MINUTES.toMillis(1));
    }

    private void fetchData()
    {
        tableInfoObservable = repository.getTableDetails();
    }
}
