package life.langteng.community.exception;

import life.langteng.community.bean.ReminderMessage;

public class QuestionExcepiton extends CommunityException {
    public QuestionExcepiton() {
    }

    public QuestionExcepiton(ReminderMessage errorMessage) {
        super(errorMessage);
    }
}
