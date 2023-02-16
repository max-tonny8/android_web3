package com.one.web3.task.decimal

import com.one.web3.task.EvmCall
import com.one.web3.task.SolCall
import retrofit2.Retrofit

class DecimalSolCallTask(private val retrofit: Retrofit) : DecimalCallTask, SolCall {

    override fun providerRetrofit(): Retrofit = retrofit

    override suspend fun executeTask(param: DecimalParam): Int {

        return read("getTokenSupply", listOf(param.tokenAddress), param.rpcUrls).let {

            it.get("value")?.get("decimals")?.toString()?.toIntOrNull() ?: 0
        }
    }
}