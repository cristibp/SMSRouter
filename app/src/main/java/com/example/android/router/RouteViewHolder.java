/*
 * Copyright (C) 2020 Google Inc.
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

package com.example.android.router;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class RouteViewHolder extends RecyclerView.ViewHolder {
    private final TextView textViewSMS;
    private final TextView textViewURL;
    private final Switch status;

    private RouteViewHolder(View itemView) {
        super(itemView);
        textViewSMS = itemView.findViewById(R.id.textViewSMS);
        textViewURL = itemView.findViewById(R.id.textViewURL);
        status = itemView.findViewById(R.id.status);
    }

    public void bind(String text, String sms, boolean active) {
        textViewSMS.setText(sms);
        textViewURL.setText(text);
        status.setChecked(active);
    }

    static RouteViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new RouteViewHolder(view);
    }
}
