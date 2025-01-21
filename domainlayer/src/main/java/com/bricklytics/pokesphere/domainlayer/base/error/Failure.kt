package com.bricklytics.pokesphere.domainlayer.base.error


abstract class Failure<ERROR>(
    val code: Int = 0,
    val body: ERROR? = null
)

abstract class FailureFactory<ERROR> : Failure<ERROR>() {
    abstract fun getFailure(code: Int): Failure<ERROR>
}