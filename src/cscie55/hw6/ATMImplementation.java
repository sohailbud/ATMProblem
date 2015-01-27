package cscie55.hw6;

/**
 * Created by Sohail on 3/28/14.
 */
public class ATMImplementation implements ATM {

    private Account account;

    public ATMImplementation() {
        account = new Account();
    }

    public void deposit(float amount){
        account.deposit(amount);
    }

    public void withdraw(float amount) {
            account.withdraw(amount);
    }

    public Float getBalance() {
       return account.getBalance();
    }

}
