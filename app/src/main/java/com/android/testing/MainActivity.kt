package com.android.testing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.android.testing.models.RemoteDataSource
import com.android.testing.models.Topics
import com.android.testing.utils.Constants
import com.google.gson.Gson

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.inEmail)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val isValid = Utils.checkEmailForValidity(editText.text.toString())

                if (isValid) {
                    Toast.makeText(applicationContext, "Email is valid", Toast.LENGTH_LONG).show()
                }

                var intent = Intent(view.context,SearchActivity::class.java)

                startActivity(intent)
            }
        })

        //        callTopics();

    }

    private fun callTopics() {
        val okHttpClient = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://guessthebeach.herokuapp.com/api/topics/")
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val remoteDataSource = RemoteDataSource(retrofit)

        remoteDataSource.getTopicsRx()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { topics ->
                    for ((id, name) in topics) {
                        Log.d("TAG", "$name $id")
                    }
                }
    }


}
