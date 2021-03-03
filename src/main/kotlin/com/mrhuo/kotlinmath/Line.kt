package com.mrhuo.kotlinmath

import kotlin.math.*

/**
 * Line segment
 * 线段
 */
interface ILine: ICloneable<ILine> {
    var start: Point2
    var end: Point2
}

open class Line(override var start: Point2, override var end: Point2): ILine {
    constructor(x1: Int, y1: Int, x2: Int, y2: Int) : this(Point2(x1, y1), Point2(x2, y2)) {}
    constructor(x1: Float, y1: Float, x2: Float, y2: Float) : this(Point2(x1, y1), Point2(x2, y2)) {}
    constructor(start: IPoint2, end: IPoint2) : this(start as Point2, end as Point2) {}

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Line) {
            return false
        }
        return this.start == other.start && this.end == other.end
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }

    override fun toString(): String {
        return "Line{($start), ($end)}"
    }

    override fun clone(): ILine {
        return Line(this.start.clone(), this.end.clone())
    }
}

/**
 * Get line length
 * 获取线段长度
 * sqrt((x1.x - x2.x) ^ 2 + (x1.y - x2.y) ^ 2)
 */
val ILine.length: Float
    get() {
        return this.start.distanceTo(this.end)
    }

/**
 * Get the slope of the line
 * 获取线段斜率，斜率不存在时，返回 null
 * k = (y2 - y1) / (x2 - x1)
 */
val ILine.slope: Float?
    get() {
        //垂直于 x 轴的线，不存在斜率
        if (end.x == start.x) {
            return null
        }
        return (end.y - start.y) / (end.x - start.x)
    }

/**
 * 获取线段的反斜率
 */
val ILine.verticalLineSlope: Float?
    get() {
        if(this.slope == null || abs(this.slope!!) == 0f) {
            return null
        }
        return -1 / this.slope!!
    }

/**
 * 获取线段的倾斜角（弧度）
 * 如果线段的斜率为k，倾斜角为α，则：
 * 1、当k不存在时,α = PI / 2;
 * 2、当k≥0时,α = arctan(k);
 * 3、当k≥0时,α = PI + arctan(k);
 */
val ILine.angleRad: Float
    get() {
        val k = this.slope ?: return (PI / 2).toFloat()
        if (k >= 0) {
            return atan(k)
        }
        //else k < 0
        return (PI + atan(k)).toFloat()
    }

/**
 * Gets the inclination angle (degree) of the line segment
 * 获取线段的倾斜角（度数）
 */
val ILine.angleDegree: Float
    get() {
        return this.angleRad.degree
    }

/**
 * Whether it is a horizontal line. When the slope is equal to 0, it is a horizontal line
 * 是否水平线。斜率等于0时，为水平线
 */
val ILine.isHorizontalLine: Boolean
    get() {
        return this.slope == 0f
    }

/**
 * Whether it is a vertical line. When the slope does not exist, it is a vertical line
 * 是否垂直线。斜率不存在时为垂线
 */
val ILine.isVerticalLine: Boolean
    get() {
        return this.slope == null
    }

/**
 * Gets the midpoint of a line segment
 * 获取线段中点
 * x = (x1.x + x2.x) / 2
 * y = (x1.y + x2.y) / 2
 */
fun ILine.center(): IPoint2 {
    return this.start.centerTo(this.end)
}

/**
 * 延长或缩短线段，length < 0 时为缩短
 * 对于线段 startPoint(x1, y1) ~ endPoint(x2, y2)，从 startPoint 到 endPoint 方向延长或缩短，
 * 即在 endPoint 基础上延长，返回延长或缩短后的线段
 *
 * 对于线段 A(x1, y1)-B(x2, y2)，延长 length 后得到 C(x3, y3)。
 * 延长的 length 已知。
 * y 轴方向的点 C 距离点 B的距离长度 abs(y3 - y2) 设为 ylen
 * x 轴方向的点 C 距离点 B的距离长度 abs(x3 - x2) 设为 xlen
 * 线段的倾斜角已知，所以根据延长长度，就可以获取到另外两边的距离。
 * sin(angle) = ylen / length ===> ylen = length * sin(angle)
 * cos(angle) = xlen / length ===> xlen = length * cos(angle)
 */
