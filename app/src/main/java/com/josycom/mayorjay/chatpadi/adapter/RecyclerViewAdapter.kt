package com.josycom.mayorjay.chatpadi.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.josycom.mayorjay.chatpadi.utils.inflate

abstract class RecyclerViewAdapter<T>(diffUtil: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, ViewHolder<T>>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return getViewHolder(parent.inflate(viewType), this)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getLayoutRes(getItem(position))


    @LayoutRes
    abstract fun getLayoutRes(model: T): Int

    abstract fun getViewHolder(
        view: View,
        recyclerViewAdapter: RecyclerViewAdapter<T>
    ): ViewHolder<T>

}