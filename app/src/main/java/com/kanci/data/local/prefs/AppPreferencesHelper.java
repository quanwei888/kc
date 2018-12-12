/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.kanci.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class AppPreferencesHelper {

    public static final String PREF_KEY_USER_ID = "PREF_KEY_USER_ID";
    public static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME";
    public static final String PREF_KEY_USER_PIC = "PREF_KEY_USER_PIC";
    public static final String PREF_KEY_COOKIE_ID = "PREF_KEY_COOKIE_ID";

    public final SharedPreferences prefs;

    public AppPreferencesHelper(Context context, String prefFileName) {
        prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    public HashSet getCookie() {
        return (HashSet) prefs.getStringSet(PREF_KEY_COOKIE_ID, new HashSet<>());
    }

    public void setCookie(HashSet cookies) {
        prefs.edit()
                .putStringSet(PREF_KEY_COOKIE_ID, cookies)
                .apply();
    }
}
