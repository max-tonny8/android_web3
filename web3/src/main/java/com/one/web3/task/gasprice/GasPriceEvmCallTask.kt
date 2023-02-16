package com.one.web3.task.gasprice

import com.one.web3.task.EvmCall
import org.web3j.utils.Numeric
import retrofit2.Retrofit
import java.math.BigInteger

class GasPriceEvmCallTask(private val retrofit: Retrofit) : GasPriceCallTask, EvmCall {

    override fun providerRetrofit(): Retrofit = retrofit

    override suspend fun executeTask(param: GasPriceParam): BigInteger {

        return read("eth_gasPrice", emptyList(), param.rpcUrls).textValue()?.let {

            Numeric.decodeQuantity(it)
        }.let {

            it ?: BigInteger.ZERO
        }
    }
}