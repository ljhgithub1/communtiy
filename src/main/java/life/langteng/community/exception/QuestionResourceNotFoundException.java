package life.langteng.community.exception;

import life.langteng.community.bean.ErrorMessage;

public class QuestionResourceNotFoundException extends ResourceNotFoundException {

    public QuestionResourceNotFoundException() {
    }

    public QuestionResourceNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
