package com.villevalta.intentlauncher

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.villevalta.intentlauncher.model.Extra
import com.villevalta.intentlauncher.model.History
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by villevalta on 11/12/17.
 */
class App : Application() {

    override fun onCreate() {
        instance = this
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build())
        super.onCreate()
    }

    fun tryLaunch(action: String, uri: String, extras: List<Extra>): Pair<Boolean, String?> {
        var success = false
        var error: String? = null

        try {
            val i = Intent(action, Uri.parse(uri))

            extras.filter { it.getType() == Extra.Type.String }.forEach { i.putExtra(it.key, it.value) }

            startActivity(i)
            success = true
        } catch (e: Exception) {
            error = e.message
        }

        val realm = Realm.getDefaultInstance()
        val history = History()
        history.successful = success
        history.action = action
        history.uri = uri
        history.extras.addAll(extras)
        realm.beginTransaction()
        realm.copyToRealm(history)
        realm.commitTransaction()


        realm.close()
        return Pair(success, error)
    }

    companion object {
        lateinit var instance: App
    }
}