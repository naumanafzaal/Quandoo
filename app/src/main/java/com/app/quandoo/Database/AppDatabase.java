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

package com.app.quandoo.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.app.quandoo.Database.DAO.CustomerDao;
import com.app.quandoo.Database.DAO.TableInfoDao;
import com.app.quandoo.QuandooApp;
import com.app.quandoo.Service.Model.Customer;
import com.app.quandoo.Service.Model.TableInfo;

@Database(entities = {Customer.class, TableInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    private static AppDatabase instance;

    public abstract CustomerDao customerDao();

    public abstract TableInfoDao tableInfoDao();

    public static AppDatabase getInstance()
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(QuandooApp.context, AppDatabase.class, "quandoo")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}