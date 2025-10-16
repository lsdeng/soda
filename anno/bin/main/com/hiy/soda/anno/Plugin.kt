package com.hiy.soda.anno

@Target(AnnotationTarget.CLASS) // 确保此注解只能用于类
@Retention(AnnotationRetention.SOURCE) // 建议改为 SOURCE，因为注解仅在编译期需要
annotation class Plugin(val value: String = "test")