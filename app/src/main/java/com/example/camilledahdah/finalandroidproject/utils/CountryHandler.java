package com.example.camilledahdah.finalandroidproject.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camilledahdah.finalandroidproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by camilledahdah on 4/19/18.
 */

public class CountryHandler {

    Locale[] locale = Locale.getAvailableLocales();
    private SimpleAdapter adapter;
    String country;
    List<String> countries = new ArrayList();
    RecyclerView recyclerView;
    TextView countryText;
    Context context;

    public CountryHandler(RecyclerView recyclerView, TextView countryText, Context context){

        this.recyclerView = recyclerView;
        this.countryText = countryText;
        this.context = context;

    }

    public void addCountryListener() {
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // call the adapter with argument list of items and context.
        adapter = new SimpleAdapter(countries, context);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.INVISIBLE);

        addTextListener();
    }

    private void addTextListener(){

        countryText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                recyclerView.setVisibility(View.VISIBLE);

                query = query.toString().toLowerCase();

                final List<String> filteredList = new ArrayList<>();

                for (int i = 0; i < countries.size(); i++) {

                    final String text = countries.get(i).toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(countries.get(i));
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                adapter = new SimpleAdapter(filteredList, context);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    class SimpleAdapter extends
            RecyclerView.Adapter<SimpleAdapter.MyViewHolder> {

        private List<String> list_item ;
        public Context mcontext;



        public SimpleAdapter(List<String> list, Context context) {

            list_item = list;
            mcontext = context;
        }

        // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
        @Override
        public SimpleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            // create a layout
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item, null);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        // Called by RecyclerView to display the data at the specified position.
        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, final int position ) {


            viewHolder.country_name.setText(list_item.get(position));
            viewHolder.country_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(mcontext, list_item.get(position),
                       //     Toast.LENGTH_LONG).show();

                    countryText.setText(list_item.get(position));
                    recyclerView.setVisibility(View.INVISIBLE);
                    //searchText.setFocusable(false);

                }
            });

        }

        // initializes textview in this class
        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView country_name;

            public MyViewHolder(View itemLayoutView) {
                super(itemLayoutView);

                country_name = itemLayoutView.findViewById(R.id.country_name);

            }
        }

        //Returns the total number of items in the data set hold by the adapter.
        @Override
        public int getItemCount() {
            return list_item.size();
        }

    }
}
