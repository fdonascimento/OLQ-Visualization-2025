package optimalLocation.clientProviderTxt.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import optimalLocation.clientProviderTxt.utils.CalculateMd5Exception;
import optimalLocation.clientProviderTxt.utils.Md5Calculator;

public class Md5CalculatorTest {

	@Test
	public void getMd5_inexistentFile_throwsException() {
		//given
		Path inexistentPath = Paths.get("inexistentPath.txt");
		
		//when
		assertThatThrownBy(() -> {
			Md5Calculator.getMd5(inexistentPath);
			
			//then
		}).isInstanceOf(CalculateMd5Exception.class);
	}
	
	@Test
	public void getMd5_existentFile_nonEmptyString() throws CalculateMd5Exception{
		//given
		Path inexistentPath = Paths.get(getClass().getResource("inputMd5.txt").getPath());
		
		//when
		String md5 = Md5Calculator.getMd5(inexistentPath);
		
		//then
		assertThat(md5).isNotEmpty();
	}
}
