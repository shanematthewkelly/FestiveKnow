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

package com.shanekelly.example.festiveknow.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.List;

import com.shanekelly.example.festiveknow.data.ListItem;
import com.shanekelly.example.festiveknow.data.ListItemRepository;


public class ListItemCollectionViewModel extends ViewModel {

    private ListItemRepository repository;

    ListItemCollectionViewModel(ListItemRepository repository) {
        this.repository = repository;
    }

    //gets the list items as LiveData
    public LiveData<List<ListItem>> getListItems() {
        return repository.getListOfData();
    }

    //sets up a new Task to delete the ListItem from the DB
    public void deleteListItem(ListItem listItem) {
        DeleteItemTask deleteItemTask = new DeleteItemTask();
        deleteItemTask.execute(listItem);
    }

    private class DeleteItemTask extends AsyncTask<ListItem, Void, Void> {

        @Override
        protected Void doInBackground(ListItem... item) {
            repository.deleteListItem(item[0]);
            return null;
        }
    }

}

