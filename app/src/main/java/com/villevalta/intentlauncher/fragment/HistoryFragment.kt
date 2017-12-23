package com.villevalta.intentlauncher.fragment

import android.view.*
import android.widget.Toast
import com.villevalta.intentlauncher.App
import com.villevalta.intentlauncher.R
import com.villevalta.intentlauncher.adapter.ListAdapter
import com.villevalta.intentlauncher.addMenuItem
import com.villevalta.intentlauncher.convertToFavorite
import com.villevalta.intentlauncher.databinding.ListitemHistoryBinding
import com.villevalta.intentlauncher.fragment.viewholder.AViewHolder
import com.villevalta.intentlauncher.model.History
import io.realm.OrderedRealmCollection
import io.realm.Sort

/**
 * Created by villevalta on 11/19/17.
 */

class HistoryFragment : ListFragment<History, HistoryFragment.HistoryViewHolder>() {

    override fun getAdapter(): ListAdapter<History, HistoryViewHolder> {
        return Adapter(realm?.where(History::class.java)?.findAll()?.sort("id", Sort.DESCENDING))
    }

    private class Adapter(data: OrderedRealmCollection<History>?) : ListAdapter<History, HistoryViewHolder>(data) {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HistoryViewHolder {
            return HistoryViewHolder(ListitemHistoryBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
        }
    }

    class HistoryViewHolder(private val binding: ListitemHistoryBinding) : AViewHolder<History>(binding) {

        companion object {
            const val ID_FAVORITE = 0
            const val ID_REMOVE_FROM_HISTORY = 1
        }

        init {
            binding.actionOpenMenu.setOnClickListener({
                binding.actions.showOverflowMenu()
            })
        }

        override fun bind(item: History) {
            binding.item = item
            binding.actions.setOnMenuItemClickListener { menuitem: MenuItem? ->
                when (menuitem?.itemId) {
                    ID_FAVORITE -> {
                        val realm = item.realm
                        realm.beginTransaction()
                        val fav = convertToFavorite(item)
                        realm.copyToRealm(fav)
                        realm.commitTransaction()
                        return@setOnMenuItemClickListener true
                    }
                    ID_REMOVE_FROM_HISTORY -> {
                        val realm = item.realm
                        realm.beginTransaction()
                        item.deleteFromRealm()
                        realm.commitTransaction()
                        return@setOnMenuItemClickListener true
                    }
                }
                return@setOnMenuItemClickListener false
            }
            binding.root.setOnClickListener {
                val result = App.instance.tryLaunch(item.action, item.uri)
                if (!result.first) {
                    Toast.makeText(binding.root.context, result.second ?: "Launch unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        if (menu?.findItem(R.drawable.ic_delete_forever_primarytextcolor_24dp) == null) {
            addMenuItem(menu, id, Menu.CATEGORY_SECONDARY, R.drawable.ic_delete_forever_primarytextcolor_24dp, "Clear all")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.drawable.ic_delete_forever_primarytextcolor_24dp) {
            realm?.beginTransaction()
            realm?.where(History::class.java)?.findAll()?.deleteAllFromRealm()
            realm?.commitTransaction()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(isVisible)
    }

    override fun onPause() {
        super.onPause()
        setHasOptionsMenu(isVisible)
    }

}
