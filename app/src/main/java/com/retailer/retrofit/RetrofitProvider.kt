package de.footprinttech.wms.retrofit

import retrofit2.Retrofit

class RetrofitProvider {

    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var retrofit_for_dev_server: Retrofit
        private lateinit var retrofit_for_measurment: Retrofit

         fun getRetrofitInstance(): Retrofit {
            if (!Companion::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                        .addConverterFactory(GSONConverterFactoryProvider.getGsonConverterFactoryInstance())
                        .client(OkHttpClientProvider.getInstance())
                        .baseUrl(WsParamUtils.BaseUrl)
                        .build()
            }
            return retrofit
        }
     }

}