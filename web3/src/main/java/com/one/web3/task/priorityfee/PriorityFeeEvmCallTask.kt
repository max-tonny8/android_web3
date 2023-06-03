package com.one.web3.task.priorityfee

import com.one.web3.task.EvmCall
import com.one.web3.utils.fromWei
import org.web3j.utils.Numeric
import java.math.BigDecimal

class PriorityFeeEvmCallTask : PriorityFeeTask, EvmCall {

    override suspend fun executeTask(param: PriorityFeeParam): BigDecimal {

        return kotlin.runCatching {

            call("eth_maxPriorityFeePerGas", emptyList(), param.rpcUrls, param.sync)
        }.getOrNull()?.textValue()?.let {

            Numeric.decodeQuantity(it).toBigDecimal().fromWei()
        } ?: BigDecimal.ZERO
    }
}