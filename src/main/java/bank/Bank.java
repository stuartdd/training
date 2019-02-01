package bank;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    /**
     * Only the bank know how many pennies are in a pound!
     */
    private static final Double CURRENCY_UNITS = 100.0;
    /**
     * Each customer has their own safe!
     */
    public Map<String, Contents> safe = new HashMap<>();
    /**
     * Current customers
     */
    public Map<String, Customer> customers = new HashMap<>();

    /**
     * Add a new Customer. Note we must create a safe and set tit's balance to
     * 0.0. No action if customer already exists!
     *
     * @param c The customer definition
     */
    public void newCustomer(Customer c) {
        if (findCustomer(c.getId()) == null) {
            customers.put(c.getId(), c);
            safe.put(c.getId(), new Contents(0));
        }
    }

    /**
     * Return a customer given an id.
     *
     * @param id of the customer to find
     * @return The customer or null if not found
     */
    public Customer findCustomer(String id) {
        return customers.get(id);
    }

    /**
     * Remove an existing customer. Note we must remove the safe as well. No
     * action if customer does not exists!
     *
     * @param c The customer to be removed
     */
    public void removeCustomer(Customer c) {
        if (findCustomer(c.getId()) != null) {
            customers.remove(c.getId());
            safe.remove(c.getId());
        }
    }

    public boolean isOverdrawn(Customer c) {
        if (findCustomer(c.getId()) != null) {
            return safe.get(c.getId()).isOverdrawn();
        }
        return false;
    }

    /**
     * Find current balance. Reduce it by required amount and return it to the
     * safe. Note the safe stores values in a non currency (unit) so we need to
     * do some maths. No action if customer does not exists!
     *
     * @param c The customer
     * @param amount The amount
     */
    public void withdraw(Customer c, double amount) {
        if (findCustomer(c.getId()) != null) {
            Contents contents = safe.get(c.getId());
            long value = contents.getAmount();
            value = value - Math.round(amount * CURRENCY_UNITS);
            contents.setAmount(value);
        }
    }

    /**
     * Find current balance. Increase it by required amount and return it to the
     * safe. Note the safe stores values in a non currency (unit) so we need to
     * do some maths. No action if customer does not exists!
     *
     * @param c The customer
     * @param amount The amount
     */
    public void deposite(Customer c, double amount) {
        if (findCustomer(c.getId()) != null) {
            Contents contents = safe.get(c.getId());
            long value = contents.getAmount();
            value = value + Math.round(amount * CURRENCY_UNITS);
            contents.setAmount(value);
        }
    }

    /**
     * Find current balance. Return it in the correct currency value.
     *
     * @param c The customer
     * @return The decimal value
     */
    public Double balance(Customer c) {
        if (findCustomer(c.getId()) != null) {
            Contents contents = safe.get(c.getId());
            return contents.getAmount() / CURRENCY_UNITS;
        }
        return null;
    }
}
