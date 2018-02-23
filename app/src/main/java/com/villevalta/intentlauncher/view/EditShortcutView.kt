package com.villevalta.intentlauncher.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import com.villevalta.intentlauncher.R
import com.villevalta.intentlauncher.actionToSpinnerPosition
import com.villevalta.intentlauncher.databinding.EditorListExtraItemBinding
import com.villevalta.intentlauncher.model.Extra
import com.villevalta.intentlauncher.spinnerPositionToAction
import com.villevalta.intentlauncher.toVisibility
import kotlinx.android.synthetic.main.view_edit_shortcut.view.*

/**
 * Created by villevalta on 12/21/17.
 */

class EditShortcutView : FrameLayout {

    private val extrasAdapter = ExtrasRecyclerAdapter()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.view_edit_shortcut, this)
        spinner_actions.adapter = ArrayAdapter.createFromResource(context, R.array.intent_actions, android.R.layout.simple_list_item_1)
        recycler_extras.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_extras.adapter = extrasAdapter
    }

    fun setShowName(show: Boolean) {
        edit_name.visibility = toVisibility(show)
    }

    var name: String
        get() = edit_name.text.toString()
        set(value) = edit_name.setText(value)

    var uri: String
        get() = edit_uri.text.toString()
        set(value) = edit_uri.setText(value)

    var action: String
        get() = spinnerPositionToAction(spinner_actions.selectedItemPosition)
        set(value) {
            spinner_actions.setSelection(actionToSpinnerPosition(value))
        }
    var extras: ArrayList<Extra>
        get() = extrasAdapter.items
        set(value) {
            extrasAdapter.items = value
            extrasAdapter.notifyDataSetChanged()
        }
}

class ExtrasRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = ArrayList<Extra>()

    companion object {
        const val TYPE_ADD = 0
        const val TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        if (viewType == TYPE_ADD) {
            val vh = AddViewHolder(inflater.inflate(R.layout.editor_list_extra_add, parent, false))
            vh.view.setOnClickListener {
                items.add(Extra.create("", Extra.Type.String, ""))
                notifyItemInserted(items.size)
            }
            return vh
        } else {
            return ItemViewHolder(EditorListExtraItemBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ItemViewHolder) {
            holder.extra = items[position]
            holder.binding.actionRemove.setOnClickListener {
                val posInItems = items.indexOf(holder.extra)
                items.remove(holder.extra)
                notifyItemRemoved(posInItems)
            }
        }
    }

    override fun getItemCount(): Int = items.size + 1 // last item is always "add"

    override fun getItemViewType(position: Int): Int {
        if (position == items.size) return TYPE_ADD
        return TYPE_ITEM
    }

    class AddViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    class ItemViewHolder(val binding: EditorListExtraItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var extra: Extra
            set(value) {
                binding.extra = value
                binding.editKey.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        extra.key = binding.editKey.text.toString()
                    }
                }
                binding.editValue.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        extra.value = binding.editValue.text.toString()
                    }
                }
            }
            get() = binding.extra!!

    }

}
