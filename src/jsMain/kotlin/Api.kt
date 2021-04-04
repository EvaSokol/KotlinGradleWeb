import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getMarketList(): List<MarketListItem> {
    return jsonClient.get(endpoint + MarketListItem.path)
}

suspend fun addMarketListItem(marketListItem: MarketListItem) {
    jsonClient.post<Unit>(endpoint + MarketListItem.path) {
        contentType(ContentType.Application.Json)
        body = marketListItem
    }
}

suspend fun deleteMarketListItem(marketListItem: MarketListItem) {
    jsonClient.delete<Unit>(endpoint + MarketListItem.path + "/${marketListItem.id}")
}