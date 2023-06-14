package com.one.web3.task.transaction

import android.util.Log
import com.one.web3.BuildConfig
import com.one.web3.Web3
import com.one.web3.task.EvmCall
import com.one.web3.utils.toWei
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.ChainId
import org.web3j.utils.Numeric
import java.math.BigDecimal
import java.math.BigInteger

interface TransactionEvmCall : EvmCall {

    suspend fun doTransaction(

        to: String,
        from: String,

        data: String,
        value: BigInteger,

        nonce: BigInteger?,
        gasLimit: BigInteger,
        gasPrice: BigDecimal,
        priorityFee: BigDecimal,

        chainId: Long,
        rpcUrls: List<String>,

        broadcastTransaction: Boolean = true
    ): Pair<String, BigInteger> {


        val credentials = getCredentials(walletAddress = from)


        val gasPriceWei = gasPrice.toWei().toBigInteger()


        if (gasPriceWei == BigInteger.ZERO) {

            error("")
        }


        var localNonce: BigInteger = nonce?.takeIf { it > BigInteger.ZERO } ?: getTransactionNonce(chainId, credentials.address, rpcUrls)

        if (localNonce <= BigInteger.ZERO) {
            localNonce = BigInteger.ZERO
        }


        val tx = if (isEIP1559(rpcUrls, true)) {

            RawTransaction.createTransaction(chainId, localNonce, gasLimit, to, value, data, priorityFee.toWei().toBigInteger(), gasPriceWei)
        } else {

            RawTransaction.createTransaction(localNonce, gasPriceWei, gasLimit, to, value, data)
        }


        if (BuildConfig.DEBUG) {

            Log.d("tuanha", "doTransaction: localNonce:$localNonce minedNonce:${getMinedNonce(credentials.address, rpcUrls)}")
        }


        return if (broadcastTransaction) {
            Pair(broadcastTransaction(chainId, credentials, tx, rpcUrls), localNonce)
        } else {
            Pair(sign(chainId, credentials, tx), localNonce)
        }
    }

    suspend fun getCredentials(walletAddress: String): Credentials {

        return Credentials.create(Web3.instance().getPrivateKey(walletAddress))
    }


    suspend fun getTransactionNonce(chainId: Long, walletAddress: String, rpcUrls: List<String>): BigInteger {

        val minedNonce = getMinedNonce(walletAddress, rpcUrls)

        val pendingNonce = getTransactionPendingNonce(chainId, walletAddress)

        return listOf(minedNonce, pendingNonce).maxOf { it }
    }

    suspend fun getMinedNonce(walletAddress: String, rpcUrls: List<String>): BigInteger {

        val params = listOf(walletAddress, DefaultBlockParameterName.LATEST)

        return call("eth_getTransactionCount", params, rpcUrls, false).textValue()?.let { Numeric.decodeQuantity(it) } ?: BigInteger.ZERO
    }

    suspend fun getTransactionPendingNonce(chainId: Long, walletAddress: String): BigInteger {

        return Web3.instance().getPendingNonce(chainId, walletAddress)
    }


    private suspend fun broadcastTransaction(chainId: Long, credentials: Credentials, rawTransaction: RawTransaction, rpcUrls: List<String>): String {

        val hexValue = sign(chainId, credentials, rawTransaction)

        val params = listOf(hexValue)

        return call("eth_sendRawTransaction", params, rpcUrls, true).textValue()
    }


    private fun sign(chainId: Long, credentials: Credentials, rawTransaction: RawTransaction?): String = (if (chainId > ChainId.NONE) {

        TransactionEncoder.signMessage(rawTransaction, chainId, credentials)
    } else {

        TransactionEncoder.signMessage(rawTransaction, credentials)
    }).let {

        Numeric.toHexString(it)
    }
}