package com.example.dnb.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import com.example.dnb.R

interface DateFormatter {
     fun convertDateStringToFormattedTime(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
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
    fun convertDateStringToHourString(dateStr : String) : String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val outputFormat  = SimpleDateFormat ("HH:00" , Locale.getDefault())
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
    //Возвращает код строки для дня недели, соответствующий дате
    fun convertDateStringToDayOfWeekCode(dateStr : String) : Int{
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = format.parse(dateStr)
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        return when(calendar.get(Calendar.DAY_OF_WEEK)){
            1 -> R.string.sunday
            2 -> R.string.monday
            3 -> R.string.tuesday
            4 -> R.string.wednesday
            5 -> R.string.thursday
            6 -> R.string.friday
            7 -> R.string.saturday
            else -> 1
        }
    }

}