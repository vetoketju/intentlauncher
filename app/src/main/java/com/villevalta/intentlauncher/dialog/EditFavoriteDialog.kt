package com.villevalta.intentlauncher.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.villevalta.intentlauncher.R
import com.villevalta.intentlauncher.model.Favorite
import com.villevalta.intentlauncher.view.EditShortcutView
import io.realm.Realm

/**
 * Created by villevalta on 11/19/17.
 */
class EditFavoriteDialog : DialogFragment() {

    companion object {
        const val KEY_ID = "id"

        fun newInstance(id: Long?): EditFavoriteDialog {
            val dialog = EditFavoriteDialog()
            val bundle = Bundle()
            if (id != null) bundle.putLong(KEY_ID, id)
            dialog.arguments = bundle
            return dialog
        }
    }

    private var fav: Favorite? = null
    private var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getLong(KEY_ID)
        realm = Realm.getDefaultInstance()
        fav = realm?.where(Favorite::class.java)?.equalTo("id", id)?.findFirst()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!).setCancelable(true)
                .setTitle(getString(R.string.edit_favorite))
                .setPositiveButton(getString(R.string.save)) { dialog, _ -> save() }
                .setView(R.layout.shortcut_editor)
                .create()
    }

    override fun onResume() {
        super.onResume()
        val editor = dialog.findViewById<EditShortcutView>(R.id.edit_shortcut)
        editor.action = fav?.action.orEmpty()
        editor.uri = fav?.uri.orEmpty()
        editor.name = fav?.name.orEmpty()
    }

    private fun save() {
        val editor = dialog.findViewById<EditShortcutView>(R.id.edit_shortcut)
        realm?.beginTransaction()
        fav?.name = editor.name
        fav?.uri = editor.uri
        fav?.action = editor.action
        realm?.commitTransaction()
    }

    override fun onDestroy() {
        realm?.close()
        super.onDestroy()
    }

}