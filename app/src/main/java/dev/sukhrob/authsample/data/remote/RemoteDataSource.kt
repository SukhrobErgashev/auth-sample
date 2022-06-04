package dev.sukhrob.authsample.data.remote

import com.chuckerteam.chucker.api.ChuckerInterceptor
import dev.sukhrob.authsample.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object {
        private const val BASE_URL = "https://youtube.com"
    }

    // function for create instance of service api
    fun <Api> buildApi(
        api: Class<Api>,
        authToken: String? = null
    ): Api {

        // Retrofit is a type-safe HTTP client for android
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("Authorization", "$authToken")
                        }.build())
                    }
                    .also { client ->
                        if (BuildConfig.DEBUG) {
                            val logging = HttpLoggingInterceptor()
                            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                            client.addInterceptor(logging)
                        }
                    }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)

    }
}

//OkHttpClient.Builder()
//.addInterceptor(ChuckerInterceptor.Builder(context).build())
//.build()