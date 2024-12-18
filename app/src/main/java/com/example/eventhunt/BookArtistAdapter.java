package com.example.eventhunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookArtistAdapter extends RecyclerView.Adapter<BookArtistViewHolder> {

    Context context;
    List<BookArtistDataClass> dataList2;

    public BookArtistAdapter(Context context, List<BookArtistDataClass> dataList) {
        this.context = context;
        this.dataList2 = dataList;
    }

    @NonNull
    @Override
    public BookArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_book_artist, parent, false);
        return new BookArtistViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BookArtistViewHolder holder, int position) {
        holder.recTitle.setText(dataList2.get(position).getName());
        holder.recTotal.setText(dataList2.get(position).getTotal());
        holder.recLoc.setText(dataList2.get(position).getLoc());
        holder.recDate.setText(dataList2.get(position).getDate());
        holder.recTime.setText(dataList2.get(position).getTime());
        holder.recEntry.setText(dataList2.get(position).getFee());
        holder.recTicket.setText(dataList2.get(position).getDuration());


    }

    @Override
    public int getItemCount() {
        return dataList2.size();
    }
}

class BookArtistViewHolder extends RecyclerView.ViewHolder{

    TextView recTitle, recTotal, recLoc,recDate,recTime,recEntry,recTicket;

    public BookArtistViewHolder(@NonNull View itemView) {
        super(itemView);

        recTicket = itemView.findViewById(R.id.recNoTicket);
        recTotal = itemView.findViewById(R.id.recTotAmount);
        recLoc = itemView.findViewById(R.id.recLoc);
        recTitle = itemView.findViewById(R.id.recTitle);
        recTime = itemView.findViewById(R.id.recTime);
        recEntry = itemView.findViewById(R.id.recEntry);
        recDate = itemView.findViewById(R.id.recDate);

    }
}
