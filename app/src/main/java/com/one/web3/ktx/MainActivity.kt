package com.one.web3.ktx

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.one.web3.ktx.databinding.ActivityMainBinding
import com.one.web3.task.decimal.DecimalEvmCallTask
import com.one.web3.task.gasprice.GasPriceCallTask
import com.one.web3.task.gasprice.GasPriceSolCallTask
import com.one.web3.utils.Task
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ActivityMainBinding.inflate(layoutInflater).root)


        val retrofit = Retrofit.Builder().baseUrl("https://www.youtube.com/").build()

        val b = listOf<Task<*, *>>(GasPriceSolCallTask(retrofit), DecimalEvmCallTask(retrofit))

        Log.d("tuanha", "onCreate: ${b.size}")

       val bFilter =  b.filterIsInstance<GasPriceCallTask>()

        Log.d("tuanha", "onCreate: filter: ${bFilter.size}")
//        val a =  listOf<Task<GasPriceParam, BigInteger>>().executeAsyncByFast(GasPriceParam())
    }
}