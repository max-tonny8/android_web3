package com.one.web3.task.priorityfee

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigDecimal
import java.math.BigInteger

interface PriorityFeeTask : Web3Task<PriorityFeeParam, BigDecimal>

data class PriorityFeeParam(override val chainId: Long, override val rpcUrls: List<String>, override val sync: Boolean) : Param(chainId, rpcUrls, sync)
