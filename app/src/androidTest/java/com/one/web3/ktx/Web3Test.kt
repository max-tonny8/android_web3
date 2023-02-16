package com.one.web3.ktx

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.one.core.utils.extentions.toJson
import com.one.web3.Web3
import com.one.web3.ktx.data.api.ChainService
import com.one.web3.ktx.entities.Chain
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

    private var chainList: List<Chain>? = null

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


        val list = chainList ?: retrofit.create(ChainService::class.java).call("https://raw.githubusercontent.com/hoanganhtuan95ptit/config/main/chain/chains.json").apply {

            chainList = this
        }


        list.map { chain ->

            val rpcUrls = chain.urls.filter { it.type == "PRC" }.sortedByDescending { it.priority }.map { it.url }

            if (rpcUrls.isEmpty()) return@map

            val balance = Web3(retrofit).runCatching { balanceNative("0x8d61ab7571b117644a52240456df66ef846cd999", chain.id, rpcUrls) }.getOrNull()

            Log.d("tuanha", "testBalanceNative: chainName:${chain.name} chainId:${chain.id} - balance:$balance")
        }

        Unit
    }

    @Test
    fun testBalanceMulti() = runBlocking {

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


        val list = chainList ?: retrofit.create(ChainService::class.java).call("https://raw.githubusercontent.com/hoanganhtuan95ptit/config/main/chain/chains.json").apply {

            chainList = this
        }


        val tokens = listOf("0x0000000000085d4780b73119b644ae5ecd22b376", "0x028171bca77440897b824ca71d1c56cac55b68a3")

        val wallets = listOf("0x8d61ab7571b117644a52240456df66ef846cd999", "0xf7bac63fc7ceacf0589f25454ecf5c2ce904997c")

        list.first().let { chain ->

            val rpcUrls = chain.urls.filter { it.type == "PRC" }.sortedByDescending { it.priority }.map { it.url }

            if (rpcUrls.isEmpty()) return@let

            val balance = Web3(retrofit).runCatching {

                balanceMulti(tokens, wallets, "0x5BA1e12693Dc8F9c48aAD8770482f4739bEeD696", chain.id, rpcUrls)
            }.getOrElse {

                Log.d("tuanha", "testBalanceMulti: ", it)
                null
            }

            Log.d("tuanha", "testBalanceNative: chainName:${chain.name} chainId:${chain.id} - balance:${balance.toJson()}")
        }

        Unit
    }
}