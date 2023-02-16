package com.one.web3.ktx

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.one.core.utils.extentions.downloadSync
import com.one.core.utils.extentions.getFile
import com.one.core.utils.extentions.saveSync
import com.one.core.utils.extentions.toJson
import com.one.coreapp.utils.extentions.toBitmap
import com.one.web3.ktx.entities.Chain
import com.one.web3.ktx.entities.Token
import com.one.web3.ktx.entities.Url
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() = runBlocking {

        val docs = Jsoup.connect("https://chainlist.org/").get().getElementsByTag("script")

        val doc = docs.firstOrNull { it.attr("id") == "__NEXT_DATA__" } ?: return@runBlocking

        val docJson = kotlin.runCatching { doc.childNode(0).outerHtml() }.getOrNull() ?: return@runBlocking

        val arrayNode = ObjectMapper().readTree(docJson).get("props").get("pageProps").get("chains") as? ArrayNode ?: return@runBlocking


        Log.d("tuanha", "useAppContext: 1")

        val chains = arrayListOf<Chain>()

        arrayNode.forEach { chainNode ->

            val chainIdString = chainNode.get("chainSlug")?.textValue() ?: return@forEach

            Log.d("tuanha", "useAppContext: $chainIdString start")

            val logo = "https://raw.githubusercontent.com/hoanganhtuan95ptit/config/main/chain/${chainIdString}.png"

            if (runCatching { logo.toBitmap(cache = false) }.getOrNull() == null) handleFile("chain/$chainIdString.png", true) { shortPath ->

                shortPath.getFile(false).downloadSync("https://icons.llamao.fi/icons/chains/rsz_${chainIdString}.jpg")
            }

            val chain = Chain(
                id = chainNode.get("chainId").longValue(),
                name = chainNode.get("name").textValue().replace("Mainnet", "").trim(),

                logo = logo,

                nativeToken = Token(
                    name = chainNode.get("nativeCurrency").get("name").textValue(),
                    symbol = chainNode.get("nativeCurrency").get("symbol").textValue(),
                    decimals = chainNode.get("nativeCurrency").get("decimals").longValue()
                )
            )


            val urls = arrayListOf<Url>()

            (chainNode.get("rpc") as? ArrayNode)?.forEach { urlNode ->

                if (urls.size < 3 && urlNode.get("tracking")?.textValue() in listOf("limited", "yes")) Url(
                    url = urlNode.get("url").textValue(),
                    type = "PRC",
                    priority = "${Int.MAX_VALUE - urls.size}"
                ).let {

                    urls.add(it)
                }
            }

            chain.urls = urls

            chains.add(chain)

            Log.d("tuanha", "useAppContext: $chainIdString end")
        }

        "chain/chains.json".getFile(true).saveSync(chains.toJson())

        Log.d("tuanha", "useAppContext: end")
    }

    suspend fun handleFile(shortPath: String, checkExits: Boolean = true, block: suspend (shortPath: String) -> Unit) {

        val file = shortPath.getFile(false)

        if (file.exists() && checkExits) return

        block.invoke(shortPath)
    }
}