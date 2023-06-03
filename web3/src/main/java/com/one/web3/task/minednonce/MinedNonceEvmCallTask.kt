package com.one.web3.task.minednonce

import com.one.web3.task.EvmCall
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.utils.Numeric
import java.math.BigInteger

class MinedNonceEvmCallTask : MinedNonceTask, EvmCall {

    override suspend fun executeTask(param: MinedNonceParam): BigInteger {

        val params = listOf(param.walletAddress, DefaultBlockParameterName.LATEST)

        return call("eth_getTransactionCount", params, param.rpcUrls, param.sync).textValue().let {

            Numeric.decodeQuantity(it)
        }
    }
}