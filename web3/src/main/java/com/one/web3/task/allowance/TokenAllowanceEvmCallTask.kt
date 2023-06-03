package com.one.web3.task.allowance

import com.one.web3.task.EvmCall
import com.one.web3.task.METHOD_NAME_ETH_CALL
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigDecimal

fun functionAllowanceEvmOf(walletAddress: String, contractAddress: String) = Function(
    "allowance", listOf(Address(walletAddress), Address(contractAddress)), listOf<TypeReference<*>>(object : TypeReference<Uint256>() {})
)

class TokenAllowanceEvmCallTask() : TokenAllowanceTask, EvmCall {

    override suspend fun executeTask(param: AllowanceParam): BigDecimal {

        return call(METHOD_NAME_ETH_CALL, functionAllowanceEvmOf(param.walletAddress, param.contractAddress), null, param.tokenAddress, param.rpcUrls, param.sync).firstOrNull().let { type ->

            (type as? Uint256)?.value?.let { BigDecimal(it) } ?: BigDecimal.ZERO
        }
    }
}
