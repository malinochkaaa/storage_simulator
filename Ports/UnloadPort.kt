package Coursework.Ports

import Coursework.Trucks.UnloadTruck
import Coursework.Units.MyUnit
import java.util.concurrent.ConcurrentLinkedDeque

class UnloadPort(generator: Generator, storage: Storage, private val maxAvailablePorts: Int = 3) {

    var waitList = Stack<UnloadTruck>()
    private var currentAvailablePorts = maxAvailablePorts
    private var globalStorageList: ConcurrentLinkedDeque<MyUnit> = storage.storage
    private val unloadTrucksChannel = generator.generateUnloadTrucks()

    suspend fun portUnload(){
        if(currentAvailablePorts in 1..maxAvailablePorts){
            currentAvailablePorts--
            val truck = if(!waitList.isEmpty()){
                waitList.pop()!!
            } else {
                unloadTrucksChannel.receive()
            }
            println("[${truck.id}] Грузовик с весом ${truck.weight} встал в порт на разгрузку")
            truck.unloadTo(truck.id, globalStorageList)
            currentAvailablePorts++
            println("[${truck.id}] Грузовик с весом ${truck.weight} закончил разгрузку")
        } else {
            val truck = unloadTrucksChannel.receive()
            waitList.push(truck)
        }
    }


}