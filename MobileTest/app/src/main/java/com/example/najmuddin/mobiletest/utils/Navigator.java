package com.example.najmuddin.mobiletest.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.najmuddin.mobiletest.DetailsActivity;
import com.example.najmuddin.mobiletest.ListActivity;
import com.example.najmuddin.mobiletest.MainActivity;

public class Navigator {

    private final Activity activity;

    public Navigator(Activity activity) {
        this.activity = activity;
    }

    public void toMainActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void toListActivity(String places) {
        Intent intent = new Intent(activity, ListActivity.class);
        intent.putExtra("places",places);
        activity.startActivity(intent);
    }

    public void toDetailsActivity(String idUrl) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra("idurl",idUrl);
        activity.startActivity(intent);
    }
}
