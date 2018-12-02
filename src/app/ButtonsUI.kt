package app

import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.button
import react.dom.div

fun RBuilder.buttonsUI(play: Play){
    div {
        button {
            +"Submit"
            attrs.onClickFunction={
                play.submit()
            }
        }
        button {
            +"Clear"
            attrs.onClickFunction={
                play.clear()
            }
        }
        button{
            +"Skip"
            attrs.onClickFunction={
                play.skip()
            }
        }
    }
}