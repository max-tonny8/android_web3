package com.one.web3.task.transaction.approve

import com.one.web3.task.functionApproveEvmOf
import com.one.web3.task.transaction.TransactionEvmCall
import org.web3j.abi.FunctionEncoder
import java.math.BigInteger

class TokenApproveEvmCallTask : TokenApproveTask, TransactionEvmCall {

    override suspend fun executeTask(param: TokenApproveParam): Pair<String, BigInteger> {


        val value = BigInteger.ZERO

        var pair = if (param.tokenAmount <= BigInteger.ZERO) {

            Pair("", -BigInteger.ONE)
        } else doTransaction(

            to = param.to,
            from = param.from,

            data = FunctionEncoder.encode(functionApproveEvmOf(param.smartContractAddress, BigInteger.ZERO)),

            value = value,

            nonce = param.nonce,
            gasLimit = param.gasLimit,
            gasPrice = param.gasPrice,
            priorityFee = param.priorityFee,

            chainId = param.chainId,
            rpcUrls = param.rpcUrls,
        )


        pair = doTransaction(

            to = param.to,
            from = param.from,

            data = FunctionEncoder.encode(functionApproveEvmOf(param.smartContractAddress, param.tokenAmount)),

            value = value,

            nonce = pair.second + BigInteger.ONE,
            gasLimit = param.gasLimit,
            gasPrice = param.gasPrice,
            priorityFee = param.priorityFee,

            chainId = param.chainId,
            rpcUrls = param.rpcUrls,
        )


        return pair
    }
}
