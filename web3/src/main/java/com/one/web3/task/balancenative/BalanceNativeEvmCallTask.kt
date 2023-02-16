package com.one.web3.task.balancenative

import com.one.web3.task.EvmCall
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.utils.Numeric
import retrofit2.Retrofit
import java.math.BigInteger

class BalanceNativeEvmCallTask(private val retrofit: Retrofit) : BalanceNativeTask, EvmCall {

    override fun providerRetrofit(): Retrofit = retrofit

    override suspend fun executeTask(param: BalanceNativeParam): BigInteger {

        return read("eth_getBalance", param.walletAddress, DefaultBlockParameterName.LATEST, param.rpcUrls).textValue()?.let {

            Numeric.decodeQuantity(it)
        }.let {

            it ?: BigInteger.ZERO
        }
    }
}