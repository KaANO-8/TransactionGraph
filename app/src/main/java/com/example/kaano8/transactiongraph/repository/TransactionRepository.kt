package com.example.kaano8.transactiongraph.repository

class TransactionRepository {

    fun getTransactions(): List<String> {
        val transactions = mutableListOf<String>()
        return transactions.apply {
            add("1.0")
            add("2.0")
            add("3.0")
            add("-1.0")
            add("8.0")
            add("15.0")
            add("12.0")
            add("-9.0")
            add("1.0")
            add("11.0")
            add("23.0")
            add("-11.0")
        }
    }

    fun getTransactionsDate(): List<String> {
        val dates = mutableListOf<String>()
        return dates.apply {
            add("01-01-2018 05:30")
            add("02-10-2018 06:30")
            add("03-09-2018 05:06")
            add("04-15-2018 10:16")
            add("05-08-2018 05:05")
            add("06-07-2018 11:30")
            add("07-18-2018 15:30")
            add("08-20-2018 23:30")
            add("09-25-2018 18:40")
            add("10-28-2018 15:09")
            add("11-30-2018 06:30")
            add("12-09-2018 07:30")
        }
    }
}