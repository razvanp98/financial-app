package com.example.financialtimes;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchDatabase extends AppCompatActivity {

    private RecyclerView companyList;
    private RecyclerView.LayoutManager companyLayout;
    private List<Companies> companies;
    private AdapterSearch adaptor;
    private Api_Interface apiInterface;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_database);
        progressBar = findViewById(R.id.progress_bar);
        companyList = findViewById(R.id.company_list);
        companyLayout = new LinearLayoutManager(this);
        companyList.setLayoutManager(companyLayout);
        companyList.setHasFixedSize(true);

        fetchData("");
    }

    // Invokes the ApiClient interface, which makes the call to the database and creates the List of Companies objects
    public void fetchData(String key){
        apiInterface = ApiClient.getAPI().create(Api_Interface.class);
        Call<List<Companies>> callToDatabase = apiInterface.stocks_API(key);


        callToDatabase.enqueue(new Callback<List<Companies>>() {
            @Override
            public void onResponse(Call<List<Companies>> call, Response<List<Companies>> response) {
                progressBar.setVisibility(View.GONE);
                companies = response.body();
                adaptor = new AdapterSearch(companies, SearchDatabase.this);
                // Settting the adapter to AdapterSearch for showing the results in the ListView
                companyList.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Companies>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SearchDatabase.this, "Could not fetch data matching your input \n" + t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

    // Search bar implementation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView  = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                fetchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                fetchData(newText);
                return false;
            }
        });
        return true;
    }
}
