package com.one.web3.ktx

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.one.web3.Web3
import com.one.web3.ktx.data.api.ChainService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class Web3Test {

    @Test
    fun testBalanceNative() = runBlocking {

        val okHttpClient = OkHttpClient
            .Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            .hostnameVerifier { _, _ -> true }
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://github.com/hoanganhtuan95ptit/Web3Ktx/")
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build()


        retrofit.create(ChainService::class.java).call("https://raw.githubusercontent.com/hoanganhtuan95ptit/config/main/chain/chains.json").map { chain ->

            val rpcUrls = chain.urls.filter { it.type == "PRC" }.sortedByDescending { it.priority }.map { it.url }

            if (rpcUrls.isEmpty()) return@map

            val balance = Web3(retrofit).runCatching { balanceNative("0x8d61ab7571b117644a52240456df66ef846cd999", chain.id, rpcUrls) }.getOrNull()

            Log.d("tuanha", "testBalanceNative: chainName:${chain.name} chainId:${chain.id} - balance:$balance")
        }

        Unit
    }
}