package com;

import ij.*;
import ij.io.FileSaver;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import org.apache.log4j.Logger;


class MyImageProcessor implements IImageProcessor {
    private static String outPutImagesFolder ;
    private static final Logger _logger = Logger.getRootLogger();
    private ImagePlus imp;
    private final String url;

    MyImageProcessor(String url, String outPutImagesFolder) {
        this.url = url;
        MyImageProcessor.outPutImagesFolder =outPutImagesFolder;
    }

    public IImageProcessor changeImageSize(int width, int height) throws ImageProcessorException {
        if (imp != null) {
            ImageProcessor ip = this.imp.getProcessor();
            ip = ip.resize(width, height);
            this.imp.setProcessor(ip);
            _logger.info("resizing the image from "+url+" to width: "+width+" height: "+height );
        } else {
            _logger.error("problem in downloading the file");
            throw new ImageProcessorException("problem in downloading the file from "+url);
        }
        return this;
    }

    public void downloadImage() throws ImageProcessorException {
        imp = IJ.openImage(url);
        if(imp==null){
            _logger.error("problem in downloading the file in "+url);
            throw new ImageProcessorException("problem in downloading the file from "+url);
        }

    }

    public IImageProcessor grayScaleImage() throws ImageProcessorException {
        if (imp != null) {
            ImageProcessor ip = this.imp.getProcessor();
            ip = new ByteProcessor(ip, true);
            this.imp.setProcessor(ip);
            _logger.info("changing image in "+url+" to grayScale");
        } else {
            throw new ImageProcessorException("problem in downloading the file from "+url);
        }
        return this;
    }

    public String saveImage() throws ImageProcessorException {
        String filePath = "";
        if (imp != null) {
            try {
                String[] splitedArray = this.url.split("/");
                String imageName = splitedArray[splitedArray.length - 1];
                filePath = outPutImagesFolder + "//" + imageName;
                FileSaver fileSaver = new FileSaver(imp);
                fileSaver.saveAsJpeg(filePath);
            } catch (Exception e) {
                _logger.error("error in saving image from "+url, e);
                throw new ImageProcessorException(e.getMessage());
            }
        } else {
            throw new ImageProcessorException("problem in downloading the file from "+url);
        }
        return filePath;

    }
}