fun ILine.extendLength(length: Float): ILine {
    if (length == 0f) {
        //增加长度 0，线段无变化
        return this
    }
    if (this.start.x == this.end.x) {
        //平行于 y 轴的线段，增加长度后，只增加 y 轴数据即可
        if (this.end.y > this.start.y) {
            this.end.addY(length) //线段方向朝上
        } else {
            this.end.addY(-length)//线段方向朝下
        }
        return this
    }
    if (this.start.y == this.end.y) {
        //平行于 x 轴的线段，增加长度后，只增加 x 轴数据即可
        if (this.end.x > this.start.x) {
            this.end.addX(length) //线段方向朝右
        } else {
            this.end.addX(-length)//线段方向朝左
        }
        return this
    }
    //计算倾斜角
    var angleDegree = this.angleDegree
    if (angleDegree > 90) {
        angleDegree = 180 - angleDegree
    }
    val angleRad = angleDegree.radian
    val ylen = length * sin(angleRad)
    val xlen = length * cos(angleRad)
    if (this.end.y > this.start.y) {
        this.end.addY(ylen) //线段方向朝右上或左上
    } else {
        this.end.addY(-ylen)//线段方向朝左下或右下
    }
    if (this.end.x > this.start.x) {
        this.end.addX(xlen) //线段方向朝右上或左上
    } else {
        this.end.addX(-xlen)//线段方向朝左下或右下
    }
    return this
}

/**
 * 延长或缩短线段，length < 0 时为缩短
 */
fun ILine.extendLength(length: Int): ILine {
    return this.extendLength(length.toFloat())
}

/**
 * 反向延长或缩短线段，length < 0 时为缩短。
 * 对于线段 startPoint(x1, y1) ~ endPoint(x2, y2)，从 endPoint 到 startPoint 方向延长，
 * 即在 startPoint 基础上延长，返回线段
 */
fun ILine.reverseExtendLength(length: Float): ILine {
    if (length == 0f) {
        //增加长度 0，线段无变化
        return this
    }
    if (this.start.x == this.end.x) {
        //平行于 y 轴的线段，增加长度后，只增加 y 轴数据即可
        if (this.end.y > this.start.y) {
            this.start.addY(-length) //线段方向朝上，起点在下，所以用减
        } else {
            this.start.addY(length)//线段方向朝下，起点在上，所以用加
        }
        return this
    }
    if (this.start.y == this.end.y) {
        //平行于 x 轴的线段，增加长度后，只增加 x 轴数据即可
        if (this.end.x > this.start.x) {
            this.start.addX(-length) //线段方向朝右
        } else {
            this.start.addX(length)//线段方向朝左
        }
        return this
    }
    //计算倾斜角
    var angleDegree = this.angleDegree
    if (angleDegree > 90) {
        angleDegree = 180 - angleDegree
    }
    val angleRad = angleDegree.radian
    val ylen = length * sin(angleRad)
    val xlen = length * cos(angleRad)
    if (this.end.y > this.start.y) {
        this.start.addY(-ylen) //线段方向朝右上或左上
    } else {
        this.start.addY(ylen)//线段方向朝左下或右下
    }
    if (this.end.x > this.start.x) {
        this.start.addX(-xlen) //线段方向朝右上或左上
    } else {
        this.start.addX(xlen)//线段方向朝左下或右下
    }
    return this
}

/**
 * Reverse lengthening or shortening
 * 反向延长或缩短
 */
fun ILine.reverseExtendLength(length: Int): ILine {
    return this.reverseExtendLength(length.toFloat())
}

/**
 * Connect two points and return a line. (the target point is the end point)
 * 两点连接，返回一条线。（目标点为终点）
 */
fun Point2.connect(point: Point2): ILine {
    return Line(this, point)
}

/**
 * Connect two points and return a line. (the target point is the start point)
 * 两点连接，返回一条线。（目标点为起点）
 */
fun Point2.connectTo(point: Point2): ILine {
    return Line(point, this)
}

/**
 * 返回线段经过的象限，返回2个元素的 IntArray
 *
 * [0] 是起点所在的象限
 * [1] 是终点所在的象限
 */
fun ILine.passingQuadrant(): Array<Int> {
    return arrayOf(start.quadrant, end.quadrant)
}

/**
 * 延 x 轴平移
 */
fun ILine.translationX(offset: Int): ILine {
    return translationX(offset.toFloat())
}

/**
 * 延 x 轴平移
 */
fun ILine.translationX(offset: Float): ILine {
    this.start.addX(offset)
    this.end.addX(offset)
    return this
}

/**
 * 延 y 轴平移
 */
fun ILine.translationY(offset: Int): ILine {
    return translationY(offset.toFloat())
}

/**
 * 延 y 轴平移
 */
