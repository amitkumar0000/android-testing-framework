package com.android.testing.models

import com.android.testing.interfaces.Service
import io.reactivex.Observable
import retrofit2.Retrofit


class RemoteDataSource(retrofit: Retrofit) : Service {


    val api: Service

    init {
        this.api = retrofit.create<Service>(Service::class.java!!)
    }

    override fun getTopicsRx(): Observable<List<Topics>> {
        return api.getTopicsRx();

    }

}
