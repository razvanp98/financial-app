package com.example.financialtimes;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.InvalidPropertiesFormatException;

public class bursaFragment extends Fragment {
    FloatingActionButton addStockButton = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bursa_fragment, container, false);
        OpenStockSearch(view);
        return view;
    }

    // Sets FAB listener => opens the SearchView for querying the SQL DB
    public void OpenStockSearch(View v){
        addStockButton = v.findViewById(R.id.add_stock);
        addStockButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent makeSearchIntent = new Intent(v.getContext(), SearchDatabase.class);
                startActivity(makeSearchIntent);
            }
        });
    }


}

