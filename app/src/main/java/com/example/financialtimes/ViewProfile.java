package com.example.financialtimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfile extends AppCompatActivity {

    String symbol, price;
    TextView symbol_container;

    private RecyclerView profile_list;
    private List<Profile> profile;
    private ViewProfileAdapter adaptor;
    ProfileFetcher profile_fetcher;
    RecyclerView.LayoutManager profile_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);


        Intent req = getIntent();
        symbol = req.getStringExtra("SYMBOL");
        price = req.getStringExtra("CURRENT_PRICE");
        symbol_container = findViewById(R.id.profile_symbol);
        symbol_container.setText(symbol);

        profile_list = findViewById(R.id.recycler_profiles);
        profile_layout = new LinearLayoutManager(this);
        profile_list.setLayoutManager(profile_layout);
        profile_list.setHasFixedSize(true);
        fetchProfile(symbol);
    }

    public void fetchProfile(String key){
        profile_fetcher = ProfileClient.getApi().create(ProfileFetcher.class);
        Call<List<Profile>> fetch_profile = profile_fetcher.profileAPI(key);

        fetch_profile.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                profile = response.body();
                adaptor = new ViewProfileAdapter(profile, ViewProfile.this, price);
                profile_list.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
                Toast.makeText(ViewProfile.this, "Failed to fetch profiles. " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
