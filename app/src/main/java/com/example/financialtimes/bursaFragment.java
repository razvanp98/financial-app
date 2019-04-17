package com.example.financialtimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class bursaFragment extends Fragment {
    Button addStockButton = null;
    private RecyclerView company_list;
    private RecyclerView.LayoutManager favourite_layout;
    private List<Companies> fav_companies;
    private FavouriteAdapter adaptor;
    private ShowFavouritesInterface favourite_api;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bursa_fragment, container, false);
        company_list = getActivity().findViewById(R.id.fav_container);
        favourite_layout = new LinearLayoutManager(getContext());
        company_list.setLayoutManager(favourite_layout);
        company_list.setHasFixedSize(true);
        openStockSearch(view);
        fetchFavourites("");
        return view;
    }

    // Sets Button "View more" click listener => opens the SearchView for querying the SQL DB
    public void openStockSearch(View v){
        addStockButton = v.findViewById(R.id.add_stock);
        addStockButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent makeSearchIntent = new Intent(v.getContext(), SearchDatabase.class);
                startActivity(makeSearchIntent); // SearchDatabase is the activity for querying all the companies information
            }
        });
    }

    public void fetchFavourites(String key){
        favourite_api = ShowFavouritesClient.getAPI().create(ShowFavouritesInterface.class);
        final Call<List<Companies>> show_favourites = favourite_api.showFavourites(key);

        show_favourites.enqueue(new Callback<List<Companies>>() {
            @Override
            public void onResponse(Call<List<Companies>> call, Response<List<Companies>> response) {
                fav_companies = response.body();
                adaptor = new FavouriteAdapter(fav_companies, getContext());
                company_list.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Companies>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch data! " + t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }
}

