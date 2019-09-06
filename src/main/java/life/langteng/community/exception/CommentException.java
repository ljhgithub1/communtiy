package life.langteng.community.exception;

import life.langteng.community.bean.ReminderMessage;

public class CommentException extends CommunityException {

    public CommentException() {
    }

    public CommentException(ReminderMessage errorMessage) {
        super(errorMessage);
    }
}
