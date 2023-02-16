package com.one.web3.task.balance

import com.fasterxml.jackson.databind.node.ArrayNode
import com.one.web3.task.SolCall
import retrofit2.Retrofit
import java.math.BigDecimal

class BalanceSolCallTask(private val retrofit: Retrofit) : BalanceTask, SolCall {

    override fun providerRetrofit(): Retrofit = retrofit

    override suspend fun executeTask(param: BalanceParam): BigDecimal {

        val params = listOf(
            param.walletAddress,
            hashMapOf("mint" to param.tokenAddress),
            hashMapOf("encoding" to "jsonParsed"),
        )

        return read("getTokenAccountsByOwner", params, param.rpcUrls).let {

            (it.get("value") as? ArrayNode)?.get(0)?.get("decimals")?.toString()?.toBigDecimal() ?: BigDecimal.ZERO
        }
    }
}
