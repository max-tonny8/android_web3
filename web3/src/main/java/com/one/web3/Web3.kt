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
import com.one.web3.task.gasprice.GasPriceCallTask
import com.one.web3.task.gasprice.GasPriceEvmCallTask
import com.one.web3.task.gasprice.GasPriceParam
import com.one.web3.task.gasprice.GasPriceSolCallTask
import com.one.web3.utils.ResultState
import com.one.web3.utils.executeAsyncByFast
import retrofit2.Retrofit
import java.math.BigDecimal
import java.math.BigInteger

class Web3(private val retrofit: Retrofit, private val tasks: List<Web3Task<*, *>> = emptyList()) {

    val taskList: List<Web3Task<*, *>> by lazy {

        arrayListOf<Web3Task<*, *>>().apply {

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

        val state = taskList.filterIsInstance<DecimalCallTask>().executeAsyncByFast(DecimalParam(tokenAddress, chainId, rpcUrls))

        if (state is ResultState.Failed) {

            throw  state.cause
        }

        if (state is ResultState.Success) {

            return state.data
        } else {

            error("not found decimal")
        }
    }

    suspend fun gasPrice(chainId: Long, rpcUrls: List<String>): BigInteger {

        val state = taskList.filterIsInstance<GasPriceCallTask>().executeAsyncByFast(GasPriceParam(chainId, rpcUrls))

        if (state is ResultState.Failed) {

            throw  state.cause
        }

        if (state is ResultState.Success) {

            return state.data
        } else {

            error("not found gasPrice")
        }
    }

    suspend fun balance(tokenAddress: String, walletAddress: String, chainId: Long, rpcUrls: List<String>): BigDecimal {

        val state = taskList.filterIsInstance<BalanceTask>().executeAsyncByFast(BalanceParam(tokenAddress, walletAddress, chainId, rpcUrls))

        if (state is ResultState.Failed) {

            throw  state.cause
        }

        if (state is ResultState.Success) {

            return state.data
        } else {

            error("not found balance")
        }
    }

    suspend fun balanceMulti(tokenAddressList: List<String>, walletAddressList: List<String>, multiCallAddress: String, chainId: Long, rpcUrls: List<String>): Map<Pair<String, String>, BigDecimal> {

        val state = taskList.filterIsInstance<BalanceMultiTask>().executeAsyncByFast(BalanceMultiParam(tokenAddressList, walletAddressList, multiCallAddress, chainId, rpcUrls))

        if (state is ResultState.Failed) {

            throw  state.cause
        }

        if (state is ResultState.Success) {

            return state.data
        } else {

            error("not found balanceNative")
        }
    }

    suspend fun balanceNative(walletAddress: String, chainId: Long, rpcUrls: List<String>): BigInteger {

        val state = taskList.filterIsInstance<BalanceNativeTask>().executeAsyncByFast(BalanceNativeParam(walletAddress, chainId, rpcUrls))

        if (state is ResultState.Failed) {

            throw  state.cause
        }

        if (state is ResultState.Success) {

            return state.data
        } else {

            error("not found balanceNative")
        }
    }
}