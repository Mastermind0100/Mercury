package com.a_team.mercury;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment fragment = new MoviesFragment();
    EditText editText;
    Button submitButton;
    String inputUrl = "";
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
        bottomNavigationView.setSelectedItemId(R.id.movies_list);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_new_element:
//                        Toast.makeText(MainActivity.this, "This is for adding an element", Toast.LENGTH_SHORT).show();
                        openDialog();
                        break;

                    case R.id.movies_list:
                        fragment = new MoviesFragment();
                        break;

                    case R.id.tvshows_list:
                        fragment = new TvShowsFragment();
                        break;

                    case R.id.music_list:
                        fragment = new MusicFragment();
                        break;

                    case R.id.search_list:
                        fragment = new SearchFragment();
                        break;
                }
                assert fragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();

                return true;
            }
        });
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.input_dialog, null);
        builder.setView(view);

        editText = view.findViewById(R.id.input_url);
        submitButton = view.findViewById(R.id.submit_new_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = editText.getText().toString();
                if(!inputText.equals("")){
                    inputUrl = inputText;
                }
                dialog.dismiss();
                Log.d("url_data", inputUrl);
                try {
                    getResourceData resourceData = new getResourceData(inputUrl);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}