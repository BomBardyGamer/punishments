package com.prevarinite.punishments.util

import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit.*
import java.util.regex.Pattern

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
