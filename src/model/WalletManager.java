package src.model;
import java.util.ArrayList;
import java.util.List;
public class WalletManager {
    private List<AccountWallet> wallets;

    public WalletManager() {
        this.wallets = new ArrayList<>();
    }

    public void addWallet(AccountWallet wallet) {
        wallets.add(wallet);
    }

    public boolean editWallet(int id, String newName) {
        for (AccountWallet wallet : wallets) {
            if (wallet.getAccountID() == id) {
                wallet.setAccountName(newName);
                return true;
            }
        }

        return false;
    }

    public boolean deleteWallet(int id) {
        AccountWallet walletYangMauDihapus = null;

        for (AccountWallet wallet : wallets) {
            if (wallet.getAccountID() == id) {
                walletYangMauDihapus = wallet;
                break;
            }
        }

        if (walletYangMauDihapus != null) {
            wallets.remove(walletYangMauDihapus);
            return true;
        }

        return false;
    }

    public double calculateTotalBalance() {
        double total = 0;

        for (AccountWallet wallet : wallets) {
            total += wallet.calculateBalance();
        }

        return total;
    }

    public List<AccountWallet> getWalletList() {
        return wallets;
    }

    public AccountWallet findWalletById(int id) {
        for (AccountWallet wallet : wallets) {
            if (wallet.getAccountID() == id) {
                return wallet;
            }
        }

        return null;
    }

    public void showAllWallets() {
        if (wallets.isEmpty()) {
            System.out.println("Belum ada dompet.");
            return;
        }

        for (AccountWallet wallet : wallets) {
            System.out.println(wallet);
        }
    }
}
