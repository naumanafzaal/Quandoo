package com.app.quandoo.Service.Repository;

import android.arch.lifecycle.MutableLiveData;

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

        quandooAppService.getTablesInfo().enqueue(new Callback<List<Boolean>>()
        {
            @Override
            public void onResponse(Call<List<Boolean>> call, Response<List<Boolean>> response)
            {
                List<Boolean> resp = response.body();
                List<TableInfo> infos = new ArrayList<>();
                for (Boolean bool : resp)
                {
                    infos.add(new TableInfo(bool));
                }
                data.setValue(infos);
            }

            @Override
            public void onFailure(Call<List<Boolean>> call, Throwable t)
            {
                data.setValue(null);
            }
        });

        return data;
    }
}
