package com.one.web3.task.pendingnonce

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigInteger

interface PendingNonceTask : Web3Task<PendingNonceParam, BigInteger>

data class PendingNonceParam(val walletAddress: String, override val chainId: Long) : Param(chainId, emptyList(), false)