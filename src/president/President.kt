//820->700

public class President {
    enum class FiveCardCombos {
        NONE, STRAIGHT, FULL_HOUSE, FLUSH, STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    val players: MutableList<Player> = mutableListOf()
    val activeCards: MutableList<Cards> = mutableListOf()
    private var activeFiveCardState: FiveCardCombos = FiveCardCombos.NONE
    private val presValue: String.() -> Int = {
        presConvert(this)
    }
    private var turnCount = 1

    init {
        val deck = Deck(presValue)
        deck.shuffleDeck()
//        deck.sortDeckFullHouseForEachPlayer()
//        deck.sortDifferentStraightsForEachPlayer()
//        deck.sortFlushes()
//        deck.sortStraightFlushes()
//        deck.sortDeckOriginal()
        for (i in 1..4) {
            players.add(Player(deck.getDeck().subList((i - 1) * 13, i * 13).toMutableList()))
            Util.sortHand(players[i - 1].getHand())
            Util.printCardsInLine(players[i - 1].getHand())
            players[i - 1].initializePlayer()
        }
    }

    fun submit(){
        console.log("submit")
        if (turnCount == 4) activeCards.clear()
        var hand=players[0].getHand()
        var indices:MutableList<Int> = mutableListOf()
        for((index,card) in hand.withIndex()){
            if(card.isSelected){
                indices.add(index)
            }
        }
        if(verifyInputCards(hand,indices)){
            if (burned(hand, indices)) {
                activeCards.clear()
                addEntriesIntoActiveCards(indices,hand)
                Util.removeAllIndicesFromHand(indices,hand)
                activeCards.clear()
                turnCount = 1
            }else{
                activeCards.clear()
                addEntriesIntoActiveCards(indices,hand)
                Util.removeAllIndicesFromHand(indices,hand)
                turnCount = 1
                for (i in 2..players.size) {

                    playerTurn(i)
                    if (players[i - 1].getHand().isEmpty()) {
                        println("Player $i wins")
                        break
                    }
                }
            }

        }else{
            Util.resetCardIsSelected(hand)
        }

    }

//    fun start() {
//        println("President")
//        var gameContinue = true
//        while (gameContinue) {
//            for (i in 1..players.size) {
//                playerTurn(i)
//                if (players[i - 1].getHand().isEmpty()) {
//                    gameContinue = false
//                    println("Player $i wins")
//                    break
//                }
//            }
//        }
//    }

    private fun playerTurn(num: Int) {
        print("Player: $num\n")
        val hand = players[num - 1].getHand()
        Util.printCardsInLine(hand)
        var goAgain = false
        if (turnCount == 4) activeCards.clear()
        val indices: List<Int> = getIndex(num)

        if (indices.isNotEmpty() && indices[0] != -1) {
            Util.printIndicesOfHand(indices, hand)
            if (burned(hand, indices)) {
                println("BURN")
                goAgain = true
                activeCards.clear()
            } else {
                activeCards.clear()
                addEntriesIntoActiveCards(indices, hand)
            }
            Util.removeAllIndicesFromHand(indices, hand)
            if (goAgain && hand.size > 0) playerTurn(num)
            turnCount = 1
        } else {
            println("Player $num couldn't go")
            turnCount++
        }
    }
//
    private fun getIndex(playerNum: Int): MutableList<Int> {
        var indices: MutableList<Int> = mutableListOf()
        val player = players[playerNum - 1]
        player.initializePlayer()
        val hand: MutableList<Cards> = player.getHand()
        if (activeCards.isEmpty() || hand.last() >= activeCards[0]) {
            when (activeCards.size) {
                0 -> indices.add(0)
                1 -> indices.add(Util.searchCard(hand, 0, hand.size - 1, activeCards[0]))
                2 -> handleIdenticalCards(player, hand, indices, 2)
                3 -> handleIdenticalCards(player, hand, indices, 3)
                4 -> handleIdenticalCards(player, hand, indices, 4)
                5 -> computerDetectFiveCardCombos(player, hand, indices)
            }
        }
        return indices
    }
//
    private fun verifyInputCards(hand: MutableList<Cards>, numIndices:MutableList<Int>): Boolean {
//        print("Select Card (s for skip):\n")
//        var numList: MutableList<Int> = mutableListOf()
//        val input = readLine()!!
//        val inputList = input.split(",").toMutableList()
//        var numIndices: MutableList<Int> = mutableListOf()
//        if (inputList.size > 1) {
//            numIndices = inputList.map { it.toInt() - 1 }.toMutableList()
//        }
    when (numIndices.size) {
        1 -> {
            when {
                activeCards.size > 1 -> return false
                activeCards.size == 0 -> return true
                hand[numIndices[0]].getPerceivedValue() >= activeCards[0].getPerceivedValue() -> return true
                else -> return false
            }
        }
        2, 3, 4 -> {
            when {
                numIndices.all { checkValidEntry(it, hand) } && checkIfIndicesHaveSameCardPerceivedValue(numIndices, hand) -> return true
                else -> return false
            }
        }
        5 -> {
//            numIndices.sortBy { it }
            when {
                handleFiveCardCombinations(hand, numIndices) -> return true
                else -> return false
            }
        }
        else->return false
//        2, 3, 4 -> {
//            when {
//                numIndices.all { checkValidEntry(it, hand) } && checkIfIndicesHaveSameCardPerceivedValue(numIndices, hand) -> numList = numIndices
//                else -> numList = inputCard(hand)
//            }
//        }
//        5 -> {
//            numIndices.sortBy { it }
//            when {
//                handleFiveCardCombinations(hand, numIndices) -> numList = numIndices
//                else -> numList = inputCard(hand)
//            }
//        }
//        else -> numList = inputCard(hand)
    }
    //if user is able to select a new card combination thats not length 5 and it wasn't a skip
//    if (numList.size < 5 && numList[0] != -1) {
//        activeFiveCardState = FiveCardCombos.NONE
//    }
    return true
//        when (inputList.size) {
//            1 -> {
//                when {
//                    inputList[0].equals("s") -> numList.add(-1)
//                    activeCards.size > 1 -> numList = inputCard(hand)
//                    inputList[0].toIntOrNull() != null -> {
//                        var numIndex: Int = input.toInt() - 1
//                        when {
//                            checkValidEntry(numIndex, hand) -> numList.add(numIndex)
//                            else -> numList = inputCard(hand)
//                        }
//                    }
//                    else -> numList = inputCard(hand)
//                }
//            }
//            2, 3, 4 -> {
//                when {
//                    numIndices.all { checkValidEntry(it, hand) } && checkIfIndicesHaveSameCardPerceivedValue(numIndices, hand) -> numList = numIndices
//                    else -> numList = inputCard(hand)
//                }
//            }
//            5 -> {
//                numIndices.sortBy { it }
//                when {
//                    handleFiveCardCombinations(hand, numIndices) -> numList = numIndices
//                    else -> numList = inputCard(hand)
//                }
//            }
//            else -> numList = inputCard(hand)
//        }
//        //if user is able to select a new card combination thats not length 5 and it wasn't a skip
//        if (numList.size < 5 && numList[0] != -1) {
//            activeFiveCardState = FiveCardCombos.NONE
//        }
//        return numList
    }

