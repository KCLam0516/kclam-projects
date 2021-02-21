package com.example.finalyearproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.R;
import com.example.finalyearproject.model.carListItems;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private ArrayList<carListItems> carListItems;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public CartListAdapter(java.util.ArrayList<carListItems> carListItems, Context context) {
        this.carListItems = carListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_design, parent,false);
        ViewHolder viewHolder= new ViewHolder(view,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final carListItems carListItem=carListItems.get(position);
        holder.carImage.setImageResource(carListItem.getImageResources());
        holder.carName.setText(carListItem.getCarName());
        holder.ownerName.setText(carListItem.getOwnerName());
        holder.price.setText(carListItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return carListItems.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button bAdd;
        ImageView carImage;
        TextView carName,ownerName,price;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            carImage = itemView.findViewById(R.id.carImage);
            carName = itemView.findViewById(R.id.carName);
            ownerName = itemView.findViewById(R.id.ownerName);
            price = itemView.findViewById(R.id.price);
        }
    }
}