fun ILine.translationY(offset: Float): ILine {
    this.start.addY(offset)
    this.end.addY(offset)
    return this
}

/**
 * 获取线段镜像
 */
fun ILine.mirror(): ILine {
    this.start.mirror()
    this.end.mirror()
    return this
}

/**
 * 延 x 轴镜像
 */
fun ILine.mirrorX(): ILine {
    this.start.mirrorX()
    this.end.mirrorX()
    return this
}

/**
 * 延 y 轴镜像
 */
fun ILine.mirrorY(): ILine {
    this.start.mirrorY()
    this.end.mirrorY()
    return this
}

fun ILine.rotateWithEndPoint(angle: Int): ILine {
    return rotateWithEndPoint(angle.toFloat())
}

fun ILine.rotateWithEndPoint(angle: Float): ILine {
    this.start.rotateWith(this.end, angle)
    return this
}

fun ILine.rotateWithStartPoint(angle: Int): ILine {
    return rotateWithStartPoint(angle.toFloat())
}

fun ILine.rotateWithStartPoint(angle: Float): ILine {
    this.end.rotateWith(this.start, angle)
    return this
}

fun ILine.rotateWithPoint(point: IPoint2, angle: Int): ILine {
    return rotateWithPoint(point, angle.toFloat())
}

fun ILine.rotateWithPoint(point: IPoint2, angle: Float): ILine {
    this.start.rotateWith(point, angle)
    this.end.rotateWith(point, angle)
    return this
}

fun ILine.rotate(angle: Int): ILine {
    return rotate(angle.toFloat())
}

fun ILine.rotate(angle: Float): ILine {
    val center = this.center()
    return rotateWithPoint(center, angle)
}

/**
 * 线段是否包含点
 */
fun ILine.containsPoint(point: IPoint2): Boolean {
    if ((point.x - this.start.x) * (this.end.y - this.start.y) == (point.y - this.start.y) * (this.end.x - this.start.x)  &&
            min(this.start.x, this.end.x) <= point.x && point.x <= max(this.start.x, this.end.x) &&
            min(this.start.y, this.end.y) <= point.y && point.y <= max(this.start.y, this.end.y)
    ) {
        return true;
    }
    return false
}

/**
 * 点是否在线段上
 */
fun IPoint2.isInLine(line: ILine): Boolean {
    return line.containsPoint(this)
}

///**
// * Gets the vertical line of the specified length at the midpoint of the line segment
// * 获取线段中点的指定长度的垂直线
// */
//fun ILine.centerPointVerticalLine(verticalLineLength: Float) {
//
//}
//
///**
// * Gets the vertical line of the specified length at the beginning of the line segment
// * 获取线段起点的指定长度的垂直线
// */
//fun ILine.startPointVerticalLine(verticalLineLength: Float) {
//
//}
//
///**
// * Gets the vertical line of the specified length at the end of the line segment
// * 获取线段终点的指定长度的垂直线
// */
//fun ILine.endPointVerticalLine(verticalLineLength: Float) {
//
//}

/**
 * 获取距离起点 distance 的点坐标
 */
fun ILine.getLinePointDistanceToStartPoint(distance: Float): IPoint2 {
    return getOnLineOnePoint(this.start, distance)
}

fun ILine.getOnLineOnePoint(onlinePoint: IPoint2, relativeDistance: Int): IPoint2 {
    return getOnLineOnePoint(onlinePoint, relativeDistance.toFloat())
}

/**
 * 获取直线上某一点 onlinePoint 距离 relativeDistance 的点坐标
 *
 * relativeDistance < 0，则取在 onlinePoint 左侧的点
 * relativeDistance > 0，则取在 onlinePoint 右侧的点
 */
fun ILine.getOnLineOnePoint(onlinePoint: IPoint2, relativeDistance: Float): IPoint2 {
    if (relativeDistance == 0f) {
        return onlinePoint
    }
    if (onlinePoint.x == 0f) {
        return Point2(onlinePoint.x, onlinePoint.y + relativeDistance)
    }
    if (onlinePoint.y == 0f) {
        return Point2(onlinePoint.x + relativeDistance, onlinePoint.y)
    }
    val distance = abs(relativeDistance)
    val angleRad = this.angleRad
    val dx = distance * cos(angleRad)
    val dy = distance * sin(angleRad)
    if (relativeDistance < 0) {
        return onlinePoint.clone().addX(-dx).addY(-dy)
    }
    return onlinePoint.clone().addX(dx).addY(dy)
}