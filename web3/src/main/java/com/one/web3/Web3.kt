package com.one.web3

import com.one.web3.task.balance.BalanceEvmCallTask
import com.one.web3.task.balance.BalanceParam
import com.one.web3.task.balance.BalanceSolCallTask
import com.one.web3.task.balance.BalanceTask
import com.one.web3.task.balancemulti.BalanceMultiEvmCallTask
import com.one.web3.task.balancemulti.BalanceMultiParam
import com.one.web3.task.balancemulti.BalanceMultiTask
import com.one.web3.task.balancenative.BalanceNativeEvmCallTask
import com.one.web3.task.balancenative.BalanceNativeParam
import com.one.web3.task.balancenative.BalanceNativeSolCallTask
import com.one.web3.task.balancenative.BalanceNativeTask
import com.one.web3.task.decimal.DecimalCallTask
import com.one.web3.task.decimal.DecimalEvmCallTask
import com.one.web3.task.decimal.DecimalParam
import com.one.web3.task.decimal.DecimalSolCallTask
import com.one.web3.task.decimalmulti.DecimalMultiEvmCallTask
import com.one.web3.task.decimalmulti.DecimalMultiParam
import com.one.web3.task.decimalmulti.DecimalMultiTask
import com.one.web3.task.gasprice.GasPriceCallTask
import com.one.web3.task.gasprice.GasPriceEvmCallTask
import com.one.web3.task.gasprice.GasPriceParam
import com.one.web3.task.gasprice.GasPriceSolCallTask
import com.one.web3.utils.ResultState
import com.one.web3.utils.executeAsyncByFast
import retrofit2.Retrofit
import java.math.BigDecimal
import java.math.BigInteger

open class Web3(private val retrofit: Retrofit, private val tasks: List<Web3Task<*, *>> = emptyList()) {

    val taskList: List<Web3Task<*, *>> by lazy {

        arrayListOf<Web3Task<*, *>>().apply {

            add(DecimalMultiEvmCallTask(retrofit))

            add(BalanceMultiEvmCallTask(retrofit))

            add(BalanceEvmCallTask(retrofit))
            add(BalanceSolCallTask(retrofit))

            add(BalanceNativeEvmCallTask(retrofit))
            add(BalanceNativeSolCallTask(retrofit))

            add(DecimalEvmCallTask(retrofit))
            add(DecimalSolCallTask(retrofit))

            add(GasPriceEvmCallTask(retrofit))
            add(GasPriceSolCallTask(retrofit))

            addAll(tasks)
        }
    }

    suspend fun decimal(tokenAddress: String, chainId: Long, rpcUrls: List<String>): Int {

        return execute<DecimalParam, Int, DecimalCallTask>(DecimalParam(tokenAddress, chainId, rpcUrls))
    }

    suspend fun decimalMulti(tokenAddressList: List<String>, multiCallAddress: String, chainId: Long, rpcUrls: List<String>): Map<String, Int> {

        return execute<DecimalMultiParam, Map<String, Int>, DecimalMultiTask>(DecimalMultiParam(tokenAddressList, multiCallAddress, chainId, rpcUrls))
    }


    suspend fun gasPrice(chainId: Long, rpcUrls: List<String>): BigInteger {

        return execute<GasPriceParam, BigInteger, GasPriceCallTask>(GasPriceParam(chainId, rpcUrls))
    }


    suspend fun balance(tokenAddress: String, walletAddress: String, chainId: Long, rpcUrls: List<String>): BigDecimal {

        return execute<BalanceParam, BigDecimal, BalanceTask>(BalanceParam(tokenAddress, walletAddress, chainId, rpcUrls))
    }

    suspend fun balanceMulti(tokenAddressList: List<String>, walletAddressList: List<String>, multiCallAddress: String, chainId: Long, rpcUrls: List<String>): Map<Pair<String, String>, BigDecimal> {

        return execute<BalanceMultiParam, Map<Pair<String, String>, BigDecimal>, BalanceMultiTask>(BalanceMultiParam(tokenAddressList, walletAddressList, multiCallAddress, chainId, rpcUrls))
    }


    suspend fun balanceNative(walletAddress: String, chainId: Long, rpcUrls: List<String>): BigInteger {

        return execute<BalanceNativeParam, BigInteger, BalanceNativeTask>(BalanceNativeParam(walletAddress, chainId, rpcUrls))
    }

    suspend inline fun <Param : com.one.web3.Param, Result, reified T : Web3Task<Param, Result>> execute(param: Param): Result {

        val state = taskList.filterIsInstance<T>().executeAsyncByFast(param)

        if (state is ResultState.Failed) {

            throw  state.cause
        }

        if (state is ResultState.Success) {

            return state.data
        } else {

            error("not found")
        }
    }
}