package com.one.web3.task

import com.fasterxml.jackson.databind.JsonNode
import com.one.web3.Web3Call
import com.one.web3.Web3Request
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction

const val METHOD_NAME_ETH_CALL = "eth_call"

interface EvmCall : Web3Call {

    override fun checkSupportChain(chainId: Long) {

        if (chainId in listOf(101L)) {

            error("${this.javaClass.simpleName} not support chain $chainId")
        }
    }

    suspend fun read(method: String, function: Function, fromAddress: String?, toAddress: String?, rpcUrls: List<String>): List<Type<*>> {


        val encodedFunction = FunctionEncoder.encode(function)


        val transaction = Transaction.createEthCallTransaction(fromAddress, toAddress, encodedFunction)

        val result = read(method, transaction, DefaultBlockParameterName.LATEST, rpcUrls).textValue() ?: error("result not found")


        return FunctionReturnDecoder.decode(result, function.outputParameters)
    }


    suspend fun read(method: String, walletAddress: String, defaultBlockParameter: DefaultBlockParameter = DefaultBlockParameterName.LATEST, rpcUrls: List<String>): JsonNode {

        return read(Web3Request(method, listOf(walletAddress, defaultBlockParameter)), rpcUrls)
    }


    suspend fun read(method: String, transaction: Transaction, defaultBlockParameter: DefaultBlockParameter = DefaultBlockParameterName.LATEST, rpcUrls: List<String>): JsonNode {

        return read(Web3Request(method, listOf(transaction, defaultBlockParameter)), rpcUrls)
    }
}