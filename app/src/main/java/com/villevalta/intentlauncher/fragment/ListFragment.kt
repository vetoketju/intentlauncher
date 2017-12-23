package com.villevalta.intentlauncher.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villevalta.intentlauncher.R
import com.villevalta.intentlauncher.adapter.ListAdapter
import com.villevalta.intentlauncher.databinding.FragmentListBinding
import com.villevalta.intentlauncher.fragment.viewholder.AViewHolder
import com.villevalta.intentlauncher.view.ListItemDivider
import io.realm.Realm
import io.realm.RealmModel
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by villevalta on 11/12/17.
 */

abstract class ListFragment<T : RealmModel?, VM : AViewHolder<T>?> : Fragment() {

    var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            FragmentListBinding.inflate(layoutInflater,container,false).root

    override fun onResume() {
        super.onResume()
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = getAdapter()
    }

    abstract fun getAdapter(): ListAdapter<T, VM>

    override fun onDestroy() {
        realm?.close()
        super.onDestroy()
    }

}