package com.example.dnb.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

interface DateFormatter {
     fun convertDateStringToFormattedTime(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getDefault()

       try {val date = inputFormat.parse(dateStr)
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        return outputFormat.format(calendar.time)}
         catch (e : Exception){
             return ""
         }
    }
     fun convertISO8601ToCustomDateFormat(iso8601Date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())

        val date = try{
            inputFormat.parse(iso8601Date)
        }catch (e : ParseException){
            return ""
        }
         return outputFormat.format(date)
     }
}