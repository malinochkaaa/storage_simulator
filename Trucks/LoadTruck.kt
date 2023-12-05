package Coursework.Trucks

import Coursework.Units.MyUnit
import kotlinx.coroutines.delay
import java.util.*
import kotlin.time.Duration.Companion.seconds

class LoadTruck(val truckType: TruckType) {
    var currentWeight = 0
    val id = UUID.randomUUID().toString()

    suspend fun load(myUnit: MyUnit){
        delay(myUnit.timeMin.seconds.inWholeMilliseconds)
        currentWeight = myUnit.weight
    }
}