package mmcom.ui.dranil.webservice

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {
    val BASE_URL = "http://staging.nlevelup.com/application/apis/"
    private var retrofit: Retrofit? = null


    fun getClient(baseUrl: String): Retrofit? {

        // Logging Interceptor
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)// Set connection timeout
                .readTimeout(10, TimeUnit.SECONDS)// Read timeout
                .writeTimeout(10, TimeUnit.SECONDS)// Write timeout
                .addInterceptor(interceptor)// Add log interceptor
                //  .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)// Add cache interceptor
                // .cache(cache)// Add cache
                .build()

        val gson = GsonBuilder()
                .setLenient()
                .create()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
        }
        return retrofit
    }


}
