package com.mrhuo.samples

import com.mrhuo.kotlinmath.*

fun main() {
    testPointProperty()
    testPointConnect()
    testLineProperty()
    testLineExtend1()
    testLineExtend2()
    testLineReverseExtendLength1()
    testLineReverseExtendLength2()
    testLineTranslationX()
    testLineTranslationY()
    testLineContainsPoint()
    testLineRotate()
}

fun printSplitLine() {
    println("----------------------------------------------------")
}

fun testPointProperty() {
    println("测试点基本属性：")
    val p1 = Point2(5, -5)
    println("p1 = $p1")
    printSplitLine()
}

fun testPointConnect() {
    println("测试点连接：")
    val p1 = Point2(5, -5)
    val p2 = Point2(-5, 5)
    val line1 = p1.connect(p2)
    println("p1.connect(p2) => line1 = $line1")

    val p3 = Point2(5, -5)
    val p4 = Point2(-5, 5)
    val line2 = Line(p3, p4)
    println("line2 = $line2")
    println("line1 == line2 ? ${line1 == line2}")

    printSplitLine()
}

fun testLineProperty() {
    println("测试线段基本属性：")
    val p1 = Point2(5, -5)
    val p2 = Point2(-5, 5)
    val line1 = Line(p1, p2)
    println("line1 = $line1")
    println("line1.length = ${line1.length}")
    println("line1.slope = ${line1.slope}")
    println("line1.verticalLineSlope = ${line1.verticalLineSlope}")
    println("line1.angleRad = ${line1.angleRad}")
    println("line1.angleDegree = ${line1.angleDegree}")
    println("line1.center() = ${line1.center()}")
    println("line1.passingQuadrant() = ${line1.passingQuadrant().joinToString()}")
    printSplitLine()
}

fun testLineExtend1() {
    println("测试线段终点扩展：")
    val p1 = Point2(5, -5)
    val p2 = Point2(-5, 5)
    val line1 = Line(p1, p2)
    println("line1 = $line1")
    println("line1.length = ${line1.length}")
    println("line1.extendLength(1) = ${line1.extendLength(1)}")
    println("line1.length = ${line1.length}")
    printSplitLine()
}

fun testLineExtend2() {
    println("测试线段终点缩短：")
    val p1 = Point2(5, -5)
    val p2 = Point2(-5, 5)
    val line1 = Line(p1, p2)
    println("line1 = $line1")
    println("line1.length = ${line1.length}")
    println("line1.extendLength(1) = ${line1.extendLength(-1)}")
    println("line1.length = ${line1.length}")
    printSplitLine()
}

fun testLineReverseExtendLength1() {
    println("测试线段起点扩展：")
    val p1 = Point2(5, -5)
    val p2 = Point2(-5, 5)
    val line1 = Line(p1, p2)
    println("line1 = $line1")
    println("line1.length = ${line1.length}")
    println("line1.reverseExtendLength(1) = ${line1.reverseExtendLength(1)}")
    println("line1.length = ${line1.length}")
    printSplitLine()
}

fun testLineReverseExtendLength2() {
    println("测试线段起点缩短：")
    val p1 = Point2(5, -5)
    val p2 = Point2(-5, 5)
    val line1 = Line(p1, p2)
    println("line1 = $line1")
    println("line1.length = ${line1.length}")
    println("line1.reverseExtendLength(-1) = ${line1.reverseExtendLength(-1)}")
    println("line1.length = ${line1.length}")
    printSplitLine()
}

fun testLineTranslationX() {
    println("测试X轴平移：")
    val p1 = Point2(5, -5)
    val p2 = Point2(-5, 5)
    val line1 = Line(p1, p2)
    println("line1 = $line1")
    line1.translationX(3)
    println("line1 = $line1")
    line1.translationX(-3)
    println("line1 = $line1")
    printSplitLine()
}

fun testLineTranslationY() {
    println("测试Y轴平移：")
    val p1 = Point2(5, -5)
    val p2 = Point2(-5, 5)
    val line1 = Line(p1, p2)
    println("line1 = $line1")
    line1.translationY(3)
    println("line1 = $line1")
    line1.translationY(-3)
    println("line1 = $line1")
    printSplitLine()
}

fun testLineContainsPoint() {
    println("测试线段包含某点：")
    val p1 = Point2(5, -5)
    val p2 = Point2(-5, 5)
    val p3 = Point2(4, -4)
    val p4 = Point2(5, -4)
    val line1 = Line(p1, p2)
    println("line1 = $line1")
    println("p3 = $p3")
    println("p4 = $p4")
    println("line1.containsPoint(p1) = ${line1.containsPoint(p1)}")
    println("line1.containsPoint(p2) = ${line1.containsPoint(p2)}")
    println("line1.containsPoint(p3) = ${line1.containsPoint(p3)}")
    println("line1.containsPoint(p4) = ${line1.containsPoint(p4)}")
    printSplitLine()
}

fun testLineRotate() {
    println("测试线段旋转：")
    val p1 = Point2(5, -5)
    val p2 = Point2(-5, 5)
    val line1 = Line(p1, p2)
    println("line1 = $line1")
//    println("line1.rotate(30) = ${line1.rotate(30)}")
//    println("line1.rotate(-30) = ${line1.rotate(-30)}")
    println("line1.rotate(90) = ${line1.rotate(90)}")
    println("line1.slope = ${line1.slope}")
    println("line1.rotate(-90) = ${line1.rotate(-90)}")
    println("line1.slope = ${line1.slope}")
    printSplitLine()
}