package com.san.englishbender

import com.san.englishbender.core.navigation.Navigator
import com.san.englishbender.data.local.DatabaseDriverFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import platform.UIKit.UIDevice
import kotlinx.datetime.LocalDateTime
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext
import platform.Foundation.NSUUID
import kotlin.system.getTimeMillis

// Note: no need to define CommonParcelize here (bc its @OptionalExpectation)
actual interface CommonParcelable  // not used on iOS

// Note: no need to define CommonTypeParceler<T,P : CommonParceler<in T>> here (bc its @OptionalExpectation)
actual interface CommonParceler<T> // not used on iOS
actual object LocalDateTimeParceler : CommonParceler<LocalDateTime> // not used on iOS


//class IOSPlatform: Platform {
//    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
//}

actual class Platform actual constructor() {
    actual val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun platformModule() = module {
    single { Navigator() }
    single { DatabaseDriverFactory() }
}

actual val mainDispatcher: CoroutineDispatcher = NsQueueDispatcher(dispatch_get_main_queue())
actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
actual val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

internal class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
}

actual fun randomUUID(): String = NSUUID().UUIDString()

actual fun getSystemTimeInMillis() = getTimeMillis()