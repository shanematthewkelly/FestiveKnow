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

package com.shanekelly.example.festiveknow.list;


import android.app.ActivityOptions;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import com.shanekelly.example.festiveknow.LoginActivity;
import com.shanekelly.example.festiveknow.R;
import com.shanekelly.example.festiveknow.RoomApp;
import com.shanekelly.example.festiveknow.create.CreateActivity;
import com.shanekelly.example.festiveknow.data.ListItem;
import com.shanekelly.example.festiveknow.detail.DetailActivity;
import com.shanekelly.example.festiveknow.viewmodel.EditViewModel;
import com.shanekelly.example.festiveknow.viewmodel.ListItemCollectionViewModel;


public class ListFragment extends Fragment {

    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";

    //Variables
    private List<ListItem> listOfData;
    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private Toolbar toolbar;
    private FirebaseAuth myAuth;
    private TextView firstreview, logouttext;
    private ListItem listItem;


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ListItemCollectionViewModel listItemCollectionViewModel;
    EditViewModel editViewModel;

    public ListFragment() {
        //Required empty public constructor
    }

    //
    public static ListFragment newInstance() {
        return new ListFragment();
    }

        /*------------------------------- Lifecycle -------------------------------*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //FireBase Auth setup
        myAuth = FirebaseAuth.getInstance();

        //Using Dagger 2 Dependency Injection Framework we inject the list fragment from the List Activity
        ((RoomApp) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //Set up and subscribe (observe) to the ViewModel
        listItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ListItemCollectionViewModel.class);

        listItemCollectionViewModel.getListItems().observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(@Nullable List<ListItem> listItems) {
                //within the onChanged function if the listOfData is null then it sets list items
                if (listOfData == null) {
                    setListData(listItems);
                }


            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        //----------------------------------Init Views ---------------------------------------//
        recyclerView =  v.findViewById(R.id.rec_list_activity);
        layoutInflater = getActivity().getLayoutInflater();
        toolbar =  v.findViewById(R.id.tlb_list_activity);
        firstreview = v.findViewById(R.id.firstreviewtext);
        logouttext = v.findViewById(R.id.logouttext);
        toolbar.setTitle(R.string.title_toolbar);
        toolbar.setLogo(R.drawable.ic_sms_black_24dp);
        toolbar.setTitleMarginStart(72);

        FloatingActionButton fabulous =  v.findViewById(R.id.fab_create_new_item);
        FloatingActionButton exit = v.findViewById(R.id.logout_page_button);

        fabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starts the create activity
                startCreateActivity();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Firebase's built in SignOut method is called and signs the user out and redirects them to the Login Activity
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                Intent signedOut = new Intent(getActivity(), LoginActivity.class);
                startActivity(signedOut);
            }
        });



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //starts the Detail Activity and passes in the item ID from the List Activity
    public void startDetailActivity(String itemId, View viewRoot) {
        AppCompatActivity container = (AppCompatActivity) getActivity();
        Intent i = new Intent(container, DetailActivity.class);
        i.putExtra(EXTRA_ITEM_ID, itemId);

        //if statement responsible for transitions between the List Activity and the Detail Activity
        //I check the build version to see if it is LOLLIPOP and if it is then run the transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            container.getWindow().setEnterTransition(new Fade(Fade.IN));
            container.getWindow().setEnterTransition(new Fade(Fade.OUT));

            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(container,
                            new Pair<>(viewRoot.findViewById(R.id.imv_list_item_circle),
                                    getString(R.string.transition_drawable)),
                            new Pair<>(viewRoot.findViewById(R.id.lbl_message),
                                    getString(R.string.transition_message)),
                            new Pair<>(viewRoot.findViewById(R.id.lbl_date_and_time),
                                    getString(R.string.transition_time_and_date)));

            startActivity(i, options.toBundle());

        } else {
            startActivity(i);
        }
    }

    //simple method that starts the Create Activity
    public void startCreateActivity() {
        startActivity(new Intent(getActivity(), CreateActivity.class));
    }


    //Constructor
    public void setListData(List<ListItem> listOfData) {
        this.listOfData = listOfData;

        //Recyclerview & Adapter Setup
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);


        //I used online resources for this to find out more about decorations - I added a divider between the items in the recyclerview
        //however it is barely noticable
        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );

        itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                        getActivity(),
                        R.drawable.divider_white
                )
        );

        recyclerView.addItemDecoration(
                itemDecoration
        );



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    /*-------------------- RecyclerView Boilerplate ----------------------*/

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {//6

        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.item_data, parent, false);
            return new CustomViewHolder(v);
        }

        //binding the views
        @Override
        public void onBindViewHolder(CustomAdapter.CustomViewHolder holder, int position) {

            ListItem currentItem = listOfData.get(position);
            holder.image.setImageResource(currentItem.getColorResource());
            holder.message.setText(
                    currentItem.getMessage()
            );

            holder.dateAndTime.setText(
                    currentItem.getItemId()
            );

            holder.loading.setVisibility(View.INVISIBLE);
        }

        @Override
        public int getItemCount() {
            return listOfData.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            //------------------------Binding Layouts ---------------------------- //

            //Variables
            private ImageView image;
            private TextView dateAndTime;
            private TextView message;
            private ViewGroup container;
            private ProgressBar loading;

            //holder, gets the resources from the XML files
            public CustomViewHolder(View itemView) {
                super(itemView);
                this.image = itemView.findViewById(R.id.imv_list_item_circle);
                this.dateAndTime =  itemView.findViewById(R.id.lbl_date_and_time);
                this.message =  itemView.findViewById(R.id.lbl_message);
                this.loading =  itemView.findViewById(R.id.pro_item_data);
                this.container = itemView.findViewById(R.id.root_list_item);
                this.container.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //getAdapterPosition() get's an Integer based on which the position of the current
                //ViewHolder (this) in the Adapter. This is how we get the correct Data.
                ListItem listItem = listOfData.get(
                        this.getAdapterPosition()
                );

                startDetailActivity(listItem.getItemId(), v);

            }
        }

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                listItemCollectionViewModel.deleteListItem(
                        listOfData.get(position)
                );

                //ensure View is consistent with underlying data
                listOfData.remove(position);
                adapter.notifyItemRemoved(position);


            }
        };
        return simpleItemTouchCallback;
    }





}
