package com.one.web3.task.gasprice

import com.one.web3.task.EvmCall
import retrofit2.Retrofit
import java.math.BigInteger

class GasPriceSolCallTask(val retrofit: Retrofit) : GasPriceCallTask, EvmCall {

    override fun providerRetrofit(): Retrofit = retrofit

    override suspend fun executeTask(param: GasPriceParam): BigInteger {

        return read("getRecentBlockhash", param.rpcUrls).let {

            it.get("value")?.get("feeCalculator")?.get("lamportsPerSignature")?.toString()?.toBigInteger() ?: BigInteger.ZERO
        }
    }
}