package com;


public interface IImageProcessor {
    /**
     * changing the image size
     *
     * @return MyImageProcessor
     */
    IImageProcessor changeImageSize(int width, int height) throws ImageProcessorException;


    /**
     * converting the image to grayScale
     *
     * @return MyImageProcessor
     */
    IImageProcessor grayScaleImage() throws ImageProcessorException;

    /**
     * save the file in the file system return the file path
     *
     * @return file path
     */
    String saveImage() throws ImageProcessorException;

    /**
     * download the  t
     */
    public void downloadImage() throws ImageProcessorException;
}

