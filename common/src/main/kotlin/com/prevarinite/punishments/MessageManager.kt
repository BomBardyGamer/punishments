package com.prevarinite.punishments

import org.koin.dsl.module

interface MessageManager {

    fun getMessage(path: String): String

    fun broadcastMessage(message: String)
}

fun messageManagerModule(manager: () -> MessageManager) = module { single { manager() } }