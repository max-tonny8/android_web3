package com.one.web3.task

import com.fasterxml.jackson.databind.JsonNode
import com.one.task.LowException
import com.one.web3.Web3Call
import com.one.web3.Web3Request
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.DynamicBytes
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Bytes4
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.utils.Numeric
import java.math.BigInteger

const val METHOD_NAME_ETH_CALL = "eth_call"

val UNLIMITED: BigInteger by lazy {
    BigInteger.TEN.pow(27)
}


const val ERC721_INTERFACE_ID = "0x5b5e139f"
const val ERC1155_INTERFACE_ID = "0xd9b67a26"

fun functionApproveEvmOf(contractAddress: String, amount: BigInteger) = Function(
    "approve", listOf(Address(contractAddress), Uint256(amount)), emptyList()
)

fun functionTransferEvmOf(receiverAddress: String, amount: BigInteger): Function {

    val paramFirst = arrayOf(Address(receiverAddress), Uint256(amount))

    return Function("transfer", listOfNotNull(*paramFirst), emptyList())
}

fun functionTransferNftEvmOf(walletAddress: String, receivedAddress: String, tokenId: BigInteger, value: BigInteger? = null): Function {

    val paramFirst = arrayOf(Address(walletAddress), Address(receivedAddress), Uint256(tokenId))

    val paramSecond = if (value != null) arrayOf(Uint256(value), DynamicBytes("".toByteArray())) else emptyArray()

    return Function("safeTransferFrom", listOfNotNull(*paramFirst, *paramSecond), emptyList())
}

fun functionOwnerEvmOf(tokenId: BigInteger): Function {
    return Function("ownerOf", listOf(Uint256(tokenId)), listOf<TypeReference<*>>(object : TypeReference<Address>() {}))
}

fun functionBalanceEvmOf(walletAddress: String) = Function(
    "balanceOf", listOf(Address(walletAddress)), listOf<TypeReference<*>>(object : TypeReference<Uint256>() {})
)

fun functionBalanceNftEvmOf(walletAddress: String, tokenId: BigInteger) = Function(
    "balanceOf", listOf(Address(walletAddress), Uint256(tokenId)), listOf<TypeReference<*>>(object : TypeReference<Uint256>() {})
)

fun functionSupportsInterfaceEvmOf(id: String): Function {

    val paramFirst = arrayOf(Bytes4(Numeric.hexStringToByteArray(id)))

    return Function("supportsInterface", listOfNotNull(*paramFirst), listOf<TypeReference<*>>(object : TypeReference<Bool>() {}))
}

interface EvmCall : Web3Call {

    override fun checkSupportChain(chainId: Long) {

        if (chainId in listOf(101L)) {

            throw LowException("${this.javaClass.simpleName} not support chain $chainId")
        }
    }

    suspend fun isEIP1559(rpcUrls: List<String> = emptyList(), sync: Boolean): Boolean {

        return kotlin.runCatching { call("eth_maxPriorityFeePerGas", emptyList(), rpcUrls, sync) }.getOrNull() != null
    }


    suspend fun isEIP721(tokenAddress: String, rpcUrls: List<String> = emptyList(), sync: Boolean): Boolean? {

        return call(METHOD_NAME_ETH_CALL, functionSupportsInterfaceEvmOf(ERC721_INTERFACE_ID), null, tokenAddress, rpcUrls, sync).firstOrNull()?.let { (it as? Bool)?.value } ?: false
    }

    suspend fun isEIP1155(tokenAddress: String, rpcUrls: List<String> = emptyList(), sync: Boolean): Boolean? {

        return call(METHOD_NAME_ETH_CALL, functionSupportsInterfaceEvmOf(ERC1155_INTERFACE_ID), null, tokenAddress, rpcUrls, sync).firstOrNull()?.let { (it as? Bool)?.value } ?: false
    }


    suspend fun call(method: String, function: Function, fromAddress: String?, toAddress: String?, rpcUrls: List<String>, sync: Boolean): List<Type<*>> {


        val encodedFunction = FunctionEncoder.encode(function)


        val transaction = Transaction.createEthCallTransaction(fromAddress, toAddress, encodedFunction)

        val result = call(method, transaction, DefaultBlockParameterName.LATEST, rpcUrls, sync).textValue() ?: error("result not found")


        return FunctionReturnDecoder.decode(result, function.outputParameters)
    }


    suspend fun call(method: String, walletAddress: String, defaultBlockParameter: DefaultBlockParameter = DefaultBlockParameterName.LATEST, rpcUrls: List<String>, sync: Boolean): JsonNode {

        return call(Web3Request(method, listOf(walletAddress, defaultBlockParameter)), rpcUrls, sync)
    }


    suspend fun call(method: String, transaction: Transaction, defaultBlockParameter: DefaultBlockParameter = DefaultBlockParameterName.LATEST, rpcUrls: List<String>, sync: Boolean): JsonNode {

        return call(Web3Request(method, listOf(transaction, defaultBlockParameter)), rpcUrls, sync)
    }
}