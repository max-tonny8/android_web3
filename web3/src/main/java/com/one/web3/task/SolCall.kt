package com.one.web3.task

import com.one.web3.Web3Call

interface SolCall : Web3Call {

    override fun checkSupportChain(chainId: Long) {

        if (chainId !in listOf(101L)) {

            error("${this.javaClass.simpleName} not support chain $chainId")
        }
    }
}