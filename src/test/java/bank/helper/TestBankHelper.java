/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.helper;

import bank.Bank;
import bank.Customer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author stuart
 */
public class TestBankHelper {

    public static final Double SMALL_VALUE = 0.01;

    public static RunnerAsync testDecrementAsync(Bank bank, Customer c, int count, double finalValue) {
        return (new RunnerAsync(bank, c, count, finalValue, false)).begin();
    }

    public static RunnerAsync testIncrementAsync(Bank bank, Customer c, int count, double finalValue) {
        return (new RunnerAsync(bank, c, count, finalValue, true)).begin();
    }

    private static class RunnerAsync extends Thread {

        private final Bank bank;
        private final Customer customer;
        private final int count;
        private final double finalValue;
        private final boolean inc;
        private boolean running = false;
        private Throwable exception = null;

        public RunnerAsync(Bank bank, Customer customer, int count, double finalValue, boolean inc) {
            this.bank = bank;
            this.customer = customer;
            this.count = count;
            this.finalValue = finalValue;
            this.inc = inc;
            this.running = true;
        }

        public RunnerAsync begin() {
            super.start();
            return this;
        }

        @Override
        public void run() {
            System.out.print("ASYNC:START:BAL:" + bank.balance(customer));
            try {
                for (int i = 0; i < count; i++) {
                    Double current = bank.balance(customer);
                    if (inc) {
                        bank.deposite(customer, SMALL_VALUE);
                        assertEquals(current + SMALL_VALUE, bank.balance(customer), 0.00001);
                    } else {
                        bank.withdraw(customer, SMALL_VALUE);
                        assertEquals(current - SMALL_VALUE, bank.balance(customer), 0.00001);
                    }
                    sleeep(1);
                    System.out.print(".");
                    if (((i+1) % 20) == 0) {
                        System.out.println(":" + bank.balance(customer));
                    }

                }
                assertEquals(finalValue, bank.balance(customer), 0.00001);
            } catch (Throwable e) {
                exception = e;
            } finally {
                running = false;
            }
            System.out.println("\nASYNC:END:BALANCE:" + bank.balance(customer));
        }

        public Throwable getException() {
            return exception;
        }

        public boolean isNoException() {
            return getException() == null;
        }

        public boolean isRunning() {
            return running;
        }
    }

    public static void waitForRunner(RunnerAsync runnerAsync) {
        sleeep(10);
        while (runnerAsync.isRunning() && runnerAsync.isNoException()) {
            sleeep(10);
        }
        if (runnerAsync.isNoException()) {
            return;
        }
        runnerAsync.getException().printStackTrace();
    }

    private static void sleeep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestBankHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
