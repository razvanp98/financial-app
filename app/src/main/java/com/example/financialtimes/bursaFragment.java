package com.example.financialtimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class bursaFragment extends Fragment {
    Button addStockButton = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bursa_fragment, container, false);
        OpenStockSearch(view);
        return view;
    }

    // Sets Button "View more" click listener => opens the SearchView for querying the SQL DB
    public void OpenStockSearch(View v){
        addStockButton = v.findViewById(R.id.add_stock);
        addStockButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent makeSearchIntent = new Intent(v.getContext(), SearchDatabase.class);
                startActivity(makeSearchIntent); // SearchDatabase is the activity for querying all the companies information
            }
        });
    }
}

