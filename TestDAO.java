import src.dao.*;
import src.model.*;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("        TEST DAO FINTRACK");
        System.out.println("======================================");

        UserDAO userDAO = new UserDAO();
        ProfileDAO profileDAO = new ProfileDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        WalletDAO walletDAO = new WalletDAO();
        IncomeDAO incomeDAO = new IncomeDAO();
        ExpenseDAO expenseDAO = new ExpenseDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        BudgetDAO budgetDAO = new BudgetDAO();
        NotificationDAO notificationDAO = new NotificationDAO();
        ReportDAO reportDAO = new ReportDAO();
        AnalysisDAO analysisDAO = new AnalysisDAO();

        try {
            // =========================================================
            // 1. TEST USER REGISTER & LOGIN
            // =========================================================
            System.out.println("\n=== 1. TEST USER ===");

            long time = System.currentTimeMillis();

            String username = "j" + (time % 100000);
            String email = "j" + (time % 100000) + "@g.co";

            User user = new User(
                    0,
                    username,
                    email,
                    "12345",
                    null);

            boolean registerSuccess = userDAO.register(user);

            System.out.println("Register user : " + registerSuccess);
            System.out.println("User ID       : " + user.getUserID());
            System.out.println("Username      : " + username);
            System.out.println("Email         : " + email);

            if (!registerSuccess || user.getUserID() == 0) {
                System.out.println("TEST DIHENTIKAN: Register user gagal.");
                return;
            }

            int userID = user.getUserID();

            User loginUser = userDAO.login(email, "12345");
            System.out.println("Login result  : " + loginUser);

            if (loginUser == null) {
                System.out.println("WARNING: Login gagal. Cek UserDAO.login().");
            }

            // =========================================================
            // 2. TEST PROFILE
            // =========================================================
            System.out.println("\n=== 2. TEST PROFILE ===");

            Profile profile = new Profile(
                    0,
                    userID,
                    "Julio Chrysanto Tanlain",
                    "08123456789",
                    "Surabaya");

            boolean profileSuccess = profileDAO.insertProfile(profile);
            System.out.println("Insert profile : " + profileSuccess);
            System.out.println("Profile ID     : " + profile.getProfileID());

            Profile foundProfile = profileDAO.findByUserId(userID);
            System.out.println("Find profile   : " + foundProfile);

            // =========================================================
            // 3. TEST CATEGORY
            // =========================================================
            System.out.println("\n=== 3. TEST CATEGORY ===");

            Category incomeCategory = new Category(0, "Gaji Test " + time, "income");
            Category expenseCategory = new Category(0, "Makan Test " + time, "expense");

            int incomeCategoryID = categoryDAO.insertCategory(incomeCategory);
            int expenseCategoryID = categoryDAO.insertCategory(expenseCategory);

            boolean incomeCategorySuccess = incomeCategoryID != -1;
            boolean expenseCategorySuccess = expenseCategoryID != -1;

            System.out.println("Insert income category  : " + incomeCategorySuccess);
            System.out.println("Income Category ID      : " + incomeCategoryID);

            System.out.println("Insert expense category : " + expenseCategorySuccess);
            System.out.println("Expense Category ID     : " + expenseCategoryID);

            if (!incomeCategorySuccess || !expenseCategorySuccess) {
                System.out.println("TEST DIHENTIKAN: Insert category gagal.");
                return;
            }

            Category foundIncomeCategory = categoryDAO.findById(incomeCategoryID);
            Category foundExpenseCategory = categoryDAO.findById(expenseCategoryID);

            System.out.println("Find income category    : " + foundIncomeCategory);
            System.out.println("Find expense category   : " + foundExpenseCategory);

            List<Category> allCategories = categoryDAO.findAll();
            System.out.println("Total categories        : " + allCategories.size());

            List<Category> incomeCategories = categoryDAO.findByType("income");
            List<Category> expenseCategories = categoryDAO.findByType("expense");

            System.out.println("Total income categories : " + incomeCategories.size());
            System.out.println("Total expense categories: " + expenseCategories.size());

            // =========================================================
            // 4. TEST WALLET
            // =========================================================
            System.out.println("\n=== 4. TEST WALLET ===");

            AccountWallet cashWallet = new AccountWallet(
                    0,
                    userID,
                    "Dompet Cash Test " + time,
                    100000);

            boolean walletSuccess = walletDAO.insertPhysicalWallet(cashWallet);

            System.out.println("Insert physical wallet : " + walletSuccess);
            System.out.println("Wallet ID              : " + cashWallet.getAccountID());

            if (!walletSuccess || cashWallet.getAccountID() == 0) {
                System.out.println("TEST DIHENTIKAN: Insert wallet gagal.");
                return;
            }

            int accountID = cashWallet.getAccountID();

            AccountWallet foundWallet = walletDAO.findById(accountID);
            System.out.println("Find wallet            : " + foundWallet);

            double totalBalanceBefore = walletDAO.getTotalBalance(userID);
            System.out.println("Total balance awal     : " + totalBalanceBefore);

            // Optional test e-wallet, kalau WalletDAO kamu punya
            // insertEWallet(AccountWallet, String, String)
            AccountWallet danaWallet = new AccountWallet(
                    0,
                    userID,
                    "DANA Test " + time,
                    50000);

            boolean ewalletSuccess = walletDAO.insertEWallet(danaWallet, "DANA", "08123456789");
            System.out.println("Insert ewallet         : " + ewalletSuccess);
            System.out.println("EWallet ID             : " + danaWallet.getAccountID());

            // =========================================================
            // 5. TEST INCOME
            // =========================================================
            System.out.println("\n=== 5. TEST INCOME ===");

            Income income = new Income(
                    0,
                    0,
                    userID,
                    accountID,
                    incomeCategoryID,
                    "Uang Bulanan Test",
                    50000,
                    "2026-06-02",
                    "Test pemasukan DAO");

            boolean incomeSuccess = incomeDAO.insertIncome(income);

            System.out.println("Insert income          : " + incomeSuccess);
            System.out.println("Income Transaction ID  : " + income.getTransactionID());

            if (!incomeSuccess) {
                System.out.println("WARNING: Insert income gagal. Lanjut test berikutnya mungkin ikut terganggu.");
            }

            AccountWallet walletAfterIncome = walletDAO.findById(accountID);
            System.out.println("Saldo setelah income   : " + walletAfterIncome.getBalance());

            // =========================================================
            // 6. TEST EXPENSE
            // =========================================================
            System.out.println("\n=== 6. TEST EXPENSE ===");

            Expense expense = new Expense(
                    0,
                    0,
                    userID,
                    accountID,
                    expenseCategoryID,
                    "Beli Nasi Test",
                    20000,
                    "2026-06-02",
                    "Test pengeluaran DAO");

            boolean expenseSuccess = expenseDAO.insertExpense(expense);

            System.out.println("Insert expense         : " + expenseSuccess);
            System.out.println("Expense Transaction ID : " + expense.getTransactionID());

            if (!expenseSuccess) {
                System.out.println("WARNING: Insert expense gagal. Lanjut test berikutnya mungkin ikut terganggu.");
            }

            AccountWallet walletAfterExpense = walletDAO.findById(accountID);
            System.out.println("Saldo setelah expense  : " + walletAfterExpense.getBalance());

            // Expected saldo:
            // Awal 100000 + income 50000 - expense 20000 = 130000
            System.out.println("Expected saldo         : 130000.0");

            // =========================================================
            // 7. TEST TRANSACTION
            // =========================================================
            System.out.println("\n=== 7. TEST TRANSACTION ===");

            List<Transaction> transactions = transactionDAO.findByUserId(userID);
            System.out.println("Jumlah transaksi user  : " + transactions.size());

            for (Transaction t : transactions) {
                System.out.println(t);
            }

            List<Transaction> filteredTransactions = transactionDAO.findByFilter(
                    userID,
                    accountID,
                    null,
                    null,
                    "2026-06-01",
                    "2026-06-30");

            System.out.println("Jumlah filter Juni     : " + filteredTransactions.size());

            List<Transaction> filteredExpense = transactionDAO.findByFilter(
                    userID,
                    accountID,
                    expenseCategoryID,
                    "expense",
                    "2026-06-01",
                    "2026-06-30");

            System.out.println("Jumlah filter expense  : " + filteredExpense.size());

            // =========================================================
            // 8. TEST BUDGET
            // =========================================================
            System.out.println("\n=== 8. TEST BUDGET ===");

            Budget budget = new Budget(
                    0,
                    userID,
                    expenseCategoryID,
                    500000,
                    100000.0,
                    0.8,
                    "2026-06-01",
                    "2026-06-30");

            boolean budgetSuccess = budgetDAO.insertBudget(budget);

            System.out.println("Insert budget          : " + budgetSuccess);
            System.out.println("Budget ID              : " + budget.getBudgetID());

            if (!budgetSuccess) {
                System.out.println("WARNING: Insert budget gagal.");
            }

            double totalExpensePeriod = budgetDAO.getTotalExpenseInPeriod(
                    userID,
                    "2026-06-01",
                    "2026-06-30");

            double categoryExpensePeriod = budgetDAO.getCategoryExpenseInPeriod(
                    userID,
                    expenseCategoryID,
                    "2026-06-01",
                    "2026-06-30");

            System.out.println("Total expense period   : " + totalExpensePeriod);
            System.out.println("Category expense period: " + categoryExpensePeriod);

            boolean totalExceeded = budgetDAO.isTotalBudgetExceeded(budget);
            boolean categoryExceeded = budgetDAO.isCategoryBudgetExceeded(budget);

            System.out.println("Total budget exceeded  : " + totalExceeded);
            System.out.println("Category budget exceeded: " + categoryExceeded);

            // =========================================================
            // 9. TEST NOTIFICATION
            // =========================================================
            System.out.println("\n=== 9. TEST NOTIFICATION ===");

            Notification notification = new Notification(
                    0,
                    userID,
                    budget.getBudgetID(),
                    "Pengeluaran kategori makan mendekati batas budget.",
                    "2026-06-02");

            boolean notificationSuccess = notificationDAO.insertNotification(notification);

            System.out.println("Insert notification    : " + notificationSuccess);
            System.out.println("Notification ID        : " + notification.getNotificationID());

            List<Notification> notifications = notificationDAO.findByUserId(userID);
            System.out.println("Jumlah notification    : " + notifications.size());

            for (Notification n : notifications) {
                System.out.println(n);
            }

            // =========================================================
            // 10. TEST REPORT
            // =========================================================
            System.out.println("\n=== 10. TEST REPORT ===");

            Report report = reportDAO.generateReport(
                    userID,
                    accountID,
                    "monthly",
                    "2026-06-01",
                    "2026-06-30");

            System.out.println("Generated report       : " + report);

            if (report != null) {
                System.out.println("Report ID              : " + report.getReportID());
                System.out.println("Total income           : " + report.getTotalIncome());
                System.out.println("Total expense          : " + report.getTotalExpense());
                System.out.println("Ending balance         : " + report.getEndingBalance());
            }

            // =========================================================
            // 11. TEST ANALYSIS
            // =========================================================
            System.out.println("\n=== 11. TEST ANALYSIS ===");

            Analysis analysis = analysisDAO.generateAnalysis(
                    userID,
                    "2026-06-01",
                    "2026-06-30");

            System.out.println("Generated analysis     : " + analysis);

            if (analysis != null) {
                System.out.println("Analysis ID            : " + analysis.getAnalysisID());
                System.out.println("Total expense          : " + analysis.getTotalExpense());
                System.out.println("Average expense        : " + analysis.getAverageExpense());
                System.out.println("Largest category ID    : " + analysis.getLargestCategoryID());

                List<AnalysisCategoryPercentage> percentages = analysisDAO
                        .findPercentagesByAnalysisId(analysis.getAnalysisID());

                System.out.println("Jumlah percentage      : " + percentages.size());

                for (AnalysisCategoryPercentage p : percentages) {
                    System.out.println(p);
                }
            }

            // =========================================================
            // 12. TEST UPDATE CATEGORY
            // =========================================================
            System.out.println("\n=== 12. TEST UPDATE CATEGORY ===");

            boolean updateCategoryNameSuccess = categoryDAO.updateCategoryName(expenseCategoryID, "Makan Updated Test");

            System.out.println("Update category name   : " + updateCategoryNameSuccess);

            Category updatedCategory = categoryDAO.findById(expenseCategoryID);
            System.out.println("Updated category       : " + updatedCategory);

            // =========================================================
            // 13. TEST UPDATE WALLET BALANCE MANUAL
            // =========================================================
            System.out.println("\n=== 13. TEST UPDATE WALLET BALANCE ===");

            boolean addBalanceSuccess = walletDAO.addBalance(accountID, 10000);
            System.out.println("Add balance 10000      : " + addBalanceSuccess);

            AccountWallet walletAfterAdd = walletDAO.findById(accountID);
            System.out.println("Saldo setelah add      : " + walletAfterAdd.getBalance());

            boolean subtractBalanceSuccess = walletDAO.subtractBalance(accountID, 5000);
            System.out.println("Subtract balance 5000  : " + subtractBalanceSuccess);

            AccountWallet walletAfterSubtract = walletDAO.findById(accountID);
            System.out.println("Saldo setelah subtract : " + walletAfterSubtract.getBalance());

            // =========================================================
            // SELESAI
            // =========================================================
            System.out.println("\n======================================");
            System.out.println("        TEST DAO SELESAI");
            System.out.println("======================================");

        } catch (Exception e) {
            System.out.println("\nTERJADI ERROR SAAT TEST DAO:");
            e.printStackTrace();
        }
    }
}