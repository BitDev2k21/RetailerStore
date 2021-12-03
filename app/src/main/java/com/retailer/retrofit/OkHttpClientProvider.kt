package de.footprinttech.wms.retrofit

import com.retailer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientProvider {
    companion object {
        private lateinit var okHttpClient: OkHttpClient
        fun getInstance(): OkHttpClient {
            if (!Companion::okHttpClient.isInitialized) {
                val okHttpClientBuilder = OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                 if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    okHttpClientBuilder.addInterceptor(logging)
                }
                okHttpClient = okHttpClientBuilder.build()


            }
            return okHttpClient
        }
    }

}