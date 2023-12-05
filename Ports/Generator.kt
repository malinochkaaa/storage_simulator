package Coursework.Ports

import Coursework.Trucks.LoadTruck
import Coursework.Trucks.TruckType
import Coursework.Trucks.UnloadTruck
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class Generator(val coroutineContext: CoroutineContext) {
    fun generateUnloadTrucks(): Channel<UnloadTruck>{
        val channel = Channel<UnloadTruck>()
        var type: Int

        CoroutineScope(coroutineContext).launch {
            while (true) {
                type = Random.nextInt(1,4)
                when (type){
                    1 -> {
                        channel.send(UnloadTruck(TruckType.LARGE))
                    }
                    2 -> {
                        channel.send(UnloadTruck(TruckType.MEDIUM))
                    }
                    3 -> {
                        channel.send(UnloadTruck(TruckType.LIGHT))
                    }
                }
                delay(60_000L)
            }
        }
        return channel
    }
    fun generateLoadTrucks(): Channel<LoadTruck>{
        val channel = Channel<LoadTruck>()
        var type: Int
        CoroutineScope(coroutineContext).launch {
            while (true) {
                type = Random.nextInt(1,3)
                when (type){
                    1 -> channel.send(LoadTruck(TruckType.MEDIUM))
                    2 -> channel.send(LoadTruck(TruckType.LIGHT))
                }
            }
        }
        return channel
    }
}