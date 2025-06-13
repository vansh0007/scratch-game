package com.scratchgame.exceptions;

/**
 * Custom exception class for the Scratch Game application.
 * Used to handle game-specific errors and provide meaningful error messages.
 */
public class GameException extends Exception {
    private final ErrorCode errorCode;

    public GameException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GameException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public GameException(ErrorCode errorCode, String additionalMessage) {
        super(errorCode.getMessage() + ": " + additionalMessage);
        this.errorCode = errorCode;
    }

    public GameException(ErrorCode errorCode, String additionalMessage, Throwable cause) {
        super(errorCode.getMessage() + ": " + additionalMessage, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Enumeration of error codes with corresponding messages.
     */
    public enum ErrorCode {
        INVALID_CONFIG("Invalid game configuration"),
        INVALID_BET_AMOUNT("Invalid betting amount"),
        INVALID_MATRIX("Invalid game matrix generated"),
        INVALID_SYMBOL("Invalid symbol encountered"),
         FILE_READ_ERROR("Error reading configuration file"),
        JSON_PARSE_ERROR("Error parsing JSON configuration"),
        MISSING_REQUIRED_FIELD("Required field is missing in configuration"),
        INVALID_PROBABILITY("Invalid probability configuration"),
        UNEXPECTED_ERROR("Unexpected error occurred");

        private final String message;

        ErrorCode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}