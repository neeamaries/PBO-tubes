package src.model;

public class Expense extends Transaction implements FinancialAction {
    private int expenseID;

    public Expense() {
        super();
        setTransactionType("expense");
    }

    public Expense(int expenseID, int transactionID, int userID, int accountID, int categoryID,
                   String transactionName, double amount, String transactionDate, String note) {
        super(transactionID, userID, accountID, categoryID, transactionName, amount, "expense", transactionDate, note);
        this.expenseID = expenseID;
    }
    public Expense(int expenseID, int transactionID, int userID, int accountID, int categoryID,
                  String transactionName, double amount, String transactionDate, String note, Category category) {
        super(transactionID, userID, accountID, categoryID, transactionName, amount, "Expense", transactionDate, note, category);
        this.expenseID = expenseID;
    }
    @Override
    public void execute(AccountWallet wallet) {
        if (wallet != null) {
            wallet.updateBalance(-getAmount());
        }
    }

    @Override
    public void rollback(AccountWallet wallet) {
        if (wallet != null) {
            wallet.updateBalance(getAmount());
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
        return "Expense{" +
                "expenseID=" + expenseID +
                ", transactionID=" + getTransactionID() +
                ", userID=" + getUserID() +
                ", accountID=" + getAccountID() +
                ", categoryID=" + getCategoryID() +
                ", transactionName='" + getTransactionName() + '\'' +
                ", amount=" + getAmount() +
                ", transactionType='" + getTransactionType() + '\'' +
                ", transactionDate='" + getTransactionDate() + '\'' +
                ", note='" + getNote() + '\'' +
                '}';
    }
}