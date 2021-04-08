import react.*
import react.dom.*
import kotlinx.html.js.*
import kotlinx.html.InputType
import org.w3c.dom.events.Event
import org.w3c.dom.HTMLInputElement

external interface InputProps : RProps {
    var onSubmit: (String) -> Unit
}

var inputNameText = "input name here"
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


val InputName = functionalComponent<InputProps> { props ->
    val (text, setText) = useState("input name here")

    val submitHandler: (Event) -> Unit = {
        it.preventDefault()
        setText(inputNameText)
        props.onSubmit(text)
//        setText(text)
    }

    val changeHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setText(value)
    }

    form {
        attrs.onSubmitFunction = submitHandler
        input(InputType.text) {
            attrs.onChangeFunction = changeHandler
            attrs.value = text
        }
    }
}
val InputNumber = functionalComponent<InputProps> { props ->
    val (text, setText) = useState("1")

    val submitHandler: (Event) -> Unit = {
        it.preventDefault()
        setText("input number here")
        props.onSubmit(text)
    }

    val changeHandler: (Event) -> Unit = {
        val value = (it.target as HTMLInputElement).value
        setText(value)
    }

    form {
        attrs.onSubmitFunction = submitHandler
        input(InputType.text) {
            attrs.onChangeFunction = changeHandler
            attrs.value = text
        }
    }
}