package Coursework

import Coursework.Ports.Generator
import Coursework.Ports.LoadPort
import Coursework.Ports.Storage
import Coursework.Ports.UnloadPort
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val storage = Storage()
        val generator = Generator(coroutineContext)
        val unloadPort = UnloadPort(generator, storage)
        val loadPort = LoadPort(generator, storage)
        launch {
            while (true) {
                unloadPort.portUnload()
            }
        }
        launch {
            while (true) {
                loadPort.portLoad()
            }
        }
    }
}
