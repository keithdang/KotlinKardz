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
            logo()
            h2 {
                +"Welcome to React with Kotlin"
            }
        }
        GameUI(state.presObj,object: Play{
            override fun submit() {
                setState{
                    presObj.submit()
                }
            }

            override fun clear() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}

fun RBuilder.app() = child(App::class) {}
