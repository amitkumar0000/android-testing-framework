package com.android.testing

import io.reactivex.Observable
import org.junit.Test

class RxjavaTesting {

    @Test
    fun test(){
        Observable.just(1, 2)
                .test()
                .assertValueAt(0, 1)
    }
}