package app

import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.button
import react.dom.div

fun RBuilder.buttonsUI(play: Play){
    div ("buttonList"){
        div("button") {
            +"Submit"
            attrs.onClickFunction={
                play.submit()
            }
        }
        div("button") {
            +"Clear"
            attrs.onClickFunction={
                play.clear()
            }
        }
        div("button") {
            +"Skip"
            attrs.onClickFunction={
                play.skip()
            }
        }
        div("button") {
            +"Restart"
            attrs.onClickFunction={
                play.playAgain()
            }
        }
    }
}