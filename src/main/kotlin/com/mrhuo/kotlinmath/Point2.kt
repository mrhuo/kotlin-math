package com.mrhuo.kotlinmath

import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * 点
 */
interface IPoint2: ICloneable<IPoint2> {
    var x: Float
    var y: Float
}

open class Point2(override var x: Float, override var y: Float): IPoint2 {
    constructor(): this(0, 0) {}
    constructor(x: Int, y: Int): this(x.toFloat(), y.toFloat()) {}
    constructor(point: Point2): this(point.x, point.y) {}
    constructor(point: IPoint2): this(point.x, point.y) {}

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Point2) {
            return false
        }
        return this.x == other.x && this.y == other.y
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    override fun toString(): String {
        return "Point2($x, $y)"
    }

    override fun clone(): IPoint2 {
        return Point2(x, y)
    }
}

operator fun IPoint2.plus(point: IPoint2): IPoint2 {
    return this.moveTo(this.x + point.x, this.y + point.y)
}

operator fun IPoint2.minus(point: IPoint2): IPoint2 {
    return this.moveTo(this.x - point.x, this.y - point.y)
}

operator fun IPoint2.times(point: IPoint2): IPoint2 {
    return this.moveTo(this.x * point.x, this.y * point.y)
}

operator fun IPoint2.div(point: IPoint2): IPoint2 {
    return this.moveTo(this.x / point.x, this.y / point.y)
}

operator fun IPoint2.rem(point: IPoint2): IPoint2 {
    return this.moveTo(this.x % point.x, this.y % point.y)
}

operator fun IPoint2.inc(): IPoint2 {
    return this.moveTo(this.x + 1, this.y + 1)
}

operator fun IPoint2.dec(): IPoint2 {
    return this.moveTo(this.x - 1, this.y - 1)
}

fun IPoint2.add(x1: Int, y1: Int): IPoint2 {
    return this.addX(x1).addY(y1)
}

fun IPoint2.add(x1: Float, y1: Float): IPoint2 {
    return this.addX(x1).addY(y1)
}

fun IPoint2.add(point: IPoint2): IPoint2 {
    return this.addX(point.x).addY(point.y)
}

fun IPoint2.addX(x1: Int): IPoint2 {
    this.x += x1
    return this
}

fun IPoint2.addX(x1: Float): IPoint2 {
    this.x += x1
    return this
}

fun IPoint2.addY(y1: Int): IPoint2 {
    this.y += y1
    return this
}

fun IPoint2.addY(y1: Float): IPoint2 {
    this.y += y1
    return this
}

fun IPoint2.moveXTo(x1: Int): IPoint2 {
    this.x = x1.toFloat()
    return this
}

fun IPoint2.moveXTo(x1: Float): IPoint2 {
    this.x = x1
    return this
}

fun IPoint2.moveYTo(y1: Int): IPoint2 {
    this.y = y1.toFloat()
    return this
}

fun IPoint2.moveYTo(y1: Float): IPoint2 {
    this.y = y1
    return this
}

fun IPoint2.moveTo(x1: Int, y1: Int): IPoint2 {
    this.x = x1.toFloat()
    this.y = y1.toFloat()
    return this
}

fun IPoint2.moveTo(x1: Float, y1: Float): IPoint2 {
    this.x = x1
    this.y = y1
    return this
}

fun IPoint2.mirror(): IPoint2 {
    this.mirrorX()
    this.mirrorY()
    return this
}

fun IPoint2.mirrorX(): IPoint2 {
    this.x = -1 * x
    return this
}

fun IPoint2.mirrorY(): IPoint2 {
    this.y = -1 * y
    return this
}

/**
 * 获取点在平面直角坐标系中的象限，返回 0, 1, 2, 3, 4
 * 如果点坐标 x 或 y 过轴时不在任何一象限，返回0。
 *
 * （x，y）的正负性为+，+（正，正）时该点在第一象限
 * （x，y）的正负性为-，+（负，正）时该点在第二象限
 * （x，y）的正负性为-，-（负，负）时该点在第三象限
 * （x，y）的正负性为+，-（正，负）时该点在第四象限
 */
val IPoint2.quadrant: Int
    get(){
        if (this.x > 0 && this.y > 0) {
            return 1
        }
        if (this.x < 0 && this.y > 0) {
            return 2
        }
        if (this.x < 0 && this.y < 0) {
            return 3
        }
        if (this.x > 0 && this.y < 0) {
            return 4
        }
        return 0
    }

fun IPoint2.rotateWith(point: IPoint2, angle: Int): IPoint2 {
    return rotateWith(point, angle.toFloat())
}

fun IPoint2.rotateWith(point: IPoint2, angle: Float): IPoint2 {
    val _angleRad = angle.radian.toDouble()
    val x0 = (x - point.x) * cos(_angleRad) - (y - point.y) * sin(_angleRad) + point.x
    val y0 = (x - point.x) * sin(_angleRad) + (y - point.y) * cos(_angleRad) + point.y
    return this.moveTo(x0.toFloat(), y0.toFloat())
}

fun IPoint2.distanceTo(point: IPoint2): Float {
    return sqrt(
            (this.x - this.y).toDouble().pow(2.0) + (point.x - point.y).toDouble().pow(2.0)
    ).toFloat()
}

fun IPoint2.centerTo(point: IPoint2): IPoint2 {
    if (this == point) {
        return Point2()
    }
    val x = (this.x + point.x) / 2
    val y = (this.y + point.y) / 2
    return Point2(x, y)
}