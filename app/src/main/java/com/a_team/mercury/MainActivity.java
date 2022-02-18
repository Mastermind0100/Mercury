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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment fragment = new MoviesFragment();
    EditText editText;
    EditText titleText;
    Button submitButton;
    AlertDialog dialog;
    Spinner spinner;
    List<CardData> cardDataList = new ArrayList<CardData>();
    String post_request_url = "https://mercury-list-api.herokuapp.com/api/v1/items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Bottom Navigation Handler Code Starts here
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
        // Bottom Navigation Handler Code ends here
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

        titleText = view.findViewById(R.id.input_title);
        editText = view.findViewById(R.id.input_url);
        submitButton = view.findViewById(R.id.submit_new_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinnerSelection = spinner.getSelectedItem().toString();
                String inputUrl = editText.getText().toString();
                dialog.dismiss();
                try {
                    parseUserInput(titleText.getText().toString(), inputUrl, spinnerSelection);
                } catch (IOException | JSONException e) {
                    Toast.makeText(MainActivity.this, "Something went wrong. Contact Admin", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void parseUserInput(String inputTitleText, String inputUrl, String spinnerSelection) throws IOException, JSONException {
        if(!inputUrl.equals("")){
            String thumbnail_url;
            if(inputUrl.startsWith("https://youtu.be")){
                getResourceData resourceData = new getResourceData(inputUrl);
                thumbnail_url = resourceData.responseJSON.getString("thumbnail_url");
            }
            else{
                thumbnail_url = "spotify";
            }
            String title = inputTitleText;
            CardData cardData = new CardData(20022020, title, inputUrl, thumbnail_url, spinnerSelection.toLowerCase(Locale.ROOT));
            cardDataList.add(cardData);
            updateServerData(cardData);
            Log.d("thumb_url", thumbnail_url);
            Log.d("spinner_value", spinnerSelection);
        }
    }

    private void updateServerData(CardData cardData) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", cardData.getTitle());
        jsonObject.put("url", cardData.getMain_url());
        jsonObject.put("user_id", cardData.getType_id());
        okHttpParser httpParser = new okHttpParser();
        String response = httpParser.post(post_request_url, jsonObject.toString());
        JSONObject response_object = new JSONObject(response);
        JSONObject response_data_object = response_object.getJSONObject("data");
        int id = response_data_object.getInt("id");
        Log.d("get_response", response);
        cardData.setId(id);
        List<CardData> all_data_list = Hawk.get("all_data");
        all_data_list.add(0, cardData);
        Hawk.put("all_data", all_data_list);
    }
}