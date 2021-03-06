package test.java.server;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import main.java.data.management.DBManager;
import main.java.util.LogPrinter;

/* @author Shay Segal
 * @since 14-12-2016
 */
public class serverConnection {
	@Test
	public void test() {
		DBManager.initialize();
		Assert.assertEquals("ParkingNav", Parse.getApplicationId());
		Assert.assertEquals("2139f-231ff2-738ff", Parse.getRestAPIKey());
		final ParseObject testConnectionObject = new ParseObject("testConnectionObject");
		testConnectionObject.put("integerCheck", 9999);
		testConnectionObject.put("stringCheck", "John Dow");
		testConnectionObject.put("boolCheck", false);
		try {
			testConnectionObject.save();
		} catch (final ParseException e) {
			Assert.assertEquals(true, false);
		}
		try {
			Thread.sleep(100);
		} catch (final InterruptedException e1) {
			LogPrinter.createLogFile(e1);
		}
		final String id = testConnectionObject.getObjectId();
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("testConnectionObject");
		ParseObject ret;
		try {
			ret = query.get(id);
		} catch (final ParseException e) {
			Assert.assertEquals(true, false);
			return;
		}
		Assert.assertEquals(9999, ret.getInt("integerCheck"));
		Assert.assertEquals("John Dow", ret.getString("stringCheck"));
		Assert.assertEquals(false, ret.getBoolean("boolCheck"));

		try {
			testConnectionObject.delete();
			ret = query.get(id);
			Assert.assertEquals(ret, null);
		} catch (final ParseException e) {
			Assert.assertEquals(true, false);
		}

	}
}