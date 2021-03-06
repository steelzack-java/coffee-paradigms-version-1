package com.jesperancinha.coffee.api.manager;

import com.jesperancinha.coffee.api.concurrency.QueueCallable;
import com.jesperancinha.coffee.system.input.CoffeeMachines.CoffeMachine.Coffees.Coffee;
import com.jesperancinha.coffee.system.input.CoffeeMachines.CoffeMachine.PaymentTypes.Payment;
import com.jesperancinha.coffee.system.input.Employees.Employee;
import com.jesperancinha.coffee.system.input.Employees.Employee.Actions.PostAction;

import java.util.List;

/**
 * Created by joao on 29-4-16.
 */
public interface CoffeeProcessor extends Processor {
    void callMakeCoffee(Employee employee, String name, Coffee chosenCoffee, Payment payment, List<PostAction> postActions, QueueCallable parentCallable);
}
