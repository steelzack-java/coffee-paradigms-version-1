package com.jesperancinha.coffee.system.manager;

import com.jesperancinha.coffee.api.concurrency.QueueCallable;
import com.jesperancinha.coffee.system.queues.Queue;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by joaofilipesabinoesperancinha on 05-05-16.
 */
public abstract class ProcessorAbstract {

    public static final String SCHEDULED_TASK_FAILED_TO_EXECUTE = "scheduled task faild to execute!";

    public abstract Queue getExecutorServiceQueue();

    public void waitForAllCalls(QueueCallable queueCallable) {
        queueCallable.waitForCalls();
    }


    public abstract String getExecutorName(Callable<Boolean> callable);

    public void runAllCalls(QueueCallable queueCallable) {
        queueCallable.getAllCallables().forEach(
                booleanCallable -> {
                    final Map<String, ThreadPoolExecutor> executorServiceMap = getExecutorServiceQueue().getExecutorServiceMap();
                    final String executorName = getExecutorName(booleanCallable);
                    final ThreadPoolExecutor threadPoolExecutor = executorServiceMap.get(executorName);
                    final Future<Boolean> submitResult = threadPoolExecutor.submit(booleanCallable);
                    addSubmitResult(submitResult, queueCallable);
                }
        );
    }

    private void addSubmitResult(Future<Boolean> submitResult, QueueCallable queueCallable) {
        queueCallable.addSubmitResult(submitResult);
    }
}
