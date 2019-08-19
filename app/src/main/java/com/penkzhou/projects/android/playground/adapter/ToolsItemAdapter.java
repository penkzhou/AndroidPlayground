package com.penkzhou.projects.android.playground.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.penkzhou.projects.android.playground.activity.ExoPlayerActivity;
import com.penkzhou.projects.android.playground.R;
import com.penkzhou.projects.android.playground.model.ItemModel;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

public class ToolsItemAdapter extends RecyclerView.Adapter<ToolsItemAdapter.ItemViewHolder> {

    private List<ItemModel> itemModelList;

    public ToolsItemAdapter(List<ItemModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(itemModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemModelList == null ? 0 : itemModelList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemIcon;
        private TextView itemTitle;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == NO_POSITION) {
                        return;
                    }
                    if (position < 0 || position >= itemModelList.size()) {
                        return;
                    }
                    ItemModel itemModel = itemModelList.get(position);
                    if ("exoplayer".equals(itemModel.getPath())) {
                        Intent intent = new Intent(view.getContext(), ExoPlayerActivity.class);
                        view.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(itemView.getContext(), itemModel.getPath(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            itemIcon = itemView.findViewById(R.id.item_icon);
            itemTitle = itemView.findViewById(R.id.item_name);
        }

        public void onBind(ItemModel itemModel) {
            Glide.with(itemView.getContext()).load(itemModel.getIcon()).into(itemIcon);
            itemTitle.setText(itemModel.getName());
        }
    }
}
