/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import bank.helper.TestBankHelper;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author stuart
 */
public class TestBank {

    private static final String CUST1_ID = "cust-001";
    private Bank bank = new Bank();

    @Test
    public void test() {
        /*
        Test add customer
         */
        assertNull(bank.findCustomer(CUST1_ID));
        /*
        Test find customer!
         */
        bank.newCustomer(new Customer(CUST1_ID, "Customer"));
        Customer c = bank.findCustomer(CUST1_ID);
        /*
        Test initial balence and isOverdrawn 
         */
        assertNotNull(c);
        assertNotNull(bank.balance(c));
        assertEquals(0, bank.balance(c), 0.0);
        assertFalse(bank.isOverdrawn(c));
        /*
        Add money and test balence and isOverdrawn 
         */
        bank.deposite(c, 100.11);
        assertEquals(100.11, bank.balance(c), 0.0);
        assertFalse(bank.isOverdrawn(c));
        /*
        Subtract money and test balence and isOverdrawn 
         */
        bank.withdraw(c, 50.11);
        assertEquals(50.0, bank.balance(c), 0.0);
        assertFalse(bank.isOverdrawn(c));
        /*
        Go Overdrawn and test balence and isOverdrawn 
         */
        bank.withdraw(c, 50.01);
        assertEquals(-0.01, bank.balance(c), 0.0);
        assertTrue(bank.isOverdrawn(c));
        /*
        Test remove customer
         */
        assertNotNull(bank.findCustomer(CUST1_ID));
        bank.removeCustomer(c);
        assertNull(bank.findCustomer(CUST1_ID));
        /*
        Test small increments
         */
        bank.newCustomer(new Customer(CUST1_ID, "Customer"));
        c = bank.findCustomer(CUST1_ID);
        assertNotNull(c);
        assertEquals(0, bank.balance(c), 0.0);
        TestBankHelper.waitForRunner(TestBankHelper.testIncrementAsync(bank, c, 1000, 1000 * TestBankHelper.SMALL_VALUE));
        TestBankHelper.waitForRunner(TestBankHelper.testDecrementAsync(bank, c, 1000, 0));
        bank.removeCustomer(c);
        assertNull(bank.findCustomer(CUST1_ID));
    }
    
//    @Test 
//    public void testAsync() {
//         /*
//        Test add customer
//         */
//        assertNull(bank.findCustomer(CUST1_ID));
//        /*
//        Test find customer!
//         */
//        bank.newCustomer(new Customer(CUST1_ID, "Customer"));
//        Customer c = bank.findCustomer(CUST1_ID);
//        
//        bank.removeCustomer(c);
//        assertNull(bank.findCustomer(CUST1_ID));
//    }

}
