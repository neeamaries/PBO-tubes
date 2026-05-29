package src.model;

public interface FinancialAction {
    void execute();
    void rollback();
    
}