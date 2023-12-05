package Coursework.Trucks

import Coursework.Units.*
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque
import kotlin.properties.Delegates
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class UnloadTruck (val truckType: TruckType){
    private var myUnits = mutableListOf<MyUnit>()
    val id = UUID.randomUUID().toString()
    var weight by Delegates.notNull<Int>()

    private var largeUnitList = listOf(LargeMyUnit.Bed, LargeMyUnit.Fridge, LargeMyUnit.Table)
    private var mediumUnitList = listOf(MediumMyUnit.Chair, MediumMyUnit.Microwave, MediumMyUnit.Mirror)
    private var smallUnitList = listOf(SmallMyUnit.Cup, SmallMyUnit.Kettle, SmallMyUnit.Shoes)
    private var foodUnitList = listOf(FoodMyUnit.Bread, FoodMyUnit.Milk, FoodMyUnit.Potatoes)

    init {
        myUnits = generateUnits()
    }
    suspend fun unloadTo(id: String, storage: ConcurrentLinkedDeque<MyUnit>) {
        for (unit in myUnits) {
            println("[$id] Разгружаем ${unit.name} весом ${unit.weight} на склад...")
            delay(unit.timeMin.seconds.inWholeMilliseconds)
            storage.add(unit)
            println("[$id] ${unit.name} весом ${unit.weight} разгружен!")
        }
        myUnits.clear()
    }

    private fun generateUnits(): MutableList<MyUnit>{
        var weight = Random.nextInt(0, truckType.maxWeight + 1)
        this.weight = weight
        println("[$id] Грузовик весом $weight поступил на разгрузку")
        val list = myUnits
        var type: Int
        var category: Int
        while(weight > 0){
            var currentUnit : MyUnit
            if(list.isEmpty()){
                type = Random.nextInt(1,5)
                category = Random.nextInt(0,3)
                currentUnit = when (type) {
                    1 -> {
                        largeUnitList[category]
                    }
                    2 -> {
                        mediumUnitList[category]
                    }
                    3 -> {
                        smallUnitList[category]
                    }
                    else -> {
                        foodUnitList[category]
                    }
                }
                if (currentUnit.weight <= weight) {
                    list.add(currentUnit)
                } else continue
            }
            else{
                if(list[0] is FoodMyUnit){
                    category = Random.nextInt(0,3)
                    currentUnit = foodUnitList[category]
                    if (currentUnit.weight <= weight) {
                        list.add(currentUnit)
                    } else continue
                }else{
                    type = Random.nextInt(1,4)
                    category = Random.nextInt(0,3)
                    currentUnit = when (type) {
                        1 -> {
                            largeUnitList[category]
                        }
                        2 -> {
                            mediumUnitList[category]
                        }
                        else-> {
                            smallUnitList[category]
                        }
                    }
                    if (currentUnit.weight <= weight) {
                        list.add(currentUnit)
                    } else continue
                }
            }
            weight -= currentUnit.weight
        }
        return list
    }
}