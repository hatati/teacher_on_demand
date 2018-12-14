package util;

import play.mvc.Http;
import views.formdata.ReferenceFormData;

import java.io.*;
import java.util.Base64;
import java.util.List;

public class Encoding {

	public static String loadAndEncodeImage(String path) {
		try {
			File file = new File(path);
			InputStream fileInputStream = new FileInputStream(file);
			byte[] imageBytes = new byte[(int) file.length()];
			fileInputStream.read(imageBytes, 0, imageBytes.length);
			fileInputStream.close();
			String imageString = Base64.getEncoder().encodeToString(imageBytes);
			return imageString;
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		return null;
	}

	public static String encodeFile(File file) {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] imageBytes = new byte[(int) file.length()];
			fileInputStream.read(imageBytes, 0, imageBytes.length);
			System.out.println("imageBytes: " + imageBytes.length);
			String imageString = Base64.getEncoder().encodeToString(imageBytes);

			return imageString;
		} catch(IOException exception) {
			exception.printStackTrace();
		}

		return null;
	}


	public static void unpackEncodeAddImageTo(List<ReferenceFormData> references,
									   Http.MultipartFormData.FilePart<File> imagePart, int index) {
		if (imagePart != null) {
			String jobImageString = Encoding.encodeFile(imagePart.getFile());
			ReferenceFormData reference = references.get(index);
			reference.setImage(jobImageString);
			references.set(index, reference);
		}
	}
}
