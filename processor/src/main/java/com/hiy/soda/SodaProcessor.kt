package com.hiy.soda

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic


@AutoService(Processor::class)
@SupportedAnnotationTypes("com.hiy.soda.anno.Plugin")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions("debug")
class SodaProcessor : AbstractProcessor() {
    private val tag = "SodaProcess"
    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        processingEnv?.messager?.printMessage(Diagnostic.Kind.NOTE, "SodaProcessor init")

    }
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "SodaProcessor process")
        val greeterClass = ClassName("com.tustar.demo.data", "Greeter")
        var mFiler = processingEnv.filer
        val file = FileSpec.builder("com.tustar.demo.data", "HelloWorld")
            .addType(
                TypeSpec.classBuilder("Greeter")
                    .primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter("name", String::class)
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("name", String::class)
                            .initializer("name")
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("greet")
                            .addStatement("println(%P)", "Hello, \$name")
                            .build()
                    )
                    .build()
            )
            .addFunction(
                FunSpec.builder("main")
                    .addParameter("args", String::class, KModifier.VARARG)
                    .addStatement("%T(args[0]).greet()", greeterClass)
                    .build()
            )
            .build()


//        file.writeTo(System.out)
        try {
//            file.writeTo(mFiler)
//            throw RuntimeException("查看堆栈")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return super.getSupportedAnnotationTypes().apply {
            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "${this.size}: ${this.toString()}")
        }
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return super.getSupportedSourceVersion().apply {
            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "${this.name}")
        }
    }

    override fun getSupportedOptions(): MutableSet<String> {
        return super.getSupportedOptions().apply {
            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "SodaProcessor option=${this.toString()}")
        }
    }
}