package decryption;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DecryptImage {
	public static void decryptMessage(String imgLoc) {
		BufferedImage img = loadImage(imgLoc);
		System.out.println("Decrypting Message in image");
		extractMessage(img);
		System.out.println("Done");
	}

	private static BufferedImage loadImage(String imgLoc) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(imgLoc));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return img;
	}

	private static void extractMessage(BufferedImage img) {
		int x = 0, y = 1;
		int messageSize = extractMessageSize(img);
		String message = "";
		
		for (int i = 0; i < messageSize; i++) {
			String intChar = "";
			for (int j = 0; j < 3; j++) {
				intChar += String.valueOf(extractDigit(img.getRGB(x, y)));
				
				x++;
				if (x == img.getWidth()) {
					x = 0;
					y++;
				}
				
				if (y == img.getHeight()) {
					break;
				}
			}
			message += (char)((int)Integer.valueOf(intChar));
		}
		
		System.out.println("Hidden Message: " + message);
	}

	private static int extractMessageSize(BufferedImage img) {
		String messageSize = "";

		for (int i = 0; i < 32; i++) {
			messageSize += String.valueOf(extractDigit(img.getRGB(i, 0)));
		}
		
		return Integer.parseInt(messageSize, 2);
	}

	private static int extractDigit(int rgb) {
		return Math.abs(rgb % 10);
	}
}
