package com.one.web3.ktx.utils

import com.one.web3.Web3
import com.one.web3.ktx.data.api.ChainService
import com.one.web3.ktx.entities.Chain
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit


private val retrofit: Retrofit by lazy {

    val okHttpClient = OkHttpClient
        .Builder()
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)
//        .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
        .hostnameVerifier { _, _ -> true }
        .build()


    Retrofit.Builder()
        .baseUrl("https://github.com/hoanganhtuan95ptit/Web3Ktx/")
        .addConverterFactory(JacksonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

val web3: Web3 by lazy {

    Web3.init(retrofit)

    Web3.instance()
}


private var chainList: List<Chain>? = null

suspend fun getChainList(): List<Chain> {

    if (chainList != null) {

        return chainList!!
    }

    retrofit.create(ChainService::class.java).call("https://raw.githubusercontent.com/hoanganhtuan95ptit/config/main/chain/chains.json").apply {

        chainList = this
    }

    return chainList!!.filter { chain ->

        chain.urls.any { it.type == "RPC" }
    }
}
