package com.prevarinite.punishments

interface PunishPlayer : Sender {

    fun kick(reason: String)
}