package com.example.financialtimes;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileAdapter extends RecyclerView.Adapter<ViewProfileAdapter.ViewHolderProfile> {
    private List<Profile> profiles;
    private Context context;
    private String last_price;

    public ViewProfileAdapter(List<Profile> profile, Context context, String price){
        this.profiles = profile;
        this.context = context;
        this.last_price = price;
    }

    @Override
    public ViewHolderProfile onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_cardview, parent, false);
        return new ViewHolderProfile(view);
    }


    // Implements the ViewHolder needed
    public static class ViewHolderProfile extends RecyclerView.ViewHolder{
        TextView id, name, amount_invested, profit_share, actual_profit_share, worth_coins, coin_amount, tip, remove;

        public ViewHolderProfile(View item_view){
            super(item_view);

            name = item_view.findViewById(R.id.name);
            amount_invested = item_view.findViewById(R.id.amount_value);
            profit_share = item_view.findViewById(R.id.share_value);
            actual_profit_share = item_view.findViewById(R.id.actual_profit_value);
            worth_coins = item_view.findViewById(R.id.today_worth_value);
            coin_amount = item_view.findViewById(R.id.coins_owned_prof_val);
            tip = item_view.findViewById(R.id.tip);
            remove = item_view.findViewById(R.id.remove_btn_profile);
            id = item_view.findViewById(R.id.order);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolderProfile holder, final int position){

        holder.id.setText("#" + profiles.get(position).getId());
        holder.name.setText(profiles.get(position).getName());
        holder.amount_invested.setText(profiles.get(position).getInvested() + " $");
        holder.profit_share.setText(profiles.get(position).getProfit() + " %");
        holder.coin_amount.setText(profiles.get(position).getCoins() + " units");

        // Call functions below for calculating the actual profit share and today's worth

        String today = String.valueOf(getTodayWorth(profiles.get(position).getCoins()));
        String actual_profit = getActualProfit(profiles.get(position).getCoins(), profiles.get(position).getInvested());

        holder.worth_coins.setText(today + " $");
        holder.actual_profit_share.setText(actual_profit + " %");
        holder.remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RemoveProfile remove = RemoveProfileClient.getAPI().create(RemoveProfile.class);
                Call<Void> remove_profile = remove.removeProfileApi(profiles.get(position).getId());
                remove_profile.enqueue(new Callback<Void>(){
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(context, "Removed profile " + holder.name.getText(), Toast.LENGTH_SHORT).show();
                        profiles.remove(position);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, "Failed to remove profile " + holder.name.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        String tip = getTips(actual_profit, profiles.get(position).getProfit());
        holder.tip.setText(tip);

        if (tip.equals("You should sell right away.")){
            holder.tip.setTextColor(Color.GREEN);
        }else if (tip.equals("You should not sell yet.")){
            holder.tip.setTextColor(Color.RED);
        }else{
            holder.tip.setTextColor(Color.YELLOW);
        }
}

    @Override
    public int getItemCount(){
        return profiles.size();
    }

    // Functions for calculating actual profit share, today's worth and changing tips

    public String getTodayWorth(String coins){
        if (coins == null){
            return "Not set.";
        }else {
            Double coins_db = Double.parseDouble(coins);
            Double price_db = Double.parseDouble(this.last_price);
            Double result = coins_db * price_db;
            return String.valueOf(result);
        }
    }

    public String getActualProfit(String coins, String cash){
        if (coins == null){
            return "Not set";
        }else{
            Double cash_db = Double.parseDouble(cash);
            Double today_value = Double.parseDouble(this.getTodayWorth(coins));
            Double profit_percentage = ((today_value / cash_db) - 1) * 100;
            return String.valueOf(profit_percentage);
        }
    }

    public String getTips(String actual_profit, String share_profit){
        Double act_prof_db = Double.parseDouble(actual_profit);
        Double share_prof_db = Double.parseDouble(share_profit);

        if (act_prof_db > share_prof_db){
            return "You should sell right away.";
        }else if (act_prof_db > share_prof_db + 2 || act_prof_db > share_prof_db + 1 || act_prof_db > share_prof_db + 7){
            return "Profit is not too high, maybe wait a bit.";
        }else{
            return "You should not sell yet.";
        }
    }
}
