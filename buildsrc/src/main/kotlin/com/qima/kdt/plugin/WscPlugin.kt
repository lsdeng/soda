package com.qima.kdt.plugin

import com.qima.kdt.plugin.ext.WscExt
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.ProjectState
import org.gradle.api.artifacts.component.ModuleComponentSelector
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle

/**
 * auther: liusaideng
 * created on :  2023/3/13 11:26 上午
 * desc:
 */
class WscPlugin : Plugin<Project> {
    companion object {
        val TAG = "WSC PLUGIN ==> "
    }

    override fun apply(target: Project) {
        target.rootProject.gradle.addBuildListener(object : BuildListener {
            override fun beforeSettings(settings: Settings) {
                super.beforeSettings(settings)
                logger("beforeSettings")
            }

            override fun settingsEvaluated(p0: Settings) {
                logger("beforeSettings")
            }

            override fun projectsLoaded(p0: Gradle) {
                logger("projectsLoaded")
            }

            override fun projectsEvaluated(p0: Gradle) {
                logger("projectsEvaluated")
            }

            override fun buildFinished(p0: BuildResult) {
                logger("buildFinished")
            }
        })

        target.gradle.addProjectEvaluationListener(object : ProjectEvaluationListener {
            override fun beforeEvaluate(p0: Project) {
                logger("beforeEvaluate")

            }

            override fun afterEvaluate(p0: Project, p1: ProjectState) {
                logger("afterEvaluate")
            }

        })

        target.extensions.create("Wsc", WscExt::class.java)

        logger("hello wsc Plugin")
        target.tasks.create("opt") {
            logger("hello wsc ${it.name}")
        }

        val childProjects = target.rootProject.childProjects
        childProjects.forEach {
            logger("${it.value.name}")
        }

        target.extensions.getByType(WscExt::class.java).apply {
            logger("ext ${this.value}")
        }

        logger("ext ${(target.properties["Wsc"] as WscExt).value}")

//        target.gradle.afterProject {
//            target.extensions.getByType(WscExt::class.java).apply {
//                logger("ext 2 => ${this.value}")
//            }
//        }

        target.afterEvaluate {
            target.extensions.getByType(WscExt::class.java).apply {
                logger("ext 3 => ${this.value}")
            }
        }
//
        switchRemoteToLocal(target)
    }

    private fun logger(content: String) {
        println("${TAG} ${content}")
    }

    private fun switchRemoteToLocal(project: Project) {
        project.configurations.all { configuration ->
            configuration.resolutionStrategy.dependencySubstitution.all { dependencySubstitution ->
                (dependencySubstitution.requested as? ModuleComponentSelector)?.let { moduleComponentSelector ->
//                    logger("moduleComponentSelector = ${moduleComponentSelector.group} ${moduleComponentSelector.module} ")
                    if (moduleComponentSelector.module == "core" && moduleComponentSelector.group == "com.hiy.mobile") {
                        project.findProject(":sodacore")?.let { projectAlias ->
                            dependencySubstitution.useTarget(projectAlias)
                        }
                    }
                }
            }
        }
    }

//    private void switchRemoteToLocal(Project project) {
//        project.getConfigurations().all(new Action<Configuration>() {
//            @Override
//            void execute(Configuration configuration) {
//                configuration.getResolutionStrategy().getDependencySubstitution().all(
//                    new Action<DependencySubstitution>() {
//                        @Override
//                        void execute(DependencySubstitution dependencySubstitution) {
//                            if (dependencySubstitution.requested instanceof ModuleComponentSelector) {
//                                def requested = dependencySubstitution.requested as ModuleComponentSelector
//                                        if (requested.module == "core" && requested.group == "com.hiy.mobile") {
//                                            println("lsd core111  ==> start")
//                                            def lib = project.findProject(":sodacore")
//                                            if (lib != null) {
//                                                println("lsd core111  ==> ${lib.path}")
//                                                dependencySubstitution.useTarget(lib)
//                                            } else {
//                                                println("lsd core111 is empty")
//                                            }
//                                        }
//                            }
//                        }
//                    }
//                )
//
//
//                configuration.getResolutionStrategy().eachDependency(new Action<DependencyResolveDetails>() {
//                    @Override
//                    void execute(DependencyResolveDetails dependencyResolveDetails) {
//                        if (dependencyResolveDetails.requested == null) {
//                            return
//                        }
//
//
//                        def name = dependencyResolveDetails.requested.name
//                                def group = dependencyResolveDetails.requested.group
//                                if (dependencyResolveDetails.requested instanceof ModuleComponentSelector && name == "core" && group == "com.hiy.mobile") {
//                                    println("lsd core222  ==> start")
//                                    def lib = project.findProject(":sodacore")
//                                    if (lib != null) {
//                                        println("lsd core  ==> ${lib.path}")
//                                        dependencyResolveDetails.useTarget(lib)
//                                    } else {
//                                        println("lsd core is empty")
//                                    }
//                                }
//                    }
//                })
//            }
//        })
//    }

}