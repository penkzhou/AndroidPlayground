package com.penkzhou.projects.android.playground.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.bumptech.glide.Glide
import com.penkzhou.projects.android.playground.R
import com.penkzhou.projects.android.playground.activity.ExoPlayer2Activity
import com.penkzhou.projects.android.playground.activity.ExoPlayerActivity
import com.penkzhou.projects.android.playground.model.ItemModel

class ToolsItemAdapter(private val itemModelList: List<ItemModel>?) : RecyclerView.Adapter<ToolsItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(itemModelList!![position])
    }

    override fun getItemCount(): Int {
        return itemModelList?.size ?: 0
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemIcon: ImageView
        private val itemTitle: TextView

        init {
            itemView.setOnClickListener(View.OnClickListener { view ->
                val position = adapterPosition
                if (position == NO_POSITION) {
                    return@OnClickListener
                }
                if (position < 0 || position >= itemModelList!!.size) {
                    return@OnClickListener
                }
                val itemModel = itemModelList[position]
                if ("exoplayer" == itemModel.path) {
                    val intent = Intent(view.context, ExoPlayerActivity::class.java)
                    view.context.startActivity(intent)
                } else if ("googleplayer" == itemModel.path) {
                    val intent = Intent(view.context, ExoPlayer2Activity::class.java)
                    view.context.startActivity(intent)
                } else {
                    Toast.makeText(itemView.context, itemModel.path, Toast.LENGTH_SHORT).show()
                }
            })
            itemIcon = itemView.findViewById(R.id.item_icon)
            itemTitle = itemView.findViewById(R.id.item_name)
        }

        fun onBind(itemModel: ItemModel) {
            Glide.with(itemView.context).load(itemModel.icon).into(itemIcon)
            itemTitle.text = itemModel.name
        }
    }
}
