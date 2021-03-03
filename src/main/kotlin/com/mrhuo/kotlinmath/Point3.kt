package com.mrhuo.kotlinmath

interface IPoint3: IPoint2 {
    val z: Float
}

//open class Point3(override var x: Float, override var y: Float, override val z: Float): IPoint3 {
//    constructor(x: Int, y: Int, z: Int): this(x.toFloat(), y.toFloat(), z.toFloat()) {
//    }
//
//    override fun equals(other: Any?): Boolean {
//        if (other == null || other !is Point3) {
//            return false
//        }
//        return this.x == other.x && this.y == other.y && this.y == other.y
//    }
//
//    override fun hashCode(): Int {
//        var result = x.hashCode()
//        result = 31 * result + y.hashCode()
//        result = 31 * result + z.hashCode()
//        return result
//    }
//
//    override fun toString(): String {
//        return "Point3($x, $y, $z)"
//    }
//}