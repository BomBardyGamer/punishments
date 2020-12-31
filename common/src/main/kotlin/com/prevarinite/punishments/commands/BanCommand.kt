package com.prevarinite.punishments.commands

import com.prevarinite.punishments.*
import com.prevarinite.punishments.api.punishment.PunishmentType
import com.prevarinite.punishments.orm.HistoryTable
import com.prevarinite.punishments.orm.PlayerEntity
import com.prevarinite.punishments.orm.PunishmentEntity
import com.prevarinite.punishments.util.*
import org.jetbrains.exposed.sql.SortOrder
import org.koin.core.inject
import java.time.LocalDateTime
import java.time.Duration
import kotlin.time.seconds
import kotlin.time.toJavaDuration

class BanCommand : PunishCommand {

    private val messageManager by inject<MessageManager>()
    private val mojangAPI by inject<MojangAPI>()
    private val playerManager by inject<PlayerManager>()

    override val name = "ban"

    override suspend fun execute(sender: Sender, args: Array<String>) {
        val timeNow: LocalDateTime = LocalDateTime.now()
        val iterator = args.iterator()

        if (!iterator.hasNext()) throw CommandException("Invalid Usage!")
        val playerName = iterator.next()

        val expiryDate = if (iterator.hasNext()) timeNow + iterator.next() else null
        val duration = expiryDate?.let { it.second - timeNow.second }?.seconds?.toJavaDuration()

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

        val punishment = createPunishment(player, executor, timeNow, expiryDate, reason, PunishmentType.BAN)
        messageManager.broadcastMessage(messageManager.getMessage(""))

        playerManager.players[player.uuid]?.kick(substitute(
                if (duration == null) "banned_permanent" else "banned_temporary",
                player,
                executor,
                duration,
                reason,
                punishment
            ).format())
    }

    override fun tabComplete(sender: Sender, args: Array<String>) {
        TODO("Not yet implemented")
    }

    private fun substitute(
        path: String,
        target: PlayerEntity,
        executor: PlayerEntity,
        duration: Duration?,
        reason: String,
        punishment: PunishmentEntity
    ): String = messageManager.getMessage(path).replace(mapOf(
        "header" to lazy { substitute("banned_header", target, executor, duration, reason, punishment) },
        "type" to punishment.type,
        "date" to punishment.time,
        "until" to punishment.until,
        "executor_uuid" to executor.uuid,
        "player" to target.history.orderBy(HistoryTable.time to SortOrder.DESC).limit(1).single().name,
        "executor" to executor.history.orderBy(HistoryTable.time to SortOrder.DESC).limit(1).single().name,
        "duration" to (duration?.formatToHumanReadable() ?: "Permanent"),
        "reason" to reason,
        "appeal_message" to lazy { substitute("banned_appeal", target, executor, duration, reason, punishment) }
    ))
}