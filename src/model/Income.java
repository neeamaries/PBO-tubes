package src.model;

public class Income extends Transaction implements FinancialAction {
    private int incomeID;

    public Income() {
        super();
        setTransactionType("income");
    }

    public Income(int incomeID, int transactionID, int userID, int accountID, int categoryID,
                  String transactionName, double amount, String transactionDate, String note) {
        super(transactionID, userID, accountID, categoryID, transactionName, amount, "income", transactionDate, note);
        this.incomeID = incomeID;
    }
    public Income(int incomeID, int transactionID, int userID, int accountID, int categoryID,
                  String transactionName, double amount, String transactionDate, String note, Category category) {
        super(transactionID, userID, accountID, categoryID, transactionName, amount, "income", transactionDate, note, category);
        this.incomeID = incomeID;
    }
    @Override
    public void execute(AccountWallet wallet) {
        if (wallet != null) {
            wallet.updateBalance(getAmount());
        }
    }

    @Override
    public void rollback(AccountWallet wallet) {
        if (wallet != null) {
            wallet.updateBalance(-getAmount());
        }
    }

    public int getIncomeID() {
        return incomeID;
    }

    public void setIncomeID(int incomeID) {
        this.incomeID = incomeID;
    }

    @Override
    public String toString() {
        return "Income{" +
                "incomeID=" + incomeID +
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