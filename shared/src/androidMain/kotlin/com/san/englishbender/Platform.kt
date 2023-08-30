package com.san.englishbender

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.san.englishbender.core.navigation.Navigator
import com.san.englishbender.data.local.DatabaseDriverFactory
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import org.koin.dsl.module
import java.util.UUID

actual typealias CommonParcelize = Parcelize
actual typealias CommonParcelable = Parcelable

actual typealias CommonParceler<T> = Parceler<T>
actual typealias CommonTypeParceler<T,P> = TypeParceler<T, P>
actual object LocalDateTimeParceler : Parceler<LocalDateTime> {
    override fun create(parcel: Parcel): LocalDateTime {
        val date = parcel.readString()
        return date?.toLocalDateTime()
            ?: LocalDateTime(0, 0, 0, 0, 0)
    }

    override fun LocalDateTime.write(parcel: Parcel, flags: Int) {
        parcel.writeString(this.toString())
    }
}

//class AndroidPlatform : Platform {
//    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
//}

actual class Platform actual constructor() {
    actual val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = Platform()

actual val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
actual val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
actual val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

actual fun randomUUID() = UUID.randomUUID().toString()

actual fun getSystemTimeInMillis() = System.currentTimeMillis()

actual fun platformModule() = module {
    single { Navigator() }
    single { DatabaseDriverFactory(get()) }
}

actual class Strings(
    private val context: Context
) {
    actual fun get(id: StringResource, args: List<Any>): String {
        return if (args.isEmpty()) {
            StringDesc.Resource(id).toString(context)
        } else {
            id.format(*args.toTypedArray()).toString(context)
        }
    }
}