package Coursework.Units

sealed class MediumMyUnit(override val weight: Int, override val name: String, override val timeMin: Int): MyUnit {
    object Chair: MediumMyUnit(20, "стол", 5)
    object Microwave: MediumMyUnit(10, "микроволновая печь", 7)
    object Mirror: MediumMyUnit(15, "зеркало", 8)
}