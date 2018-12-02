public class Deck(styleValue:String.()->Int){
    private val cards:MutableList<Cards> = mutableListOf()
    init{
        var count:Int=1
        for(suit in Suits.values()){
            for(i in 1..13){
                cards.add(Cards(i,suit,styleValue,count))
            }
        }
    }
    fun getDeck():MutableList<Cards> = cards
    fun shuffleDeck(){
        cards.shuffle()
    }
    fun sortDeckOriginal(){
        cards.sortBy { it.getOriginalPlacement() }
    }
    fun sortDeckPerceived(){
        cards.sortBy { it.getPerceivedValue() }
    }
    fun sortDeckFullHouseForEachPlayer(){
        sortDeckOriginal()
        //A series of arbitrary swaps just to fake data
        Util.swap(3,15,cards)
        Util.swap(4,28,cards)
        Util.swap(6,18,cards)
        Util.swap(17,29,cards)
        Util.swap(32,44,cards)
        Util.swap(40,10,cards)
        Util.swap(41,23,cards)
    }
    fun sortDifferentStraightsForEachPlayer(){
        sortDeckOriginal()
        Util.swap(7,15,cards)
        Util.swap(29,43,cards)
    }
    fun sortFlushes(){
        sortDeckOriginal()

//        Util.swap(7,16,cards)
        Util.swap(8,17,cards)
        Util.swap(9,18,cards)
        Util.swap(10,19,cards)
//        Util.swap(5,20,cards)
        Util.swap(12,21,cards)
    }
    fun sortStraightFlushes(){
        sortDeckOriginal()
        Util.swap(7,16,cards)
        Util.swap(8,24,cards)
        Util.swap(30,45,cards)
        Util.swap(35,51,cards)
    }
    fun printDeck(){
        Util.printCardsInLine(cards)
        println()
    }

}