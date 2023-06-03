package com.one.web3.task.allowance

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigDecimal

interface TokenAllowanceTask : Web3Task<AllowanceParam, BigDecimal>

data class AllowanceParam(
    val tokenAddress: String,
    val walletAddress: String,
    val contractAddress: String,
    override var chainId: Long,
    override var rpcUrls: List<String>,
    override var sync: Boolean,
) : Param(chainId, rpcUrls, sync)