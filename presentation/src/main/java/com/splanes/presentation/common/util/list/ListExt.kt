package com.splanes.presentation.common.util.list

fun <T> List<T>.removeFirst(): List<T> =
    if (size > 1) {
        subList(1, size)
    } else {
        listOf()
    }

fun <T> List<T>.removeLast(): List<T> =
    if (size > 1) {
        subList(0, lastIndex)
    } else {
        listOf()
    }