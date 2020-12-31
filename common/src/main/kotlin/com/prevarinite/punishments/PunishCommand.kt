package com.prevarinite.punishments

interface PunishCommand : PunishmentsKoinComponent {

    val name: String

    suspend fun execute(sender: Sender, args: Array<String>)

    fun tabComplete(sender: Sender, args: Array<String>)
}