public class Player(private val hand:MutableList<Cards>){
    fun getHand():MutableList<Cards> = hand
    private var doublesInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var triplesInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var quadsInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var straightsInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var fullHouseInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var flushInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var straightFlushInHand:MutableList<MutableList<Cards>> = mutableListOf()
    private var royalFlush:MutableList<MutableList<Cards>> = mutableListOf()

    fun initializePlayer(){
        clearVar()
        initializeIdenticals()
        fiveCardCombos()
    }
    fun clearVar(){
        doublesInHand.clear()
        triplesInHand.clear()
        quadsInHand.clear()
        fullHouseInHand.clear()
        straightsInHand.clear()
        flushInHand.clear()
        straightFlushInHand.clear()
        royalFlush.clear()
    }
    fun printHand(){
        Util.printCards(getHand())
    }
    private fun fiveCardCombos(){
        initialStraights(hand,straightsInHand)
        initialFullHouse()
        initializeFlush()
        initializeStraightFlush()
        initializeRoyalFlush()
    }
    private fun initialStraights(hand: MutableList<Cards>,straightsInHand:MutableList<MutableList<Cards>>,royal:Boolean=false){
        var straightHand:MutableList<Cards> = mutableListOf()
        var i:Int=0
        straightHand.add(hand[i])
        while(i<hand.size-1){
            i++
            if(straightHand.last().getPerceivedValue()==hand[i].getPerceivedValue()){
                continue
            }else if((straightHand.last().getPerceivedValue()+1)==hand[i].getPerceivedValue()){
                straightHand.add(hand[i])
            }else{
                addStraightIntoList(straightHand,straightsInHand,royal)
                straightHand.clear()
                straightHand.add(hand[i])
            }
        }
        addStraightIntoList(straightHand,straightsInHand,royal)
    }
    private fun addStraightIntoList(straightHand:MutableList<Cards>, straightsInHand: MutableList<MutableList<Cards>>, royal: Boolean=false){
        if(straightHand.size>=5){
            if(royal){
                if(straightHand.last().getCardName().equals("2")){
                    var royal=straightHand.toMutableList()
                    while(royal.size>5){
                        royal.removeAt(0)
                    }
                    straightsInHand.add(royal)
                }
            }else{
                straightsInHand.add(straightHand.toMutableList())
            }
        }
    }
    private fun initialFullHouse(){
        if(doublesInHand.isNotEmpty() && triplesInHand.isNotEmpty()){
            for(i in triplesInHand){
                for(j in doublesInHand){
                    if(i[0].getPerceivedValue()!=j[0].getPerceivedValue()){
                        var tempHand:MutableList<Cards> = i.toMutableList()
                        tempHand.addAll(j)
                        fullHouseInHand.add(tempHand)
                    }
                }
            }
        }
    }
    private fun initializeFlush(){
        for(suit in Suits.values()){
            flushInHand.add(hand.filter { it.getSuit() == suit }.toMutableList())
        }
    }
    private fun initializeStraightFlush(){
        for(flush in flushInHand){
            if(flush.size>=5){
                initialStraights(flush,straightFlushInHand)
            }
        }
    }
    private fun initializeRoyalFlush(){
        for(flush in flushInHand){
            if(flush.size>=5){
                initialStraights(flush,royalFlush,true)
            }
        }
    }
    private fun initializeIdenticals(){
        for(i in 0..hand.size-2){
            if(Util.perceivedEquals(hand[i],hand[i+1]))
            {
                doublesInHand.add(mutableListOf(hand[i],hand[i+1]))
                if(i+2<hand.size && Util.perceivedEquals(hand[i],hand[i+2]))
                {
                    triplesInHand.add(mutableListOf(hand[i],hand[i+1],hand[i+2]))
                    if(i+3<hand.size && Util.perceivedEquals(hand[i],hand[i+3]))
                    {
                        quadsInHand.add(mutableListOf(hand[i],hand[i+1],hand[i+2]))
                    }
                }
            }
        }
    }
    fun getDoubles():MutableList<MutableList<Cards>>  = doublesInHand
    fun getTriples():MutableList<MutableList<Cards>>  = triplesInHand
    fun getQuads():MutableList<MutableList<Cards>>  = quadsInHand
    fun getFullHouse():MutableList<MutableList<Cards>> = fullHouseInHand
    fun getStraights():MutableList<MutableList<Cards>> = straightsInHand
    fun getFlush():MutableList<MutableList<Cards>> = flushInHand
    fun getStraightFlush():MutableList<MutableList<Cards>> = straightFlushInHand
    fun getRoyalFlush():MutableList<MutableList<Cards>>  = royalFlush
}