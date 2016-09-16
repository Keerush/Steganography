import java.util.Scanner;

import decryption.DecryptImage;
import encryption.EncryptImage;

public class Main {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
	
		System.out.println("Enter location of files to encrypt message into: ");
		String imgLoc = reader.nextLine();
		System.out.println("Enter message: \n");
		String message = reader.nextLine();
		reader.close();
		
		EncryptImage.addMessage(imgLoc, message);
		DecryptImage.decryptMessage(imgLoc);
	}
}
