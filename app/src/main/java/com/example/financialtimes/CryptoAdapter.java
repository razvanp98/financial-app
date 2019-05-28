package com.example.financialtimes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        TextView name, symbol, low, high, latest_price, latest_volume, profile_no;
        ImageView add_transaction;

        public ViewHolderCrypto(View item_view){
            super(item_view);

            name = item_view.findViewById(R.id.crypto_name);
            symbol = item_view.findViewById(R.id.crypto_symbol);
            low = item_view.findViewById(R.id.low_value);
            high = item_view.findViewById(R.id.high_value);
            latest_price = item_view.findViewById(R.id.latest_price_value);
            latest_volume = item_view.findViewById(R.id.latest_volume_value);
            add_transaction = item_view.findViewById(R.id.add_transaction);
            profile_no = item_view.findViewById(R.id.profile_number);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolderCrypto holder, final int position){
        holder.name.setText(currencies.get(position).getName());
        holder.symbol.setText(currencies.get(position).getSymbol());
        holder.low.setText(currencies.get(position).getLowest() + " $");
        holder.high.setText(currencies.get(position).getHighest() + " $");
        holder.latest_price.setText(currencies.get(position).getLatest_price() + " $");
        holder.latest_volume.setText(currencies.get(position).getLatest_volume() + " $");
        int no = currencies.get(position).getCount_no();
        String click_prompt = " Click to see all profiles";
        if (no == 0) {
            holder.profile_no.setText("No profiles are active for " + holder.name.getText() + "." + click_prompt);
        }else if(no == 1){
            holder.profile_no.setText("1 profile is active for " + holder.name.getText() + "." + click_prompt);
        }else{
            holder.profile_no.setText(no + " profiles are active for " + holder.name.getText() + "." + click_prompt);
        }

        // set click listener for transaction profile

        holder.add_transaction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent profile_activity = new Intent(context, CryptoTransactionProfile.class);
                profile_activity.putExtra("COIN_NAME", currencies.get(position).getName());
                profile_activity.putExtra("COIN_SYMBOL", currencies.get(position).getSymbol());
                profile_activity.putExtra("LAST_PRICE", currencies.get(position).getLatest_price());
                context.startActivity(profile_activity);
            }
        });

        holder.profile_no.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent view_profiles = new Intent(context, ViewProfile.class);
                view_profiles.putExtra("CURRENT_PRICE", currencies.get(position).getLatest_price());
                view_profiles.putExtra("SYMBOL", currencies.get(position).getSymbol());
                context.startActivity(view_profiles);
            }
        });
    }

    @Override
    public int getItemCount(){
        return currencies.size();
    }
}
