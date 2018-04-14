package org.fuqinqin.code.exception;

public class BeanCopyException extends Exception {

    private static final long serialVersionUID = -1109101399703220536L;

    private Integer code;
    private String message;

    public BeanCopyException(Exception ex){}

    public BeanCopyException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
