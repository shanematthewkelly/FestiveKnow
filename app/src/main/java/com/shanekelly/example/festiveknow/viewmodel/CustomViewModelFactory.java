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

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.shanekelly.example.festiveknow.data.ListItemRepository;

//This file contains the arguments which I want to give my View Models,
//In this case they all want the Repository

//setting up CustomViewModelFactory to have only 1 instance
@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final ListItemRepository repository;

    @Inject
    public CustomViewModelFactory(ListItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListItemCollectionViewModel.class))
            return (T) new ListItemCollectionViewModel(repository);

        else if (modelClass.isAssignableFrom(ListItemViewModel.class))
            return (T) new ListItemViewModel(repository);

        else if (modelClass.isAssignableFrom(EditViewModel.class))
            return (T) new EditViewModel(repository);

        else if (modelClass.isAssignableFrom(NewListItemViewModel.class))
            return (T) new NewListItemViewModel(repository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
