package bank;

public class Contents {

    private long currencyUnits;

    public Contents(long amount) {
        this.currencyUnits = amount;
    }

    public long getAmount() {
        return currencyUnits;
    }

    public void setAmount(long amount) {
        this.currencyUnits = amount;
    }

    public boolean isOverdrawn() {
        return getAmount() < 0;
    }

    @Override
    public String toString() {
        return " amount=" + getAmount() + " isOverdrawn=" + isOverdrawn();
    }
}
