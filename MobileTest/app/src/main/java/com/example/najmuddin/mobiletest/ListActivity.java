package com.example.najmuddin.mobiletest;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.najmuddin.mobiletest.manager.InternetChecker;
import com.example.najmuddin.mobiletest.manager.JsonRetriever;
import com.example.najmuddin.mobiletest.manager.PersonAdapter;
import com.example.najmuddin.mobiletest.object.Person;
import com.example.najmuddin.mobiletest.utils.Navigator;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Najmuddin on 30/11/2016.
 */

public class ListActivity extends AppCompatActivity implements JsonRetriever.TaskList{
    private ListView listview;
    private TextView title;

    private ArrayList<Person> data;
    PersonAdapter sd;

    public int TOTAL_LIST_ITEMS = 0;
    public int NUM_ITEMS_PAGE   = 10;
    int currentPage=0;
    private int noOfBtns;
    private Button[] btns;
    JsonRetriever jsonRetriever;
    Navigator navigator;
    String url="http://52.76.85.10/test/datalist.json";
    String urlDetails1="http://52.76.85.10/test/profile/";
    String urlDetails2=".json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_list);

        title	 = (TextView)findViewById(R.id.title);
        listview = (ListView)findViewById(R.id.list);

        navigator=new Navigator(this);
        data = new ArrayList<Person>();

        if(new InternetChecker().isConnectingToInternet(this)){
            jsonRetriever=new JsonRetriever(this);
            jsonRetriever.requestJson(url,2);
        }else{
            Toast.makeText(this, "No internet Connection",
                    Toast.LENGTH_LONG).show();
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigator.toDetailsActivity(urlDetails1+((position+(currentPage*NUM_ITEMS_PAGE))+1)+urlDetails2);
            }
        });
    }

    @Override
    public void onSuccessList(String Json){
        Toast.makeText(this, "Retrieve success",
                Toast.LENGTH_LONG).show();
        try{
            TypeToken<List<Person>> token = new TypeToken<List<Person>>() {};
            ArrayList<Person> personList=new Gson().fromJson(Json,token.getType());
            data.addAll(personList);
            TOTAL_LIST_ITEMS=personList.size();
            Btnfooter();
            loadList(0);
            CheckBtnBackGroud(0);


        }catch (JsonParseException e){
            Log.d("Json parse","Failed");
        }
    }

    @Override
    public void onFailedList(){
        Toast.makeText(this, "Failed to retrieve Json",
                Toast.LENGTH_LONG).show();

    }

    private void Btnfooter()
    {
        int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
        val = val==0?0:1;
        noOfBtns=TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;

        LinearLayout ll = (LinearLayout)findViewById(R.id.btnLay);

        btns	=new Button[noOfBtns];

        for(int i=0;i<noOfBtns;i++)
        {
            btns[i]	=	new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+(i+1));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            ll.addView(btns[i], lp);

            final int j = i;
            btns[j].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v)
                {
                    loadList(j);
                    CheckBtnBackGroud(j);
                    currentPage=j;
                }
            });
        }

    }

    private void CheckBtnBackGroud(int index)
    {
        title.setText("Page "+(index+1)+" of "+noOfBtns);
        for(int i=0;i<noOfBtns;i++)
        {
            if(i==index)
            {
//                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_circle));
                btns[index].setBackgroundColor(getResources().getColor(R.color.cyan_700));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
            }
            else
            {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }

    }

    private void loadList(int number)
    {
        ArrayList<Person> sort = new ArrayList<Person>();

        int start = number * NUM_ITEMS_PAGE;
        for(int i=start;i<(start)+NUM_ITEMS_PAGE;i++)
        {
            if(i<data.size())
            {
                sort.add(data.get(i));
            }
            else
            {
                break;
            }
        }
        sd = new PersonAdapter(this,sort);
        listview.setAdapter(sd);
    }
}
