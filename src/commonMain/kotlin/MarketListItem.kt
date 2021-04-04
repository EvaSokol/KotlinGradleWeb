import kotlinx.serialization.Serializable

@Serializable
data class MarketListItem(val name: String, val number: Int) {
    val id: Int = name.hashCode()

    companion object {
        const val path = "/marketList"
    }
}
