package com.android.testing

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class Presenter {

    val titleLiveData = MutableLiveData<String>()

    fun showTitle(title: String) {
        titleLiveData.postValue(title)
    }
}

class LiveDataTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun showTitleTest() {
        val presenter = Presenter()

        presenter.showTitle("title")

        assertEquals("title1", presenter.titleLiveData.value)
    }
}