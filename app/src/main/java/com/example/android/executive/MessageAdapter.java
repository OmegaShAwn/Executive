package com.example.android.executive;

/*
  Created by RoshanJoy on 16-03-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

class MessageAdapter extends ArrayAdapter<User> {
    private String cuser;

    MessageAdapter(Context context, int resource, List<User> objects, String cuser) {

        super(context, resource, objects );
        this.cuser=cuser;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        TextView mName = (TextView)convertView.findViewById(R.id.name);
        TextView mUsername = (TextView)convertView.findViewById(R.id.username);
        TextView mPassword = (TextView)convertView.findViewById(R.id.password);
        TextView mPhno = (TextView)convertView.findViewById(R.id.phno);
        LinearLayout mSpecialLayout = (LinearLayout)convertView.findViewById(R.id.specialLinearLayout);
        TextView mspecial = (TextView)convertView.findViewById(R.id.special);


        User user = getItem(position);


        assert user != null;
        mName.setText(String.format("%s", user.getname()));
        mUsername.setText(String.format("%s", user.getUsername()));
        mPassword.setText(String.format("%s", user.getPassword()));
        mPhno.setText(String.format("%s", user.getPhno().toString()));

        if(cuser.equals("doctor")||cuser.equals("other")){
            mspecial.setText(String.format("%s", user.getSpeciality()));

        }
        else
        mSpecialLayout.setVisibility(View.GONE);

        return convertView;
    }
}

