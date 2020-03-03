package test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class downloadImage {
    private static final Logger logger = LoggerFactory.getLogger(downloadImage.class);
    private static final String IMAGE_PATH = "D:\\酿造\\数据库数据2\\酱香\\image";

    public  void downImages(String imgUrl) {
        File imgFilePath = new File(IMAGE_PATH);
        //如果目录不存在，创建目录
        if (!imgFilePath.exists()) {
            boolean mkdir = imgFilePath.mkdir();
            logger.info(mkdir ? "程序已自动创建目录" : "");
        }
        //拼接image下载地址
        String beforeUrl = imgUrl.substring(0, imgUrl.lastIndexOf("/") + 1);
        String imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
        String newImgName = imgName;

        try {
            newImgName = URLEncoder.encode(imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("图片名称有误");
            e.printStackTrace();
        }
        imgUrl = beforeUrl + newImgName;
        try {
            //获取下载地址
            URL url = new URL(imgUrl);
            InputStream in = null;
            OutputStream out = null;
            try {
                //链接网络地址，创建连接对象
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //建立到远程对象的实际连接
                connection.connect();
                //连接成功
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //获取链接的输入流
                    in = connection.getInputStream();
                    //创建下载到本地的文件
                    File imgFile = new File(IMAGE_PATH, imgName);
                    if (imgFile.exists()) {
                        logger.info(imgName + " 该图片已存在");
                    } else {
                        //写入文件
                        out = new FileOutputStream(imgFile);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) != -1) {
                            out.write(buf, 0, len);
                        }
                        out.flush();
                        //断开连接
                        connection.disconnect();
                        logger.info(imgName + "下载成功");
                    }
                } else {
                    logger.warn("连接失败");
                }

            } catch (IOException e) {
                logger.error("下载图片失败");
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }

                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    logger.error("读写流关闭出现异常");
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            logger.error("网址格式错误");
            e.printStackTrace();
        }
    }
}
