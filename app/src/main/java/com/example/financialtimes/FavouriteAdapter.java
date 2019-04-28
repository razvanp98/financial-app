package com.example.financialtimes;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolderFavourite> {

    private List<Companies> companies;
    private Context context;
    private Activity activity;

    SwipeRefreshLayout refresh;

    public FavouriteAdapter(List<Companies> companies, Context context, Activity activity){
        this.companies = companies;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public ViewHolderFavourite onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_favourite_company, parent, false);
        return new ViewHolderFavourite(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderFavourite holder, final int position) {

        //Percentage calculator
        String yesterdayPrice = companies.get(position).getPrice_yesterday();
        String todayPrice = companies.get(position).getPrice_today();

        double pricePercentage = getPercentage(todayPrice, yesterdayPrice);

        if (pricePercentage < 0) {
            holder.arrow.setImageResource(R.drawable.ic_red_down_arrow);
        }else {
            holder.arrow.setImageResource(R.drawable.ic_green_arrow_up);
        }

        // Truncated double for setting precision

        Double initPercentage = new Double(pricePercentage);
        Double truncatedPercentage = BigDecimal.valueOf(initPercentage).setScale(3, RoundingMode.HALF_UP).doubleValue();

        // String builder for percentage
        StringBuilder percentageString = new StringBuilder();
        percentageString.append(String.valueOf(truncatedPercentage)).append(" ").append("%");

        // String builder for Market Cap

        StringBuilder volumeBuilder = new StringBuilder();
        volumeBuilder.append(companies.get(position).getVolume()).append(" ").append("$");

        // Setting all the information in their containers

        holder.name.setText(companies.get(position).getCompany_name());
        holder.symbol.setText(companies.get(position).getCompany_symbol());
        holder.price_today.setText(companies.get(position).getPrice_today() + " " + "$");
        holder.price_yesterday.setText(companies.get(position).getPrice_yesterday() + " " + "$");
        holder.market_cap.setText(volumeBuilder.toString());
        holder.percentage.setText(percentageString);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveFavourite remove_interface = RemoveFavouriteAPI.getAPI().create(RemoveFavourite.class);
                Call<Void> remove_favourite = remove_interface.remove_API(companies.get(position).getCompany_name());

                // on clicking 'delete', make a call to database and set the 'favourite' field to value of '0'
                remove_favourite.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response){
                        Toast.makeText(context, companies.get(position).getCompany_name() + " removed from favourites.", Toast.LENGTH_SHORT).show();
                        companies.remove(position);
                        notifyDataSetChanged(); // remove the card and updates the database on method callback
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, "Unable to remove " + companies.get(position).getCompany_name(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public static class ViewHolderFavourite extends RecyclerView.ViewHolder{

        TextView name, symbol, price_today, price_yesterday, market_cap, percentage;
        ImageView arrow;
        Button delete;

        public ViewHolderFavourite(View itemView) {
            super(itemView);

            // Gets the containers from the layout resource
            name = itemView.findViewById(R.id.fav_name);
            symbol = itemView.findViewById(R.id.fav_symbol);
            price_today = itemView.findViewById(R.id.today_value);
            price_yesterday = itemView.findViewById(R.id.yesterday_value);
            market_cap = itemView.findViewById(R.id.market_cap_value);
            percentage = itemView.findViewById(R.id.percentage);
            arrow = itemView.findViewById(R.id.arrow);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    // Calculates percentage of rising/falling price
    double getPercentage(String todayPrice, String yesterdayPrice){
        double todayPriceDouble = Double.parseDouble(todayPrice);
        double yesterdayPriceDouble = Double.parseDouble(yesterdayPrice);

        double percentage = ((todayPriceDouble / yesterdayPriceDouble) - 1) * 100;

        return percentage;
    }
}
