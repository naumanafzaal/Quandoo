package com.app.quandoo.Service.Repository;

import android.arch.lifecycle.MutableLiveData;

import com.app.quandoo.Database.AppDatabase;
import com.app.quandoo.Database.DAO.TableInfoDao;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Model.TableInfo;
import com.app.quandoo.Service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableInfoRepository
{
    private QuandooAppService quandooAppService;

    public TableInfoRepository()
    {
        this.quandooAppService = RetrofitClient.getInstance().create(QuandooAppService.class);
    }

    public MutableLiveData<List<TableInfo>> getTableDetails()
    {
        final MutableLiveData<List<TableInfo>> data = new MutableLiveData<>();

        TableInfoDao tableInfoDao = AppDatabase.getInstance().tableInfoDao();
        quandooAppService.getTablesInfo().enqueue(new Callback<List<Boolean>>()
        {
            @Override
            public void onResponse(Call<List<Boolean>> call, Response<List<Boolean>> response)
            {
                tableInfoDao.deleteAll();
                List<Boolean> resp = response.body();
                List<TableInfo> infos = new ArrayList<>();
                for (int i = 0; i < resp.size(); i++)
                {
                    infos.add(new TableInfo(i + 1, resp.get(i)));
                }
                tableInfoDao.insertOrReplaceTables(infos);
                data.setValue(getTablesInformation());
            }

            @Override
            public void onFailure(Call<List<Boolean>> call, Throwable t)
            {
                // Incase of network failure load local data.
                data.setValue(getTablesInformation());
            }
        });

        return data;
    }

    public void bookTable(TableInfo tableInfo, Customer customer)
    {
        tableInfo.bookTable(customer);
        AppDatabase.getInstance().tableInfoDao().insertOrReplaceTable(tableInfo);
    }

    // This will locally mark tables free if any.
    public List<TableInfo> getTablesInformation()
    {
        TableInfoDao tableInfoDao = AppDatabase.getInstance().tableInfoDao();
        List<TableInfo> tableInfoList = tableInfoDao.loadAllTableInfos();
        for (TableInfo tableInfo : tableInfoList)
        {
            if (tableInfo.canBookTable())
            {
                tableInfo.clearTable();
            }
        }
        AppDatabase.getInstance().tableInfoDao().insertOrReplaceTables(tableInfoList);
        return tableInfoList;
    }
}
