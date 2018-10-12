package com.app.quandoo;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.app.quandoo.Database.AppDatabase;
import com.app.quandoo.Database.DAO.CustomerDao;
import com.app.quandoo.Database.DAO.TableInfoDao;
import com.app.quandoo.Service.Generic.DataWrapper;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Model.TableInfo;
import com.app.quandoo.Service.Repository.CustomerRepository;
import com.app.quandoo.Service.Repository.TableInfoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RepositoryInstrumentedTest
{
    private CustomerRepository customerRepository;
    private TableInfoRepository tableInfoRepository;

    TableInfoDao tableInfoDao;
    CustomerDao customerDao;

    @Before
    public void useAppContext()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();

        AppDatabase appDatabase = AppDatabase.getInstance(appContext);
        tableInfoDao = appDatabase.tableInfoDao();
        customerDao = appDatabase.customerDao();

        customerRepository = new CustomerRepository();
        tableInfoRepository = new TableInfoRepository();
    }

    @Test
    public void addCustomer()
    {
        Customer customer = new Customer();
        customer.id = 1;
        customer.firstName = "Nauman";
        customer.lastName = "Afzaal";
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customerDao.insertOrReplaceUsers(customers);

        Customer loadedCustomer = customerDao.customerWithId(customer.id);
        assertNotNull(loadedCustomer);
        assertEquals(loadedCustomer.id, customer.id);
        assertTrue(loadedCustomer.firstName.equals(customer.firstName));
        assertTrue(loadedCustomer.lastName.equals(customer.lastName));
    }

    @Test
    public void testSearchCustomer() throws InterruptedException
    {
        Customer customer = new Customer();
        customer.id = 100;
        customer.firstName = "Dummy";
        customer.lastName = "User";
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customerDao.insertOrReplaceUsers(customers);

        MutableLiveData<DataWrapper<List<Customer>>> liveData = new MutableLiveData<>();
        DataWrapper<List<Customer>> dataWrapper = customerRepository.searchCustomersMatchingName("du ser");
        List<Customer> data = dataWrapper.getData();
        assertNotNull(data);
        assertTrue(data.get(0).firstName.equals(customer.firstName));
        assertTrue(data.get(0).lastName.equals(customer.lastName));
    }

    @Test
    public void testBooking() throws InterruptedException
    {
        TableInfo tableInfo = new TableInfo(10, false);
        tableInfoDao.insertOrReplaceTable(tableInfo);

        Customer customer = customerDao.customerWithId(1);

        tableInfoRepository.bookTable(tableInfo, customer);
        TableInfo dbTable = tableInfoDao.tableWithId(tableInfo.id);
        assertTrue(dbTable.isBooked);
        assertTrue(!dbTable.canBookTable());
    }
}
