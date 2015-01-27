package cscie55.hw6;

/**
 * Created by Sohail on 3/28/14.
 */
public class Account {

    private float balance;

    public Account() {
        balance = 0;
    }

    public synchronized float getBalance() {
        return balance;
    }

    public synchronized void deposit(float amount) {
        balance = balance + amount;
    }

    public synchronized void withdraw(float amount) {
        if(amount <= balance) {
            balance = balance - amount;
        }
    }
}
