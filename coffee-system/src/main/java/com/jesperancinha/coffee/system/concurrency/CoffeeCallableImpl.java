package com.jesperancinha.coffee.system.concurrency;

import com.jesperancinha.coffee.system.input.CoffeeMachines.CoffeMachine.Coffees.Coffee.TimesToFill.FillTime;
import com.jesperancinha.coffee.system.manager.MachineProcessor;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by joao on 29-4-16.
 */
@Getter
@Service
public class CoffeeCallableImpl extends QueueCallableAbstract implements CoffeCallable {

    private static final Logger logger = Logger.getLogger(CoffeeCallableImpl.class);
    private FillTime fillTime;
    private String name;

    CoffeeCallableImpl(FillTime fillTime, String name) {
        this.fillTime = fillTime;
        this.name = name;
    }

    @Override
    public Boolean call() {
        logger.info(MessageFormat.format( //
                "{0} - Starting task {1} to make coffee", //
                fillTime.getIndex(), //
                fillTime.getDescription() //
        ));
        try {
            TimeUnit.MILLISECONDS.sleep(fillTime.getValue());
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return true;
    }

}