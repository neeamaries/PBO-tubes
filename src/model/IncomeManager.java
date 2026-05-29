package src.model;
import java.util.ArrayList;
import java.util.List;
public class IncomeManager {
    private List<Income> listIncome;

    public IncomeManager() {
        this.listIncome = new ArrayList<>();
    }

    public void addIncome(Income data, AccountWallet target) {
        listIncome.add(data);
        data.execute(target);
    }

    public boolean editIncome(int transactionID, Income newData, AccountWallet target) {
        for (int i = 0; i < listIncome.size(); i++) {
            Income oldIncome = listIncome.get(i);

            if (oldIncome.getTransactionID() == transactionID) {
                oldIncome.rollback(target);
                listIncome.set(i, newData);
                newData.execute(target);

                return true;
            }
        }

        return false;
    }

    public boolean deleteIncome(int transactionID, AccountWallet target) {
        Income incomeYangMauDihapus = null;

        for (Income income : listIncome) {
            if (income.getTransactionID() == transactionID) {
                incomeYangMauDihapus = income;
                break;
            }
        }

        if (incomeYangMauDihapus != null) {
            incomeYangMauDihapus.rollback(target);
            listIncome.remove(incomeYangMauDihapus);
            return true;
        }

        return false;
    }

    public Income findIncomeById(int id) {
        for (Income income : listIncome) {
            if (income.getTransactionID() == id) {
                return income;
            }
        }

        return null;
    }

    public List<Income> getListIncome() {
        return listIncome;
    }

    public void showAllIncome() {
        if (listIncome.isEmpty()) {
            System.out.println("Belum ada data pemasukan.");
            return;
        }

        for (Income income : listIncome) {
            System.out.println(income);
        }
    }
}
