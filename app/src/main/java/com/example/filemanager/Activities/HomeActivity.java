package com.example.filemanager.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.filemanager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_home);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.internalStorageFragment,
                R.id.externalStorageFragment)
                .build();

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            navView.setOnNavigationItemSelectedListener(this);
            NavigationUI.setupWithNavController(navView, navController);




    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.e(TAG, "onNavigationItemSelected: ");
        if(item.getItemId()== R.id.externalStorageFragment && InternalStorageFragment.actionMode!=null)
                    InternalStorageFragment.actionMode.finish();

        return true;
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "onBackPressed: ");
        super.onBackPressed();
    }
}
