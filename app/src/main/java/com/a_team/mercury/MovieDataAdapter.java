package com.a_team.mercury;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.ViewHolder>{

    MovieData[] movieData;
    Context context;

    public MovieDataAdapter(MovieData[] movieData, FragmentActivity activity) {
        this.movieData = movieData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MovieData movieData = this.movieData[position];
        holder.textViewName.setText(movieData.getName());
        holder.textViewBody.setText(movieData.getBody());
//        holder.imageView.setImageResource(movieData.ge);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, movieData.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewName;
        TextView textViewBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.display_image_movie);
            textViewName = itemView.findViewById(R.id.display_text_movie_name);
            textViewBody = itemView.findViewById(R.id.display_text_movie_body);
        }
    }
}
