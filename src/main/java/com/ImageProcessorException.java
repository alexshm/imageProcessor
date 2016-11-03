package com;


public class ImageProcessorException extends Exception {

    public ImageProcessorException(String msg){
       super(msg);
    }

    public ImageProcessorException(String msg, Throwable cause){
        super(msg, cause);
    }

}
