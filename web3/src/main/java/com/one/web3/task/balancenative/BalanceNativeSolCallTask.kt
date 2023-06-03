package com.one.web3.task.balancenative

import com.one.web3.task.SolCall
import retrofit2.Retrofit
import java.math.BigInteger

class BalanceNativeSolCallTask() : BalanceNativeTask, SolCall {



    override suspend fun executeTask(param: BalanceNativeParam): BigInteger {

        val params = listOf(
            param.walletAddress,
            hashMapOf("encoding" to "jsonParsed"),
        )

        return call("getBalance", params, param.rpcUrls, param.sync).let {

            it.get("value")?.get("lamports")?.toString()?.toBigInteger() ?: BigInteger.ZERO
        }
    }
}