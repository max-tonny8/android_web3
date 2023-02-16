package com.one.web3.task.balancenative

import com.one.web3.task.SolCall
import retrofit2.Retrofit
import java.math.BigInteger

class BalanceNativeSolCallTask(private val retrofit: Retrofit) : BalanceNativeTask, SolCall {

    override fun providerRetrofit(): Retrofit = retrofit

    override suspend fun executeTask(param: BalanceNativeParam): BigInteger {

        val params = listOf(
            param.walletAddress,
            hashMapOf("encoding" to "jsonParsed"),
        )

        return read("getBalance", params, param.rpcUrls).let {

            it.get("value")?.get("lamports")?.toString()?.toBigInteger() ?: BigInteger.ZERO
        }
    }
}