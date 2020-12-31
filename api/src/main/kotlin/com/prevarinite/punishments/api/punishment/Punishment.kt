package com.prevarinite.punishments.api.punishment

import com.prevarinite.punishments.api.user.PunishUser
import java.time.LocalDateTime

interface Punishment {

    val user: PunishUser

    val executor: PunishUser

    val time: LocalDateTime

    val until: LocalDateTime?

    val reason: String

    val type: PunishmentType

    val expired: Boolean
}