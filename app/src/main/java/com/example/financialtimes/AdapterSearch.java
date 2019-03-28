package com.example.financialtimes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(ViewHolderCompany holder, int position) {
        holder.name.setText(companies.get(position).getCompany_name());
        holder.symbol.setText(companies.get(position).getCompany_symbol());
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public static class ViewHolderCompany extends RecyclerView.ViewHolder{
        TextView name, symbol;

        public ViewHolderCompany(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.company_name);
            symbol = itemView.findViewById(R.id.company_symbol);
        }
    }
}
