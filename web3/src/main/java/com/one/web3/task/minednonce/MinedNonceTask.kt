package com.one.web3.task.minednonce

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigInteger

interface MinedNonceTask : Web3Task<MinedNonceParam, BigInteger>

data class MinedNonceParam(val walletAddress: String, override val chainId: Long, override val rpcUrls: List<String>, override val sync: Boolean) : Param(chainId, rpcUrls, sync)
