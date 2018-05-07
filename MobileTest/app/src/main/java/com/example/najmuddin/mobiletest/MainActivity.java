package com.example.najmuddin.mobiletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.najmuddin.mobiletest.manager.InternetChecker;
import com.example.najmuddin.mobiletest.manager.JsonRetriever;
import com.example.najmuddin.mobiletest.object.Place;
import com.example.najmuddin.mobiletest.utils.Navigator;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,JsonRetriever.TaskDone {
    MultiAutoCompleteTextView mactvArea;
    ImageView ivSearch;
    JsonRetriever jsonRetriever;
    Navigator navigator;
    String url="http://52.76.85.10/test/location.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mactvArea=(MultiAutoCompleteTextView) findViewById(R.id.mactvArea);
        ivSearch=(ImageView) findViewById(R.id.ivSearch);

        ivSearch.setOnClickListener(this);
        navigator=new Navigator(this);

        if(new InternetChecker().isConnectingToInternet(this)){
            jsonRetriever=new JsonRetriever(this);
            jsonRetriever.requestJson(url,1);
        }else{
            Toast.makeText(this, "No internet Connection",
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ivSearch:
                navigator.toListActivity(mactvArea.getText().toString());
                break;
        }

    }

    @Override
    public void onSuccess(String Json){
        Toast.makeText(this, "Retrieve success",
                Toast.LENGTH_LONG).show();

        try{
            ArrayList<String> areaList=new ArrayList<>();
            TypeToken<List<Place>> token = new TypeToken<List<Place>>() {};
            ArrayList<Place> placesList=placesList=new Gson().fromJson(Json,token.getType());


            if(placesList.size()!=0){
                for (int i=0;i<placesList.size();i++){
                    areaList.add(placesList.get(i).getArea()+", "+placesList.get(i).getCity());
                }
            }else {
                Log.d("Place List","Empty");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this,android.R.layout.simple_list_item_1,areaList);

            mactvArea.setAdapter(adapter);
            mactvArea.setThreshold(1);
            // comma to separate the different colors
            mactvArea.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

            mactvArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String temp = String.valueOf(arg0.getItemAtPosition(arg2));
//                    mactvArea.append(temp);

                }
            });

        }catch (JsonParseException e){
            Log.d("Json parse","Failed");
        }


    }

    @Override
    public void onFailed(){
        Toast.makeText(this, "Failed to retrieve Json",
                Toast.LENGTH_LONG).show();

    }


}
