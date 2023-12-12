package com.example.foodfinder_02.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodfinder_02.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LocalesAdapter extends RecyclerView.Adapter<LocalesAdapter.LocalesViewHolder> {

    private List<locales> localList;

    public LocalesAdapter(List<locales> localList) {
        this.localList = localList;
    }

    @NonNull
    @Override
    public LocalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locales, parent, false);
        return new LocalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalesViewHolder holder, int position) {
        locales local = localList.get(position);
        holder.bind(local);
    }

    @Override
    public int getItemCount() {
        return localList.size();
    }
    public void setLocalList(List<locales> localList) {
        this.localList = localList;
        notifyDataSetChanged();
    }

    public static class LocalesViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreTextView;
        private TextView descripcionTextView;

        public LocalesViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreLocal);
            descripcionTextView = itemView.findViewById(R.id.descripcionLocal);
        }

        public void bind(locales local) {
            nombreTextView.setText(locales.getNombre());
            descripcionTextView.setText(locales.getDescripcion());
        }
    }
}
