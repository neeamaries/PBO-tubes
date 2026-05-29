package src.model;

public class Notification {
    private String message;
    private String date;

    public Notification() {
    }

    public Notification(String message, String date) {
        this.message = message;
        this.date = date;
    }

    public void sendNotification() {
        System.out.println("=== NOTIFIKASI ===");
        System.out.println("Tanggal : " + date);
        System.out.println("Pesan   : " + message);
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
