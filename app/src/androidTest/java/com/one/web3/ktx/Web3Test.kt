package com.one.web3.ktx

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.one.core.utils.extentions.toJson
import com.one.web3.ktx.utils.getChainList
import com.one.web3.ktx.utils.web3
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class Web3Test {

    @Test
    fun testDecimalMulti() = runBlocking {

        val list = getChainList()

        val tokens = listOf("0x0000000000085d4780b73119b644ae5ecd22b376", "0x028171bca77440897b824ca71d1c56cac55b68a3")

        list.find { chain ->

            chain.id == 1L
        }?.let { chain ->

            val rpcUrls = chain.urls.filter { it.type == "RPC" }.sortedByDescending { it.priority }.map { it.url }

            val balance = web3.runCatching {

                decimalMulti(tokens, "0x5BA1e12693Dc8F9c48aAD8770482f4739bEeD696", chain.id, rpcUrls)
            }.getOrElse {

                Log.d("tuanha", "testDecimalMulti: ", it)
                null
            }

            Log.d("tuanha", "testDecimalMulti: chainName:${chain.name} chainId:${chain.id} - balance:${balance.toJson()}")
        }

        Unit
    }

    @Test
    fun testBalanceNative() = runBlocking {

        val list = getChainList()

        list.find { chain ->

            chain.id == 1L
        }?.let { chain ->

            val rpcUrls = chain.urls.filter { it.type == "RPC" }.sortedByDescending { it.priority }.map { it.url }

            val balance = web3.runCatching {

                balanceNative("0x8d61ab7571b117644a52240456df66ef846cd999", chain.id, rpcUrls)
            }.getOrElse {

                Log.d("tuanha", "testBalanceNative: ", it)
                null
            }

            Log.d("tuanha", "testBalanceNative: chainName:${chain.name} chainId:${chain.id} - balance:$balance")
        }

        Unit
    }

    @Test
    fun testBalanceMulti() = runBlocking {

        val list = getChainList()

        val tokens = listOf("0x0000000000085d4780b73119b644ae5ecd22b376", "0x028171bca77440897b824ca71d1c56cac55b68a3")

        val wallets = listOf("0x8d61ab7571b117644a52240456df66ef846cd999", "0xf7bac63fc7ceacf0589f25454ecf5c2ce904997c")

        list.find { chain ->

            chain.id == 1L
        }?.let { chain ->

            val rpcUrls = chain.urls.filter { it.type == "RPC" }.sortedByDescending { it.priority }.map { it.url }

            val balance = web3.runCatching {

                balanceMulti(tokens, wallets, "0x5BA1e12693Dc8F9c48aAD8770482f4739bEeD696", chain.id, rpcUrls)
            }.getOrElse {

                println("testBalanceMulti: $it")
                null
            }

            println("testBalanceMulti: chainName:${chain.name} chainId:${chain.id} - balance:${balance.toJson()}")
        }

        Unit
    }
}