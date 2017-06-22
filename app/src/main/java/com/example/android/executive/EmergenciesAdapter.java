package com.example.android.executive;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RoshanJoy on 20-03-2017.
 */

public class EmergenciesAdapter  extends ArrayAdapter<Emergencies>{

    public EmergenciesAdapter(Context context, int resource, List<Emergencies> objects) {

        super(context, resource, objects );
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_emergency_message, parent, false);
        }

        TextView mUsername = (TextView)convertView.findViewById(R.id.EmergenciesUsername);
        TextView mseverity = (TextView)convertView.findViewById(R.id.EmergenciesSeverity);
        TextView mtype = (TextView)convertView.findViewById(R.id.EmergenciesType);
        TextView no = (TextView)convertView.findViewById(R.id.no);
        View v = convertView.findViewById(R.id.view1);


        final Emergencies emergencies = getItem(position);

        String string;
        string="Not Specified";
        v.setBackgroundColor(getContext().getResources().getColor(R.color.colordarkblue));

        if(emergencies.emergencyDetails.getSi().equals("1"))
        {
            string="low";
            v.setBackgroundColor(Color.parseColor("#ff99cc00"));
        }
        if(emergencies.emergencyDetails.getSi().equals("2"))
        {
            string="medium";
            v.setBackgroundColor(Color.parseColor("#ffffbb33"));
        }
        else if(emergencies.emergencyDetails.getSi().equals("3"))
        {
            string="high";
            v.setBackgroundColor(Color.parseColor("#ffff4444"));
        }

        String string1="Not Specified";
        if(emergencies.emergencyDetails.getTi().equals("1"))
        {
            string1="fire";
        }
        if(emergencies.emergencyDetails.getTi().equals("2"))
        {
            string1="pregnancy";
        }
        if(emergencies.emergencyDetails.getTi().equals("3"))
        {
            string1="heart";
        }
        if(emergencies.emergencyDetails.getTi().equals("4"))
        {
            string1="accident";
        }
        if(emergencies.emergencyDetails.getTi().equals("5"))
        {
            string1="head injury";
        }
        if(emergencies.emergencyDetails.getTi().equals("6"))
        {
            string1="other";
        }

        String string2;
        if(emergencies.emergencyDetails.getno().equals("0"))
            string2="Not Specified";
        else
            string2=emergencies.emergencyDetails.getno();
        mUsername.setText(""+emergencies.emergencyDetails.getUsername());
        Log.v("emer","");
        mseverity.setText(""+string);
        Log.v("emer","");
        mtype.setText(""+string1);
        Log.v("emer","");
        no.setText(string2);




        return convertView;
    }
}
