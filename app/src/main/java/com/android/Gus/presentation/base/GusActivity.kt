package com.android.Gus.presentation.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.testing.R

class GusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gus)
    }
}
