package com.one.web3.task.transaction.send

import com.one.web3.task.transaction.TransactionEvmCall
import java.math.BigInteger

open class SendTransactionEvmCallTask : SendTransactionTask, TransactionEvmCall {

    override suspend fun executeTask(param: SendTransactionParam): Pair<String, BigInteger> {

        return doTransaction(

            to = param.to,
            from = param.from,

            data = param.data,

            value = param.value,

            nonce = param.nonce,
            gasLimit = param.gasLimit,
            gasPrice = param.gasPrice,
            priorityFee = param.priorityFee,

            chainId = param.chainId,
            rpcUrls = param.rpcUrls,
        )
    }
}