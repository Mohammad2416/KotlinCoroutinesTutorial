package com.mohammadmirzakhani.interview.kotlincoroutinestutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "result #1"
    private val RESULT_2 = "result #2"
    private val JOB_TIMEOUT = 1900L

    lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logThread("onCreate")

        textView = findViewById(R.id.textView)

        val mainButton = findViewById<Button>(R.id.mainButton)
        val mainButtonTwo = findViewById<Button>(R.id.mainButtonTwo)
        val mainButton4 = findViewById<Button>(R.id.button4)

        mainButton.setOnClickListener {

////            Dispatchers.IO
//            CoroutineScope(IO).launch {
//
////                fakeApiRequest()
//                fakeRequestTwo()

//            }

//            GlobalScope.launch {
//                val answer = doNetWorkCall()
//                val answer2 = doNetWorkCall2()
//
//                println("answerOne $answer")
//                println("answerTwo $answer2")
//            }


//            val intent =Intent(this, MainActivity2::class.java).apply {
//
//            }
//            startActivity(intent)

            Intent(this, MainActivity2::class.java).also { startActivity(it) }


        }


//        GlobalScope.launch {
//            val answer = doNetWorkCall()
//        }


        mainButtonTwo.setOnClickListener {
            startActivity(Intent(this, MainActivity3::class.java))
        }


        mainButton4.setOnClickListener {

            lifecycleScope.launch {
                while (true){
//                    if (isActive)
                    delay(1000L)
                    println("GlobalScope is still running..")
                }
            }

            GlobalScope.launch {
//                delay(4000L)

                Intent(this@MainActivity, MainActivity4::class.java).also {
                    startActivity(it)
                    finish()
                }
            }


        }


    }//--


    suspend fun doNetWorkCall(): String {
        delay(1000L)
        return "Network Call Answer1"
    }

    suspend fun doNetWorkCall2(): String {
        delay(3000L)
        return "Network Call Answe2r"
    }

    //....................................................
    private suspend fun getResultFromApi(): String {
        logThread("getResultFromApi")
        delay(1000)
        return RESULT_1
    }

    private suspend fun getResultApiTwo(resultOne: String): String {
        logThread("getResultApiTwo - I have Result form first api call here : (${resultOne})")
        delay(1000)
        return RESULT_2
    }

    private fun logThread(methodName: String) {
        println("debug $methodName : ${Thread.currentThread().name}")
    }


    private suspend fun fakeApiRequest() {
        val resultOne = getResultFromApi()
        val resultTwo = getResultApiTwo(resultOne)

        //to Run in Dispatcher.Main
        setTextOnMainThread(resultOne)
        setTextOnMainThread(resultTwo)
    }

    private fun setNewTextToTextView(input: String) {
        textView.text = textView.text.toString().plus("\n $input")
    }

    private suspend fun setTextOnMainThread(input: String) {

        withContext(Main) {
            setNewTextToTextView(input)
        }

    }

    private suspend fun fakeRequestTwo() {

        withContext(IO) {
            //launch {  }
            val job = withTimeoutOrNull(JOB_TIMEOUT) {

                val resultOne = getResultFromApi()  //wait 1000
                println("Debug : $resultOne")
                setTextOnMainThread("I Got ${RESULT_1}")

                println("Debug : ${getResultApiTwo(resultOne)}") //wait 1000
                setTextOnMainThread("I Got ${RESULT_2} ")

            } // wait



            if (job == null) { // means it has Timeout
                println("Job has been Cancelled ")
                setTextOnMainThread("Cancelling job ... ")
            }

        }

    }

}