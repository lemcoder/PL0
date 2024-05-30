package pl.lemanski.pandaloop.domain.platform.log

import android.util.Log
import kotlin.reflect.KClass

// FIXME remove android dependency
open class Logger private constructor(private val tag: String) {
    companion object {
        fun get(kClass: KClass<*>): Logger {
            return Logger(kClass::simpleName.name)
        }
    }

    fun v(msg: () -> String) {
        Log.v(tag, "V/${msg()}")
    }

    fun d(msg: () -> String) {
        Log.d(tag, "D/${msg()}")
    }

    fun i(msg: () -> String) {
        Log.i(tag, "I/${msg()}")
    }

    fun w(msg: () -> String) {
        Log.w(tag, "W/${msg()}")
    }

    fun e(msg: () -> String) {
        Log.e(tag, "E/${msg()}")
    }
}