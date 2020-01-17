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

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;

import com.shanekelly.example.festiveknow.R;
import com.shanekelly.example.festiveknow.util.BaseActivity;

public class CreateActivity extends BaseActivity {

    private static final String CREATE_FRAG = "CREATE_FRAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Fragment Manager
        FragmentManager manager = getSupportFragmentManager();

        //Creates the fragment by finding its' TAG
        CreateFragment fragment = (CreateFragment) manager.findFragmentByTag(CREATE_FRAG);

        //if statement where if the fragment is null then create a new instance of the fragment
        if (fragment == null) {
            fragment = CreateFragment.newInstance();
        }

        //adds the fragment to the activity by declaring a frameLayout in the XML and inflating it
        addFragmentToActivity(manager,
                fragment,
                R.id.root_activity_create,
                CREATE_FRAG
        );

    }
}
