package com.example.latestnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.latestnews.databinding.NewsLayoutBinding


class Adapter(private val listener:NewsItemClicked) :RecyclerView.Adapter<Adapter.QViewHolder>() {
    inner class QViewHolder(val binding: NewsLayoutBinding) :RecyclerView.ViewHolder(binding.root)

    //callback is for each object just type
    private val diffCallback = object: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.publishedAt == newItem.publishedAt
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    var todo: List<Article>
        get()=differ.currentList
        set(value){differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QViewHolder {
        val view =QViewHolder(NewsLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        //needed to get holder view by itemView then write method:)
        view.itemView.setOnClickListener{
            listener.onItemClicked(todo[view.adapterPosition])
        }
        return view
    }

    override fun getItemCount(): Int {
    return todo.size
    }

    override fun onBindViewHolder(holder: QViewHolder, position: Int) {
        holder.binding.apply {
            val items = todo[position]
            textView.text=items.title
            textView2.text=items.description
            imageView.apply {
                Glide.with(this).load(items.urlToImage).fitCenter().into(imageView)}
        }

    }

}
