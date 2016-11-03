package com;

/**
 * create support for other lib in the future
 */
public class ImageProcessorFacory {

    /**
     * Return a ImageProcessor
     *
     * @param ImageProcessorType
     * @param outPutImagesFolder
     * @return
     */
    public static IImageProcessor getImageProcessorr(String ImageProcessorType, String url, String outPutImagesFolder) {

        if (ImageProcessorEnum.MY_IMAGE_PROCESSOR.value().equalsIgnoreCase(ImageProcessorType)) {
            return new MyImageProcessor(url, outPutImagesFolder);
        }

        return null;
    }
}
