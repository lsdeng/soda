package com.hiy.soda.anno

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Plugin(val value: String = "test") {
}