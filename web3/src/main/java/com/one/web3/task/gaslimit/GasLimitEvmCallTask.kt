package com.one.web3.task.gaslimit

import com.one.web3.Param
import com.one.web3.task.EvmCall
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Function
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.utils.Numeric
import java.math.BigInteger

const val METHOD_NAME_ETH_ESTIMATE_GAS = "eth_estimateGas"

abstract class GasLimitEvmCallTask : GasLimitTask, EvmCall {

    override suspend fun executeTask(param: Param): BigInteger {

        return call(METHOD_NAME_ETH_ESTIMATE_GAS, listOf(provideTransaction(param)), param.rpcUrls, param.sync).let {

            Numeric.decodeQuantity(it.textValue()).multiply(120.toBigInteger()).divide(100.toBigInteger())
        }
    }

    open suspend fun provideTransaction(param: Param): Transaction {

        error("function need override")
    }

    open suspend fun provideData(param: Param): String {

        return FunctionEncoder.encode(provideFunction(param))
    }

    open suspend fun provideFunction(param: Param): Function {

        error("function need override")
    }
}