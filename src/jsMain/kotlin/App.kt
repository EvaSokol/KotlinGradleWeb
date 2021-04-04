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
                +"[${item.number}] ${item.name} "
            }
        }
    }
    child(
        InputComponent,
        props = jsObject {
            onSubmit = { input ->
                val cartItem = MarketListItem(input.replace("!", ""), input.count { it == '!' })
                scope.launch {
                    addMarketListItem(cartItem)
                    setMarketList(getMarketList())
                }
            }
        }
    )
}