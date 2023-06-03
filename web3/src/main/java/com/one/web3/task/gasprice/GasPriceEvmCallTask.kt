package com.one.web3.task.gasprice

import com.one.web3.task.EvmCall
import org.web3j.utils.Numeric
import retrofit2.Retrofit
import java.math.BigInteger

class GasPriceEvmCallTask() : GasPriceCallTask, EvmCall {

    override suspend fun executeTask(param: GasPriceParam): BigInteger {

        return call("eth_gasPrice", emptyList(), param.rpcUrls, param.sync).textValue()?.let {

            Numeric.decodeQuantity(it)
        }.let {

            it ?: BigInteger.ZERO
        }
    }
}