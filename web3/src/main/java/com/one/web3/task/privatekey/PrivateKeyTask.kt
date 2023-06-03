package com.one.web3.task.privatekey

import com.one.web3.Param
import com.one.web3.Web3Task

interface PrivateKeyTask : Web3Task<PrivateKeyParam, String>

data class PrivateKeyParam(val walletAddress: String) : Param(0L, emptyList(), false)