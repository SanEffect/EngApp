package com.san.englishbender.data

import co.touchlab.stately.concurrency.Synchronizable
import co.touchlab.stately.concurrency.synchronize

class ConcurrentHashMap<K, V> : Synchronizable() {

    private val map = mutableMapOf<K, V>()

    val values = map.values

    fun put(key: K, value: V) {
        synchronize { map[key] = value }
    }

    fun get(key: K): V? = synchronize { map[key] }

    fun remove(key: K): V? = synchronize { map.remove(key) }

    fun containsKey(key: K): Boolean = synchronize { map.containsKey(key) }

    fun containsValue(value: V): Boolean = synchronize { map.containsValue(value) }

    fun isEmpty(): Boolean = synchronize { map.isEmpty() }

    fun isNotEmpty(): Boolean = synchronize { map.isNotEmpty() }

    fun clear() = synchronize { map.clear() }

    fun size(): Int = synchronize { map.size }
}