package app

import kotlinx.html.js.onClickFunction
import react.*
import react.dom.div
import President

interface GameProps : RProps {
    var presObject: President
    var playTime: Play
}

interface GameState : RState {
    var presObject: President
    var playTime: Play
}

interface Play {
    fun submit()
    fun clear()
}

class GameUI(props: GameProps) : RComponent<GameProps, GameState>(props) {
    override fun GameState.init(props: GameProps) {
        presObject = props.presObject
        playTime = props.playTime
    }

    override fun RBuilder.render() {
        for((index,player) in state.presObject.players.withIndex()){
//            console.log("Player ${index+1}")
//            player.printHand()
            div {
                +"Player ${index+1}"
            }
            div("hand"){
                for(card in player.getHand()){
                    div(cardDisplay(card.isSelected)){
                        div{
                            +card.getCardName()
                        }
                        div{
                            +card.getSuit().toString()
                        }
                        attrs.onClickFunction={
                            setState{
                                card.isSelected=!card.isSelected
                            }
                        }
                    }
                }
            }
        }
        buttonsUI(state.playTime)
    }
}
fun cardDisplay(isSelected:Boolean):String=when(isSelected){
    true->"card isSelected"
    false->"card notSelected"
}
fun RBuilder.GameUI(pres: President, play: Play) = child(GameUI::class) {
    attrs.presObject = pres
    attrs.playTime = play
}