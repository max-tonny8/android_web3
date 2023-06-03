package com.one.web3.task.decimal

import com.one.web3.task.EvmCall
import com.one.web3.task.SolCall
import retrofit2.Retrofit

class DecimalSolCallTask() : DecimalCallTask, SolCall {



    override suspend fun executeTask(param: DecimalParam): Int {

        return call("getTokenSupply", listOf(param.tokenAddress), param.rpcUrls, param.sync).let {

            it.get("value")?.get("decimals")?.toString()?.toIntOrNull() ?: 0
        }
    }
}