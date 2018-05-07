package com.example.najmuddin.mobiletest.manager;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyLog;
import com.example.najmuddin.mobiletest.manager.AppController;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Najmuddin on 30/11/2016.
 */

public class JsonRetriever {

    public interface TaskDone
    {
        void onSuccess(String json);
        void onFailed();
    }

    public interface TaskList
    {
        void onSuccessList(String json);
        void onFailedList();
    }

    public interface TaskDetails
    {
        void onSuccessDetails(String json);
        void onFailedDetails();
    }

    TaskDone TaskDoneCallback;
    TaskDetails TaskDetailsCallback;
    TaskList TaskListCallback;

    int typeDone=1,typeList=2,typeDetails=3;

    public JsonRetriever(TaskDone callBack){
        TaskDoneCallback=callBack;

    }

    public JsonRetriever(TaskDetails callBack){
        TaskDetailsCallback=callBack;

    }

    public JsonRetriever(TaskList callBack){
        TaskListCallback=callBack;

    }


    public void requestJson(String url, final int type) {

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if(type==typeDone){
                    onSuccess(response);
                }else if(type==typeList){
                    onSuccessList(response);
                }else if (type==typeDetails){
                    onSuccessDetails(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(type==typeDone){
                    onFailed();
                }else if(type==typeList){
                    onFailedList();
                }else if (type==typeDetails){
                    onFailedDetails();
                }
                VolleyLog.d("Volley Version", "Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "Volley Version");
    }

    public void onSuccess(String Json){
        TaskDoneCallback.onSuccess(Json);

    }

    public void onFailed(){
        TaskDoneCallback.onFailed();

    }

    public void onSuccessList(String Json){
        TaskListCallback.onSuccessList(Json);

    }

    public void onFailedList(){
        TaskListCallback.onFailedList();

    }

    public void onSuccessDetails(String Json){
        TaskDetailsCallback.onSuccessDetails(Json);

    }

    public void onFailedDetails(){
        TaskDetailsCallback.onFailedDetails();

    }
}
