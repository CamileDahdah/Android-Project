package com.example.camilledahdah.finalandroidproject;

/**
 * Created by camilledahdah on 4/17/18.
 */


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camilledahdah.finalandroidproject.API.authentication.AuthenticationApiManager;
import com.example.camilledahdah.finalandroidproject.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class RegisterFragment extends BaseFragment {

    public static final String TAG = RegisterFragment.class.getSimpleName();

    private SimpleAdapter adapter;

    private AuthenticationApiManager authenticationApiManager;

    private RegisterFragmentListener listener;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.first_name_holder)
    TextInputLayout firstNameHolder;

    @BindView(R.id.first_name)
    EditText firstNameEditText;

    @BindView(R.id.last_name_holder)
    TextInputLayout lastNameHolder;

    @BindView(R.id.last_name)
    EditText lastNameText;

    @BindView(R.id.phone_number)
    EditText phoneNumberText;

    @BindView(R.id.phone_number_holder)
    TextInputLayout phoneNumberHolder;

    @BindView(R.id.city)
    EditText cityText;

    @BindView(R.id.city_holder)
    TextInputLayout cityHolder;

    @BindView(R.id.confirm_password)
    EditText confirmPasswordText;

    @BindView(R.id.confirm_password_placeholder)
    TextInputLayout confirmPasswordHolder;

    @BindView(R.id.country_holder)
    TextInputLayout countryHolder;

    @BindView(R.id.country)
    EditText countryText;

    @BindView(R.id.email_placeholder)
    TextInputLayout emailHolder;

    @BindView(R.id.email)
    EditText emailEditText;

    @BindView(R.id.password_placeholder)
    TextInputLayout passwordHolder;

    @BindView(R.id.password)
    EditText passwordEditText;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    List<String> countries = new ArrayList();


    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationApiManager = AuthenticationApiManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup, container, false);
        ButterKnife.bind(this, view);


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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // call the adapter with argument list of items and context.
        adapter = new SimpleAdapter(countries, getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.INVISIBLE);

        addTextListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof RegisterFragmentListener) {
            listener = (RegisterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RegisterFragmentListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick(R.id.sign_up_button)
    public void attemptRegister() {

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordText.getText().toString().trim();
        String country = countryText.getText().toString().trim();
        String city = cityText.getText().toString().trim();
        String phoneNumber = phoneNumberText.getText().toString().trim();

        boolean flag = true, passwordFlag = true;

        if (TextUtils.isEmpty(firstName)) {
            firstNameHolder.setError("first name field is required");
            flag = false;
        } else {
            firstNameHolder.setError(null);
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameHolder.setError("last name field is required");
            flag = false;
        } else {
            lastNameHolder.setError(null);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordHolder.setError("confirm password field is required");
            flag = false;
        } else {
            confirmPasswordHolder.setError(null);
        }

        if (TextUtils.isEmpty(city)) {
            cityHolder.setError("city field is required");
            flag = false;
        } else {
            cityHolder.setError(null);
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberHolder.setError("phone number field is required");
            flag = false;
        } else {
            phoneNumberHolder.setError(null);
        }

        if (TextUtils.isEmpty(country)) {
            countryHolder.setError("country field is required");
            flag = false;
        } else {
            countryHolder.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            emailHolder.setError("email field is required");
            flag = false;
        } else {
            emailHolder.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordHolder.setError("password field is required");
            flag = false;
            passwordFlag = false;
        } else {
            passwordHolder.setError(null);
        }

        if(passwordFlag && !password.equals(confirmPassword)){
            flag = false;
            confirmPasswordHolder.setError("passwords don't match");
        }

        if (flag) {
            showProgressBar();
            User registerUser = new User(firstName, lastName, email, password, phoneNumber, city, country);
            authenticationApiManager
                    .register(registerUser)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            hideProgressBar();
                            if (response.isSuccessful()) {
                                listener.onRegisterSuccess();
                            } else {
                                listener.onRegisterFailure();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            hideProgressBar();
                            listener.onRegisterFailure();
                            showToastMessage(t.getMessage());
                        }
                    });
        }
    }

    @OnClick(R.id.country_holder)
    public void search() {
        recyclerView.setVisibility(View.VISIBLE);

    }

    @OnFocusChange(R.id.country)
    public void onFocusChange(boolean hasFocus) {

        if (hasFocus) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

    public void addTextListener(){

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

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new SimpleAdapter(filteredList, getActivity());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    public interface RegisterFragmentListener {

        void onRegisterSuccess();

        void onRegisterFailure();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
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
