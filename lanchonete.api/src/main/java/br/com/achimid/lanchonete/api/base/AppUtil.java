package br.com.achimid.lanchonete.api.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AppUtil {
    private static AppUtil ourInstance = new AppUtil();

    public static AppUtil getInstance() {
        return ourInstance;
    }

    private AppUtil() {
    }

    //@Value("${resource.folder.images}")
    private String RESOURCE_IMG = "img/";

    public String uploadImageAPI(String url){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://uploads.im/api?upload={url}", String.class, url);
        return response.getBody();
    }

    public String copyImageToResource(String urlImg, String nome){
        //String imageUrl = "http://www.technicalkeeda.com/img/images/article/spring-file-upload-eclipse-setup.png";

        InputStream inputStream = null;
        OutputStream outputStream = null;
        String returnPath = null;

        try {
            inputStream = new URL(urlImg).openStream();
            returnPath = RESOURCE_IMG + nome;

            // caso nao exista o diretorio
            File directory = new File(RESOURCE_IMG);
            if (! directory.exists()) directory.mkdir();

            outputStream = new FileOutputStream(new File(returnPath));

            // redimencionando imagem
            BufferedImage imBuff = ImageIO.read(inputStream);
            if(imBuff.getWidth() > 1200){
                imBuff = getScaledInstance(imBuff, imBuff.getWidth() / 2, imBuff.getHeight() / 2, RenderingHints.VALUE_INTERPOLATION_BICUBIC, true);
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(imBuff, "png", os);
            inputStream = new ByteArrayInputStream(os.toByteArray());

            byte[] buffer = new byte[2048];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            System.out.println("Salvando imagem.");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException :- " + e.getMessage());

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException :- " + e.getMessage());

        } catch (IOException e) {
            System.out.println("IOException :- " + e.getMessage());

        } finally {
            try {

                inputStream.close();
                outputStream.close();

            } catch (IOException e) {
                System.out.println("Finally IOException :- " + e.getMessage());
            }
        }

        return returnPath;
    }

    public BufferedImage getScaledInstance(BufferedImage img,
                                           int targetWidth,
                                           int targetHeight,
                                           Object hint,
                                           boolean higherQuality)
    {
        System.out.println("Convertendo imagem.");
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        System.out.println("Terminando Conversão.");
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(
                AppUtil.getInstance().uploadImageAPI("http://blog.alphasignage.com.br/wp-content/uploads/2016/03/natureza_1920x1080.jpg")
        );


    }
}
