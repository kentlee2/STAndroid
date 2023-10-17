package com.qfh.route.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class CustomPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        //添加扩展
        project.extensions.add('myextension',MyExtension)

        project.task("cusplugin"){
            doLast{
                println("cusplugin任务执行111")

                MyExtension extension = project.myextension
                //3.输出插件扩展属性
                println ">>>>>> name: ${extension.channelCode} age:${extension.appName}"
            }
        }doLast {

        }
//        project.tasks.whenObjectAdded { Task theTask ->
//            if (theTask.name == 'assembleDebug') {
//                println("第一步 打包")
//                theTask.dependsOn(task)
//            }
//        }
    }
}
