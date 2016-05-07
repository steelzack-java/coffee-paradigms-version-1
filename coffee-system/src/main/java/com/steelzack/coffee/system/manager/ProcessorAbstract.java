package com.steelzack.coffee.system.manager;

import com.steelzack.coffee.system.queues.QueueAbstract;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static com.steelzack.coffee.system.concurrency.EmployeeCallableImpl.SCHEDULED_TASK_FAILED_TO_EXECUTE;

/**
 * Created by joaofilipesabinoesperancinha on 05-05-16.
 */
public abstract class ProcessorAbstract {

    private static final Logger logger = Logger.getLogger(ProcessorAbstract.class);

    private final List<Future<Boolean>> allResults = new ArrayList<>();

    final List<Callable<Boolean>> allCallables = new ArrayList<>();

    public abstract QueueAbstract getExecutorServiceQueue();

    public void waitForAllCalls() {
        allResults.stream().forEach( //
                booleanFuture -> { //
                    try { //
                        if (booleanFuture.get() != null && !booleanFuture.get()) { //
                            logger.error(SCHEDULED_TASK_FAILED_TO_EXECUTE); //
                        }
                    } catch (NullPointerException | InterruptedException | ExecutionException e) { //
                        logger.error(e.getMessage(), e); //
                    }
                }
        );
    }

    public abstract String getExecutorName(Callable<Boolean> callable);

    public void runAllCalls() {
        allCallables.stream().forEach(
                booleanCallable -> {
                    final Map<String, ThreadPoolExecutor> executorServiceMap = getExecutorServiceQueue().getExecutorServiceMap();
                    final String executorName = getExecutorName(booleanCallable);
                    final ThreadPoolExecutor threadPoolExecutor = executorServiceMap.get(executorName);
                    allResults.add(threadPoolExecutor.submit(booleanCallable));
                } //
        );
    }
}
