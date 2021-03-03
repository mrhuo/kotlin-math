package com.mrhuo.kotlinmath

val Float.radian: Float
    get() {
        return (this * Math.PI / 180).toFloat()
    }
val Double.radian: Float
    get() {
        return (this * Math.PI / 180).toFloat()
    }
val Int.radian: Float
    get() {
        return (this * Math.PI / 180).toFloat()
    }

val Float.degree: Float
    get() {
        return (this * 180 / Math.PI).toFloat()
    }
val Double.degree: Float
    get() {
        return (this * 180 / Math.PI).toFloat()
    }
val Int.degree: Float
    get() {
        return (this * 180 / Math.PI).toFloat()
    }