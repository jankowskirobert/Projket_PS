package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.projectnine.logic.TCPDriver;

public class TestOne {

	@Test
	public void test() {
		TCPDriver driver = new TCPDriver(6660);
		new Thread(driver).start();
		try {
		    Thread.sleep(90 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}

}
