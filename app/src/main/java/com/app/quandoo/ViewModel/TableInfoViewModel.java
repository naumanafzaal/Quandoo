package com.app.quandoo.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.app.quandoo.Database.AppDatabase;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Model.TableInfo;
import com.app.quandoo.Service.Repository.TableInfoRepository;

import java.util.List;

/**
 * Created by Nauman Afzaal on 09/10/2018.
 */
public class TableInfoViewModel extends ViewModel
{
    private MutableLiveData<List<TableInfo>> tableInfoObservable;

    public TableInfoViewModel()
    {
        fetchData();
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
            tableInfo.bookTable(customer);
            AppDatabase.getInstance().tableInfoDao().insertOrReplaceTable(tableInfo);
            // Update UI
            tableInfoObservable.postValue(tableInfoObservable.getValue());
        }
        return canBookTable;
    }

    private void fetchData()
    {
        TableInfoRepository repository = new TableInfoRepository();
        tableInfoObservable = repository.getTableDetails();
    }

}
