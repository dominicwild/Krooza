package helper;

/**
 * A store for the players currency. 
 * @author DominicWild, Alex Bently
 */
public class Wallet {

    private int scrap;
    private final int SCRAP_CAP = 999;

    public Wallet() {
        this.scrap = 0;
    }

    public Wallet(int amount) {
        this.scrap = amount;
    }

    public int getScrap() {
        return scrap;
    }
    
    /**
     * Deposit an amount into the wallet.
     *
     * @param amount The amount to be deposited.
     * @return The new balance of the wallet.
     */
    public int add(int amount) {
        if(this.scrap + amount < this.SCRAP_CAP){
            this.scrap += amount;
        } else {
            this.scrap = this.SCRAP_CAP;
        }
        return this.scrap;
    }
    
    public boolean canAfford(int itemCost) {
        return scrap >= itemCost;
    }

    /**
     * Attempts to withdraw money from wallet. If it's a valid transaction, it
     * does so, returning a boolean to represent this.
     *
     * @param amount The amount to take out.
     * @return A boolean determining if transaction was successful.
     */
    public boolean withdraw(int amount) {
        if (this.scrap - amount < 0) {
            return false;
        } else {
            this.scrap -= amount;
            return true;
        }
    }
}
