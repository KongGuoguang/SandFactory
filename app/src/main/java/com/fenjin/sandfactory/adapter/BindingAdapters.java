/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fenjin.sandfactory.adapter;

import android.databinding.BindingAdapter;
import android.view.View;


public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void visibleGone(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visibleOrNot")
    public static void visibleOrNot(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("enableOrNot")
    public static void enableOrNot(View view, boolean enable){
        view.setEnabled(enable);
        view.setAlpha(enable ? 1f : 0.5f);
    }
}