package com.doppelgunner.doppeleater.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by robertoguazon on 14/01/2017.
 */
public class ImageHandler {

    public static ImageIcon convertToImageIcon(Image image) {
        if (image == null) return null;

        return new ImageIcon(convertToBufferedImage(image));
    }

    public static BufferedImage convertToBufferedImage(Image image) {
        if (image == null) return null;

        return SwingFXUtils.fromFXImage(image, null);
    }

    public static Image convertToImage(ImageIcon imageIcon) {
        if (imageIcon == null) return null;
        java.awt.Image image = imageIcon.getImage();
        if (!(image instanceof RenderedImage)) {
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bufferedImage.createGraphics();
            g.drawImage(image,0,0,null);
            g.dispose();
            image = bufferedImage;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)image,"png",out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        return new Image(in);
    }
}
