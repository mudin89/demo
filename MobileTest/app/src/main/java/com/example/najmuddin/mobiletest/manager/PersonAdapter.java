package com.example.najmuddin.mobiletest.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.najmuddin.mobiletest.R;
import com.example.najmuddin.mobiletest.object.Person;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Najmuddin on 30/11/2016.
 */

public class PersonAdapter extends ArrayAdapter<Person> {
    Context context;

    public PersonAdapter(Activity context, ArrayList<Person> products) {
        super(context, R.layout.single, products);
        this.context = context;
    }

    private static class ViewHolder {
        CircularImageView ivPhoto;
        TextView tvName, tvArea, tvSpeciality,tvCurrency,tvRate;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Person person = getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View rowView = layoutInflater.inflate(R.layout.single, null, true);
        LinearLayout linearLayout=(LinearLayout) rowView.findViewById(R.id.llSingle);

        viewHolder.ivPhoto = (CircularImageView) rowView.findViewById(R.id.ivPhoto);
        viewHolder.tvName = (TextView) rowView.findViewById(R.id.tvName);
        viewHolder.tvArea = (TextView) rowView.findViewById(R.id.tvArea);
        viewHolder.tvSpeciality = (TextView) rowView.findViewById(R.id.tvSpeciality);
        viewHolder.tvCurrency = (TextView) rowView.findViewById(R.id.tvCurrency);
        viewHolder.tvRate = (TextView) rowView.findViewById(R.id.tvRate);

        if(new InternetChecker().isConnectingToInternet(context)){
            new DownloadImageTask(viewHolder.ivPhoto).execute(person.getPhotolink());
        }else{
            Log.d("Product View: ","No internet");
        }

        viewHolder.tvName.setText(person.getName());
        viewHolder.tvArea.setText(person.getArea());
        viewHolder.tvSpeciality.setText(person.getSpeciality());
        viewHolder.tvCurrency.setText(person.getCurrency());
        viewHolder.tvRate.setText(String.valueOf(person.getRate()));


        return rowView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        CircularImageView bmImage;

        public DownloadImageTask(CircularImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}