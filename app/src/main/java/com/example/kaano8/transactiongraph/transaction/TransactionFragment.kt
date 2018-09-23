package com.example.kaano8.transactiongraph.transaction

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kaano8.transactiongraph.R
import com.example.kaano8.transactiongraph.data.TransactionData
import com.example.kaano8.transactiongraph.repository.TransactionRepository
import com.example.kaano8.transactiongraph.utils.DateTimeFormatter
import com.example.kaano8.transactiongraph.utils.HourAxisValueFormatter
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_transaction.*


class TransactionFragment : Fragment() {

    private lateinit var transactionData: TransactionData
    private lateinit var dateTimeFormatter: DateTimeFormatter
    private lateinit var repository: TransactionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = TransactionRepository()
        transactionData = TransactionData(repository.getTransactions(), repository.getTransactionsDate())
        initGraph(transactionData)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initGraph(transactionData: TransactionData) {
        dateTimeFormatter = DateTimeFormatter()
        val formattedData = dateTimeFormatter.formatData(transactionData)
        val lineDataSet = LineDataSet(formattedData, "Transactions")
        val lineData = LineData(lineDataSet)

        val xAxis = lineChart?.xAxis
        xAxis?.apply {
            valueFormatter = HourAxisValueFormatter(dateTimeFormatter.getReferenceTimeStamp())
            granularity = 1f
        }
        lineChart?.apply {
            data = lineData
            invalidate()

        }


    }



    companion object {
        @JvmStatic
        fun newInstance() =
                TransactionFragment()
    }
}
