package me.martonpito.moviedbapp.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_main.view.*
import me.martonpito.moviedbapp.R
import me.martonpito.moviedbapp.network.model.Movie
import me.martonpito.moviedbapp.utils.loadImage

class MainAdapter(): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val itemList: ArrayList<Movie> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    inner class MainViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        var item: Movie? = null

        fun bind(item: Movie) = with(itemView) {
            this@MainViewHolder.item = item
            tv_title?.text = item.originalTitle
            tv_overview?.text = item.overView
            iv_movie?.loadImage("https://image.tmdb.org/t/p/w500${item.posterPath}")
        }
    }

    fun setList(itemList: List<Movie>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

}