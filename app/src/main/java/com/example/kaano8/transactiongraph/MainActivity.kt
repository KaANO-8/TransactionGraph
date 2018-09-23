package com.example.kaano8.transactiongraph

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kaano8.transactiongraph.transaction.TransactionFragment
import com.example.kaano8.transactiongraph.utils.add
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add(TransactionFragment.newInstance(), container = container.id)
    }
}
