package com.example.eventhunt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistViewHolder> {

    private Context context;
    private List<ArtistDataClass> dataList2;

    public ArtistAdapter(Context context, List<ArtistDataClass> dataList) {
        this.context = context;
        this.dataList2 = dataList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Glide.with(context).load(dataList2.get(position).getDataImage()).into(holder.recImage);
        holder.recTitle.setText(dataList2.get(position).getDataTitle());
        holder.recDesc.setText(dataList2.get(position).getDataDesc());
        holder.recEntry.setText(dataList2.get(position).getDataEntry());


        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailArtist.class);
                intent.putExtra("Image", dataList2.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Description", dataList2.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Title", dataList2.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Key",dataList2.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Fees for a hour", dataList2.get(holder.getAdapterPosition()).getDataEntry());


                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList2.size();
    }

}

class ArtistViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recTitle, recDesc,recEntry;
    CardView recCard;

    public ArtistViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);

        recTitle = itemView.findViewById(R.id.recTitle);
        recEntry = itemView.findViewById(R.id.recEntry);


    }
}
