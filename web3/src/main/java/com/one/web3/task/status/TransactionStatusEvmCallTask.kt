package com.one.web3.task.status

import com.one.web3.task.EvmCall
import org.web3j.utils.Numeric
import java.math.BigInteger

class TransactionStatusEvmCallTask : TransactionStatusTask, EvmCall {

    override suspend fun executeTask(param: TransactionStatusParam): BigInteger {

        val status = call("eth_getTransactionReceipt", listOf(param.txHash), param.rpcUrls, param.sync).get("status")?.textValue() ?: ""

        return kotlin.runCatching { Numeric.decodeQuantity(status) }.getOrDefault(BigInteger("1000"))
    }
}