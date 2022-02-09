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

import com.bumptech.glide.Glide;

import java.util.List;

public class CardDataAdapter extends RecyclerView.Adapter<CardDataAdapter.ViewHolder>{

    List<CardData> cardData;
    Context context;

    public CardDataAdapter(List<CardData> cardData, FragmentActivity activity) {
        this.cardData = cardData;
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
        final CardData cardData = this.cardData.get(position);
        holder.textViewName.setText(cardData.getTitle());
        String type_id = cardData.getType_id();
        String url = cardData.getThumbnail_url();
        if(type_id.equals("movie")||type_id.equals("tvshow")){
            Glide.with(holder.itemView).load(url).into(holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, String.valueOf(cardData.getId()), Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, "Wow this was unexpected", Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewName;
        TextView textViewBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.display_image);
            textViewName = itemView.findViewById(R.id.display_text_name);
            textViewBody = itemView.findViewById(R.id.display_text_body);
        }
    }
}
