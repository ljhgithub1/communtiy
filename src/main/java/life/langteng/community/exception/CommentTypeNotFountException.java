package life.langteng.community.exception;

import life.langteng.community.bean.ErrorMessage;

public class CommentTypeNotFountException extends ResourceNotFoundException {

    public CommentTypeNotFountException() {
    }

    public CommentTypeNotFountException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
