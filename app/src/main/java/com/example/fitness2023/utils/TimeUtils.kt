package com.example.fitness2023.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils { //14
    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("mm:ss")
    fun getTime(time: Long): String{
        val cv = Calendar.getInstance()
        cv.timeInMillis = time
        return formatter.format(cv.time)
    }
}