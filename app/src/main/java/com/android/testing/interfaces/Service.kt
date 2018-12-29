package com.android.testing.interfaces

import com.android.testing.models.Topics
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET


interface Service {

    @GET("topics/")
    fun getTopicsRx(): Observable<List<Topics>>
}