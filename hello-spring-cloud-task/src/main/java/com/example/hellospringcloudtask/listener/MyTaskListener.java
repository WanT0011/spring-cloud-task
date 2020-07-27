package com.example.hellospringcloudtask.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * @author want
 * @createTime 2020.07.19.9:19
 */
@Component
@Slf4j
public class MyTaskListener implements TaskExecutionListener {

    @Override
    public void onTaskStartup(TaskExecution taskExecution) {
        log.info("task is start");
//        在某些情况下，您可能需要考虑请求任务与基础结构实际启动任务之间的时间差。
//        Spring Cloud Task可让您TaskExecution在请求任务时创建一个。然后将生成的执行ID传递TaskExecution给任务，
//        以便它可以更新TaskExecution任务的整个生命周期。
//        TaskExecution可以通过createTaskExecution在的实现上调用方法来创建A ，
//        该实现TaskRepository引用保存TaskExecution对象的数据存储区。
//        为了将您的任务配置为使用generate TaskExecutionId，请添加以下属性：
//        spring.cloud.task.executionid=yourtaskId
        taskExecution.setExternalExecutionId("1");

//        Spring Cloud Task允许您为每个存储一个外部任务ID TaskExecution。
//        例如，在平台上启动任务时由Cloud Foundry提供的任务ID。
//        为了将您的任务配置为使用generate TaskExecutionId，请添加以下属性：
//        spring.cloud.task.external-execution-id=<externalTaskId>

//        Spring Cloud Task可让您存储每个任务的父任务ID TaskExecution。
//        一个示例就是执行另一个任务或多个任务的任务，并且您想记录哪个任务启动了每个子任务。
//        为了配置您的任务以设置父 TaskExecutionId项，请在子任务上添加以下属性：
//        spring.cloud.task.parent-execution-id=<parentExecutionTaskId>
    }

    @Override
    public void onTaskEnd(TaskExecution taskExecution) {
        log.info("task is end");
        //可以在此处设置 退出的一些信息
        taskExecution.setExitMessage("exec success");
    }

    @Override
    public void onTaskFailed(TaskExecution taskExecution, Throwable throwable) {
        log.info("task is failed, {}",throwable.getMessage());
        log.error("error msg : {}",throwable);
        // 如果异常是导致任务结束的原因（如所示 ApplicationFailedEvent），则该异常的堆栈跟踪存储在此处。
        //可以在此处设置 任务错误信息
        taskExecution.setErrorMessage("exec with error，："+throwable);
    }
}
