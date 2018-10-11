package com.app.quandoo.Service.Repository;

import android.arch.lifecycle.MutableLiveData;

import com.app.quandoo.Database.AppDatabase;
import com.app.quandoo.Database.DAO.CustomerDao;
import com.app.quandoo.Database.DAO.TableInfoDao;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Model.TableInfo;
import com.app.quandoo.Service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.quandoo.QuandooApp.context;

public class TableInfoRepository
{
    private final ExecutorService executorService;
    private final CustomerDao customerDao;
    private QuandooAppService quandooAppService;
    private TableInfoDao tableInfoDao;

    public TableInfoRepository()
    {
        this.quandooAppService = RetrofitClient.getInstance().create(QuandooAppService.class);
        tableInfoDao = AppDatabase.getInstance(context).tableInfoDao();
        customerDao = AppDatabase.getInstance(context).customerDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public MutableLiveData<List<TableInfo>> getTableDetails()
    {
        final MutableLiveData<List<TableInfo>> data = new MutableLiveData<>();
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
                refreshTablesInformation(data);
            }

            @Override
            public void onFailure(Call<List<Boolean>> call, Throwable t)
            {
                // Incase of network failure load local data.
                refreshTablesInformation(data);
            }
        });

        return data;
    }

    public void bookTable(MutableLiveData<List<TableInfo>> data, TableInfo tableInfo, Customer customer)
    {
        executorService.execute(new Runnable()
        {
            @Override
            public void run()
            {
                tableInfo.bookTable(customer);
                tableInfoDao.insertOrReplaceTable(tableInfo);
                refreshTablesInformation(data);
            }
        });
    }

    // This will locally mark tables free if any.
    public void refreshTablesInformation(MutableLiveData<List<TableInfo>> data)
    {
        executorService.execute(new Runnable()
        {
            @Override
            public void run()
            {
                List<TableInfo> tableInfoList = tableInfoDao.loadAllTableInfos();
                for (TableInfo tableInfo : tableInfoList)
                {
                    if (tableInfo.canBookTable())
                    {
                        tableInfo.clearTable();
                    }
                    else
                    {
                        tableInfo.customer = customerDao.customerWithId(tableInfo.customerId);
                    }
                }
                tableInfoDao.insertOrReplaceTables(tableInfoList);
                data.postValue(tableInfoList);
            }
        });
    }
}