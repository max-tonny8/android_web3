package com.one.web3.task.balance

import com.one.web3.task.EvmCall
import com.one.web3.task.METHOD_NAME_ETH_CALL
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import retrofit2.Retrofit
import java.math.BigDecimal

fun functionBalanceEvmOf(walletAddress: String) = Function(
    "balanceOf", listOf(Address(walletAddress)), listOf<TypeReference<*>>(object : TypeReference<Uint256>() {})
)

class BalanceEvmCallTask() : BalanceTask, EvmCall {

    override suspend fun executeTask(param: BalanceParam): BigDecimal {

        return call(METHOD_NAME_ETH_CALL, functionBalanceEvmOf(param.walletAddress), null, param.tokenAddress, param.rpcUrls, param.sync).firstOrNull().let { type ->

            (type as? Uint256)?.value?.let { BigDecimal(it) } ?: BigDecimal.ZERO
        }
    }
}
