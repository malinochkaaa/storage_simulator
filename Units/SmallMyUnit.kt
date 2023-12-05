package Coursework.Units

sealed class SmallMyUnit(override val weight: Int, override val name: String, override val timeMin: Int): MyUnit {
    object Cup: SmallMyUnit(1, "кружка", 2)
    object Kettle: SmallMyUnit(3, "бутылка", 2)
    object Shoes: SmallMyUnit(2, "обувь", 2)
}