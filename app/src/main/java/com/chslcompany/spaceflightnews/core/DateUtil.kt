package com.chslcompany.spaceflightnews.core

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    const val formatyyyyMMddTHHmmss = "yyyy-MM-ddHH:mm:ss"
    const val formatyyyyMMddTHHmmssSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val formatHHmmss = "HH:mm:ss"
    const val formatddMMyyyy = "dd/MM/yyyy"
    private const val EMPTY_STRING = ""

    fun formatDate(
        time: String,
        inputFormat: String = formatyyyyMMddTHHmmssSSSZ,
        outputFormat: String = formatddMMyyyy,
    ): String =
        try {
            val sdf = SimpleDateFormat(inputFormat, Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            val date = sdf.parse(time)
            sdf.applyPattern(outputFormat)
            date?.let { sdf.format(date) } ?: EMPTY_STRING
        } catch (e: Exception) {
            e.printStackTrace()
            EMPTY_STRING
        }

    fun formatHour(
        time: String,
        inputFormat: String = formatyyyyMMddTHHmmssSSSZ,
        outputFormat: String = formatHHmmss,
    ): String =
        try {
            val sdf = SimpleDateFormat(inputFormat, Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            val date = sdf.parse(time)
            sdf.applyPattern(outputFormat)
            date?.let { sdf.format(date) } ?: EMPTY_STRING
        } catch (e: Exception) {
            e.printStackTrace()
            EMPTY_STRING
        }


}