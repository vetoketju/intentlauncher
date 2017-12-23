package com.villevalta.intentlauncher.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import com.villevalta.intentlauncher.R
import com.villevalta.intentlauncher.actionToSpinnerPosition
import com.villevalta.intentlauncher.spinnerPositionToAction
import com.villevalta.intentlauncher.toVisibility
import kotlinx.android.synthetic.main.view_edit_shortcut.view.*

/**
 * Created by villevalta on 12/21/17.
 */

class EditShortcutView : FrameLayout {

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
    }

    fun setShowName(show: Boolean){
        label_name.visibility = toVisibility(show)
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

}
