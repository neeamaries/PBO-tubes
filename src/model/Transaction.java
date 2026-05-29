package src.model;

public class Transaction implements FinancialAction {
    protected int userID;
    protected int accountID;
    protected int transactionID;
    protected double amount;
    protected String date;
    protected String note;
    private Category category;
    private boolean deleted;

    public Transaction(int userID, int accountID, int transactionID, double amount, Category category, String date, String note) {
        this.userID = userID;
        this.accountID = accountID;
        this.transactionID = transactionID;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
        this.deleted = false;
    }
    public void createTransaction() {
        System.out.println("ransaksi berhasil dibuat.");
        System.out.println("ID Transaksi : " + transactionID);
        System.out.println("ID User      : " + userID);
        System.out.println("ID Account   : " + accountID);
        System.out.println("Jumlah       : " + amount);
        System.out.println("Kategori     : " + category.getName());
        System.out.println("Tanggal      : " + date);
        System.out.println("Catatan      : " + note);
    }

    public void setCategory(Category cat) {
        this.category = cat;
    }

    public void deleteTransaction() {
        this.deleted = true;
        System.out.println("Transaksi dengan ID " + transactionID + " berhasil dihapus.");
    }

    public void updateTransaction(double amount, Category category, String date, String note) {
        if (this.deleted) {
            System.out.println("Gagal update: Transaksi ini sudah dihapus!");
            return;
        }
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;

        System.out.println("Transaksi berhasil diperbarui.");
    }
    public boolean isDeleted() {
        return deleted;
    }

    public void showTransaction(int accountID, String startDate, String endDate) {
        if (this.accountID == accountID && !deleted) {
            System.out.println("=== Detail Transaksi ===");
            System.out.println("ID Transaksi : " + transactionID);
            System.out.println("ID User      : " + userID);
            System.out.println("ID Account   : " + this.accountID);
            System.out.println("Jumlah       : " + amount);
            System.out.println("Kategori     : " + getCategoryName());
            System.out.println("Tanggal      : " + date);
            System.out.println("Catatan      : " + note);
            System.out.println("Periode      : " + startDate + " sampai " + endDate);
        }
    }

    @Override
    public void execute() {
        System.out.println("Transaksi dijalankan.");
    }

    @Override
    public void rollback() {
        System.out.println("Transaksi dibatalkan.");
    }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryName() {
        if (category != null) {
            return category.getName();
        }

        return "Tidak ada kategori";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
            "userID=" + userID +
            ", accountID=" + accountID +
            ", transactionID=" + transactionID +
            ", amount=" + amount +
            ", category=" + getCategoryName() +
            ", date='" + date + '\'' +
            ", note='" + note + '\'' +
            ", deleted=" + deleted +
            '}';
        }

}
