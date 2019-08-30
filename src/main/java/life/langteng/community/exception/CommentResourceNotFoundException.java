package life.langteng.community.exception;

import life.langteng.community.bean.ErrorMessage;

public class CommentResourceNotFoundException extends ResourceNotFoundException{

    public CommentResourceNotFoundException() {
    }

    public CommentResourceNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
