package com.example.kaano8.transactiongraph.transaction

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.kaano8.transactiongraph.R
import com.example.kaano8.transactiongraph.data.TransactionData
import com.example.kaano8.transactiongraph.repository.TransactionRepository
import com.example.kaano8.transactiongraph.utils.DateTimeFormatter
import com.example.kaano8.transactiongraph.utils.HourAxisValueFormatter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_transaction.*


class TransactionFragment : Fragment(), OnChartGestureListener, OnChartValueSelectedListener {

    private lateinit var transactionData: TransactionData
    private lateinit var dateTimeFormatter: DateTimeFormatter
    private lateinit var repository: TransactionRepository
    private lateinit var hourAxisValueFormatter: HourAxisValueFormatter

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
        val dataColors = IntArray(transactionData.amount.size)

        val formattedData = dateTimeFormatter.formatData(transactionData)
        val lineDataSet = LineDataSet(formattedData, "Transactions")

        formattedData.forEachIndexed { index, entry ->
            if (entry.y > 0)
                dataColors[index] = R.color.green
            else
                dataColors[index] = R.color.red
        }


        lineDataSet.apply {
            enableDashedLine(10f, 5f, 0f)
            enableDashedHighlightLine(10f, 5f, 0f)
            color = Color.BLACK
            setCircleColors(dataColors, context)
            lineWidth = 1f
            circleRadius = 5f
            setDrawCircleHole(true)
            valueTextSize = 9f
            //setDrawFilled(true)
            formLineWidth = 1f
            formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            formSize = 15f
        }


        val lineData = LineData(lineDataSet)
        lineChart?.apply {
            onChartGestureListener = this@TransactionFragment
            setOnChartValueSelectedListener(this@TransactionFragment)
            setDrawGridBackground(false)
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(false)
            animateX(2500)
            isAutoScaleMinMaxEnabled = true
            description.isEnabled = false
            axisRight.isEnabled = false
        }

        val xAxis = lineChart?.xAxis
        hourAxisValueFormatter = HourAxisValueFormatter(dateTimeFormatter.getReferenceTimeStamp())
        xAxis?.apply {
            valueFormatter = hourAxisValueFormatter
            granularity = 1000*60f
        }
        lineChart?.apply {
            data = lineData
            invalidate()

        }
    }

    override fun onChartGestureEnd(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            lineChart?.highlightValues(null);
    }

    override fun onChartFling(me1: MotionEvent?, me2: MotionEvent?, velocityX: Float, velocityY: Float) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    override fun onChartSingleTapped(me: MotionEvent?) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    override fun onChartGestureStart(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
        Log.i("Gesture", "START, x: " + me?.getX() + ", y: " + me?.getY());
    }

    override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {
        hourAxisValueFormatter.updateDataFormat(scaleX)
        //lineChart?.invalidate()
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    override fun onChartLongPressed(me: MotionEvent?) {
        Log.i("LongPress", "Chart longpressed.");
    }

    override fun onChartDoubleTapped(me: MotionEvent?) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    override fun onNothingSelected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + lineChart?.getLowestVisibleX() + ", high: " + lineChart?.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + lineChart?.getXChartMin() + ", xmax: " + lineChart?.getXChartMax() + ", ymin: " + lineChart?.getYChartMin() + ", ymax: " + lineChart?.getYChartMax());

    }

    companion object {
        @JvmStatic
        fun newInstance() =
                TransactionFragment()
    }
}
