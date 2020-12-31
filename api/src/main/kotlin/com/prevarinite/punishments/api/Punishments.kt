package com.prevarinite.punishments.api

import com.prevarinite.punishments.api.punishment.PunishmentManager
import com.prevarinite.punishments.api.user.UserManager

// TODO: Actually make this do something lol
interface Punishments {

    val punishmentManager: PunishmentManager

    val userManager: UserManager
}