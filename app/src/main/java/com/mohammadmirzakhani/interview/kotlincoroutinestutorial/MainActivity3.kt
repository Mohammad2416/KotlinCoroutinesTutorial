package com.mohammadmirzakhani.interview.kotlincoroutinestutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.system.measureTimeMillis

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        GlobalScope.launch(IO) {

//            var resultOne : String? = null
//            var resultTwo : String? = null
//
//            val jobOne = launch {
//               resultOne = doNetworkCallOne()
//            }
//
//            val jobTwo = launch {
//                resultTwo = doNetworkCallTwo()
//            }

//            jobOne.join()
//            jobTwo.join()

         val time =   measureTimeMillis {

                val resultOne: Deferred<String> = async { doNetworkCallOne() }
                val resultTwo = async { doNetworkCallOne() }
                println("result : ${resultOne.await()}")  // result : I am One
                println("result : $resultTwo")  // result : DeferredCoroutine{Active}@2f43065
            }

            println("Time : $time")


        }


    }

    suspend fun doNetworkCallOne(): String {
        delay(2000L)
        return "I am One"
    }

    suspend fun doNetworkCallTwo(): String {
        delay(2000L)
        return "I am Two"
    }


}