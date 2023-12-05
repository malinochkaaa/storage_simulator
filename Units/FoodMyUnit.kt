package Coursework.Units

sealed class FoodMyUnit(override val weight: Int, override val name: String, override val timeMin: Int): MyUnit {
    object Milk: FoodMyUnit(1, "молоко", 1)
    object Bread: FoodMyUnit(2, "хлеб", 1)
    object Potatoes: FoodMyUnit(3, "картошка", 1)
}