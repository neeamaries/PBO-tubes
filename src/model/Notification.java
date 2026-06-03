package src.model;

public class Notification {
    private int notificationID;
    private int userID;
    private int budgetID;
    private String message;
    private String notificationDate;

    public Notification() {
    }

    public Notification(int notificationID, int userID, int budgetID, String message, String notificationDate) {
        this.notificationID = notificationID;
        this.userID = userID;
        this.budgetID = budgetID;
        this.message = message;
        this.notificationDate = notificationDate;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(int budgetID) {
        this.budgetID = budgetID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationID=" + notificationID +
                ", userID=" + userID +
                ", budgetID=" + budgetID +
                ", message='" + message + '\'' +
                ", notificationDate='" + notificationDate + '\'' +
                '}';
    }
}