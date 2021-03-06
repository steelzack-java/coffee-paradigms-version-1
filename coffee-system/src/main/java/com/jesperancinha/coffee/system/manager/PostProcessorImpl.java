package com.jesperancinha.coffee.system.manager;

import com.jesperancinha.coffee.api.concurrency.QueueCallable;
import com.jesperancinha.coffee.api.manager.PostProcessor;
import com.jesperancinha.coffee.system.concurrency.ActionCallable;
import com.jesperancinha.coffee.system.concurrency.PostActionCallableImpl;
import com.jesperancinha.coffee.system.input.Employees.Employee;
import com.jesperancinha.coffee.system.input.Employees.Employee.Actions.PostAction;
import com.jesperancinha.coffee.system.queues.Queue;
import com.jesperancinha.coffee.system.queues.QueuePostActivityImpl;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;

@Accessors(chain = true)
@Getter
@Service
public class PostProcessorImpl extends ProcessorAbstract implements PostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PostProcessorImpl.class);

    @Autowired
    private QueuePostActivityImpl queuePostActivity;

    @Override
    public void callPostActions(
            Employee employee,
            final String name,
            List<PostAction> postActions,
            final QueueCallable parentCallable
    ) {
        PostActionCallableImpl postActionCallable = new PostActionCallableImpl(name);
        parentCallable.getAllCallables().add(postActionCallable);
        postActions.forEach(postActionCallable::addPostAction);
    }

    @Override
    public Queue getExecutorServiceQueue() {
        return queuePostActivity;
    }

    @Override
    public String getExecutorName(Callable<Boolean> callable) {
        return ((ActionCallable) callable).getName();
    }

    @Override
    public void addQueueSize(int queueSize, String name) {
        queuePostActivity.setQueueSize(queueSize, name);
    }

    @Override
    public void initExecutors() {
        queuePostActivity.initExecutors();
    }

    @Override
    public void stopExectutors() {
        queuePostActivity.stopExecutors();
    }
}
