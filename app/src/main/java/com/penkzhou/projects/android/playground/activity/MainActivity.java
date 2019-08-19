package com.penkzhou.projects.android.playground.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.penkzhou.projects.android.playground.R;
import com.penkzhou.projects.android.playground.adapter.ToolsItemAdapter;
import com.penkzhou.projects.android.playground.model.ItemModel;
import com.penkzhou.projects.android.playground.other.ToolItemDecoration;
import com.penkzhou.projects.android.playground.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView topImage;
    private TextView topDate, topTitle;
    private RecyclerView recyclerView;

    private static final int SPAN_SIZE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        topImage = findViewById(R.id.topImage);
        topDate = findViewById(R.id.topDate);
        Glide.with(this).load("https://loremflickr.com/g/1080/720/sea").into(topImage);
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");
        topDate.setText(postFormater.format(new Date()));
        recyclerView = findViewById(R.id.indexList);
        List<ItemModel> itemModelList = new ArrayList<>();
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "exoplayer"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        itemModelList.add(new ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"));
        ToolsItemAdapter adapter = new ToolsItemAdapter(itemModelList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_SIZE));
        recyclerView.addItemDecoration(new ToolItemDecoration(Utils.dpToPx(10), SPAN_SIZE));
        recyclerView.setAdapter(adapter);
    }
}
