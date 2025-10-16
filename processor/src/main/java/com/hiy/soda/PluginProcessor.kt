package com.hiy.soda

import com.google.auto.service.AutoService
import com.hiy.soda.anno.IPlugin
import com.hiy.soda.anno.Plugin
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MUTABLE_MAP
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class) // 自动注册处理器
@SupportedSourceVersion(SourceVersion.RELEASE_8) // 支持 Java 8 版本
@SupportedAnnotationTypes("com.hiy.soda.anno.Plugin") // 声明要处理的注解
class PluginProcessor : AbstractProcessor() {

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        // 获取所有被 @Plugin 注解的元素
        val elements = roundEnv.getElementsAnnotatedWith(Plugin::class.java)
        if (elements.isEmpty()) {
            return false
        }

        // 定义要生成的类名和包名
        val packageName = "com.hiy.soda.plugin"
        val className = "PluginManager"


        // 创建一个私有的、可变的 Map 属性
        val type = MUTABLE_MAP.parameterizedBy(
            String::class.asClassName(),
            IPlugin::class.asClassName()
        )


        // 创建一个私有的、可变的 Map 属性
        val pluginMapProperty = PropertySpec.builder(
            "_plugins",
            type
        )
            .addModifiers(KModifier.PRIVATE)
            .initializer("mutableMapOf()")
            .build()


        // 创建一个 MutableMap<String, IPlugin> 类型
        val pluginMapType = Map::class.asClassName().parameterizedBy(
            String::class.asClassName(),
            IPlugin::class.asClassName()
        )
        // 创建一个公开的、可变的 MutableMap 属性，作为对外暴露的接口
        val publicPluginMapProperty = PropertySpec.builder("plugins", pluginMapType)
            .getter(
                FunSpec.getterBuilder()
                    .addStatement("return _plugins")
                    .build()
            )
            .build()

        // 创建一个 init 代码块，用于注册所有插件
        val initBlock = FunSpec.builder("init")
        for (element in elements) {
            // 确保被注解的是一个类
            if (element !is TypeElement) continue

            val pluginAnnotation = element.getAnnotation(Plugin::class.java)
            val pluginKey = pluginAnnotation.value // 获取注解的 value 作为 Key
            val classType = element.asType().asTypeName()

            // 添加代码到 init 块: _plugins["key"] = com.example.PluginImpl()
            initBlock.addStatement("_plugins[%S] = %T()", pluginKey, classType)
        }

        // 创建一个 onCreate 方法来初始化所有插件
        val onCreateFun = FunSpec.builder("onCreate")
            .addStatement("_plugins.values.forEach { it.onCreate() }")
            .build()

        // 使用 KotlinPoet 生成 PluginManager 类
        val file = FileSpec.builder(packageName, className)
            .addType(
                TypeSpec.objectBuilder(className) // 创建一个单例对象
                    .addProperty(pluginMapProperty)
                    .addProperty(publicPluginMapProperty)
                    .addInitializerBlock(initBlock.build().body)
                    .addFunction(onCreateFun)
                    .build()
            )
            .build()

        // 获取 kapt 生成 Kotlin 文件的路径并写入文件
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"]
        file.writeTo(File(kaptKotlinGeneratedDir, "$className.kt"))

        return true
    }
}