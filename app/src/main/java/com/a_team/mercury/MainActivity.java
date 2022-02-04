package com.a_team.mercury;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment fragment = new MoviesFragment();
    EditText editText;
    Button submitButton;
    AlertDialog dialog;
    Spinner spinner;
    CardData[] cardDataList;


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
                        openDialog();
                        return false; //this is to keep the current bottom navigation item high-lighted and not the '+' button

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

        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_dropdown_item, R.id.spinner_text_view_1,
                getResources().getStringArray(R.array.types));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Choose the type of entry");

        editText = view.findViewById(R.id.input_url);
        submitButton = view.findViewById(R.id.submit_new_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinnerSelection = spinner.getSelectedItem().toString();
                String inputUrl = editText.getText().toString();
                dialog.dismiss();
                try {
                    parseUserInput(inputUrl, spinnerSelection);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void parseUserInput(String inputUrl, String spinnerSelection) throws IOException, JSONException {
        if(!inputUrl.equals("")){
            getResourceData resourceData = new getResourceData(inputUrl);
            String title = resourceData.responseJSON.getString("title");
            String thumbnail_url = resourceData.responseJSON.getString("thumbnail_url");
            Log.d("thum_url", thumbnail_url);
        }
    }
}