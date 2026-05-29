package src.model;

import java.util.ArrayList;
import java.util.List;

public class TestTransaction {
    public static void main(String[] args) {
        System.out.println("===== TEST SEMUA CLASS FINTRACK =====");

        // =====================================================
        // 1. TEST USER & PROFILE
        // =====================================================
        System.out.println("\n========== 1. TEST USER & PROFILE ==========");

        Profile profileJulio = new Profile(
                "Julio Chrysanto Tanlain",
                "08123456789",
                "Surabaya");

        User userJulio = new User(
                1,
                "julio",
                "julio@gmail.com",
                "12345",
                profileJulio);

        userJulio.showUserInfo();

        boolean loginBerhasil = userJulio.login("julio", "12345");

        if (loginBerhasil) {
            System.out.println("Login berhasil.");
        } else {
            System.out.println("Login gagal.");
        }

        profileJulio.updateProfile(
                "Julio C. Tanlain",
                "08999999999",
                "Surabaya Timur");

        userJulio.editProfile("julioct", "julioct@gmail.com");
        userJulio.showUserInfo();
        userJulio.logout();

        // =====================================================
        // 2. TEST CATEGORY & CATEGORY MANAGER
        // =====================================================
        System.out.println("\n========== 2. TEST CATEGORY & CATEGORY MANAGER ==========");

        CategoryManager categoryManager = new CategoryManager();

        categoryManager.addCategory("Gaji", "Income");
        categoryManager.addCategory("Makan", "Expense");
        categoryManager.addCategory("Transportasi", "Expense");
        categoryManager.addCategory("Bonus", "Income");

        System.out.println("\nSemua kategori:");
        categoryManager.showAllCategories();

        System.out.println("\nEdit kategori ID 2 menjadi Makanan:");
        boolean editCategory = categoryManager.editCategory(2, "Makanan");

        if (editCategory) {
            System.out.println("Kategori berhasil diedit.");
        } else {
            System.out.println("Kategori tidak ditemukan.");
        }

        categoryManager.showAllCategories();

        System.out.println("\nKategori bertipe Expense:");
        for (Category category : categoryManager.getCategoriesByType("Expense")) {
            System.out.println(category);
        }

        // Ambil kategori dari CategoryManager untuk dipakai di transaksi
        Category gaji = categoryManager.getCategories().get(0);
        Category makan = categoryManager.getCategories().get(1);
        Category transportasi = categoryManager.getCategories().get(2);

        // =====================================================
        // 3. TEST WALLET & WALLET MANAGER
        // =====================================================
        System.out.println("\n========== 3. TEST WALLET & WALLET MANAGER ==========");

        AccountWallet cash = new PhysicalWallet(101, "Dompet Cash", 100000);
        AccountWallet dana = new EWallet(102, "Dompet DANA", 50000, "DANA");

        WalletManager walletManager = new WalletManager();
        walletManager.addWallet(cash);
        walletManager.addWallet(dana);

        System.out.println("\nData dompet awal:");
        walletManager.showAllWallets();
        System.out.println("Total saldo awal: " + walletManager.calculateTotalBalance());

        System.out.println("\nEdit nama dompet ID 101:");
        boolean editWallet = walletManager.editWallet(101, "Cash Harian");

        if (editWallet) {
            System.out.println("Dompet berhasil diedit.");
        } else {
            System.out.println("Dompet tidak ditemukan.");
        }

        walletManager.showAllWallets();

        // =====================================================
        // 4. TEST INCOME, EXPENSE, MANAGER, EXECUTE, ROLLBACK
        // =====================================================
        System.out.println("\n========== 4. TEST INCOME, EXPENSE, MANAGER, EXECUTE, ROLLBACK ==========");

        IncomeManager incomeManager = new IncomeManager();
        ExpenseManager expenseManager = new ExpenseManager();
        incomeManager.showAllIncome();
        walletManager.showAllWallets();

        Income income1 = new Income(
                1,
                userJulio.getUserID(),
                cash.getAccountID(),
                1001,
                200000,
                gaji,
                "2026-05-29",
                "Uang bulanan");

        Income income2 = new Income(
                2,
                userJulio.getUserID(),
                dana.getAccountID(),
                1002,
                50000,
                gaji,
                "2026-05-30",
                "Bonus kecil");

        Expense expense1 = new Expense(
                1,
                userJulio.getUserID(),
                cash.getAccountID(),
                2001,
                25000,
                makan,
                "2026-05-29",
                "Beli nasi ayam");

        Expense expense2 = new Expense(
                2,
                userJulio.getUserID(),
                dana.getAccountID(),
                2002,
                15000,
                transportasi,
                "2026-05-30",
                "Naik ojek online");
        Expense expense3 = new Expense(
                3,
                userJulio.getUserID(),
                dana.getAccountID(),
                2003,
                15000,
                transportasi,
                "2026-05-30",
                "Naik ojek online");

        System.out.println("\nTambah pemasukan:");
        incomeManager.addIncome(income1, cash);
        incomeManager.addIncome(income2, dana);
        incomeManager.showAllIncome();

        System.out.println("\nSaldo setelah pemasukan:");
        walletManager.showAllWallets();
        System.out.println("Total saldo: " + walletManager.calculateTotalBalance());

        System.out.println("\nTambah pengeluaran:");
        expenseManager.addExpense(expense1, cash);
        expenseManager.addExpense(expense2, dana);
        expenseManager.addExpense(expense3, dana);
        expenseManager.showAllExpenses();

        System.out.println("\nSaldo setelah pengeluaran:");
        walletManager.showAllWallets();
        System.out.println("Total saldo: " + walletManager.calculateTotalBalance());

        // =====================================================
        // 5. TEST EDIT INCOME
        // =====================================================
        System.out.println("\n========== 5. TEST EDIT INCOME ==========");

        Income incomeBaru = new Income(
                1,
                userJulio.getUserID(),
                cash.getAccountID(),
                1001,
                250000,
                gaji,
                "2026-05-29",
                "Uang bulanan naik");

        boolean editIncome = incomeManager.editIncome(1001, incomeBaru, cash);

        if (editIncome) {
            System.out.println("Income berhasil diedit.");
        } else {
            System.out.println("Income tidak ditemukan.");
        }

        incomeManager.showAllIncome();

        System.out.println("\nSaldo setelah edit income:");
        walletManager.showAllWallets();
        System.out.println("Total saldo: " + walletManager.calculateTotalBalance());

        // =====================================================
        // 6. TEST EDIT EXPENSE
        // =====================================================
        System.out.println("\n========== 6. TEST EDIT EXPENSE ==========");

        Expense expenseBaru = new Expense(
                1,
                userJulio.getUserID(),
                cash.getAccountID(),
                2001,
                30000,
                makan,
                "2026-05-29",
                "Beli makan malam");

        boolean editExpense = expenseManager.editExpense(2001, expenseBaru, cash);

        if (editExpense) {
            System.out.println("Expense berhasil diedit.");
        } else {
            System.out.println("Expense tidak ditemukan.");
        }

        expenseManager.showAllExpenses();

        System.out.println("\nSaldo setelah edit expense:");
        walletManager.showAllWallets();
        System.out.println("Total saldo: " + walletManager.calculateTotalBalance());

        // =====================================================
        // 7. TEST DELETE EXPENSE DENGAN ROLLBACK
        // =====================================================
        System.out.println("\n========== 7. TEST DELETE EXPENSE DENGAN ROLLBACK ==========");

        boolean deleteExpense = expenseManager.deleteExpense(2002, dana);

        if (deleteExpense) {
            System.out.println("Expense berhasil dihapus dari list dan saldo dikembalikan.");
        } else {
            System.out.println("Expense tidak ditemukan.");
        }

        expenseManager.showAllExpenses();

        System.out.println("\nSaldo setelah delete expense ID 2002:");
        walletManager.showAllWallets();
        System.out.println("Total saldo: " + walletManager.calculateTotalBalance());

        // =====================================================
        // 8. TEST TRANSACTION METHOD
        // =====================================================
        System.out.println("\n========== 8. TEST TRANSACTION METHOD ==========");

        System.out.println("\nShow transaction expenseBaru:");
        expenseBaru.showTransaction(cash.getAccountID(), "2026-05-01", "2026-05-31");

        System.out.println("Total saldo: " + walletManager.calculateTotalBalance());

        System.out.println("\nDelete transaction flag expenseBaru:");
        // expenseBaru.deleteTransaction();
        expenseBaru.showTransaction(cash.getAccountID(), "2026-05-01", "2026-05-31");
        walletManager.showAllWallets();
        System.out.println("Total saldo: " + walletManager.calculateTotalBalance());

        // =====================================================
        // 9. TEST REPORT
        // =====================================================
        System.out.println("\n========== 9. TEST REPORT ==========");

        List<Transaction> allTransactions = new ArrayList<>();

        for (Income income : incomeManager.getListIncome()) {
            allTransactions.add(income);
        }

        for (Expense expense : expenseManager.getListExpenses()) {
            allTransactions.add(expense);
        }

        Report report = new Report(
                "2026-05-29",
                "2026-05-31",
                allTransactions);

        System.out.println("\n=== LAPORAN CASH  ===");
        report.generateDaily(cash.getAccountID());
        report.generateWeekly(cash.getAccountID());
        report.generateMonthly(cash.getAccountID());
        System.out.println("\n=== LAPORAN DANA ===");
        report.generateDaily(dana.getAccountID());
        report.generateWeekly(dana.getAccountID());
        report.generateMonthly(dana.getAccountID());

        // =====================================================
        // 10. TEST ANALYSIS
        // =====================================================
        System.out.println("\n========== 10. TEST ANALYSIS ==========");

        Analysis analysis = new Analysis(expenseManager.getListExpenses());

        System.out.println("Total pengeluaran: " + analysis.getTotalExpense());
        System.out.println("Kategori pengeluaran terbesar: " + analysis.largestCategory());
        System.out.println("Rata-rata pengeluaran: " + analysis.averageExpend());
        System.out.println("Persentase kategori Makanan: " + analysis.categoryPercentage("Makanan") + "%");

        // =====================================================
        // 11. TEST BUDGET & NOTIFICATION
        // =====================================================
        System.out.println("\n========== 11. TEST BUDGET & NOTIFICATION ==========");

        Budget budget = new Budget(
                500000,
                10000,
                0.8);

        budget.showBudgetInfo();

        double totalExpense = analysis.getTotalExpense();

        if (budget.checkThreshold(totalExpense)) {
            Notification notification = new Notification(
                    "Pengeluaran sudah mendekati atau melewati batas total budget.",
                    "2026-05-29");

            notification.sendNotification();
        } else {
            System.out.println("Total pengeluaran masih aman.");
        }

        double makanExpense = analysis.totalByCategory("Makanan");

        if (budget.checkCategoryThreshold(makanExpense)) {
            Notification notification = new Notification(
                    "Pengeluaran kategori makanan sudah mendekati batas budget.",
                    "2026-05-29");

            notification.sendNotification();
        } else {
            System.out.println("Budget kategori masih aman.");
        }
        
        // =====================================================
        // 12. TEST DELETE INCOME DENGAN ROLLBACK
        // =====================================================
        System.out.println("\n========== 12. TEST DELETE INCOME DENGAN ROLLBACK ==========");
        
        System.out.println("\nSaldo akhir dompet:");
        walletManager.showAllWallets();
        System.out.println("Total saldo akhir: " + walletManager.calculateTotalBalance());


        boolean deleteIncome = incomeManager.deleteIncome(1001, cash);

        if (deleteIncome) {
            System.out.println("Income berhasil dihapus dari list dan saldo dikembalikan.");
        } else {
            System.out.println("Income tidak ditemukan.");
        }

        incomeManager.showAllIncome();

        System.out.println("\nSaldo akhir dompet:");
        walletManager.showAllWallets();
        System.out.println("Total saldo akhir: " + walletManager.calculateTotalBalance());

        System.out.println("\n===== TEST SELESAI =====");
    }
    
    
}