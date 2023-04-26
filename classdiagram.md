``` mermaid
classDiagram
Field <|-- Street
Field <|-- TrainStation
Field <|-- ServiceField
Field <|-- ChanceField
Field <|-- TaxField
Field <|-- CommunityChest
Field <|-- SpecialField
SpecialField <|-- GoField
SpecialField <|-- JailField
SpecialField <|-- FreeParkingField
SpecialField <|-- GoToJailField
note for Field "Abstract"
note for SpecialField "Abstract"

class Field{
+String name 
+String color
+boolean hasMortgage
+takeOutMortgage()
}

class Street{
+int price
+Buildings[] buildings
+int[] upgradeValues
+Player owner
+build(Building building)
+demolish(Building building)
+sell(Player newOwner)
}

class TrainStation{
+int price
+Player owner
+int[] upgradeValues
+sell(Player newOwner)
}

class ServiceField{
+int serviceFee
+Player owner
+int[] upgradeFactors
+sell(Player newOwner)
}

class ChanceField{
+Card[] chanceCards
+Card getCard()
}

class TaxField{
+int tax
}

class CommunityChest{
+Card[] communityChestCards
+Card getCard()
}

class SpecialField{
+action(Player player)
}
```

``` mermaid
classDiagram
class Board{
+Field[] fields
+Player[] players
+int calculateScore(Player player)
+setupBoard()
+boolean canBuild(Player player, Field field)
}
class Player{
+String name
+String color
+int score
+int doubles
+int money
+boolean isInJail
+boolean hasJailCard
+boolean hasDouble
+String figurine
+Field[] properties
+checkMortgages()
+drawChanceCard(Card card)
+drawCommunityChestCard(Card card)
+Field[] getProperties()
+move(int n)
+payRent(Field Field)
+payTax(TaxField taxfield)
+int[] rollDice()
+trade(Player other)
}
```
