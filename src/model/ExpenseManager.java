package src.model;
import java.util.ArrayList;
import java.util.List;
public class ExpenseManager {
   private List<Expense> listExpenses;

    public ExpenseManager() {
        this.listExpenses = new ArrayList<>();
    }

    public void addExpense(Expense data, AccountWallet source) {
        listExpenses.add(data);
        data.execute(source);
    }

    public boolean editExpense(int transactionID, Expense newData, AccountWallet source) {
        if (newData.getCategory() != null &&
            !newData.getCategory().getType().equalsIgnoreCase("Expense")) {
        System.out.println("Kategori tidak valid untuk pengeluaran.");
        return false;
    }
        for (int i = 0; i < listExpenses.size(); i++) {
            Expense oldExpense = listExpenses.get(i);

            if (oldExpense.getTransactionID() == transactionID) {
                oldExpense.rollback(source);
                listExpenses.set(i, newData);
                newData.execute(source);

                return true;
            }
        }

        return false;
    }

    public boolean deleteExpense(int transactionID, AccountWallet source) {
        Expense expenseYangMauDihapus = null;

        for (Expense expense : listExpenses) {
            if (expense.getTransactionID() == transactionID) {
                expenseYangMauDihapus = expense;
                break;
            }
        }

        if (expenseYangMauDihapus != null) {
            expenseYangMauDihapus.rollback(source);
            listExpenses.remove(expenseYangMauDihapus);
            return true;
        }

        return false;
    }

    public Expense findExpenseById(int id) {
        for (Expense expense : listExpenses) {
            if (expense.getTransactionID() == id) {
                return expense;
            }
        }

        return null;
    }

    public List<Expense> getListExpenses() {
        return listExpenses;
    }

    public void showAllExpenses() {
        if (listExpenses.isEmpty()) {
            System.out.println("Belum ada data pengeluaran.");
            return;
        }

        for (Expense expense : listExpenses) {
            System.out.println(expense);
        }
    }
}
