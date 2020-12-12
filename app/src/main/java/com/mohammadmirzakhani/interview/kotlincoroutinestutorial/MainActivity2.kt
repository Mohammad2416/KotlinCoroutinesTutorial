package com.mohammadmirzakhani.interview.kotlincoroutinestutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO

class MainActivity2 : AppCompatActivity() {
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        textView = findViewById<TextView>(R.id.textView2)


//        runBlocking {
//            delay(4000L)
//        }
//
//        GlobalScope.launch(IO) {
//            val result = doNetworkCall()
//            showInUi(result)
//        }

        textView.setOnClickListener {

            val job = GlobalScope.launch(Default) {
                repeat(5) {
//                    showInUi("coroutines still working ...$it")
                    if (isActive)
                        println("coroutines still working ...$it")
                    delay(1000L)


                    withTimeout(3000L){
                        
                    }
                }

            }

            runBlocking {
                job.join()
//                job.cancel()
                println("\"Coroutines is Done\"")

                showInUi("Coroutines is Done")
            }

        }


    }


    suspend fun doNetworkCall(): String {
        delay(2000L)
        return "This is the answer"
    }


    suspend fun showInUi(result: String) {
        withContext(Dispatchers.Main) {
            textView.text = result
        }
    }

}