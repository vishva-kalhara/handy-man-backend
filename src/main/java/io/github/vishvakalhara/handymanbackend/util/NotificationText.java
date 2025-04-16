package io.github.vishvakalhara.handymanbackend.util;

public class NotificationText {

    public static String getMessageToTaskOwnerAtBidSubmission(
            String bidderName,
            double price,
            String taskName
    ) {

        return bidderName + " has submitted a bid of " + FMT.formatCurrency(price) + " for " + taskName + ".";
    }

    public static String getMessageToHandyManAtBidSubmission(
            double price,
            String taskName
    ) {

        return "Your bid of " + FMT.formatCurrency(price) + " for " + taskName + " is not visible.";
    }

    public static String getMessageToHandyManAtBidRejection(
            double price,
            String taskName
    ) {

        return "Your bid of " + FMT.formatCurrency(price) + " for " + taskName + " wasn't chosen this time.";
    }

    public static String getMessageToHandyManAtBidAccepted(
            double price,
            String taskName
    ) {

        return "Congratulations! Your offer of " + FMT.formatCurrency(price) + " accepted for " + taskName + ".";
    }
}
