package sample

import kotlinx.cinterop.*
import platform.posix.FILE
import platform.posix.fgets
import platform.posix.fopen

fun hello(): String = "Hello, Kotlin/Native!"
const val ARRAY_SIZE = 26
const val FILE_PATH = "/Users/mgi30/theFile.txt"

fun main() {
    println(hello())
    try {
        fopen(FILE_PATH, "r")?.let {
            val cPointerToFile: CPointer<FILE> = it
            nativeHeap.allocArray<ByteVar>(ARRAY_SIZE).let {
                val byteArray = ByteArray(ARRAY_SIZE)
                fgets(it, ARRAY_SIZE, cPointerToFile )?.let {
                    for (index in 0 .. ARRAY_SIZE-1) {
                        byteArray[index] = it[index]
                    }
                }
                println("marc byte array ="+byteArray.decodeToString())
            }
        } ?: run {
            throw Exception("error opening file "+ FILE_PATH)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

