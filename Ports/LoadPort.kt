package Coursework.Ports

import Coursework.Units.*
import kotlinx.coroutines.delay
import java.util.concurrent.ConcurrentLinkedDeque
import kotlin.random.Random

class LoadPort(generator: Generator, storage: Storage, private val maxAvailablePorts: Int = 1) {

    private var currentAvailablePorts = maxAvailablePorts
    private val globalStorageList: ConcurrentLinkedDeque<MyUnit> = storage.storage
    private var truckStorageList = mutableListOf<MyUnit>()

    private var largeUnitList = listOf(LargeMyUnit.Bed, LargeMyUnit.Fridge, LargeMyUnit.Table)
    private var mediumUnitList = listOf(MediumMyUnit.Chair, MediumMyUnit.Microwave, MediumMyUnit.Mirror)
    private var smallUnitList = listOf(SmallMyUnit.Cup, SmallMyUnit.Kettle, SmallMyUnit.Shoes)
    private var foodUnitList = listOf(FoodMyUnit.Bread, FoodMyUnit.Milk, FoodMyUnit.Potatoes)
    private val loadTrucksChannel = generator.generateLoadTrucks()

    suspend fun portLoad(){
        if(currentAvailablePorts == 1) {
            val truck = loadTrucksChannel.receive()
            currentAvailablePorts = 0
            println("[${truck.id}] Грузовик весом ${truck.truckType.maxWeight} встал в порт загрузки")
            var type = 0
            while (truck.currentWeight < truck.truckType.maxWeight) {
                if(truckStorageList.isEmpty()){
                    type = Random.nextInt(1,5)
                }else{
                    when {
                        truckStorageList[0] is LargeMyUnit -> type = 1
                        truckStorageList[0] is MediumMyUnit -> type = 2
                        truckStorageList[0] is SmallMyUnit -> type = 3
                        truckStorageList[0] is FoodMyUnit -> type = 4
                    }
                }
                val currentUnit: MyUnit? = when (type) {
                    1 -> globalStorageList.find {largeUnitList.contains(it)}
                    2 -> globalStorageList.find {mediumUnitList.contains(it)}
                    3 -> globalStorageList.find {smallUnitList.contains(it)}
                    4 -> globalStorageList.find {foodUnitList.contains(it)}
                    else -> null
                }
                if (currentUnit == null){
                    delay(500)
                    continue
                }
                truckStorageList.add(currentUnit)
                truck.load(currentUnit)
                globalStorageList.remove(currentUnit)
                println("[${truck.id}] Извлекается со склада: ${currentUnit.name} весом ${currentUnit.weight}, оставшийся вес: ${truck.currentWeight}")
            }
            currentAvailablePorts = 1
            println("[${truck.id}] Грузовик закончил разгрузку")
        }
    }
}
