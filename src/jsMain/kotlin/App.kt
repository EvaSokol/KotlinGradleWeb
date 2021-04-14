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

    child(
        InputMarketListItem,
        props = jsObject {
//            var cartItemName = ""
//            var cartItemNumber = 0

//            onSubmitName = { inputName ->
//                cartItemName = inputName
//            }
//            onSubmitNumber = { inputNumber ->
//                cartItemNumber = inputNumber.toInt()
//            }
            onSubmitButton = {inputName, inputNumber ->
                val cartItem = MarketListItem(inputName, inputNumber.toInt())
                scope.launch {
                    addMarketListItem(cartItem)
                    setMarketList(getMarketList())
                }
            }
        }
    )

//    child(
//        InputMarketListItem,
//        props = jsObject {
//            onSubmitNumber = { input ->
//                cartItemNumber = input.toInt()
//            }
//        }
//    )
//
//    child(
//        InputMarketListItem,
//        props = jsObject {
//            onSubmitButton = { _ ->
//                val cartItem = MarketListItem(cartItemName, cartItemNumber)
//                scope.launch {
//                    addMarketListItem(cartItem)
//                    setMarketList(getMarketList())
//                }
//            }
//        }
//    )
}