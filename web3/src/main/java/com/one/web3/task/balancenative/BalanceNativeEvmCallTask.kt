package com.one.web3.task.balancenative

import com.one.web3.task.EvmCall
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.utils.Numeric
import retrofit2.Retrofit
import java.math.BigInteger

class BalanceNativeEvmCallTask() : BalanceNativeTask, EvmCall {



    override suspend fun executeTask(param: BalanceNativeParam): BigInteger {

        return call("eth_getBalance", param.walletAddress, DefaultBlockParameterName.LATEST, param.rpcUrls, param.sync).textValue()?.let {

            Numeric.decodeQuantity(it)
        }.let {

            it ?: BigInteger.ZERO
        }
    }
}