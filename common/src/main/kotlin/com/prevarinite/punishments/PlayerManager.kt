package com.prevarinite.punishments

import java.util.*

interface PlayerManager {

    val players: Map<UUID, PunishPlayer>
}