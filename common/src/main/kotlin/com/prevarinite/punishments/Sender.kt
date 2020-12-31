package com.prevarinite.punishments

import java.util.*

interface Sender {

    val uuid: UUID

    val name: String

    fun hasPermission(permission: String): Boolean

    fun sendMessage(message: String)
}