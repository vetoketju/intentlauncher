package com.villevalta.intentlauncher.fragment.viewholder

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * Created by villevalta on 12/3/17.
 */
abstract class AViewHolder<T>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T)
}