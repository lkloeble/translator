package org.patrologia.ocr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lkloeble on 30/01/2017.
 */
public class OCR {

    private static final int WIDTH = 630;
    private static final int HEIGHT = 800;
    //private static final int WIDTH = 39;
    //private static final int HEIGHT = 18;
    private static final int TRESHOLD = -16000000;

    private CharacterFinder characterFinder = new CharacterFinder();

    public static void main(String[] args) {
        OCR ocr = new OCR();
        ocr.process();
    }

    public void process() {
        BufferedImage image = null;
        int[][] pixels = new int[WIDTH][HEIGHT];
        try {
            File file = new File("C:\\java_projects\\src\\main\\resources\\ocr\\plurima.png");
            image = ImageIO.read(file);
            //image = image.getSubimage(100,10,110,14);
            int height = image.getHeight();
            int width = image.getWidth();
            boolean[][] blackWhite = new boolean[width][height];
            for(int i=0;i<width;i++) {
                for(int j=0;j<height;j++) {
                    int rgb = image.getRGB(i, j);
                    pixels[i][j] = rgb;
                    if(rgb < TRESHOLD) blackWhite[i][j] = true;
                }
            }
            System.out.println("end of full load");
            drawSubImage(blackWhite);
            char[] charsExtracted = characterFinder.extract(blackWhite);
            System.out.println("\n\nocr has red : " + charsExtracted);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void drawSubImage(boolean[][] pixels) {
        for(int i=0;i<pixels[0].length;i++) {
            StringBuilder sb = new StringBuilder();
            for(int j=0;j<pixels.length;j++) {
                if(pixels[j][i]) {
                    sb.append("X");
                } else {
                    sb.append(" ");
                }
            }
            System.out.println(sb);
        }
    }
}
