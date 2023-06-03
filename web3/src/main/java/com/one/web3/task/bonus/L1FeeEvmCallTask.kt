package com.one.web3.task.bonus

import com.one.task.LowException
import com.one.web3.Param
import com.one.web3.task.EvmCall
import com.one.web3.task.METHOD_NAME_ETH_CALL
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.DynamicBytes
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.utils.Numeric
import java.math.BigDecimal

fun functionL1FeeEvmOf(txObject: String) = Function(
    "getL1Fee", listOf(DynamicBytes(Numeric.hexStringToByteArray(txObject))), listOf<TypeReference<*>>(object : TypeReference<Uint256>() {})
)

abstract class L1FeeEvmCallTask : BonusFeeTask, EvmCall {

    override fun checkSupportChain(chainId: Long) {

        if (chainId !in listOf(10L)) {

            throw LowException("not support")
        }
    }

    override suspend fun executeTask(param: Param): BigDecimal {

        return call(METHOD_NAME_ETH_CALL, functionL1FeeEvmOf(provideData(param)), null, "0x420000000000000000000000000000000000000F", param.rpcUrls, param.sync).firstOrNull().let { type ->

            (type as? Uint256)?.value?.let { BigDecimal(it) } ?: BigDecimal.ZERO
        }
    }

    open suspend fun provideData(param: Param): String {

        return FunctionEncoder.encode(provideFunction(param))
    }

    open suspend fun provideFunction(param: Param): Function {

        error("function need override")
    }
}