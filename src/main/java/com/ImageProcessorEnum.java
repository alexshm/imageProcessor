package com;

public enum ImageProcessorEnum {

    MY_IMAGE_PROCESSOR("MyImageProcessor");

    private String ImageProcessorType;

    private ImageProcessorEnum(String s) {
        ImageProcessorType = s;
    }

    public String value() {
            return ImageProcessorType;
        }
}
