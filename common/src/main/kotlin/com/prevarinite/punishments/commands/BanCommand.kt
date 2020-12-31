package com.prevarinite.punishments.commands

import com.prevarinite.punishments.*
import com.prevarinite.punishments.api.punishment.PunishmentType
import com.prevarinite.punishments.orm.PlayerEntity
import com.prevarinite.punishments.orm.PunishmentEntity
import com.prevarinite.punishments.util.*
import org.koin.core.inject
import java.time.LocalDateTime
import kotlin.time.Duration
import kotlin.time.seconds

class BanCommand : PunishCommand {

    private val messageManager by inject<MessageManager>()
    private val mojangAPI by inject<MojangAPI>()
    //private val playerManager by inject<PlayerManager>()

    override val name = "ban"

    override suspend fun execute(sender: Sender, args: Array<String>) {
        val timeNow: LocalDateTime = LocalDateTime.now()
        val iterator = args.iterator()

        if (!iterator.hasNext()) throw CommandException("Invalid Usage!")
        val playerName = iterator.next()

        val expiryDate = if (iterator.hasNext()) timeNow + iterator.next() else null
        val duration = expiryDate?.let { it.second - timeNow.second }?.seconds

        val reason = iterator.asSequence().joinToString(" ").takeIf { it.isNotEmpty() }
            ?: messageManager.getMessage("ban_reason_default")

        if (!sender.hasPermission("punishments.ban")) throw CommandException("No permission!")

        var player = playerName.toDatabasePlayer()
        val executor = sender.uuid.toDatabasePlayer()

        if (player == null) {
            val uuid = mojangAPI.retrieveUUID(playerName).id.uuid
            val target = createPlayer(uuid)
            createHistory(target, playerName, "0.0.0.0")
            player = target
        }

        createPunishment(player, executor, reason, PunishmentType.BAN)
        messageManager.broadcastMessage(messageManager.getMessage(""))
    }

    override fun tabComplete(sender: Sender, args: Array<String>) {
        TODO("Not yet implemented")
    }

    suspend fun substitute(
        path: String,
        target: PlayerEntity,
        executor: PlayerEntity,
        duration: Duration?,
        reason: String,
        punishment: PunishmentEntity
    ): String = ""
}