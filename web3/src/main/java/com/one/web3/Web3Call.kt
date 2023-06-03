package com.one.web3

import com.fasterxml.jackson.databind.JsonNode
import com.one.coreapp.data.usecase.ResultState
import com.one.coreapp.data.usecase.toSuccess
import com.one.task.Task
import com.one.task.executeAsyncByFast
import com.one.task.executeSyncByPriority
import org.web3j.protocol.core.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url


var service: Web3Service? = null

interface Web3Call {

    fun providerRetrofit(): Retrofit {

        return Web3.instance().retrofit
    }

    fun checkSupportChain(chainId: Long)


    private fun providerService(): Web3Service = service ?: providerRetrofit().create(Web3Service::class.java).apply {

        service = this
    }

    suspend fun call(method: String, rpcUrls: List<String>, sync: Boolean): JsonNode {

        return call(Web3Request(method, emptyList()), rpcUrls, sync)
    }

    suspend fun call(method: String, params: List<Any>?, rpcUrls: List<String>, sync: Boolean): JsonNode {

        return call(Web3Request(method, params), rpcUrls, sync)
    }

    suspend fun call(body: Web3Request, rpcUrls: List<String>, sync: Boolean): JsonNode {


        val tasks = rpcUrls.map { rpcUrl ->

            NodeCallTask(rpcUrl, providerService())
        }


        val state = if (sync) {

            tasks.executeSyncByPriority(NodeCallTask.Param(body))
        } else {

            tasks.executeAsyncByFast(NodeCallTask.Param(body))
        }


        if (state is ResultState.Failed) {

            throw state.error
        }

        return state.toSuccess()?.data?.result ?: error("result not found")
    }
}

private class NodeCallTask(private val url: String, val ethCallService: Web3Service) : Task<NodeCallTask.Param, Response<JsonNode>> {

    override suspend fun executeTask(param: Param): Response<JsonNode> {

        val response = ethCallService.call(url, param.body)

        if (response.error != null) {

            error(response.error.message)
        }

        return response
    }

    data class Param(val body: Web3Request)
}


open class Param(open val chainId: Long, open val rpcUrls: List<String>, open val sync: Boolean)


interface Web3Service {

    @Headers("Content-Type: application/json")
    @POST
    suspend fun call(@Url url: String, @Body body: Web3Request): Response<JsonNode>
}

class Web3Request(

    val method: String,
    val params: List<Any>? = emptyList(),

    val id: Long = 2,

    var jsonrpc: String? = "2.0",
)

class Web3Response(

    val method: String? = null,
    val params: List<Any>? = null,

    val id: Long = 2,

    var jsonrpc: String? = "2.0",
)
