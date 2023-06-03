package com.one.web3.task.decimal

import com.one.web3.Param
import com.one.web3.Web3Task

interface DecimalCallTask : Web3Task<DecimalParam, Int>

data class DecimalParam(val tokenAddress: String, override val chainId: Long, override val rpcUrls: List<String>, override val sync: Boolean) : Param(chainId, rpcUrls, sync)
