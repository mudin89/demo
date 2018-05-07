package com.example.najmuddin.mobiletest;

import android.Manifest;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.najmuddin.mobiletest.manager.FragmentMap;
import com.example.najmuddin.mobiletest.manager.InternetChecker;
import com.example.najmuddin.mobiletest.manager.JsonRetriever;
import com.example.najmuddin.mobiletest.object.Details;
import com.example.najmuddin.mobiletest.utils.Navigator;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.InputStream;
/**
 * Created by Najmuddin on 30/11/2016.
 */

public class DetailsActivity extends AppCompatActivity implements JsonRetriever.TaskDetails{
    JsonRetriever jsonRetriever;
    Navigator navigator;
    TextView tvRecomNum,tvSchedule,tvName,tvArea,tvSpecialist,tvExperiences,tvDescription;
    ImageView ivPhoto;
    String MapPREFERENCES="MapPREFERENCES",LATITUDE="LATITUDE",LONGTITUDE="LONGTITUDE",NAME="NAME",DETAILS="DETAILS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleview);

        String detailsUrl;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                detailsUrl= null;
            } else {
                detailsUrl= extras.getString("idurl");
            }
        } else {
            detailsUrl= (String) savedInstanceState.getSerializable("idurl");
        }

        tvRecomNum=(TextView) findViewById(R.id.tvRecomendationNum);
        tvSchedule=(TextView) findViewById(R.id.tvSchedule);
        tvName=(TextView) findViewById(R.id.tvSingleName);
        tvArea=(TextView) findViewById(R.id.tvSingleArea);
        tvSpecialist=(TextView) findViewById(R.id.tvSingleSpecialist);
        tvExperiences=(TextView) findViewById(R.id.tvExperience);
        tvDescription=(TextView) findViewById(R.id.tvDescription);
        ivPhoto=(ImageView) findViewById(R.id.ivSinglePhoto);

        navigator = new Navigator(this);

        if (new InternetChecker().isConnectingToInternet(this)) {
            jsonRetriever = new JsonRetriever(this);
            if (!isEmptyString(detailsUrl)){
                jsonRetriever.requestJson(detailsUrl, 3);
            }else {
                Log.d("Empty","Details Url ");
            }

        } else {
            Toast.makeText(this, "No internet Connection",
                    Toast.LENGTH_LONG).show();
        }

        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            }
        }
    }


    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permisson","Granted");
                } else {
                    Toast.makeText(this, "Location disabled!",
                            Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onSuccessDetails(String Json) {
        Toast.makeText(this, "Retrieve success",
                Toast.LENGTH_LONG).show();
//        Json=Json.trim();
        try {
            Details details=new Gson().fromJson(Json,Details.class);
            tvRecomNum.setText(Integer.toString(details.getRecommendation()));
            tvSchedule.setText(details.getSchedule());
            tvName.setText(details.getName());
            tvArea.setText(details.getArea());
            tvSpecialist.setText(details.getSpeciality());
            tvDescription.setText(details.getDescription());

            SharedPreferences sp= getSharedPreferences(MapPREFERENCES,MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString(LATITUDE,String.valueOf(details.getLatitude()));
            editor.putString(LONGTITUDE,String.valueOf(details.getLongtitude()));
            editor.putString(NAME,details.getName());
            editor.putString(DETAILS,details.getArea());
            editor.apply();

            toMap();

            if(new InternetChecker().isConnectingToInternet(this)){
                new DownloadImageTask(ivPhoto).execute(details.getPhoto());
            }else{
                Log.d("Product View: ","No internet");
            }

        } catch (JsonParseException e) {
            Log.d("Json parse", "Failed");
        }
    }

    @Override
    public void onFailedDetails() {
        Toast.makeText(this, "Failed to retrieve Json",
                Toast.LENGTH_LONG).show();

    }

    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
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

    public void toMap(){
        if(!isFinishing()) {
            boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate ("MAP Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new FragmentMap());
            ft.commitAllowingStateLoss();
        }

    }
}