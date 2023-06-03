package com.one.web3.task.status

import com.fasterxml.jackson.databind.node.ArrayNode
import com.one.web3.task.SolCall
import java.math.BigInteger

class TransactionStatusSolCallTask : TransactionStatusTask, SolCall {

    override suspend fun executeTask(param: TransactionStatusParam): BigInteger {


        val values = call("getSignatureStatuses", listOf(listOf(param.txHash), hashMapOf("searchTransactionHistory" to true)), param.rpcUrls, param.sync).get("value") as? ArrayNode

        val error = values?.firstOrNull()?.get("err")?.textValue()


        return if (values != null && error == null) {

            BigInteger.ONE
        } else if (values != null) {

            BigInteger.ZERO
        } else {

            BigInteger("1000")
        }
    }
}