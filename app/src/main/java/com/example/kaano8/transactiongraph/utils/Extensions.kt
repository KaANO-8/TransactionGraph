package com.example.kaano8.transactiongraph.utils


import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity


fun AppCompatActivity.add(fragment: Fragment, container: Int) {
    supportFragmentManager.beginTransaction()
            .add(container, fragment, fragment::class.java.canonicalName)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
}