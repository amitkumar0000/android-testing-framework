package com.android.testing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.testing.models.RemoteDataSource;
import com.android.testing.models.Topics;
import com.android.testing.utils.Constants;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.inEmail);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = Utils.checkEmailForValidity(editText.getText().toString());

                if (isValid) {
                    Toast.makeText(getApplicationContext(), "Email is valid", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Email not valid", Toast.LENGTH_LONG).show();
                }

            }
        });

//        callTopics();

    }

    private void callTopics() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://guessthebeach.herokuapp.com/api/topics/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RemoteDataSource remoteDataSource = new RemoteDataSource(retrofit);

        remoteDataSource.getTopicsRx()
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<Topics>>() {
                        @Override
                        public void accept(List<Topics> topics) throws Exception {
                            for(Topics topics1 : topics){
                                Log.d("TAG",topics1.getName() +" "+ topics1.getId());
                            }
                        }
                    });
    }


}
