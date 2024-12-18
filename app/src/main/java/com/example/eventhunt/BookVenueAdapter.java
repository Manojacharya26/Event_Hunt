package com.example.eventhunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookVenueAdapter extends RecyclerView.Adapter<BookVenueViewHolder> {

    Context context;
    List<BookVenueDataClass> dataList;

    public BookVenueAdapter(Context context, List<BookVenueDataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public BookVenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_book_venue, parent, false);
        return new BookVenueViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BookVenueViewHolder holder, int position) {
        holder.recTitle.setText(dataList.get(position).getName());
        holder.recTotal.setText(dataList.get(position).getTotal());
        holder.recLoc.setText(dataList.get(position).getOccasion());
        holder.recDate.setText(dataList.get(position).getDate());
        holder.recTime.setText(dataList.get(position).getTime());
        holder.recEntry.setText(dataList.get(position).getFee());
        holder.recTicket.setText(dataList.get(position).getDuration());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class BookVenueViewHolder extends RecyclerView.ViewHolder{

    TextView recTitle, recTotal, recLoc,recDate,recTime,recEntry,recTicket;

    public BookVenueViewHolder(@NonNull View itemView) {
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
