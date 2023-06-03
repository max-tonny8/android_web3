package com.one.web3.task.gaslimit.transfer

import com.one.task.LowException
import com.one.web3.Param
import com.one.web3.task.EvmCall
import com.one.web3.task.functionTransferEvmOf
import com.one.web3.task.functionTransferNftEvmOf
import com.one.web3.task.gaslimit.GasLimitEvmCallTask
import org.web3j.abi.datatypes.Function
import org.web3j.protocol.core.methods.request.Transaction
import java.math.BigInteger

class GasLimitTransferEvmCallTask : GasLimitEvmCallTask(), EvmCall {

    override suspend fun provideTransaction(param: Param): Transaction {

        if (param !is GasLimitTransferParam) {

            throw LowException("${this.javaClass.simpleName} not support")
        }


        val from = param.walletAddress

        val to = if (param.isNativeToken) {

            param.receiverAddress
        } else {

            param.tokenAddress
        }


        val data = if (param.isNativeToken) {

            ""
        } else {

            provideData(param)
        }


        val value = if (param.isNativeToken) {

            param.tokenAmount
        } else {

            BigInteger.ZERO
        }


        return Transaction(from, null, null, null, to, value, data)
    }

    override suspend fun provideFunction(param: Param): Function {

        if (param !is GasLimitTransferParam) {

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