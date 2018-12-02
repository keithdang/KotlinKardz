enum class Suits{
    SPADES,CLUBS,DIAMONDS,HEARTS
}
public class Cards(private val num:Int, private val suit:Suits,val styleValue:String.()->Int,private val placement:Int){
    private val cardName=Util.genCardName(num)
    private val perceivedValue=cardName.styleValue()
    var isSelected=false
    fun printCardLn(){
        println("${cardName} of ${suit}")
    }
    fun printCardLabel(num:Int){
        print("${num}:${cardName} of ${suit},\t")
    }
    fun printCardTab(num:Int){
        print("${num}:${cardName} of ${suit},\t")
    }
    fun printCardTabNolabel(){
        print("${cardName} of ${suit},\t")
    }
    fun getPerceivedValue():Int=perceivedValue
    fun getOriginalPlacement():Int=placement
    fun getSuit():Suits=suit
    fun getCardName():String=cardName
}