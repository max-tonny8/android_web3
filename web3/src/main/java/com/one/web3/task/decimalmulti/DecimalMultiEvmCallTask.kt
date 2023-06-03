package com.one.web3.task.decimalmulti

import com.one.web3.task.EvmMultiCallV2
import com.one.web3.task.decimal.functionDecimalEvmOf
import org.bouncycastle.util.encoders.Hex
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.DynamicBytes
import org.web3j.abi.datatypes.DynamicStruct
import org.web3j.utils.Numeric

class DecimalMultiEvmCallTask : DecimalMultiTask, EvmMultiCallV2 {

    override suspend fun executeTask(param: DecimalMultiParam): Map<String, Int> {

        val staticStruct = hashMapOf<String, DynamicStruct>()

        param.tokenAddressList.map { tokenAddress ->

            val function = functionDecimalEvmOf()

            val encodeDataOfNameFunction = FunctionEncoder.encode(function)

            val struct = DynamicStruct(Address(tokenAddress), DynamicBytes(Hex.decode(encodeDataOfNameFunction.substring(2).toByteArray())))

            staticStruct.put(tokenAddress, struct)
        }


        val results = hashMapOf<String, Int>()

        call(param.multiCallAddress, staticStruct.values.toList(), "tryAggregate", param.rpcUrls, param.sync).forEachIndexed { index, result ->

            if (result.success.value != true) return@forEachIndexed

            val key = staticStruct.keys.toList().getOrNull(index) ?: return@forEachIndexed

            val value = Numeric.toBigInt(result.returnData.value).toInt()

            results[key] = value
        }


        return results
    }
}
