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

public class MoviesFragment extends Fragment {

    RecyclerView recyclerView;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.movie_recycler_container);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        CardData[] cardData = new CardData[]{
                new CardData("Tangled", "youtbe.com"),
                new CardData("Weathering With You", "youtbe.com"),
                new CardData("Harry Potter", "youtbe.com"),
                new CardData("Tangled", "youtbe.com"),
                new CardData("Weathering With You", "youtbe.com"),
                new CardData("Tangled", "youtbe.com"),
                new CardData("Weathering With You", "youtbe.com"),
        };

        CardDataAdapter cardDataAdapter = new CardDataAdapter(cardData, getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardDataAdapter);
        Log.d("itemCount", String.valueOf(cardDataAdapter.getItemCount()));

    }
}