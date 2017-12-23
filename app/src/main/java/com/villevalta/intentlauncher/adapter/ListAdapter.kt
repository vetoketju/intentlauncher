package com.villevalta.intentlauncher.adapter

import com.villevalta.intentlauncher.fragment.viewholder.AViewHolder
import io.realm.OrderedRealmCollection
import io.realm.RealmModel
import io.realm.RealmRecyclerViewAdapter

/**
 * Created by villevalta on 12/3/17.
 */
abstract class ListAdapter<T : RealmModel?, VM : AViewHolder<T>?>(val dataz: OrderedRealmCollection<T>?) : RealmRecyclerViewAdapter<T, VM>(dataz, true, true) {

    override fun onBindViewHolder(holder: VM, position: Int) {
        holder?.bind(getItem(position)!!)
    }

}