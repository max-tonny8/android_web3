package com.one.web3.task.decimal

import com.one.web3.task.EvmCall
import com.one.web3.task.METHOD_NAME_ETH_CALL
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint8
import retrofit2.Retrofit

fun functionDecimalEvmOf() = Function(
    "decimals", emptyList(), listOf<TypeReference<*>>(object : TypeReference<Uint8>() {})
)

class DecimalEvmCallTask(private val retrofit: Retrofit) : DecimalCallTask, EvmCall {

    override fun providerRetrofit(): Retrofit = retrofit

    override suspend fun executeTask(param: DecimalParam): Int {

        return read(METHOD_NAME_ETH_CALL, functionDecimalEvmOf(), null, param.tokenAddress, param.rpcUrls).firstOrNull().let { type ->

            (type as? Uint8)?.value?.toInt() ?: 0
        }
    }
}