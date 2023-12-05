package Coursework.Units

sealed class LargeMyUnit(override val weight: Int, override val name: String, override val timeMin: Int): MyUnit {
    object Table: LargeMyUnit(30, "стол", 15)
    object Fridge: LargeMyUnit(50, "холодильник", 17)
    object Bed: LargeMyUnit(40, "кровать", 20)
}