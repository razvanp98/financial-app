package com.example.financialtimes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cryptoFragment extends Fragment {
    private RecyclerView crypto_list;
    private List<Currency> fav_crypto;
    private CryptoAdapter adaptor;
    CryptoInterface crypto_interface;
    RecyclerView.LayoutManager crypto_layout;
    SwipeRefreshLayout refresh_page = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.crypto_fragment, container, false);
        crypto_list = view.findViewById(R.id.crypto_container);
        crypto_layout = new LinearLayoutManager(getContext());
        crypto_list.setLayoutManager(crypto_layout);
        crypto_list.setHasFixedSize(true);

        // Refresh layout implementation
        refresh_page = view.findViewById(R.id.refresh_crypto);
        refresh_page.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Fragment refreshed = new cryptoFragment();
                FragmentManager fc = getFragmentManager();
                FragmentTransaction ft = fc.beginTransaction();
                ft.replace(R.id.frag_container, refreshed).commit();
            }
        });

        fetchCrypto("");
        return view;
    }

    public void fetchCrypto(String key){
        crypto_interface = CryptoClient.getApi().create(CryptoInterface.class);
        Call<List<Currency>> fetch_currency = crypto_interface.cryptoApi(key);

        fetch_currency.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                fav_crypto = response.body();
                adaptor = new CryptoAdapter(fav_crypto, getContext());
                crypto_list.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch currencies. " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
