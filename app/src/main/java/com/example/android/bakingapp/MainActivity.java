package com.example.android.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.android.bakingapp.adapter.RecipeAdapter;
import com.example.android.bakingapp.databinding.ActivityMainBinding;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.BakingInterface;
import com.example.android.bakingapp.utilities.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;

/**
 * The MainActivity displays the list of recipes
 */
public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler,
        ConnectivityReceiver.ConnectivityReceiverListener {

    /** Member variable for RecipeAdapter */
    private RecipeAdapter mRecipeAdapter;

    /** Member variable for the list of recipes */
    private List<Recipe> mRecipeList;

    /** This field is used for data binding **/
    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Set up Timber
        Timber.plant(new Timber.DebugTree());

        // A LinearLayoutManager is responsible for measuring and positioning item views within a
        // RecyclerView into a linear list.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // Set the layout manager to the RecyclerView
        mMainBinding.rv.setLayoutManager(layoutManager);
        // Use this setting to improve performance if you know that changes in content do not
        // change the child layout size in the RecyclerView
        mMainBinding.rv.setHasFixedSize(true);

        // Create an empty ArrayList
        mRecipeList = new ArrayList<>();

        // The RecipeAdapter is responsible for displaying each recipe in the list.
        mRecipeAdapter = new RecipeAdapter(mRecipeList, this);
        // Set adapter to the RecyclerView
        mMainBinding.rv.setAdapter(mRecipeAdapter);

        callRecipeResponse();

        // Check internet connection
        checkConnection();

        checkConnectionStateMonitor();
    }

    private void callRecipeResponse() {
        Retrofit retrofit = RetrofitClient.getClient();
        BakingInterface bakingInterface = retrofit.create(BakingInterface.class);

        Call<List<Recipe>> callRecipeList = bakingInterface.getRecipes();
        callRecipeList.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipeList = response.body();
                mRecipeAdapter.addAll(recipeList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.e("onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(Recipe recipe) {
        // Wrap the parcelable into a bundle
        Bundle b = new Bundle();
        b.putParcelable(EXTRA_RECIPE, recipe);

        // Create the Intent the will start the DetailActivity
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        // Pass the bundle through Intent
        intent.putExtra(EXTRA_RECIPE, b);
        // Once the Intent has been created, start the DetailActivity
        startActivity(intent);
    }

    /**
     * Use BroadcastReceiver when checking the network connectivity status
     */
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    /**
     * Use NetworkCallback because the ability for a backgrounded application to receive network
     * connection state changes, android.net.conn.CONNECTIVITY_CHANGE, is deprecated for apps
     * targeting Android N or higher.
     *
     * Reference: @see "https://stackoverflow.com/questions/36421930/connectivitymanager-connectivity
     * -action-deprecated#36447866"
     */
    private void checkConnectionStateMonitor() {
        ConnectionStateMonitor connectionStateMonitor = new ConnectionStateMonitor();
        connectionStateMonitor.enable(this);
    }

    /**
     * Shows the network status in Snackbar
     * @param isConnected True if connected to the network
     */
    private void showSnack(boolean isConnected) {
        String message;
        if (isConnected) {
            message = "Connected to the Internet";
        } else {
            message = "No Internet Connection!";
        }

        Snackbar snackbar = Snackbar.make(mMainBinding.rv, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register connection status listener
        MyApp.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in the network connection.
     * Show snackbar message.
     * @param isConnected True if connected to the network
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
