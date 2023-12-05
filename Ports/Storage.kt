package Coursework.Ports

import Coursework.Units.MyUnit
import java.util.concurrent.ConcurrentLinkedDeque

class Storage {
    val storage = ConcurrentLinkedDeque<MyUnit>()
}