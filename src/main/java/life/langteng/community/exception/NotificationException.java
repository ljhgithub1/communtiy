package life.langteng.community.exception;

import life.langteng.community.bean.ReminderMessage;

public class NotificationException extends CommunityException {
    public NotificationException() {
    }

    public NotificationException(ReminderMessage errorMessage) {
        super(errorMessage);
    }
}
