import react.*
import react.dom.*
import kotlinext.js.*
import kotlinx.html.js.*
import kotlinx.coroutines.*

private val scope = MainScope()

val App = functionalComponent<RProps> { _ ->
    val (marketList, setMarketList) = useState(emptyList<MarketListItem>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setMarketList(getMarketList())
        }
    }

    h1 {
        +"Full-Stack Market List"
    }
    ul {
        marketList.sortedByDescending(MarketListItem::number).forEach { item ->
            li {
                key = item.toString()
                attrs.onClickFunction = {
                    scope.launch {
                        deleteMarketListItem(item)
                        setMarketList(getMarketList())
                    }
                }
                +"[${item.id}] ${item.name} - ${item.number}"
            }
        }
    }

    var cartItemName = ""
//    val cartItemNumber = 0

    child(
        InputName,
        props = jsObject {
            onSubmit = { input ->
                cartItemName = input
            }
        }
    )

    child(
        InputNumber,
        props = jsObject {
            onSubmit = { input ->
                val cartItem = MarketListItem(cartItemName, input.toInt())
                scope.launch {
                    addMarketListItem(cartItem)
                    setMarketList(getMarketList())
                }
            }
        }
    )
}