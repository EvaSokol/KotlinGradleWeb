import react.*
import react.dom.*
import kotlinx.html.js.*
import kotlinx.html.InputType
import kotlinx.html.submitInput
import org.w3c.dom.events.Event
import org.w3c.dom.HTMLInputElement

external interface InputProps : RProps {
//    var onSubmitName: (String) -> Unit
//    var onSubmitNumber: (String) -> Unit
    var onSubmitButton: (String, String) -> Unit
}

/*
Текущее поведение:

Сразу после запуска в поле Name есть текст "input name here"
Для ввода наименования требуется удалить текст "input name here" и ввести наименование
По клику Enter:
- сохраняется в переменную введенное название, без Enter - не сохраняется
- в поле Name снова появляется текст "input name here"
Вручную переходим на поле количества
Для ввода числа требуется удалить текст "input number here" и ввести число

--------------------------
Требуемое поведение:

Сразу после запуска в поле Name есть текст "input name here"
Для ввода наименования достаточно кликнуть мышкой на поле и ввести наименование
По нажатию Enter:
 - введенное значение сохраняется в поле Name,
 - курсор переходит на поле количества
 - Для ввода количества вводим число с клавиатуры без доп.действий

--------------------------
Финализация на данный момент работает корректно:

После ввода количества по нажатию Enter:
- данные сохраняются в базу,
- список обновляется
- "input name here" и "input number here" в соответствующих полях

Материалы:
Исходный мануал: https://play.kotlinlang.org/hands-on/Full%20Stack%20Web%20App%20with%20Kotlin%20Multiplatform/01_Introduction
БД ставим отсюда https://docs.mongodb.com/manual/installation/#mongodb-community-edition-installation-tutorials
Имя БД "SaladMarket"
 */

var inputNameText = "input name here"
var inputNumberText = "input number here"
var buttonText = "Save Data"

val InputMarketListItem = functionalComponent<InputProps> { props ->
    val (nameText, setNameText) = useState(inputNameText)
    val (numberText, setNumberText) = useState("1")
    val (buttonText, setButtonText) = useState(buttonText)

    var cartItemName = ""
    var cartItemNumber = ""

    val submitHandlerName: (Event) -> Unit = {
        it.preventDefault()
        setNameText(inputNameText)
//        props.onSubmitButton(nameText)
    }

    val submitHandlerNumber: (Event) -> Unit = {
        it.preventDefault()
        setNumberText(inputNumberText)
//        props.onSubmitNumber(numberText)
    }

    val submitHandlerButton: (Event) -> Unit = {
        it.preventDefault()
        setButtonText(buttonText)
        props.onSubmitButton(nameText, numberText)
    }

    val changeHandlerName: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setNameText(value)
        cartItemName = value
    }

    val changeHandlerNumber: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setNumberText(value)
        cartItemNumber = value
    }

    form {
        input(InputType.text) {
            attrs.onChangeFunction = changeHandlerName
            attrs.value = nameText
//            attrs.onSubmitFunction = submitHandlerName

        }
        input(InputType.text) {
            attrs.onChangeFunction = changeHandlerNumber
            attrs.value = numberText
//            attrs.onSubmitFunction = submitHandlerNumber
        }
        input(InputType.submit) {
//            attrs.onChangeFunction = changeHandler
            attrs.value = buttonText
            attrs.submitInput {  }
            attrs.onClickFunction = {props.onSubmitButton(cartItemName, cartItemNumber)}
        }
    }
}
