package com.one.web3.task.bonus.transfer

import com.fasterxml.jackson.databind.node.ArrayNode
import com.one.task.LowException
import com.one.web3.Param
import com.one.web3.task.SolCall
import com.one.web3.task.bonus.BonusFeeTask
import java.math.BigDecimal

class CreateTokenAccountFeeTransferSolCallTask : BonusFeeTask, SolCall {

    override suspend fun executeTask(param: Param): BigDecimal {

        if (param !is BonusFeeTransferParam) {

            throw LowException("${this.javaClass.simpleName} not support")
        }

        val params = listOf(
            param.receiverAddress,
            hashMapOf("mint" to param.tokenAddress),
            hashMapOf("encoding" to "jsonParsed"),
        )

        val pubKey = call("getTokenAccountsByOwner", params, param.rpcUrls, param.sync).let {

            (it.get("value") as? ArrayNode)?.get(0)?.get("pubkey")?.textValue() ?: ""
        }

        return if (pubKey.isNotBlank()) {

            BigDecimal.ZERO
        } else {

            call("getMinimumBalanceForRentExemption", listOf(ACCOUNT_INFO_DATA_LENGTH), param.rpcUrls, param.sync).toString().toBigDecimal()
        }
    }

    companion object {


        private const val PUBLIC_KEY_LENGTH = 32
        private const val UINT_32_LENGTH = 4
        private const val UINT_64_LENGTH = 8

        private const val ACCOUNT_INFO_DATA_LENGTH: Int = (PUBLIC_KEY_LENGTH + PUBLIC_KEY_LENGTH
            + UINT_64_LENGTH + UINT_32_LENGTH + PUBLIC_KEY_LENGTH + 1
            + UINT_32_LENGTH + UINT_64_LENGTH + UINT_64_LENGTH
            + UINT_32_LENGTH + PUBLIC_KEY_LENGTH)
    }
}