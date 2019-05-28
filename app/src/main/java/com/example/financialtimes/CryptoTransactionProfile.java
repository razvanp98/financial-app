package com.example.financialtimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoTransactionProfile extends AppCompatActivity {
    TextView name_container, symbol_container, last_price_container;
    TextView profile_name_val, share_val, amount_val, coins_val;
    String profile_name, coin_symbol;
    Double profit_share, amount, coins;
    Button create_btn;
    AddProfile profile_interface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_transaction_profile);

        create_btn = findViewById(R.id.create_profile_btn);

        Intent intent = getIntent();
        String name = intent.getStringExtra("COIN_NAME");
        String symbol = intent.getStringExtra("COIN_SYMBOL");
        String price = intent.getStringExtra("LAST_PRICE");

        name_container = findViewById(R.id.name_crypto);
        symbol_container = findViewById(R.id.symbol_crypto);
        last_price_container = findViewById(R.id.last_price_crypto);
        profile_name_val = findViewById(R.id.profile_name);
        share_val = findViewById(R.id.profit_share_val);
        amount_val = findViewById(R.id.amount_invest_val);
        coins_val = findViewById(R.id.coins_owned_val);

        name_container.setText(name);
        symbol_container.setText(symbol);
        last_price_container.setText(price + " $");

        create_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v){
                profile_name = profile_name_val.getText().toString();
                coin_symbol = symbol_container.getText().toString();
                profit_share = Double.parseDouble(share_val.getText().toString());
                amount = Double.parseDouble(amount_val.getText().toString());
                coins = Double.parseDouble(coins_val.getText().toString());

                profile_interface = AddProfileClient.getAPI().create(AddProfile.class);
                final Call<Void> add = profile_interface.addProfileApi(profile_name, coin_symbol, profit_share, amount, coins);

                add.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(v.getContext(),  "Profile created on " + coin_symbol, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(v.getContext(),  "Failed to create profile. \n " + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
