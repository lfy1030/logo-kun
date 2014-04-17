/*
 * quick program for adding watermark logo to a bunch of pictures.
 * put images to be added in input folder
 * and the program spits out the watermarked images in the output folder
 * customized for my own usage 
 * based on this very useful tutorial: 
 * http://www.codejava.net/java-se/graphics/adding-a-watermark-over-an-image-programmatically-using-java
 */
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class main {
		
	public static void main (String[] args) {
		File watermarkImageFile = new File("./logo.png");
		Scanner user_input = new Scanner( System.in );
		System.out.print("How many files are you converting?");
		int numFiles = Integer.parseInt(user_input.next());
		for(int i = 1; i <= numFiles; i ++) {
			File sourceImageFile = new File("./input/" + i + ".jpg");
			File destImageFile = new File("./output/" + i + ".png");
			addImageWatermark(watermarkImageFile, sourceImageFile, destImageFile);
			System.out.println("The watermark has been added to: " + i + ".png" );
		}	
	}

	static void addImageWatermark(File watermarkImageFile,
			File sourceImageFile, File destImageFile) {
		try {
			BufferedImage sourceImage = ImageIO.read(sourceImageFile);
			BufferedImage watermarkImage = ImageIO.read(watermarkImageFile);

			// initializes necessary graphic properties
			Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
			AlphaComposite alphaChannel = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.15f);
			g2d.setComposite(alphaChannel);

			// gets the dimensions of the source image
			int width = sourceImage.getWidth();
			int height = sourceImage.getHeight();

			// calculates the new logo size based on the width and length of the
			// source image
			double logoWidth = 0;
			double logoHeight = 0;
			if (width < height) {
				logoWidth = 0.45 * width;
			} else {
				logoWidth = 0.45 * height;
			}
			logoHeight = watermarkImage.getHeight()*(logoWidth / watermarkImage.getWidth());

			// returns logo dimensions to int
			int logoW = (int) logoWidth;
			int logoH = (int) logoHeight;
			
			// calculates the coordinate where the image is painted
			int topLeftX = width - logoW;
			int topLeftY = height - logoH;
			
			// paints the image watermark
			g2d.drawImage(watermarkImage, topLeftX, topLeftY, logoW,
					logoH, null);

			ImageIO.write(sourceImage, "png", destImageFile);
			g2d.dispose();
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
