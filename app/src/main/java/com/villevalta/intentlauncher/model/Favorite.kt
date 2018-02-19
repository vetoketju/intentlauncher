package com.villevalta.intentlauncher.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by villevalta on 11/12/17.
 */
open class Favorite(
        @PrimaryKey var id: Long = System.currentTimeMillis(),
        var name: String = "",
        var action: String = "",
        var uri: String = "",
        var extras: RealmList<Extra> = RealmList()
) : RealmObject()