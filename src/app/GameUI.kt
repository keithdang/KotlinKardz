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
            div("players"){
                for((index,player) in state.presObject.players.withIndex()){
                    div("hands"){
                        handUI(player.getHand(),index+1)
                        handUI(player.lastPlayed,index+1,false,true)
                    }
                }
            }
            handUI(state.presObject.activeCards,0,true,false)
            br {  }
            buttonsUI(state.playTime)
        }
    }

    private fun RBuilder.handUI(hand:MutableList<Cards>,num:Int,isActive:Boolean=false,isLastPlayed:Boolean=false){
        if(num!=1 || !isLastPlayed) {
            div(if(isLastPlayed) "lastPlayed" else ""){
                h4("header") {
                    +when {
                        isActive -> "Active Cards(s)"
                        isLastPlayed -> "Last Played"
                        else -> "Player $num"
                    }
                }
                div("hand") {
                    when{
                        isActive && state.presObject.gameStarted && hand.isEmpty()->handMessage("Burned")
                        isLastPlayed && state.presObject.gameStarted && hand.isEmpty()->handMessage("Can't Go")
                        else->for (card in hand)
                        {
                            when (isActive || isLastPlayed)
                            {
                                true -> cardUI(card, false, false)
                                false -> cardUI(card, card.isSelected, num == 1)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.handMessage(sentence:String){
        p("message"){
            +sentence
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

    private fun handDisplay(isLastPlayed: Boolean):String{
        var word="hand"
        if(isLastPlayed){
            word+=" lastPlayed"
        }
        return word
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