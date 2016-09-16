package encryption;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class EncryptImage {
	public static void addMessage(String imgLoc, String message) {
		System.out.println("Embedding Message in image");
		embedMessage(imgLoc, message);
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
	
	private static void saveImage(BufferedImage img, String imgLoc) {
		try {
			ImageIO.write(img, "png", new File(imgLoc));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void embedMessage(String imgLoc, String message) {
		int x = 0, y = 1;
		BufferedImage img = loadImage(imgLoc);

		injectMessageSize(img, message.length());
		for (char letter : message.toCharArray()) {
			for (int i = 0; i < 3; i++) {
				injectDigit(img, x, y, Integer.valueOf(Integer.toString((int) letter).substring(i, i + 1)));
				
				x++;
				if (x == img.getWidth()) {
					x = 0;
					y++;
				}
				
				if (y == img.getHeight()) {
					break;
				}
			}
		}
		saveImage(img, imgLoc);
	}

	private static void injectDigit(BufferedImage img, int x, int y, int digit) {
		img.setRGB(x, y, getNewRGB(img.getRGB(x, y), digit));
	}

	private static int getNewRGB(int oldRGB, int digit) {
		int newRGB = oldRGB;
		newRGB /= 10;
		newRGB *= 10;

		if (newRGB < 0) {
			newRGB -= digit;
		} else {
			newRGB += digit;
		}

		return newRGB;
	}

	private static void injectMessageSize(BufferedImage img, int size) {
		String binarySize = String.format("%32s", Integer.toBinaryString(size)).replace(' ', '0');

		for (int i = 0; i < 32; i++) {
			injectDigit(img, i, 0, Integer.valueOf(binarySize.substring(i, i + 1)));
		}
	}
}
