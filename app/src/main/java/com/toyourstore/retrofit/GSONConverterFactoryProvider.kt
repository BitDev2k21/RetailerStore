package de.footprinttech.wms.retrofit

import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory

class GSONConverterFactoryProvider {
    companion object {
        private lateinit var gsonConverterFactory: GsonConverterFactory
        fun getGsonConverterFactoryInstance(): GsonConverterFactory {
            if (!Companion::gsonConverterFactory.isInitialized) {
                gsonConverterFactory =
                    GsonConverterFactory.create(GsonBuilder()
                        .setLenient().serializeNulls()
                        .create())
            }
            return gsonConverterFactory
        }
    }

}