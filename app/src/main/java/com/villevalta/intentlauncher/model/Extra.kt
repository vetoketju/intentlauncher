package com.villevalta.intentlauncher.model

import io.realm.RealmObject

/**
 * Created by villevalta on 2/17/18.
 */
open class Extra(
        var key: String = "",
        private var type: Int = Type.String.ordinal,
        var value: String = ""
) : RealmObject() {

    companion object {
        fun create(key: String, type:Type, value: Any): Extra{
            val extra = Extra()
            extra.key = key
            extra.setType(type)
            extra.value = value.toString()
            return extra
        }
    }

    fun setType(type: Type){
        this.type = type.ordinal
    }

    fun getType():Type{
        return Type.values()[type]
    }

    enum class Type {
        Int,
        Byte,
        Char,
        Long,
        Float,
        Short,
        Double,
        Boolean,
        String
    }
}