package com.example.camilledahdah.finalandroidproject;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;


public class MainActivity extends AppCompatActivity {

    private SimpleAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.search)
    public TextInputLayout search;

    @BindView(R.id.search_text)
    public TextInputEditText searchText;

    List<String> countries = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        ButterKnife.bind(this);

        Locale[] locale = Locale.getAvailableLocales();

        String country;

        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // call the adapter with argument list of items and context.
        adapter = new SimpleAdapter(countries, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.INVISIBLE);

        addTextListener();

    }

    @OnClick(R.id.search)
    public void search() {
        recyclerView.setVisibility(View.VISIBLE);

    }

    @OnFocusChange(R.id.search_text)
    public void onFocusChange(boolean hasFocus) {

        if (hasFocus) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

    public void addTextListener(){

        searchText.addTextChangedListener(new TextWatcher() {

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

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new SimpleAdapter(filteredList, MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    public class SimpleAdapter extends
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

                    Toast.makeText(mcontext, list_item.get(position),
                            Toast.LENGTH_LONG).show();

                    searchText.setText(list_item.get(position));
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
