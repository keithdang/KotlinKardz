## A Kotlin Based Card Game
Further developed https://github.com/keithdang/cardGame from a command line based game to a kotlin-react web application with UI for you to play with.

This game is based off of card playing games like President & Big 2 where players are given an equal amount of random cards and they must play a card (or a valid combination or some sorts) from their given hand of a higher value from what previous players have played. The first player to exhaust all their cards wins

Features include
- Computers being able to autonously determine the best card to play at a given instant
- Using Sorting & Binary Search to improve time complexity when computers are finding the best card
- Ability to select and play various card combinations based on poker styles
- Detecting and responding to various card combiantions (singles, doubles, triples, straights...etc poker hand styles)
- Burns: When a player plays a card of equal value, they "burn" that round and may restart the round with any card they want to play

## Steps
```
git clone https://github.com/keithdang/KotlinKardz
cd KotlinKardz
npm i react-scripts-kotlin
npm start 
(or yarn start)
```
