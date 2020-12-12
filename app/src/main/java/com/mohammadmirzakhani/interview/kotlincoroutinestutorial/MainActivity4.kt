package com.mohammadmirzakhani.interview.kotlincoroutinestutorial

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mohammadmirzakhani.interview.kotlincoroutinestutorial.retofitApi.MyApis
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


const val BASE_URL = "https://jsonplaceholder.typicode.com"

class MainActivity4 : AppCompatActivity() {

    lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        text = findViewById(R.id.textView3)
        var result = ""

        /* ConnectionSpec.MODERN_TLS is the default value */
        /* ConnectionSpec.MODERN_TLS is the default value */
        var tlsSpecs: List<*> = listOf(ConnectionSpec.MODERN_TLS)

/* providing backwards-compatibility for API lower than Lollipop: */

/* providing backwards-compatibility for API lower than Lollipop: */
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        tlsSpecs = listOf(ConnectionSpec.COMPATIBLE_TLS)
//        }



        val retofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL) //"https://jsonplaceholder.typicode.com"
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApis::class.java)

        GlobalScope.launch(IO) {
            val response = retofit.getComments()
            if (response.isSuccessful) {
                for (comment in response.body()!!) {
                    result = result + "\n" + comment
                }

                withContext(Main) {
                    text.text = result
                }
            }
        }

    }

    private val allTrustedCerts: Array<TrustManager> by lazy { getTrustedCerts() }

    private fun getSSLSocketFactory(): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("TLSv1.2")
        sslContext.init(null, allTrustedCerts, java.security.SecureRandom())
        sslContext.createSSLEngine()
        return sslContext.socketFactory
    }

    private fun getTrustedCerts(): Array<TrustManager> {
        return arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

            override fun getAcceptedIssuers(): Array<X509Certificate> {return arrayOf()}

        })
    }

    private val okHttpClient: OkHttpClient
        get() {
            val client = OkHttpClient.Builder()
            client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            client.sslSocketFactory(getSSLSocketFactory(), allTrustedCerts[0] as X509TrustManager)
            client.hostnameVerifier { _, _ -> true }

            return client.build()
        }
}