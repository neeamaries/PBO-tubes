package src.model;
import java.time.LocalDate;
import java.util.List;

public class Report {
    private String startDate;
    private String endDate;
    private List<Transaction> transactions;

    public Report() {
    }

    public Report(String startDate, String endDate, List<Transaction> transactions) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.transactions = transactions;
    }

    public void generateDaily(int accountID) {
        System.out.println("=== LAPORAN HARIAN ===");
        generateReport(accountID, startDate, startDate);
    }

    public void generateWeekly(int accountID) {
        System.out.println("=== LAPORAN MINGGUAN ===");

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = start.plusDays(6);

        generateReport(accountID, start.toString(), end.toString());
    }

    public void generateMonthly(int accountID) {
        System.out.println("=== LAPORAN BULANAN ===");

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        generateReport(accountID, start.toString(), end.toString());
    }

    private void generateReport(int accountID, String start, String end) {
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }

        double totalIncome = 0;
        double totalExpense = 0;
        boolean found = false;

        LocalDate startLocalDate = LocalDate.parse(start);
        LocalDate endLocalDate = LocalDate.parse(end);

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = LocalDate.parse(transaction.getDate());

            boolean sameAccount = transaction.getAccountID() == accountID;
            boolean inRange = !transactionDate.isBefore(startLocalDate)
                    && !transactionDate.isAfter(endLocalDate);

            if (sameAccount && inRange && !transaction.isDeleted()) {
                System.out.println(transaction);

                if (transaction instanceof Income) {
                    totalIncome += transaction.getAmount();
                } else if (transaction instanceof Expense) {
                    totalExpense += transaction.getAmount();
                }

                found = true;

            }
        }

       if (!found) {
            System.out.println("Tidak ada transaksi pada periode tersebut.");
        } else {
            double netTotal = totalIncome - totalExpense;

            System.out.println("Total Income  : " + totalIncome);
            System.out.println("Total Expense : " + totalExpense);
            System.out.println("Saldo Bersih masuk ke total walllet  : " + netTotal);
        }
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
