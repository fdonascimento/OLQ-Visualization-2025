package optimalLocation.clientProviderTxt.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public final class Md5Calculator {

	private Md5Calculator() {
		
	}
	
	public static String getMd5(Path source) throws CalculateMd5Exception {
		try (FileInputStream fis = new FileInputStream(source.toString())) {
			return org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
		} catch (IOException e) {
			throw new CalculateMd5Exception(e);
		}
	}
}
