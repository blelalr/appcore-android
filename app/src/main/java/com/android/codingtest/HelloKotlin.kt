package com.android.appcore

fun main(){
    val a = intArrayOf(1,2,4,6,5,8)

    println(solution(a))
}


fun solution(A: IntArray): Boolean {
    var isOnlySwapOneTime = true
    val O = IntArray(A.size)
    for (i in 0 until A.size) O[i] = A.get(i)
    var count = 0
    A.sort()
    println("array : ${O.joinToString()}")
    println("array : ${A.joinToString()}")
    for(index in 0..A.size-1) {
        if(O[index] != A[index]) {
            count++
            if(count > 2) isOnlySwapOneTime = false
        }
    }
    return isOnlySwapOneTime
}