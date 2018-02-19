package com.villevalta.intentlauncher.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.villevalta.intentlauncher.R
import com.villevalta.intentlauncher.addMenuItem
import com.villevalta.intentlauncher.fragment.FavoritesFragment
import com.villevalta.intentlauncher.fragment.HistoryFragment
import com.villevalta.intentlauncher.fragment.LauncherFragment
import com.villevalta.intentlauncher.model.Favorite
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager.adapter = MainAdapter(supportFragmentManager)
        pager.addOnPageChangeListener(this)
        navigation.setOnNavigationItemSelectedListener(this)
        if(getFavoriteCount() == 0){
            navigation.selectedItemId = R.id.launcher
        }
    }

    private fun getFavoriteCount():Int{
        return Realm.getDefaultInstance().where(Favorite::class.java).findAll().size
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorites -> {
                pager.setCurrentItem(0, true)
                return true
            }
            R.id.launcher -> {
                pager.setCurrentItem(1, true)
                return true
            }
            R.id.history -> {
                pager.setCurrentItem(2, true)
                return true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        addMenuItem(menu, 0, Menu.CATEGORY_SYSTEM, R.drawable.ic_info_outline_primarytextcolor_24dp, getString(R.string.info))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.drawable.ic_info_outline_primarytextcolor_24dp){
            startActivity(Intent(this, InfoActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        navigation.menu.getItem(position).isChecked = true
        supportActionBar?.title = navigation.menu.getItem(position).title
        hideSoftInput()
    }

    private fun hideSoftInput() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    class MainAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return FavoritesFragment()
                2 -> return HistoryFragment()
            }
            return LauncherFragment()
        }

        override fun getCount(): Int = 3
    }

}
