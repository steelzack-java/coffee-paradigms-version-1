package com.steelzack.coffee.system.concurrency;

import com.steelzack.coffee.system.input.CoffeeMachines.CoffeMachine.Coffees.Coffee.TimesToFill.FillTime;
import com.steelzack.coffee.system.manager.MachineProcessor;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by joao on 29-4-16.
 */
@Getter
@Service
public class CoffeeCallableImpl implements Coffe, Callable<Boolean> {

    @Autowired
    private MachineProcessor machineProcessor;

    final Logger logger = Logger.getLogger(CoffeeCallableImpl.class);

    private FillTime fillTime;

    public CoffeeCallableImpl(FillTime fillTime)
    {
        this.fillTime = fillTime;
    }

    @Override
    public Boolean call() throws Exception {
        logger.info(MessageFormat.format( //
                "{0} - Starting tast {1} to make coffee", //
                fillTime.getIndex(), //
                fillTime.getDescription() //
        ));
        TimeUnit.MILLISECONDS.sleep(fillTime.getValue());
        return true;
    }

}