    private fun checkValidEntry(input: Int, hand: MutableList<Cards>): Boolean =
            input in 0..(hand.size - 1) && (activeCards.isEmpty() || hand[input] >= activeCards[0])

    private fun checkIfIndicesHaveSameCardPerceivedValue(indices: MutableList<Int>, hand: MutableList<Cards>): Boolean {
        val cardVal = hand[indices[0]].getPerceivedValue()
        for (i in indices) {
            if (hand[i].getPerceivedValue() != cardVal) {
                return false
            }
        }
        return true
    }

    private fun presConvert(card: String): Int {
        val newVal: Int
        when (card) {
            "Jack" -> newVal = 11
            "Queen" -> newVal = 12
            "King" -> newVal = 13
            "Ace" -> newVal = 14
            "2" -> newVal = 15
            else -> newVal = card.toInt()
        }
        return newVal
    }

    private fun addEntriesIntoActiveCards(indices: List<Int>, hand: MutableList<Cards>) {
        activeCards.clear()
        for (i in indices) {
            activeCards.add(hand[i])
        }
    }

    private fun computerDetectFiveCardCombos(player: Player, hand: MutableList<Cards>, indices: MutableList<Int>) {
        when (activeFiveCardState) {
            FiveCardCombos.FULL_HOUSE -> handleFullHouse(player.getFullHouse(), hand, indices)
            FiveCardCombos.STRAIGHT -> handleStraights(player.getStraights(), hand, indices)
            FiveCardCombos.FLUSH -> handleFlush(hand, indices, player.getFlush())
            FiveCardCombos.STRAIGHT_FLUSH -> handleFlush(hand, indices, player.getStraightFlush())
            FiveCardCombos.ROYAL_FLUSH -> handleRoyalFlush(player.getRoyalFlush(), hand, indices)
            FiveCardCombos.NONE -> println("ERROR: NONE FOUND")
        }
    }

    private fun handleFullHouse(fullHouseHands: MutableList<MutableList<Cards>>, hand: MutableList<Cards>, indices: MutableList<Int>) {
        if (fullHouseHands.isNotEmpty()) {
            for (fullHouse in fullHouseHands) {
                var acIndices = PokerHands.tripleAndDoubleInFullHouse(activeCards)
                if (acIndices.first != -1 && fullHouse[0] > activeCards[acIndices.first]) {
                    var tripleEntryOfHand = Util.searchCard(hand, 0, hand.size - 1, fullHouse[0])
                    if (tripleEntryOfHand != -1) {
                        var doubleEntryOfHand = Util.searchCard(hand, 0, hand.size - 1, fullHouse[3])
                        if (doubleEntryOfHand != -1) {
                            indices.addAll(mutableListOf(tripleEntryOfHand, tripleEntryOfHand + 1, tripleEntryOfHand + 2, doubleEntryOfHand, doubleEntryOfHand + 1))
                            return
                        }
                    }
                }
            }
        }
    }

