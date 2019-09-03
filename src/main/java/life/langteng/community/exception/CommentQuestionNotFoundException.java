package life.langteng.community.exception;

import life.langteng.community.bean.ErrorMessage;

public class CommentQuestionNotFoundException extends CommentResourceNotFoundException {

    public CommentQuestionNotFoundException() {
    }

    public CommentQuestionNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
