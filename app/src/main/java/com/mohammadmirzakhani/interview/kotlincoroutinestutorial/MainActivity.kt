package com.mohammadmirzakhani.interview.kotlincoroutinestutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.mohammadmirzakhani.interview.kotlincoroutinestutorial.retofitApi.MyApis
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com"

class MainActivity : AppCompatActivity() {


    lateinit var textView: TextView
    lateinit var mainButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textView = findViewById(R.id.mainTextView)
        mainButton = findViewById<Button>(R.id.mainButton)

        mainButton.setOnClickListener {
            lifecycleScope.launch {
                apiCall()
            }
        }

    }

    suspend fun apiCall() {
        var result = ""

        val retofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApis::class.java)

        lifecycleScope.launch(IO) {
            val response = retofit.getComments()
            if (response.isSuccessful) {
                for (comment in response.body()!!) {
                    result = result + "\n" + comment
                }

            } else {
                result = "Api Call has been Failed"
            }

            withContext(Main) {
                textView.text = result
            }

        }
    }

    private val okHttpClient: OkHttpClient
        get() {
            val client = OkHttpClient.Builder()
            client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            return client.build()
        }

}