package app

import react.*
import react.dom.*
import logo.*
import President


interface AppState:RState{
    var presObj: President
}

class App : RComponent<RProps, AppState>() {
    override fun AppState.init(){
        presObj= President()
    }
    override fun RBuilder.render() {
        div("App-header") {
            div("subheader"){
                logo()
                div("headerText"){
                    h2("header") {
                        +"Kotlin"
                    }
                    h2("header") {
                        +"React"
                    }
                    h2("header") {
                        +"President"
                    }
                }
            }
        }
        gameUI(state.presObj,object: Play{
            override fun submit() {
                setState{
                    presObj.submit()
                }
            }
            override fun clear() {
                setState{
                    presObj.clear()
                }
            }
            override fun skip() {
                setState{
                    presObj.skip()
                }
            }

            override fun playAgain() {
                setState {
                    presObj.playAgain()
                }
            }
        })
    }
}

fun RBuilder.app() = child(App::class) {}
