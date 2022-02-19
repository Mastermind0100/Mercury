package com.a_team.mercury;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    EditText searchText;
    ImageButton button;
    List<CardData> all_card_data;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        all_card_data = Hawk.get("all_data");
        Log.d("all_data_search", all_card_data.toString());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.search_recycler_container);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        searchText = view.findViewById(R.id.search_text);
        button = view.findViewById(R.id.search_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTextValue = searchText.getText().toString().toLowerCase(Locale.ROOT);
                List<CardData> search_cards = new ArrayList<CardData>();
                for(int i = 0; i < all_card_data.size(); i++) {
                    if(all_card_data.get(i).getTitle().toLowerCase(Locale.ROOT).contains(searchTextValue)) {
                        search_cards.add(all_card_data.get(i));
                    }
                }
                CardDataAdapter cardDataAdapter = new CardDataAdapter(search_cards, getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(cardDataAdapter);
            }
        });
    }
}