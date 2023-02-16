package com.one.web3.task.balancemulti

import com.one.web3.task.EvmMultiCallV2
import com.one.web3.task.balance.functionBalanceEvmOf
import org.bouncycastle.util.encoders.Hex
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.DynamicBytes
import org.web3j.abi.datatypes.DynamicStruct
import org.web3j.utils.Numeric
import retrofit2.Retrofit
import java.math.BigDecimal

class BalanceMultiEvmCallTask(private val retrofit: Retrofit) : BalanceMultiTask, EvmMultiCallV2 {

    override fun providerRetrofit(): Retrofit = retrofit

    override suspend fun executeTask(param: BalanceMultiParam): Map<Pair<String, String>, BigDecimal> {


        val staticStruct = hashMapOf<Pair<String, String>, DynamicStruct>()

        param.walletAddressList.map { walletAddress ->

            param.tokenAddressList.map { tokenAddress ->

                val function = functionBalanceEvmOf(walletAddress)

                val encodeDataOfNameFunction = FunctionEncoder.encode(function)

                val struct = DynamicStruct(Address(tokenAddress), DynamicBytes(Hex.decode(encodeDataOfNameFunction.substring(2).toByteArray())))

                staticStruct.put(Pair(walletAddress, tokenAddress), struct)
            }
        }


        val results = hashMapOf<Pair<String, String>, BigDecimal>()

        readMultiCall(param.multiCallAddress, staticStruct.values.toList(), param.rpcUrls).forEachIndexed { index, result ->

            if (result.success.value != true) return@forEachIndexed

            val key = staticStruct.keys.toList().getOrNull(index) ?: return@forEachIndexed

            val value = Numeric.toBigInt(result.returnData.value).toBigDecimal()

            results[key] = value
        }

        return results
    }
}
