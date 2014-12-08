import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.agripro.gwt.client.Data;


public class DataTest {
	
	@Test
	public void ConstructorTest() {
        try {
    		// Test constructor of a Data object.
    		Data data = new Data();
    		Assert.assertNotNull(data);          
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	};
	
	@Test
	public void TypeTest() {
        try {
    		// Test type of a Data object.
    		Data data = new Data();
    		Assert.assertNotNull(data);  
    		data.setType("production");
    		Assert.assertEquals("production", data.getType());
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	};

	@Test
	public void MetaTest(){
        try {
    		// Test Meta of a Data object.
    		Data data = new Data();
    		Assert.assertNotNull(data);  
    		data.setMeta("year");
    		Assert.assertEquals("year", data.getMeta());
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	};

	@Test
	public void RequestIDTest(){
        try {
    		// Test RequestID of a Data object.
    		Data data = new Data();
    		Assert.assertNotNull(data);  
    		data.setRequestID(1337);
    		Assert.assertEquals(1337, data.getRequestID());
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	};
	
	@Test
	public void DataTest(){
        try {
    		// Test Data of a Data object.
    		Data data = new Data();
    		Assert.assertNotNull(data);  
    		ArrayList someList = new ArrayList();
    		someList.add("string1");
    		someList.add("string2");
    		data.setData(someList);
    		Assert.assertEquals(someList.get(0), data.getData().get(0));
    		
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	};
}

