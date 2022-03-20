package com.a_team.mercury;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardDataAdapter extends RecyclerView.Adapter<CardDataAdapter.ViewHolder>{

    AlertDialog dialog;
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
        int watched = cardData.getWatched();
        if(!url.equals("spotify")){
            Glide.with(holder.itemView).load(url).into(holder.imageView);
        }
        else{
            String spotify_url = "https://i.ibb.co/HDSqkTr/spotify-logo-png-7078.png";
            Glide.with(holder.itemView).load(spotify_url).into(holder.imageView);
        }
        if(watched == 0){
            holder.watchedBorder.setBackgroundColor(ContextCompat.getColor(this.context, R.color.red));
        }
        else if(watched == 1){
            holder.watchedBorder.setBackgroundColor(ContextCompat.getColor(this.context, R.color.green));
        }
        else{
            holder.watchedBorder.setBackgroundColor(ContextCompat.getColor(this.context, R.color.purple_500));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, String.valueOf(cardData.getId()), Toast.LENGTH_SHORT).show();
                if(cardData.getMain_url().startsWith("https://youtu.be")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(cardData.getMain_url()));
                    intent.setPackage("com.google.android.youtube");
                    context.startActivity(intent);
                }
                else{
                    String getMainUrl = cardData.getMain_url();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(getMainUrl));
                    intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://" + context.getPackageName()));
                    context.startActivity(intent);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                Toast.makeText(context, "Wow this was unexpected", Toast.LENGTH_LONG).show();
                openDialog(cardData);
                return true;
            }
        });
    }

    private void openDialog(CardData cardData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.delete_dialog, null);
        builder.setView(view);

        Button deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    deleteItem(cardData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        Button cancelButton = view.findViewById(R.id.cancel_button_dialog);
        if(cardData.getWatched()==0){
            cancelButton.setText("Watched");
        }
        else if(cardData.getWatched()==1){
            cancelButton.setText("To Watch");
        }
        else{
            cancelButton.setText("Cancel");
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateItem(cardData);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private void updateItem(CardData cardData) throws JSONException, IOException {
        List<CardData> cardDataList = Hawk.get("all_data");
        int final_watched = 0;
        for(int i = 0; i < cardDataList.size(); i++) {
            CardData currentCard = cardDataList.get(i);
            if(currentCard.getId() == cardData.getId()){
                if(currentCard.getWatched()==0){
                    currentCard.setWatched(1);
                    final_watched = 1;
                }
                else if(currentCard.getWatched()==1){
                    currentCard.setWatched(0);
                    final_watched = 0;
                }
            }
        }

        String update_request = "https://api.herokuapp.com/api/v1/items/%s";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("watched", final_watched);
        okHttpParser httpParser = new okHttpParser();
        String response = httpParser.update(String.format(update_request,String.valueOf(cardData.getId())), jsonObject.toString());
        Hawk.put("all_data", cardDataList);
        Fragment reloadFragment;
        if(cardData.getType_id().equals("movie")){
            reloadFragment = new MoviesFragment();
        }
        else if(cardData.getType_id().equals("tvshow")){
            reloadFragment = new TvShowsFragment();
        }
        else {
            reloadFragment = new MusicFragment();
        }
        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.main_container, reloadFragment).commit();
    }

    private void deleteItem(CardData cardData) throws IOException {
        List<CardData> cardDataList = Hawk.get("all_data");
        List<CardData> newCardDataList = new ArrayList<CardData>();
        for(int i = 0; i < cardDataList.size(); i++) {
            if(cardDataList.get(i).getId() != cardData.getId()){
                newCardDataList.add(cardDataList.get(i));
            }
        }

        String delete_request = "https://api.herokuapp.com/api/v1/items/%s";
        okHttpParser httpParser = new okHttpParser();
        String response = httpParser.delete(String.format(delete_request,String.valueOf(cardData.getId())));
        Log.d("Delete request", response);
        Hawk.put("all_data", newCardDataList);
        Fragment reloadFragment;
        if(cardData.getType_id().equals("movie")){
            reloadFragment = new MoviesFragment();
        }
        else if(cardData.getType_id().equals("tvshow")){
            reloadFragment = new TvShowsFragment();
        }
        else {
            reloadFragment = new MusicFragment();
        }
        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.main_container, reloadFragment).commit();
    }

    @Override
    public int getItemCount() {
        return cardData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        ImageView watchedBorder;
        TextView textViewName;
        TextView textViewBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.display_image);
            textViewName = itemView.findViewById(R.id.display_text_name);
            watchedBorder = itemView.findViewById(R.id.watched_color);
        }
    }
}
