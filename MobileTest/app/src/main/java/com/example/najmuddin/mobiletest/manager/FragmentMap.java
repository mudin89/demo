package com.example.najmuddin.mobiletest.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.najmuddin.mobiletest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Najmuddin on 01/12/2016.
 */

public class FragmentMap extends Fragment implements OnMapReadyCallback {
    LatLng User = new LatLng(-33.867, 151.206);
    String MapPREFERENCES="MapPREFERENCES",LATITUDE="LATITUDE",LONGTITUDE="LONGTITUDE",NAME="NAME",DETAILS="DETAILS";
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();

        fragment.getMapAsync(this);

        sp=getActivity().getSharedPreferences(MapPREFERENCES, Context.MODE_PRIVATE);
        User=new LatLng(Double.valueOf(sp.getString(LATITUDE,"0")),Double.valueOf(sp.getString(LONGTITUDE,"0")));

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //map is ready
        try{
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(User, 13));

            map.addMarker(new MarkerOptions()
                    .title(sp.getString(NAME,"Doctor"))
                    .snippet(sp.getString(DETAILS,"Location"))
                    .position(User));

        }catch (SecurityException e) {
            Log.d("SecurityException", e.toString());
        }
    }
}