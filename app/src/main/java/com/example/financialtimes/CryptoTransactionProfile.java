package com.example.financialtimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CryptoTransactionProfile extends AppCompatActivity {

    TextView name_container, symbol_container, last_price_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_transaction_profile);

        Intent intent = getIntent();
        String name = intent.getStringExtra("COIN_NAME");
        String symbol = intent.getStringExtra("COIN_SYMBOL");
        String price = intent.getStringExtra("LAST_PRICE");

        name_container = findViewById(R.id.name_crypto);
        symbol_container = findViewById(R.id.symbol_crypto);
        last_price_container = findViewById(R.id.last_price_crypto);

        name_container.setText(name);
        symbol_container.setText(symbol);
        last_price_container.setText(price + " $");
    }
}
