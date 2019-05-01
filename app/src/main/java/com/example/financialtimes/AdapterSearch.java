package com.example.financialtimes;

import android.content.Context;
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

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolderCompany> {

    private List<Companies> companies;
    private Context context;

    public AdapterSearch(List<Companies> companies, Context context){
        this.companies = companies;
        this.context = context;
    }

    @Override
    public ViewHolderCompany onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new ViewHolderCompany(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderCompany holder, final int position) {

        if(companies.get(position).getFavourite().equals("1")){
            holder.add.setEnabled(false);
        }else{
            holder.add.setEnabled(true);
        }

        holder.name.setText(companies.get(position).getCompany_name());
        holder.symbol.setText(companies.get(position).getCompany_symbol());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread addFav = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AddFavouriteInterface favouriteInterface = ApiClientFavourites.getAPI().create(AddFavouriteInterface.class);
                        Call<Void> updateFavourite = favouriteInterface.favourite_API("1", companies.get(position).getCompany_symbol());


                        updateFavourite.enqueue(new Callback<Void>(){
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response){
                                String symbol = companies.get(position).getCompany_symbol();
                                Toast.makeText(context, symbol + " has been added to favourites." , Toast.LENGTH_SHORT).show();
                                holder.add.setEnabled(false);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t){
                                Toast.makeText(context, "Failed to add to favourites + " + t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                addFav.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public static class ViewHolderCompany extends RecyclerView.ViewHolder{

        TextView name, symbol;
        Button add;

        public ViewHolderCompany(View itemView) {
            super(itemView);

            // Gets the containers from the layout resource
            name = itemView.findViewById(R.id.company_name);
            symbol = itemView.findViewById(R.id.company_symbol);
            add = itemView.findViewById(R.id.add_btn);
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
