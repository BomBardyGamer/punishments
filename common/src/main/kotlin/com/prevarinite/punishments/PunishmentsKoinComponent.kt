package com.prevarinite.punishments

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.Koin
import org.koin.core.KoinComponent
import org.koin.core.get

interface PunishmentsKoinComponent : KoinComponent {

    companion object {
        lateinit var koin: Koin
    }

    override fun getKoin() = koin

    suspend fun <T> transaction(statement: Transaction.() -> T) = transaction(get<Database>(), statement)
}