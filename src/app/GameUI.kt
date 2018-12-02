package app

import kotlinx.html.js.onClickFunction
import react.*
import react.dom.div
import President
import react.dom.button
import react.dom.h2

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
    fun skip()
    fun playAgain()
}

class GameUI(props: GameProps) : RComponent<GameProps, GameState>(props) {
    override fun GameState.init(props: GameProps) {
        presObject = props.presObject
        playTime = props.playTime
    }

    override fun RBuilder.render() {
        if(state.presObject.winner.isNotEmpty()){
            h2 {
                +state.presObject.winner
            }
            button {
                +"Play Again"
                attrs.onClickFunction={
                    state.playTime.playAgain()
                }
            }
        }else{
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
                            if(index==0){
                                attrs.onClickFunction={
                                    setState{
                                        card.isSelected=!card.isSelected
                                    }

                                }

                            }
                        }
                    }
                }
            }
            div{
                div{
                    +"Active Card(s)"
                }
                div("active"){
                    for(card in state.presObject.activeCards){
                        console.log(card)
                        div(cardDisplay(card.isSelected)){
                            div{
                                +card.getCardName()
                            }
                            div{
                                +card.getSuit().toString()
                            }
                        }
                    }
                }
            }
            buttonsUI(state.playTime)
        }
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