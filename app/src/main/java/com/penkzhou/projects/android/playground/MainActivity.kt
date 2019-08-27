package com.penkzhou.projects.android.playground

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.penkzhou.projects.android.playground.adapter.ToolsItemAdapter
import com.penkzhou.projects.android.playground.model.ItemModel
import com.penkzhou.projects.android.playground.other.ToolItemDecoration
import com.penkzhou.projects.android.playground.utils.Utils

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

class MainActivity : AppCompatActivity() {
    private var topImage: ImageView? = null
    private var topDate: TextView? = null
    private val topTitle: TextView? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        topImage = findViewById(R.id.topImage)
        topDate = findViewById(R.id.topDate)
        Glide.with(this).load("https://loremflickr.com/g/1080/720/sea").into(topImage!!)
        val postFormater = SimpleDateFormat("yyyy-MM-dd")
        topDate!!.text = postFormater.format(Date())
        recyclerView = findViewById(R.id.indexList)
        val itemModelList = ArrayList<ItemModel>()
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "exoplayer"))
        itemModelList.add(ItemModel("GooglePlayer", "https://image.flaticon.com/icons/png/64/220/220596.png", "googleplayer"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        itemModelList.add(ItemModel("ExoPlayer", "https://image.flaticon.com/icons/png/64/131/131258.png", "other"))
        val adapter = ToolsItemAdapter(itemModelList)
        recyclerView!!.layoutManager = GridLayoutManager(this, SPAN_SIZE)
        recyclerView!!.addItemDecoration(ToolItemDecoration(Utils.dpToPx(10), SPAN_SIZE))
        recyclerView!!.adapter = adapter
    }

    companion object {

        private val SPAN_SIZE = 3
    }
}
