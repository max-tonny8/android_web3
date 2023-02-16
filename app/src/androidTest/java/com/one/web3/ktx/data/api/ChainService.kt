package com.one.web3.ktx.data.api

import com.one.web3.ktx.entities.Chain
import retrofit2.http.GET
import retrofit2.http.Url

interface ChainService {

    @GET
    suspend fun call(@Url url: String): List<Chain>
}