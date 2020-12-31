package com.prevarinite.punishments.concurrent

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

object PunishmentsScope : CoroutineScope {

    override val coroutineContext = Executors.newSingleThreadExecutor {
        Thread(it, "Punishments-Database-Dispatcher").apply { isDaemon = true }
    }.asCoroutineDispatcher()
}