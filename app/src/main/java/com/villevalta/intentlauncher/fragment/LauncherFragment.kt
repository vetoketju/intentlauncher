package com.villevalta.intentlauncher.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.villevalta.intentlauncher.App
import com.villevalta.intentlauncher.R
import kotlinx.android.synthetic.main.fragment_launcher.*
import kotlinx.android.synthetic.main.shortcut_editor.*

/**
 * Created by villevalta on 11/12/17.
 */

class LauncherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onResume() {
        super.onResume()
        edit_shortcut.setShowName(false)
        action_launch.setOnClickListener {
            val result = App.instance.tryLaunch(edit_shortcut.action, edit_shortcut.uri)
            if(!result.first){
                Toast.makeText(context, result.second ?: context?.getString(R.string.launch_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

}
