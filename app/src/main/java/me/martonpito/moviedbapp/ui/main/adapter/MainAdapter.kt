package me.martonpito.moviedbapp.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class MainAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList: List<Object>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}