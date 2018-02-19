package com.villevalta.intentlauncher

import android.content.Intent
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.v7.widget.ActionMenuView
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.villevalta.intentlauncher.fragment.FavoritesFragment
import com.villevalta.intentlauncher.fragment.HistoryFragment
import com.villevalta.intentlauncher.model.Extra
import com.villevalta.intentlauncher.model.Favorite
import com.villevalta.intentlauncher.model.History
import com.villevalta.intentlauncher.view.ListItemDivider
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by villevalta on 11/19/17.
 */

fun toVisibility(isVisible: Boolean): Int = if (isVisible) View.VISIBLE else View.GONE

@BindingAdapter("time", "format", requireAll = true)
fun setTime(tv: TextView, time: Long, format: String) {
    tv.text = SimpleDateFormat(format).format(Date(time))
}

@BindingAdapter("favorite")
fun setupActionMenuView(actions: ActionMenuView, item: Favorite) {
    actions.menu.clear()
    actions.menu.add(0, FavoritesFragment.FavoriteViewHolder.ID_UNFAVORITE, 0, actions.context.getString(R.string.unfavorite))
    actions.menu.add(0, FavoritesFragment.FavoriteViewHolder.ID_EDIT, 0, actions.context.getString(R.string.edit))
}

@BindingAdapter("history")
fun setupActionMenuView(actions: ActionMenuView, item: History) {
    actions.menu.clear()
    actions.menu.add(0, HistoryFragment.HistoryViewHolder.ID_FAVORITE, 0, actions.context.getString(R.string.favorite))
    actions.menu.add(0, HistoryFragment.HistoryViewHolder.ID_REMOVE_FROM_HISTORY, 0, actions.context.getString(R.string.remove))
}

@BindingAdapter("divider")
fun setRecyclerDivider(recycler: RecyclerView, drawable: Drawable) {
    recycler.addItemDecoration(ListItemDivider(drawable))
}

@BindingAdapter("withExtra")
fun setupEditTextWithExtra(editText: EditText, extra: Extra){
    when(extra.getType()){
        Extra.Type.String -> editText.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        Extra.Type.Double, Extra.Type.Float -> editText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
        else -> editText.inputType = InputType.TYPE_NUMBER_FLAG_SIGNED
    }

}

fun String.ifEmpty(default: String): String = if (equals("")) default else this

fun convertToFavorite(item: History): Favorite {
    val fav = Favorite()
    fav.action = item.action
    fav.uri = item.uri
    fav.extras = item.extras
    return fav
}

fun actionToSpinnerPosition(action: String): Int {
    when (action) {
        Intent.ACTION_VIEW -> return 0
        Intent.ACTION_MAIN -> return 1
        Intent.ACTION_ATTACH_DATA -> return 2
        Intent.ACTION_EDIT -> return 3
        Intent.ACTION_PICK -> return 4
        Intent.ACTION_CHOOSER -> return 5
        Intent.ACTION_GET_CONTENT -> return 6
        Intent.ACTION_DIAL -> return 7
        Intent.ACTION_CALL -> return 8
        Intent.ACTION_SEND -> return 9
        Intent.ACTION_SENDTO -> return 10
        Intent.ACTION_ANSWER -> return 11
        Intent.ACTION_INSERT -> return 12
        Intent.ACTION_DELETE -> return 13
        Intent.ACTION_RUN -> return 14
        Intent.ACTION_SYNC -> return 15
        Intent.ACTION_PICK_ACTIVITY -> return 16
        Intent.ACTION_SEARCH -> return 17
        Intent.ACTION_WEB_SEARCH -> return 18
        Intent.ACTION_FACTORY_TEST -> return 19
        else -> return 0
    }
}

fun spinnerPositionToAction(pos: Int): String {
    when (pos) {
        0 -> return Intent.ACTION_VIEW
        1 -> return Intent.ACTION_MAIN
        2 -> return Intent.ACTION_ATTACH_DATA
        3 -> return Intent.ACTION_EDIT
        4 -> return Intent.ACTION_PICK
        5 -> return Intent.ACTION_CHOOSER
        6 -> return Intent.ACTION_GET_CONTENT
        7 -> return Intent.ACTION_DIAL
        8 -> return Intent.ACTION_CALL
        9 -> return Intent.ACTION_SEND
        10 -> return Intent.ACTION_SENDTO
        11 -> return Intent.ACTION_ANSWER
        12 -> return Intent.ACTION_INSERT
        13 -> return Intent.ACTION_DELETE
        14 -> return Intent.ACTION_RUN
        15 -> return Intent.ACTION_SYNC
        16 -> return Intent.ACTION_PICK_ACTIVITY
        17 -> return Intent.ACTION_SEARCH
        18 -> return Intent.ACTION_WEB_SEARCH
        19 -> return Intent.ACTION_FACTORY_TEST

        else -> return Intent.ACTION_VIEW
    }
}

fun addMenuItem(menu: Menu?, groupId: Int, order: Int, idAndIconRes: Int, title: String) {
    menu?.add(groupId, idAndIconRes, order, title)?.setIcon(idAndIconRes)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
}