package com.topcoder.demo.error;

public class TrainNotFoundException extends RuntimeException {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	/**
     * Instantiates a new Entity not found exception.
     *
     * @param message the message
     */
    public TrainNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Entity not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TrainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Entity not found exception.
     *
     * @param cause the cause
     */
    public TrainNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Entity not found exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected TrainNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
