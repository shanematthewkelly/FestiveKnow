package com.shanekelly.example.festiveknow.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

import com.shanekelly.example.festiveknow.data.ListItem;
import com.shanekelly.example.festiveknow.data.ListItemRepository;
import com.shanekelly.example.festiveknow.detail.DetailFragment;

public class EditViewModel extends ViewModel {

    //variables
    private static ListItemRepository repository;
    private ListItem listItem;
    private DetailFragment detailFragment;


    //Constructor
    EditViewModel(ListItemRepository repository) {
        this.repository = repository;
    }


    //gets list item by ID as LiveData, makes a call to the repository and fetches the method responsible for getting the list item and passing in its ID
    public LiveData<ListItem> getListItemById(String itemId){
        return repository.getListItem(itemId);
    }

    //(Unused)
    public void findListItemByIdReturnList(String itemId,DetailFragment detailFragment){
        new FindItemTask(detailFragment).execute(itemId);
    }

    //sets up a new AsyncTask within this method
    public void updateNewItemToDatabase(ListItem listItem) {
        new UpdateItemTask().execute(listItem);
    }

    private class UpdateItemTask extends AsyncTask<ListItem, Void, Void> {


        //In the background a call is made to the repository that fetches the method responsible for updating a new list item and passes in the list item array
        @Override
        protected Void doInBackground(ListItem... listItem) {
            repository.updateNewListItem(listItem[0]);
            return null;
        }
    }

    private class FindItemTask extends AsyncTask<String, Void, Void> {
        ListItem returnlistItem;
        private WeakReference<DetailFragment> detailFragmentRef;

        public FindItemTask(DetailFragment detailFragment) {
            detailFragmentRef= new WeakReference<>(detailFragment);
        }
        @Override
        protected Void doInBackground(String... listItem) {
            this.returnlistItem = repository.findListItemByIdReturnList(listItem[0]);

            return null;
        }

        //makes a call to detail fragment and fetches the runUpdate() function
        @Override
        public void onPostExecute(Void v) {
            DetailFragment detailFragment =detailFragmentRef.get();
            detailFragment.runUpdate();
        }
    }

}
