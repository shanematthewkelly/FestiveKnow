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

package com.shanekelly.example.festiveknow.create;


import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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
import com.shanekelly.example.festiveknow.viewmodel.NewListItemViewModel;


public class CreateFragment extends Fragment {

    //Variables
    private ViewPager drawablePager;
    private CirclePageIndicator pageIndicator;
    private ImageButton back;
    private Button create;
    private EditText messageInput;
    private TextView titlecreate;
    private PagerAdapter pagerAdapter;


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private NewListItemViewModel newListItemViewModel;

    public CreateFragment() {
        //Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        //Using Dagger 2 Dependency Injection Framework we inject the create fragment from the Create Activity
        ((RoomApp) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    //instantiating a new fragment (CreateFragment)
    public static CreateFragment newInstance() {
        return new CreateFragment();
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set up and subscribe (observe) to the ViewModel (newListItemViewModel)
        newListItemViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewListItemViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create, container, false);

                                        // init views //
        //------------------------------------------------------------------------- //
        titlecreate = v.findViewById(R.id.titlecreate);

        messageInput = v.findViewById(R.id.edt_create_message);

        drawablePager = v.findViewById(R.id.vp_create_drawable);

        pagerAdapter = new DrawablePagerAdapter();
        drawablePager.setAdapter(pagerAdapter);

        pageIndicator = v.findViewById(R.id.vpi_create_drawable);
        pageIndicator.setViewPager(drawablePager);

        titlecreate.setText("Longitude Festival(3rd - 5th July)");

        back = v.findViewById(R.id.imb_create_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListActivity();
            }
        });

        create =  v.findViewById(R.id.imb_create_done);

       //--------------------------------------------------------------------------------- //

        //Create button onClickListener - within it, it creates a new ListItem which expects its' 3 parameters
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListItem listItem = new ListItem(
                        getDate(),
                        messageInput.getText().toString(),
                        getDrawableResource(drawablePager.getCurrentItem())
                );
                //makes a call to the view model to fetch the method that is is responsible for adding a new list item to the database
                newListItemViewModel.addNewItemToDatabase(listItem);

                //starts the Main Activity (startListActivity)
                startListActivity();
            }
        });

        //-----------------------------------------------------------------------------------------//


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
                        titlecreate.setText("Longitude Festival(3rd - 5th July");
                        break;
                    case 1:
                        titlecreate.setText("Body & Soul (21st – 23rd June)  ");
                        break;
                    case 2:
                        titlecreate.setText("Electric Picnic (30th August – 1st September)");
                        break;
                    case 3:
                        titlecreate.setText("Barn Dance(17th - 19th July");
                        break;
                    case 4:
                        titlecreate.setText("All Together Now (2nd – 4th August) ");
                        break;
                    case 5:
                        titlecreate.setText("Belsonic (12th – 28th June) ");
                        break;
                    case 6:
                        titlecreate.setText("Sea Sessions (21st – 23rd June)");
                        break;
                    case 7:
                        titlecreate.setText("Another Love Story (16th – 18th August) ");
                        break;
                    case 8:
                        titlecreate.setText("Forbidden Fruit (1st – 3rd June) ");
                        break;
                    case 9:
                        titlecreate.setText("Body & Soul (21st – 23rd June)");
                        break;
                    case 10:
                        titlecreate.setText("Open Ear (May 31st – June 3rd) ");
                        break;
                }
            }

        });

        return v;
    }

    //gets the view pager's position and swaps out the images when the user swipes
    public int getDrawableResource (int pagerItemPosition){
        switch (pagerItemPosition){
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

    //simple method that starts the Main Activity class
    private void startListActivity() {
        startActivity(new Intent(getActivity(), ListActivity.class));
    }

    //View Page adapter
    private class DrawablePagerAdapter extends PagerAdapter {

        //inflates the view pager from the XML
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ImageView pagerItem = (ImageView) inflater.inflate(R.layout.item_drawable_pager,
                    container,
                    false);
            //switch that passes in the view pager's position and sets the image resource
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

        //deletes the image resource from the view pager if the user swipes several times
        //in a certain direction
        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        //method that returns 10 images on the view pager
        @Override
        public int getCount() {
            return 10;
        }

        //returns the object from the view
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    //Date and time setup
    public String getDate() {

        Date currentDate = Calendar.getInstance().getTime();

        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        return format.format(currentDate);
    }
}
