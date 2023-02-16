package com.one.web3.task.balancenative

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigInteger

interface BalanceNativeTask : Web3Task<BalanceNativeParam, BigInteger>

data class BalanceNativeParam(val walletAddress: String, override val chainId: Long, override val rpcUrls: List<String>) : Param(chainId, rpcUrls)
