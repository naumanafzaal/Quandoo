/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.quandoo.Database.DAO;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import com.app.quandoo.Service.Model.Customer;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface CustomerDao
{
    @Query("select * from customer")
    List<Customer> loadAllCustomers();

    @RawQuery
    List<Customer> searchUserWithName(SupportSQLiteQuery query);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceUsers(List<Customer> customers);

    @Query("select * from customer where id is :id")
    Customer customerWithId(int id);

    @Query("DELETE FROM customer")
    void deleteAll();
}