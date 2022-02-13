package com.a_team.mercury;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

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
        if(type_id.equals("movie")||type_id.equals("tvshow")){
            Glide.with(holder.itemView).load(url).into(holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, String.valueOf(cardData.getId()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(cardData.getMain_url()));
                intent.setPackage("com.google.android.youtube");
                context.startActivity(intent);
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
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private void deleteItem(CardData cardData) throws IOException {
        List<CardData> cardDataList = Hawk.get("all_data");
        List<CardData> newCardDataList = new ArrayList<CardData>();
        for(int i = 0; i < cardDataList.size(); i++) {
            if(cardDataList.get(i).getId() != cardData.getId()){
                newCardDataList.add(cardDataList.get(i));
            }
        }

        String delete_request = "https://mercury-list-api.herokuapp.com/api/v1/items/%s";
        okHttpParser httpParser = new okHttpParser();
        String response = httpParser.delete(String.format(delete_request,String.valueOf(cardData.getId())));
        Log.d("Delete request", response);
        Hawk.put("all_data", newCardDataList);
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
        }
    }
}