    private fun handleStraights(straights: MutableList<MutableList<Cards>>, hand: MutableList<Cards>, indices: MutableList<Int>) {
        if (straights.isNotEmpty()) {
            for (straight in straights) {
                if (straight.last() >= activeCards.last()) {
                    var index = 0
                    while ((straight.size - (index + 1)) >= 5) {
                        if (straight[index] < activeCards[0]) {
                            index++
                        } else {
                            break
                        }
                    }
                    var entry: Int = Util.searchCard(hand, 0, hand.size - 1, straight[index])
                    var straightHand: MutableList<Int> = mutableListOf()
                    var i: Int = entry
                    straightHand.add(i)
                    while (i < hand.size - 2 && straightHand.size < 5) {
                        i++
                        if (Util.perceivedEquals(hand[straightHand.last()], hand[i])) {
                            continue
                        } else if ((hand[straightHand.last()].getPerceivedValue() + 1) == hand[i].getPerceivedValue()) {
                            straightHand.add(i)
                        } else {
                            break
                        }

                    }
                    if (straightHand.size == 5) {
                        indices.addAll(straightHand)
                        return
                    }
                }
            }
        }
    }

    private fun handleFlush(hand: MutableList<Cards>, indices: MutableList<Int>, list: MutableList<MutableList<Cards>>) {
        var allFlushes: MutableList<MutableList<Int>> = mutableListOf()
        for (flush in list) {
            var flushLastVal = if (flush!!.size >= 5) flush.last().getPerceivedValue() else continue
            if (activeCards.last().getPerceivedValue() < flushLastVal) {
                var index = flush.lastIndex
                while (index > 4) {
                    if (activeCards.last() < flush[index]) {
                        index--
                    } else {
                        break
                    }
                }
                var tempList: MutableList<Int> = mutableListOf()
                for (i in (index - 4)..index) {
                    tempList.add(Util.searchCard(hand, 0, hand.size - 1, flush[i], true))
                }
                allFlushes.add(tempList)
            }
        }
        if (allFlushes.isNotEmpty()) {
            var highestFlush: Int = 0
            var count = 0;
            for (flush in allFlushes) {
                if (highestFlush != 0 && hand[flush.last()] > hand[allFlushes[highestFlush].last()]) highestFlush = count
                count++
            }
            indices.addAll(allFlushes[highestFlush])
        }
    }

    private fun handleRoyalFlush(listOfRoyals: MutableList<MutableList<Cards>>, hand: MutableList<Cards>, indices: MutableList<Int>) {
        for (royals in listOfRoyals) {
            var tempList: MutableList<Int> = mutableListOf()
            for (i in 0..(royals.size - 1)) {
                tempList.add(Util.searchCard(hand, 0, hand.size - 1, royals[i], true))
            }
            indices.addAll(tempList)
            return
        }
    }

    private fun handleFiveCardCombinations(hand: MutableList<Cards>, indices: MutableList<Int>): Boolean {
        var isValidCombo = true
        when {
            PokerHands.checkIfRoyal(hand, indices) -> activeFiveCardState = FiveCardCombos.ROYAL_FLUSH
            PokerHands.checkIfStraightFlush(hand, indices) -> activeFiveCardState = FiveCardCombos.STRAIGHT_FLUSH
            PokerHands.checkIfStraight(hand, indices) -> activeFiveCardState = FiveCardCombos.STRAIGHT
            PokerHands.isFullHouse(hand, indices) -> activeFiveCardState = FiveCardCombos.FULL_HOUSE
            PokerHands.isFlush(hand, indices) -> activeFiveCardState = FiveCardCombos.FLUSH
            else -> isValidCombo = false
        }
        return isValidCombo
    }

    private fun handleIdenticalCards(player: Player, hand: MutableList<Cards>, indices: MutableList<Int>, numIdentical: Int) {
        var identicals: MutableList<MutableList<Cards>> = mutableListOf()
        when (numIdentical) {
            2 -> identicals = player.getDoubles()
            3 -> identicals = player.getTriples()
            4 -> identicals = player.getQuads()
        }
        var identicalFirstEntries = identicals.map { it[0] }.toMutableList()
        val firstEntry = Util.searchCard(identicalFirstEntries, 0, identicalFirstEntries.size - 1, activeCards[0])
        if (firstEntry != -1) {
            var indexFound = Util.searchCard(hand, 0, hand.size - 1, identicalFirstEntries[firstEntry])
            for (i in 0..(numIdentical - 1)) {
                indices.add(indexFound + i)
            }
        }
    }

    private fun burned(hand: MutableList<Cards>, indices: List<Int>): Boolean {
        if (activeCards.isEmpty() || activeCards.size != indices.size) return false
        for (i in 0..(indices.size - 1)) {
            if (!Util.perceivedEquals(activeCards[i], hand[indices[i]])) {
                return false
            }
        }
        return true
    }

    private operator fun Cards.compareTo(other: Cards): Int {
        return this.getPerceivedValue() - other.getPerceivedValue()
    }
}