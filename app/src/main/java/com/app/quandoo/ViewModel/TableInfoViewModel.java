package com.app.quandoo.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Model.TableInfo;
import com.app.quandoo.Service.Repository.TableInfoRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class TableInfoViewModel extends ViewModel
{
    private final ExecutorService executorService;
    private MutableLiveData<List<TableInfo>> tableInfoObservable;

    TableInfoRepository repository;


    public TableInfoViewModel()
    {
        repository = new TableInfoRepository();
        executorService = Executors.newSingleThreadExecutor();
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

                executorService.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        tableInfoObservable.postValue(repository.refreshTablesInformation());
                    }
                });
                refreshData();
            }
        }, TimeUnit.MINUTES.toMillis(1));
    }

    private void fetchData()
    {
        tableInfoObservable = repository.getTableDetails();
    }
}
