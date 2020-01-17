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

package com.shanekelly.example.festiveknow.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import com.shanekelly.example.festiveknow.R;
import com.shanekelly.example.festiveknow.RoomApp;
import com.shanekelly.example.festiveknow.data.ListItem;
import com.shanekelly.example.festiveknow.list.ListActivity;
import com.shanekelly.example.festiveknow.viewmodel.EditViewModel;
import com.shanekelly.example.festiveknow.viewmodel.ListItemViewModel;
import com.shanekelly.example.festiveknow.viewmodel.NewListItemViewModel;


public class DetailFragment extends Fragment {

    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";

    //Variables
    private ViewPager drawablePager;
    private CirclePageIndicator pageIndicator;
    private TextView dateAndTime;
    private EditText messageInput;
    private ImageView coloredBackground;
    private ImageButton back;
    private Button update;
    private PagerAdapter pagerAdapter;
    private LiveData<ListItem> listItemLiveData;
    private String itemId;
    private TextView title;
    private ListItem listItem;
    private ListItemViewModel listItemViewModel;
    private EditViewModel editViewModel;
    private NewListItemViewModel newListItemViewModel;
    private DetailFragment detailFragment;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public DetailFragment() {
        //Required empty public constructor
    }

    //instantiating a new fragment (DetailFragment)
    public static DetailFragment newInstance(String itemId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_ITEM_ID, itemId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Using Dagger 2 Dependency Injection Framework we inject the detail fragment from the Detail Activity
        ((RoomApp) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        Bundle args = getArguments();
        //itemId is equal to the arguments, gets the string and passes in the extra tem ID
        this.itemId = args.getString(EXTRA_ITEM_ID);


    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set up and subscribe (observe) to the ViewModel
        editViewModel = ViewModelProviders.of(this, viewModelFactory).get(EditViewModel.class);

        //makes a call to the editView model and fetches the method responsible for getting the List Item by its' ID, and passes in its' parameters
        editViewModel.getListItemById(itemId).observe(this, new Observer<ListItem>() {
            @Override
            public void onChanged(@Nullable ListItem listItem) {

                //if statement where if the listItem is not equal to null, then get the ItemId and Message
                if (listItem != null) {
                    dateAndTime.setText(listItem.getItemId());
                    messageInput.setText(listItem.getMessage());

                }

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

                                 // init views //
        //-------------------------------------------------------------------------//
        back = v.findViewById(R.id.imb_update_back);

        dateAndTime =  v.findViewById(R.id.lbl_date_and_time_header);

        messageInput = v.findViewById(R.id.lbl_message_body);

        drawablePager = v.findViewById(R.id.vp_update_drawable);

        title = v.findViewById(R.id.titledetail);

        pagerAdapter = new DrawablePagerAdapter();

        drawablePager.setAdapter(pagerAdapter);

        update = v.findViewById(R.id.imb_update_done);

        pageIndicator = v.findViewById(R.id.vpi_update_drawable);
        pageIndicator.setViewPager(drawablePager);

        detailFragment =this;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListActivity();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                runUpdate();
            }

        });

        //-------------------------------------------------------------------------//


        title.setText("Longitude Festival(3rd - 5th July");

        // listen for page changes so we can track the current index - sets the text
        //I used the following accompanied by a switch because the ViewPager loads 2 images at once meaning that
        //the text in case 0 and case 1 and so on would load at the same time, so it needs to check when a change has been made
        drawablePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int arg0) {
                //required method
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
                //required method
            }

            public void onPageSelected(int currentPage) {

                switch (currentPage) {
                    case 0:
                        title.setText("Longitude Festival(3rd - 5th July");
                        break;
                    case 1:
                        title.setText("Body & Soul (21st – 23rd June)  ");
                        break;
                    case 2:
                        title.setText("Electric Picnic (30th August – 1st September)");
                        break;
                    case 3:
                        title.setText("Barn Dance(17th - 19th July");
                        break;
                    case 4:
                        title.setText("All Together Now (2nd – 4th August) ");
                        break;
                    case 5:
                        title.setText("Belsonic (12th – 28th June) ");
                        break;
                    case 6:
                        title.setText("Sea Sessions (21st – 23rd June)");
                        break;
                    case 7:
                        title.setText("Another Love Story (16th – 18th August) ");
                        break;
                    case 8:
                        title.setText("Forbidden Fruit (1st – 3rd June) ");
                        break;
                    case 9:
                        title.setText("Body & Soul (21st – 23rd June)");
                        break;
                    case 10:
                        title.setText("Open Ear (May 31st – June 3rd) ");
                        break;
                }
            }

        });


        return v;
    }
    //gets the view pager's position and swaps out the images when the user swipes
    public int getDrawableResource(int pagerItemPosition) {
        switch (pagerItemPosition) {
            case 0:
                return R.drawable.img_longitude;
            case 1:
                return R.drawable.img_bodysoul;
            case 2:
                return R.drawable.img_electric;
            case 3:
                return R.drawable.img_barn;
            case 4:
                return R.drawable.img_alltogether;
            case 5:
                return R.drawable.img_belsonic;
            case 6:
                return R.drawable.img_sea;
            case 7:
                return R.drawable.img_love;
            case 8:
                return R.drawable.img_forbidden;
            case 9:
                return R.drawable.img_knock;
            case 10:
                return R.drawable.img_ear;
            default:

                return 0;
        }
    }

    private void startListActivity() {
        startActivity(new Intent(getActivity(), ListActivity.class));
    }


    //Date and time setup
    public String getDate() {

        Date currentDate = Calendar.getInstance().getTime();

        DateFormat format = new SimpleDateFormat("yyyy/MM/dd/");

        return format.format(currentDate);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    //View Page adapter
    private class DrawablePagerAdapter extends PagerAdapter {

        //method that returns 10 images on the view pager
        @Override
        public int getCount() {
            return 10;
        }

        //inflates the view pager from the XML
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            ImageView pagerItem = (ImageView) inflater.inflate(R.layout.item_drawable_pager,
                    container,
                    false);

            switch (position) {
                case 0:
                    pagerItem.setImageResource(R.drawable.img_longitude);
                    break;
                case 1:
                    pagerItem.setImageResource(R.drawable.img_bodysoul);
                    break;
                case 2:
                    pagerItem.setImageResource(R.drawable.img_electric);
                    break;
                case 3:
                    pagerItem.setImageResource(R.drawable.img_barn);
                    break;
                case 4:
                    pagerItem.setImageResource(R.drawable.img_alltogether);
                    break;
                case 5:
                    pagerItem.setImageResource(R.drawable.img_belsonic);
                    break;
                case 6:
                    pagerItem.setImageResource(R.drawable.img_sea);
                    break;
                case 7:
                    pagerItem.setImageResource(R.drawable.img_love);
                    break;
                case 8:
                    pagerItem.setImageResource(R.drawable.img_forbidden);
                    break;
                case 9:
                    pagerItem.setImageResource(R.drawable.img_knock);
                    break;
                case 10:
                    pagerItem.setImageResource(R.drawable.img_ear);
                    break;
            }

            container.addView(pagerItem);
            return pagerItem;
        }

        //returns the object from the view
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        //deletes the image resource from the view pager if the user swipes several times
        //in a certain direction
        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    //called from within the update button's onClickListener
    public void runUpdate() {

        //creates a new ListItem, passes in the itemId
        //and also passes in the ListItem's constructor
        listItem = new ListItem((this.itemId),messageInput.getText().toString().trim(), getDrawableResource(drawablePager.getCurrentItem()));

        //makes a call to edit view model and gets the method responsible for updating a new item to the database, and passes in its' parameters
        editViewModel.updateNewItemToDatabase(listItem);

        //starts the Main Activity (startListActivity)
        startListActivity();
    }


}



















