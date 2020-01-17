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

import java.util.List;

import javax.inject.Inject;

public class ListItemRepository {

    private final ListItemDao listItemDao;

    //Repository Constructor
    @Inject
    public ListItemRepository(ListItemDao listItemDao){
        this.listItemDao = listItemDao;
    }


    //Gets the list items as LiveData, LiveData with an array of ListItems within a List array that makes a call to the DAO and gets the Listitems
    public LiveData<List<ListItem>> getListOfData(){
        return listItemDao.getListItems();
    }

    //Gets the list item as LiveData and fetches it's ID by making a call to the DAO
    public LiveData<ListItem> getListItem(String itemId){
        return listItemDao.getListItemById(itemId);
    }

    //Gets the list item by its' ID by making a call to the DAO
    public ListItem findListItemByIdReturnList(String itemId){
        return listItemDao.findListItemByIdReturnList(itemId);
    }

    //Gets the list item by making a call to the DAO - and getting the insert function within the DAO
    public Long createNewListItem(ListItem listItem){
        return listItemDao.insertListItem(listItem);
    }


    //Gets the list item by making a call to the DAO - and getting the update function within the DAO
    public void updateNewListItem(ListItem listItem){
        listItemDao.updateListItem(listItem);
    }

    //Gets the list item by making a call to the DAO - and getting the delete function within the DAO
    public void deleteListItem(ListItem listItem){
        listItemDao.deleteListItem(listItem);
    }



}
