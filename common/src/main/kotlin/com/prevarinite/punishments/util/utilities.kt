package com.prevarinite.punishments.util

import net.md_5.bungee.api.ChatColor
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit.*
import java.util.regex.Pattern

private val durations = mapOf(
    "year" to 1000L * 60 * 60 * 24 * 365,
    "month" to 1000L * 60 * 60 * 24 * 30,
    "week" to 1000L * 60 * 60 * 24 * 7,
    "day" to 1000L * 60 * 60 * 24,
    "hour" to 1000L * 60 * 60,
    "minute" to 1000L * 60,
    "second" to 1000L
)

operator fun LocalDateTime.plus(duration: String): LocalDateTime {
    if (!duration.matches("((\\d+)\\w*)".toRegex())) throw IllegalArgumentException("Duration did not match regex!")

    val matcher = "(\\d+)([a-z]*)".toPattern(Pattern.CASE_INSENSITIVE).matcher(duration)
    var result = this

    while (matcher.find()) {
        val amount = matcher.group(1).toLong()
        val unit = when {
            matcher.group(2).matches("s(ec(onds?)?)?".toRegex()) -> SECONDS
            matcher.group(2).matches("m(in(utes?)?)?".toRegex()) -> MINUTES
            matcher.group(2).matches("h(ours?)?".toRegex()) -> HOURS
            matcher.group(2).matches("d(ays?)?".toRegex()) -> DAYS
            matcher.group(2).matches("w(eeks?)?".toRegex()) -> WEEKS
            matcher.group(2).matches("mo(nths?)?".toRegex()) -> MONTHS
            matcher.group(2).matches("y(ears?)?".toRegex()) -> YEARS
            else -> SECONDS
        }

        result += Duration.of(amount, unit)
    }

    return result
}

fun Duration.formatToHumanReadable(): String {
    var durationLong = toMillis()
    val result = StringBuilder()

    for (entry in durations) {
        if (durationLong < 1000) break
        if (durationLong >= entry.value) {
            val amount = durationLong / entry.value
            result.append("$amount ${entry.key}").run { if (amount > 1) append('s'); this }.append(", ")
            durationLong -= entry.value * amount
        }
    }

    return result.apply { setLength(kotlin.math.max(result.length - 2, 0)) }.toString()
}

fun String.format(char: Char = '&') = ChatColor.translateAlternateColorCodes(char, this)

fun String.replace(values: Map<String, Any?>) = values.entries.fold(this) { acc, (key, value) -> acc.replace("\${$key}", value.toString()) }