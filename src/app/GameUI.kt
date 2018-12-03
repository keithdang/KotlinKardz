package app

import kotlinx.html.js.onClickFunction
import react.*
import President
import Cards
import logo.reactLogo
import logo.suitLogo
import react.dom.*

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
            restartUI()
        }else{
            for((index,player) in state.presObject.players.withIndex()){
                h4("header") {
                    +"Player ${index+1}"
                }
                div("hand"){
                    for(card in player.getHand()){
                        cardUI(card,card.isSelected,index==0)
                    }
                }
            }
            h4("header") {
                +"Active Card(s)"
            }
            div("hand"){
                for(card in state.presObject.activeCards){
                    cardUI(card,false,false)
                }
            }
            br {  }
            buttonsUI(state.playTime)
        }
    }

    private fun RBuilder.restartUI(){
        h2 {
            +state.presObject.winner
        }
        div("button") {
            +"Play Again"
            attrs.onClickFunction={
                state.playTime.playAgain()
            }
        }
    }

    private fun RBuilder.cardUI(card: Cards, select:Boolean, enableSelect:Boolean){
        div(cardDisplay(select)) {
            div {
                +card.getCardName()
            }
            suitLogo(card.getSuit())
            if(enableSelect){
                attrs.onClickFunction={
                    setState{
                        card.isSelected=!card.isSelected
                    }
                }
            }
        }
    }

    private fun cardDisplay(isSelected:Boolean):String=when(isSelected){
        true->"card isSelected"
        false->"card notSelected"
    }
}
fun RBuilder.gameUI(pres: President, play: Play) = child(GameUI::class) {
    attrs.presObject = pres
    attrs.playTime = play
}