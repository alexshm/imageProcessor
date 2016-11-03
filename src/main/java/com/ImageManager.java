package com;

import db.ConfigDBUsage;
import org.apache.log4j.Logger;

import java.io.*;
import java.security.MessageDigest;
import java.sql.SQLException;



public class ImageManager {
    private static final Logger _logger = Logger.getRootLogger();
    private final ConfigDBUsage dbManager;
    private final String imagesFile;
    private final String outPutImagesFolder;

    public ImageManager(ConfigDBUsage dbManager, String imagesFile, String outPutImagesFolder) {
        this.dbManager = dbManager;
        this.imagesFile = imagesFile;
        this.outPutImagesFolder =outPutImagesFolder;
    }

    public  void downloadAndConvert() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(imagesFile));
            for (String urlString = br.readLine(); urlString != null; urlString = br.readLine()) {
                String finalUrlString = urlString;
                Runnable task = getRunnable(finalUrlString);
                try {
                    Thread thread = new Thread(task);
                    thread.start();
                } catch (Exception e) {
                    _logger.error("exaction on possessing the image ", e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Runnable getRunnable(String finalUrlString) {
        return () -> {
            IImageProcessor MyImageProcessor = ImageProcessorFacory.getImageProcessorr(ImageProcessorEnum.MY_IMAGE_PROCESSOR.value(), finalUrlString , outPutImagesFolder);
            try {
                assert MyImageProcessor != null: "Image process should be initialized";
                MyImageProcessor.downloadImage();
                String filePath = MyImageProcessor.changeImageSize(200, 200).grayScaleImage().saveImage();
                String md5 = getStringChecksum(finalUrlString);
                dbManager.insertIntoTable(filePath, finalUrlString, md5);
            } catch (SQLException e) {
                _logger.error("exaction inserting into image ", e);
            } catch (Exception e) {
                _logger.error("exaction in processing image ", e);
            }
        };
    }

    public static String  getStringChecksum(String xmlString)  {
        StringBuffer sb = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Change MD5 to SHA1 to get SHA checksum
            md.update(xmlString.getBytes(), 0, xmlString.getBytes().length);
            byte[] mdbytes = md.digest();
            //convert the byte to hex format
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e){
            _logger.error("Error in getStringChecksum: " + e.getMessage());

        }
        return sb.toString();
    }
}
