package com.one.web3.task.balance

import com.fasterxml.jackson.databind.node.ArrayNode
import com.one.web3.task.SolCall
import retrofit2.Retrofit
import java.math.BigDecimal

class BalanceSolCallTask() : BalanceTask, SolCall {



    override suspend fun executeTask(param: BalanceParam): BigDecimal {

        val params = listOf(
            param.walletAddress,
            hashMapOf("mint" to param.tokenAddress),
            hashMapOf("encoding" to "jsonParsed"),
        )

        return call("getTokenAccountsByOwner", params, param.rpcUrls, param.sync).let {

            (it.get("value") as? ArrayNode)?.get(0)?.get("decimals")?.toString()?.toBigDecimal() ?: BigDecimal.ZERO
        }
    }
}
