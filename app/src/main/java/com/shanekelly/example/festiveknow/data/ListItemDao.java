/*
 * *
 *  * Copyright (C) 2017 Ryan Kay Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.shanekelly.example.festiveknow.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface ListItemDao {

    //Gets the ListItem by it's ID from the database
    @Query("SELECT * FROM ListItem WHERE itemId = :itemId")
    LiveData<ListItem> getListItemById(String itemId);

    //Gets the ListItem by it's ID and gets only one row from the database
    @Query("SELECT * FROM ListItem WHERE itemId = :itemId LIMIT 1")
    ListItem findListItemByIdReturnList(String itemId);

    //Gets all ListItem's from the database
    @Query("SELECT * FROM ListItem")
    LiveData<List<ListItem>> getListItems();

    //Inserts the List Item into the database by replacing it
    @Insert(onConflict = REPLACE)
    Long insertListItem(ListItem listItem);
    //Updates thr ListItem to the database and replaces the existing one
    @Update (onConflict = REPLACE)
    void updateListItem(ListItem listItem);
    //Deletes the ListItem from te database
    @Delete
    void deleteListItem(ListItem listItem);


}
