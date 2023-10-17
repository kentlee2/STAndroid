package com.example.myandroidtest.bean

/**
 * 建造者模式示例
 */
data class Car(val make: String, val model: String, val year: Int, val color: String) {

    data class Builder(var make: String = "", var model: String = "", var year: Int = 0, var color: String = "") {

        fun setMake(make: String) = apply { this.make = make }

        fun setModel(model: String) = apply { this.model = model }

        fun setYear(year: Int) = apply { this.year = year }

        fun setColor(color: String) = apply { this.color = color }

        fun build() = Car(make, model, year, color)
    }

    override fun toString(): String {
        return "Car(make='$make', model='$model', year=$year, color='$color')"
    }
}

fun main() {
    val car = Car.Builder()
        .setMake("Toyota")
        .setModel("Camry")
        .setYear(2020)
        .setColor("Red")
        .build()

    println(car)
    // Output: Car(make='Toyota', model='Camry', year=2020, color='Red')
}