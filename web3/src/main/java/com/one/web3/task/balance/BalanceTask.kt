package com.one.web3.task.balance

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigDecimal

interface BalanceTask : Web3Task<BalanceParam, BigDecimal>

data class BalanceParam(val tokenAddress: String, val walletAddress: String, override val chainId: Long, override val rpcUrls: List<String>) : Param(chainId, rpcUrls)
