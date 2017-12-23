package com.villevalta.intentlauncher.fragment

import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.villevalta.intentlauncher.App
import com.villevalta.intentlauncher.adapter.ListAdapter
import com.villevalta.intentlauncher.databinding.ListitemFavoriteBinding
import com.villevalta.intentlauncher.dialog.EditFavoriteDialog
import com.villevalta.intentlauncher.fragment.viewholder.AViewHolder
import com.villevalta.intentlauncher.model.Favorite
import io.realm.OrderedRealmCollection

/**
 * Created by villevalta on 11/19/17.
 */

class FavoritesFragment : ListFragment<Favorite, FavoritesFragment.FavoriteViewHolder>() {

    override fun getAdapter(): ListAdapter<Favorite, FavoriteViewHolder> {
        return Adapter(realm?.where(Favorite::class.java)?.findAll())
    }

    private class Adapter(data: OrderedRealmCollection<Favorite>?) : ListAdapter<Favorite, FavoriteViewHolder>(data) {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FavoriteViewHolder {
            return FavoriteViewHolder(ListitemFavoriteBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
        }

    }

    class FavoriteViewHolder(private val binding: ListitemFavoriteBinding) : AViewHolder<Favorite>(binding) {

        companion object {
            const val ID_UNFAVORITE = 0
            const val ID_EDIT = 1
        }

        init {
            binding.actionOpenMenu.setOnClickListener({
                binding.actions.showOverflowMenu()
            })
        }

        override fun bind(item: Favorite) {
            binding.item = item
            binding.actions.setOnMenuItemClickListener { menuitem: MenuItem? ->
                when (menuitem?.itemId) {
                    ID_UNFAVORITE -> {
                        val realm = item.realm
                        realm.beginTransaction()
                        item.deleteFromRealm()
                        realm?.commitTransaction()
                        return@setOnMenuItemClickListener true
                    }
                    ID_EDIT -> {
                        EditFavoriteDialog.newInstance(item.id).show((binding.root.context as AppCompatActivity).supportFragmentManager, "editor")
                        return@setOnMenuItemClickListener true
                    }
                }
                return@setOnMenuItemClickListener false
            }
            binding.root.setOnClickListener {
                val result = App.instance.tryLaunch(item.action, item.uri)
                if(!result.first){
                    Toast.makeText(binding.root.context, result.second ?: "Launch unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
