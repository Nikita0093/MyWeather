package com.example.myweather.lesson4

import android.util.Log

data class Lesson4(var name: String = "Никита", var age: Int = 28)

var lesson4 = Lesson4()

fun test1() {
    Log.d("@@@", "До применения test1 : $lesson4")
    with(lesson4) {
        name = "Джон"
        age = 20
    }
    Log.d("@@@", "После применения test1 : $lesson4 \n")

}

fun test2() {
    Log.d("@@@", "До применения test2 : $lesson4")
    lesson4.let {
        it.age = 25
        it.name = "Миша"
    }
    Log.d("@@@", "После применения test2 : $lesson4 \n")

}

fun test3() {
    Log.d("@@@", "До применения test3 : $lesson4")
    lesson4.apply {
        age = 37
    }
    Log.d("@@@", "После применения test3 : $lesson4 \n")

}

fun test4() {
    Log.d("@@@", "До применения test4 : $lesson4")
    lesson4.also {
        it.name = "Карлос"
        it.age = 99
    }
    Log.d("@@@", "После применения test4 : $lesson4 \n")

}

fun test5() {
    Log.d("@@@", "До применения test5 : $lesson4")
    lesson4.run {
        age = 200
    }
    Log.d("@@@", "После применения test5 : $lesson4 \n")

}