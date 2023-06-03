package com.one.web3.task.transaction.sign

import com.one.web3.task.transaction.TransactionEvmCall
import java.math.BigInteger

open class SignTransactionEvmCallTask : SignTransactionTask, TransactionEvmCall {

    override suspend fun executeTask(param: SignTransactionParam): Pair<String, BigInteger> {

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