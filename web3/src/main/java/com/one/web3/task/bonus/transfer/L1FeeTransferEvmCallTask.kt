package com.one.web3.task.bonus.transfer

import com.one.task.LowException
import com.one.web3.Param
import com.one.web3.task.functionTransferEvmOf
import com.one.web3.task.functionTransferNftEvmOf
import com.one.web3.task.bonus.L1FeeEvmCallTask
import org.web3j.abi.datatypes.Function

class L1FeeTransferEvmCallTask : L1FeeEvmCallTask() {

    override suspend fun provideFunction(param: Param): Function {

        if (param !is BonusFeeTransferParam) {

            throw LowException("${this.javaClass.simpleName} not support")
        }


        val tokenId = param.tokenId

        val isNft = tokenId != null


        val isEIP1155 = if (isNft) {

            isEIP1155(param.tokenAddress, param.rpcUrls, param.sync) ?: false
        } else {

            null
        }


        return when (isEIP1155) {
            true -> {

                functionTransferNftEvmOf(param.walletAddress, param.receiverAddress, tokenId!!, param.tokenAmount)
            }

            false -> {

                functionTransferNftEvmOf(param.walletAddress, param.receiverAddress, tokenId!!)
            }

            else -> {

                functionTransferEvmOf(param.receiverAddress, param.tokenAmount)
            }
        }
    }
}