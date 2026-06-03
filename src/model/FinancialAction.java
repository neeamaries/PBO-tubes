package src.model;

public interface FinancialAction {
    void execute(AccountWallet wallet);
    void rollback(AccountWallet wallet);
}