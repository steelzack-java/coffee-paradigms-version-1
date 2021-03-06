package com.jesperancinha.coffee.system.concurrency;

import com.jesperancinha.coffee.api.concurrency.PaymentCallable;
import com.jesperancinha.coffee.api.manager.MachineProcessor;
import com.jesperancinha.coffee.api.manager.PostProcessor;
import com.jesperancinha.coffee.system.input.CoffeeMachines.CoffeMachine.PaymentTypes.Payment;
import com.jesperancinha.coffee.system.input.Employees.Employee;
import com.jesperancinha.coffee.system.input.Employees.Employee.Actions.PostAction;
import com.jesperancinha.coffee.system.manager.GeneralProcessorImpl;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by joaofilipesabinoesperancinha on 01-05-16.
 */
@Service
@Getter
public class PaymentCallableImpl extends QueueCallableAbstract implements PaymentCallable {

    private static final Logger logger = LoggerFactory.getLogger(PaymentCallableImpl.class);
    private final Payment chosenPayment;
    @Autowired
    private MachineProcessor machineProcessor;
    private Employee employee;
    private String name;
    private List<PostAction> postActions;


    public PaymentCallableImpl(
            Employee employee,
            String name,
            Payment payment,
            List<PostAction> postActions,
            MachineProcessor machineProcessor
    ) {
        super();
        this.employee = employee;
        this.chosenPayment = payment;
        this.name = name;
        this.postActions = postActions;
        this.machineProcessor = machineProcessor;
    }

    @Override
    public Boolean call() throws Exception {
        logger.info(MessageFormat.format("PaymentCallable with {0}", chosenPayment.getName()));
        Integer time = chosenPayment.getTime();
        if (time != null) {
            TimeUnit.MILLISECONDS.sleep(time);
        }
        final PostProcessor postProcessor = machineProcessor.getPostProcessor();
        machineProcessor.callPostActions(employee, GeneralProcessorImpl.MAIN_QUEUE_POST, postActions, this);
        postProcessor.runAllCalls(this);
        postProcessor.waitForAllCalls(this);
        return true;
    }

}
