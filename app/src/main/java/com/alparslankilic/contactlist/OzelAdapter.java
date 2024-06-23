package com.alparslankilic.contactlist;

import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;


public class OzelAdapter extends BaseAdapter {

    Context con;
    private LayoutInflater mInflater;
    private List<PersonInfo> mKisiListesi;

    public OzelAdapter(Context activity, List<PersonInfo> kisiler) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mKisiListesi = kisiler;
        con = activity;
    }

    @Override
    public int getCount() {
        return mKisiListesi.size();
    }

    @Override
    public PersonInfo getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.listview_item, null);
        TextView txtName =
                satirView.findViewById(R.id.txtName);

        final TextView txtTelefon =
                satirView.findViewById(R.id.txtNumber);

        ImageButton imgCall =
                satirView.findViewById(R.id.call_image);


        ImageButton imgMesaj =
                satirView.findViewById(R.id.msg_image);
        PersonInfo kisi = mKisiListesi.get(position);

        txtName.setText(kisi.getName());
        txtTelefon.setText(kisi.getPhoneNumber());

        imgCall.setImageResource(R.drawable.phone_icon);

        imgCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phoneNumber = (String) txtTelefon.getText();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phoneNumber));

                if (ActivityCompat.checkSelfPermission(con, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) con,
                            new String[]{Manifest.permission.CALL_PHONE}, 102);
                    return;
                }
                con.startActivity(callIntent);


            }

        });

        imgMesaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phoneNumber = (String) txtTelefon.getText();
                try
                {
                    if(Build.VERSION.SDK_INT > 23)
                    {
                        if (ActivityCompat.checkSelfPermission(con, Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) con,
                                    new String[]{Manifest.permission.SEND_SMS}, 101);
                            return;
                        }

                        Uri uri = Uri.parse("smsto:"+phoneNumber);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body", "Here goes your message...");
                        con.startActivity(intent);

                    }
                    else {
                        Uri uri = Uri.parse("smsto:"+phoneNumber);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body", "Here goes your message...");
                        con.startActivity(intent);

                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        });

        return satirView;
    }
}

