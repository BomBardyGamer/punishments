package com.prevarinite.punishments.user

import com.prevarinite.punishments.api.user.PunishUser
import com.prevarinite.punishments.api.user.UserManager
import com.prevarinite.punishments.util.toDatabasePlayer
import java.util.*

class UserManagerImpl(
    override val users: Collection<PunishUser>
) : UserManager {

    override suspend fun retrieve(uuid: UUID) = uuid.toDatabasePlayer()

    override suspend fun retrieve(name: String) = name.toDatabasePlayer()
}