package com.example.kaano8.transactiongraph.utils

import android.os.Build
import android.support.annotation.RequiresApi
import com.example.kaano8.transactiongraph.data.TransactionData
import com.github.mikephil.charting.data.Entry
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateTimeFormatter {

    private var referenceTimeStamp: Long = 0

    fun getReferenceTimeStamp(): Long = referenceTimeStamp

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatData(transactionData: TransactionData): List<Entry> {
        val data = mutableListOf<Entry>()
        val timeStampedData: MutableList<Pair<Long, String>> = mutableListOf()


        val timeStamp = convertDateToTimeStamp(transactionData.date)
        timeStamp.forEachIndexed { index, value ->
            timeStampedData.add(Pair(value, transactionData.amount[index]))
        }
        val nTimeStamp = normalizeTimeStamp(timeStampedData)

        nTimeStamp.forEach {
            data.add(Entry(it.first, it.second))
        }

        return data
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertDateToTimeStamp(date: List<String>): List<Long>{
        val timeStamp = mutableListOf<Long>()
        date.forEach {
            timeStamp.add(LocalDate.parse(it, DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"))
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .epochSecond
            )
        }
        return timeStamp
    }

    private fun normalizeTimeStamp(timeStampedTransactionData: MutableList<Pair<Long, String>>): MutableList<Pair<Float, Float>> {
        val normalizedTransactionData: MutableList<Pair<Float, Float>> = mutableListOf()
        timeStampedTransactionData.sortWith(compareBy { it.first })
        referenceTimeStamp = timeStampedTransactionData.first().first

        timeStampedTransactionData.forEach {
            normalizedTransactionData.add(Pair((it.first - referenceTimeStamp).toFloat(), it.second.toFloat()))
        }

        return normalizedTransactionData
    }

}