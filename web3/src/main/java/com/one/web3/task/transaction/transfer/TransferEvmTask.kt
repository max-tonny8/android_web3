package com.one.web3.task.transaction.transfer

import com.one.web3.task.functionTransferEvmOf
import com.one.web3.task.functionTransferNftEvmOf
import com.one.web3.task.transaction.TransactionEvmCall
import org.web3j.abi.FunctionEncoder
import java.math.BigInteger

class TransferEvmTask() : TransferTask, TransactionEvmCall {

    override suspend fun executeTask(param: TransferParam): Pair<String, BigInteger> {


        val tokenId = param.tokenId


        val isNft = tokenId != null


        val isEIP1155 = if (isNft) {

            isEIP1155(param.tokenAddress, param.rpcUrls, param.sync) ?: false
        } else {

            null
        }


        val to = if (param.isTokenNative) {

            param.receiverAddress
        } else {

            param.tokenAddress
        }


        val data = if (param.isTokenNative) {

            ""
        } else if (isEIP1155 == true) {

            FunctionEncoder.encode(functionTransferNftEvmOf(param.walletAddress, param.receiverAddress, tokenId!!, param.tokenAmount))
        } else if (isEIP1155 == false) {

            FunctionEncoder.encode(functionTransferNftEvmOf(param.walletAddress, param.receiverAddress, tokenId!!))
        } else {

            FunctionEncoder.encode(functionTransferEvmOf(param.receiverAddress, param.tokenAmount))
        }


        val value = if (param.isTokenNative) {

            param.tokenAmount
        } else {

            BigInteger.ZERO
        }


        return doTransaction(

            to = to,
            from = param.walletAddress,

            data = data,

            value = value,

            nonce = param.nonce,
            gasLimit = param.gasLimit,
            gasPrice = param.gasPrice,
            priorityFee = param.priorityFee,

            chainId = param.chainId,
            rpcUrls = param.rpcUrls,
        )
    }
}