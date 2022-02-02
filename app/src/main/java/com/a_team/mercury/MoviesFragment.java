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
import android.widget.Toast;

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

        MovieData[] movieData = new MovieData[]{
                new MovieData("Tangled", "youtbe.com", "lfbwfejbwef"),
                new MovieData("Weathering With You", "youtbe.com", "2of2fn2f"),
                new MovieData("Harry Potter", "youtbe.com", "opwfkwopkfewopf"),
                new MovieData("Tangled", "youtbe.com", "lfbwfejbwef"),
                new MovieData("Weathering With You", "youtbe.com", "2of2fn2f"),
                new MovieData("Tangled", "youtbe.com", "lfbwfejbwef"),
                new MovieData("Weathering With You", "youtbe.com", "2of2fn2f"),
        };

        MovieDataAdapter movieDataAdapter = new MovieDataAdapter(movieData, getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieDataAdapter);
        Log.d("itemCount", String.valueOf(movieDataAdapter.getItemCount()));

    }
}