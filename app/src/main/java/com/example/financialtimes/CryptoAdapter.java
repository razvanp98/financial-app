package com.example.financialtimes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.ViewHolderCrypto> {
    private List<Currency> currencies;
    private Context context;

    public CryptoAdapter(List<Currency> currency, Context context){
        this.currencies = currency;
        this.context = context;
    }

    @Override
    public ViewHolderCrypto onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_favourite_crypto, parent, false);
        return new ViewHolderCrypto(view);
    }


    // Implements the ViewHolder needed
    public static class ViewHolderCrypto extends RecyclerView.ViewHolder{
        TextView name, symbol, low, high, latest_price, latest_volume;

        public ViewHolderCrypto(View item_view){
            super(item_view);

            name = item_view.findViewById(R.id.crypto_name);
            symbol = item_view.findViewById(R.id.crypto_symbol);
            low = item_view.findViewById(R.id.low_value);
            high = item_view.findViewById(R.id.high_value);
            latest_price = item_view.findViewById(R.id.latest_price_value);
            latest_volume = item_view.findViewById(R.id.latest_volume_value);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolderCrypto holder, int position){
        holder.name.setText(currencies.get(position).getName());
        holder.symbol.setText(currencies.get(position).getSymbol());
        holder.low.setText(currencies.get(position).getLowest());
        holder.high.setText(currencies.get(position).getHighest());
        holder.latest_price.setText(currencies.get(position).getLatest_price());
        holder.latest_volume.setText(currencies.get(position).getLatest_volume());
    }

    @Override
    public int getItemCount(){
        return currencies.size();
    }
}
