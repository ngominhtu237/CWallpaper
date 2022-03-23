package com.tunm.cwallpaper2.ui.base.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

enum class ViewType(val type: Int) {
    TYPE_LEFT(0), TYPE_RIGHT(1)
}

abstract class BaseAdapter<VB: ViewBinding, T: ListItemAdapter>(
    private var data: List<T>
): RecyclerView.Adapter<BaseViewHolder<VB>>() {

    abstract fun bind(binding: VB, item: T)

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> VB

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<T>) {
        this.data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binder = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, viewType)

        return BaseViewHolder(binder)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bind(holder.binder, data[position])
    }

    override fun getItemCount(): Int = data.size
}