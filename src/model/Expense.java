package src.model;

public class Expense extends Transaction {
    private int expenseID;

    public Expense() {
        super();
    }

    public Expense(int expenseID, int userID, int accountID, int transactionID, double amount, Category category, String date, String note) {
        super(userID, accountID, transactionID, amount, category, date, note);
        this.expenseID = expenseID;
    }

    public String getDetails() {
        return "Expense{" +
                "expenseID=" + expenseID +
                ", transactionID=" + transactionID +
                ", amount=" + amount +
                ", category=" + getCategoryName() +
                ", date='" + date + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public void execute(AccountWallet wallet) {
    if (wallet != null) {
        wallet.updateBalance(-amount);
    }
    }

    @Override
    public void rollback(AccountWallet wallet) {
    if (wallet != null) {
        wallet.updateBalance(amount);
    }
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    @Override
    public String toString() {
        return getDetails();
    }
}
