package example.api.rest.errors;


public class ApiException extends RuntimeException {

    private ErrorType errorType;

    private String messageParam;

    public ApiException(ErrorType errorType, String messageParam) {
        super(getMessageFromParam(errorType, messageParam));
        this.errorType = errorType;
        this.messageParam = messageParam;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getMessageParam() {
        return messageParam;
    }

    private static String getMessageFromParam(ErrorType errorType, String messageParam) {
        return String.format(errorType.getMessageErrorTemplate(), messageParam);
    }

    @Override
    public String toString() {
        return "RestApiException{" +
                "errorType=" + errorType +
                ", messageParam='" + messageParam + '\'' +
                "} " + super.toString();
    }

}
