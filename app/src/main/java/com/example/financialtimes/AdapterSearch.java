package com.example.financialtimes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolderCompany> {

    private List<Companies> companies;
    private Context context;

    public AdapterSearch(List<Companies> companies, Context context) {
        this.companies = companies;
        this.context = context;
    }

    @Override
    public ViewHolderCompany onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new ViewHolderCompany(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderCompany holder, int position) {
        holder.name.setText(companies.get(position).getCompany_name());
        holder.symbol.setText(companies.get(position).getCompany_symbol());
        holder.today.setText(companies.get(position).getPrice_today());
        holder.yesterday.setText(companies.get(position).getPrice_yesterday());
        holder.volume.setText(companies.get(position).getVolume());
        holder.addStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendToRoot = new Intent(context, bursaFragment.class);
                sendToRoot.putExtra("Clicked", "Clicked on: " + holder.name.getText().toString());
                context.startActivity(sendToRoot);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public static class ViewHolderCompany extends RecyclerView.ViewHolder{

        TextView name, symbol, today, yesterday, volume;
        Button addStockBtn;

        public ViewHolderCompany(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.company_name);
            symbol = itemView.findViewById(R.id.company_symbol);
            today = itemView.findViewById(R.id.price_today_value);
            yesterday = itemView.findViewById(R.id.price_yesterday_value);
            volume = itemView.findViewById(R.id.volume_value);
            addStockBtn = itemView.findViewById(R.id.add_stock);
        }
    }
}
