package com.example.camilledahdah.finalandroidproject.screens.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.camilledahdah.finalandroidproject.R;
import com.example.camilledahdah.finalandroidproject.data.local.LocalStorageManager;
import com.example.camilledahdah.finalandroidproject.models.Trip;
import com.example.camilledahdah.finalandroidproject.models.User;
import com.example.camilledahdah.finalandroidproject.screens.authentication.AuthenticationActivity;
import com.example.camilledahdah.finalandroidproject.screens.main.profile.ProfileFragment;
import com.example.camilledahdah.finalandroidproject.screens.main.trips.PostTripFragment;
import com.example.camilledahdah.finalandroidproject.screens.main.trips.SearchTripsFragment;
import com.example.camilledahdah.finalandroidproject.screens.main.trips.TripsListFragment;

import java.util.List;
import android.support.v7.app.AlertDialog;
/**
 * Created by camilledahdah on 4/21/18.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.ProfileFragmentListener,
        PostTripFragment.LocationFragmentListener, TripsListFragment.TripsListFragmentListener, SearchTripsFragment.SearchTripsFragmentListener {

    private LocalStorageManager localStorageManager;
    private TextView nameHeaderTextView, emailHeaderTextView;
    private List<Trip> currentTripsList;

    public void setCurrentTripsList(List<Trip> currentTripsList) {
        this.currentTripsList = currentTripsList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        localStorageManager = LocalStorageManager.getInstance(this);

        NavigationView mNavigationView = findViewById(R.id.nav_view);
        View header = mNavigationView.getHeaderView(0);
        mNavigationView.setItemIconTintList(null);

        nameHeaderTextView = header.findViewById(R.id.name);
        emailHeaderTextView = header.findViewById(R.id.email);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchTrips();
        populateHeaderViews();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_post_trip:
                postTrip();
                break;

            case R.id.nav_logout:
                logout();
                break;

            case R.id.nav_search_trips:
                searchTrips();
                break;

            case R.id.nav_profile:
                showProfileFragment();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void postTrip() {
        //setTitle(getString(R.string.title_events));
        PostTripFragment fragment = PostTripFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(PostTripFragment.TAG).commit();
    }

    private void searchTrips() {
        //setTitle(getString(R.string.title_events));
        SearchTripsFragment fragment = SearchTripsFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(SearchTripsFragment.TAG).commit();
    }

    private void logout() {
        LocalStorageManager.getInstance(this).deleteUser();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProfileFragment() {
        //setTitle(getString(R.string.title_profile));
        ProfileFragment fragment = ProfileFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(ProfileFragment.TAG).commit();

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                int imageResource = android.R.drawable.ic_dialog_alert;
                Drawable image = getResources().getDrawable(imageResource);

                builder.setTitle("Exit").setMessage("want to exit?").setIcon(image).setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog alert = builder.create();
                alert.setCancelable(false);
                alert.show();
            }else{

                super.onBackPressed();
            }
        }
    }

    private void populateHeaderViews() {
        User user = localStorageManager.getUser();
        if (user != null) {
            nameHeaderTextView.setText(user.getFirstName() + " " + user.getLastName());
            emailHeaderTextView.setText(user.getEmail());
        }
    }

    private void gotoAuthenticationScreen() {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onProfileFetchFailure() {
        gotoAuthenticationScreen();
    }

//    @Override
//    public void onRequestCreateNewEvent() {
//        //setTitle(getString(R.string.PostTripFragment));
//        PostTripFragment fragment = PostTripFragment.newInstance();
//        getFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container, fragment)
//                .addToBackStack(PostTripFragment.TAG).commit();
//    }

    @Override
    public void onErrorFetchingTrips() {
        gotoAuthenticationScreen();
    }

    @Override
    public void onNewTripCreatedSuccessfully() {
        //setTitle(getString(R.string.title_events));
        getFragmentManager().popBackStack();
    }

    @Override
    public void onNewTripCreationFailure() {
        gotoAuthenticationScreen();
    }

    @Override
    public void onFoundTrips(List<Trip> tripList){

        setCurrentTripsList(tripList);

        TripsListFragment fragment = TripsListFragment.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(TripsListFragment.TAG).commit();

    }

    @Override
    public List<Trip> getTripsList(){

        return currentTripsList;

    }
}
