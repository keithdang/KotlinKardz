package logo

import react.*
import react.dom.*
import Suits
import kotlinext.js.*
import kotlinx.html.style

@JsModule("src/logo/react.svg")
external val reactLogo: dynamic
@JsModule("src/logo/kotlin.svg")
external val kotlinLogo: dynamic
@JsModule("src/images/hearts.png")
external val heartsLogo: dynamic
@JsModule("src/images/spade.png")
external val spadesLogo: dynamic
@JsModule("src/images/clubs.png")
external val clubsLogo: dynamic
@JsModule("src/images/diamond.png")
external val diamondLogo: dynamic
fun RBuilder.logo(height: Int = 100) {
    div("Logo") {
        attrs.jsStyle.height = height
        img(alt = "React logo.logo", src = reactLogo, classes = "Logo-react") {}
        img(alt = "Kotlin logo.logo", src = kotlinLogo, classes = "Logo-kotlin") {}
    }
}

fun RBuilder.suitLogo(suits: Suits, height: Int = if(suits==Suits.DIAMONDS)30 else 25) {
    div {
        attrs.jsStyle.height = height
        img( src = when(suits){
            Suits.HEARTS-> heartsLogo
            Suits.DIAMONDS-> diamondLogo
            Suits.CLUBS-> clubsLogo
            Suits.SPADES-> spadesLogo
        }, classes = "suits") {}
    }
}

