package com.app.quandoo.Service.Generic;

/**
 * Created by Nauman Afzaal on 10/10/2018.
 */
public class DataWrapper<T>
{
    private Exception apiException;
    private T data;

    public Exception getApiException()
    {
        return apiException;
    }

    public void setApiException(Exception apiException)
    {
        this.apiException = apiException;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }
}
