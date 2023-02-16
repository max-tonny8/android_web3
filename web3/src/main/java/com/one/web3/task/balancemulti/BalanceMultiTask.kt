package com.one.web3.task.balancemulti

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigDecimal

interface BalanceMultiTask : Web3Task<BalanceMultiParam, Map<Pair<String, String>, BigDecimal>>

data class BalanceMultiParam(val tokenAddressList: List<String>, val walletAddressList: List<String>, val multiCallAddress: String, override val chainId: Long, override val rpcUrls: List<String>) : Param(chainId, rpcUrls)
