package com.prevarinite.punishments.api.user

import java.time.LocalDateTime

interface PunishHistory {

    val name: String

    val ip: String

    val time: LocalDateTime
}