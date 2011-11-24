
import org.selenium.androframework.common.AndroFrameworkExecutor;
import org.selenium.androframework.common.BaseClass;
import org.testng.annotations.Test;

public class TestTemplate extends BaseClass{

	@Test
	public void testName(){
		driver=getDriver();
		AndroFrameworkExecutor afe = new AndroFrameworkExecutor();
		afe.androExecutor(driver,"test2.csv");
	}
}
