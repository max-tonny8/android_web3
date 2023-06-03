package com.one.web3.task.decimalmulti

import com.one.web3.Param
import com.one.web3.Web3Task

interface DecimalMultiTask : Web3Task<DecimalMultiParam, Map<String, Int>>

data class DecimalMultiParam(val tokenAddressList: List<String>, val multiCallAddress: String, override val chainId: Long, override val rpcUrls: List<String>, override val sync: Boolean) : Param(chainId, rpcUrls, sync)
