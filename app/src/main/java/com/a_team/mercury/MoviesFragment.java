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

import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {

    RecyclerView recyclerView;
    List<CardData> all_card_data;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        all_card_data = Hawk.get("all_data");
        Log.d("all_data_movies", all_card_data.toString());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.movie_recycler_container);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        List<CardData> movie_cards = new ArrayList<CardData>();
        for(int i = 0; i < all_card_data.size(); i++) {
            Log.d("type_ids", all_card_data.get(i).getType_id());
            if(all_card_data.get(i).getType_id().equals("movie")) {
                movie_cards.add(all_card_data.get(i));
            }
        }
        CardDataAdapter cardDataAdapter = new CardDataAdapter(movie_cards, getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardDataAdapter);
        Log.d("itemCount", String.valueOf(cardDataAdapter.getItemCount()));
    }
}