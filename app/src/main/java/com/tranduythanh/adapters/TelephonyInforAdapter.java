package com.tranduythanh.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tranduythanh.k22411casampleproject.R;
import com.tranduythanh.k22411casampleproject.SendSMSActivity;
import com.tranduythanh.k22411casampleproject.TelephonyActivity;
import com.tranduythanh.models.TelephonyInfor;

import org.jetbrains.annotations.Nullable;

public class TelephonyInforAdapter extends ArrayAdapter<TelephonyInfor> {
    Activity context;
    int resource;

    public TelephonyInforAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate the view for each item in the ListView
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        // Find views
        TextView txtTelephonyInforName = item.findViewById(R.id.txtTelephonyInforName);
        TextView txtTelephonyInforPhone = item.findViewById(R.id.txtTelephonyInforPhone);
        ImageView imgCallDirect = item.findViewById(R.id.imgCallDirect);
        ImageView imgCallDial = item.findViewById(R.id.imgCallDialup);
        ImageView imgSms = item.findViewById(R.id.imgSms);

        // Get the TelephonyInfor object for this position
        TelephonyInfor ti = getItem(position);

        // Set the display name and phone number
        txtTelephonyInforName.setText(ti.getDisplayName());
        txtTelephonyInforPhone.setText(ti.getPhoneNumber());

        // Set up call direct action
        imgCallDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TelephonyActivity) context).callDirect(ti);  // Call directly
            }
        });

        // Set up call dial action
        imgCallDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TelephonyActivity) context).callDialup(ti);  // Open dialer
            }
        });

        // Set up SMS action
        imgSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SendSMSActivity.class);
                intent.putExtra("TI", ti);  // Pass the TelephonyInfor object to SendSMSActivity
                context.startActivity(intent);
            }
        });

        return item;  // Return the populated view for this row
    }
}



