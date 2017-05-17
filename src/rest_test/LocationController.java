package rest_test;

import org.springframework.web.bind.annotation.*;

import main.java.data.management.DBManager;
import main.java.data.members.Destination;
import main.java.data.members.MapLocation;
import main.java.data.members.ParkingSlot;
import main.java.data.members.ParkingSlotStatus;
import main.java.data.members.StickersColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.springframework.stereotype.Controller;

@Controller
public class LocationController {
	Map<String, MapLocation> hmap;
	ArrayList<ServerParkingSlot> parkingList;

	public LocationController() {
		DBManager.initialize();
	}

	@RequestMapping(value = "/Locations", produces = "application/json")
	@ResponseBody
	public String getLocations() {
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("Destination");
		hmap = new HashMap<String, MapLocation>();
		try {
			final List<ParseObject> result = query.find();
			if (result == null)
				System.out.println("empty");
			for (final ParseObject dest : result) {
				final String destName = dest.getString("name");
				hmap.put(destName, new Destination(destName).getEntrance());
			}
		} catch (final Exception e) {
			System.out.println("exception...");
		}
		return new JSONObject(hmap) + "";
	}

	@RequestMapping(value = "/ParkingSlots", produces = "application/json")
	@ResponseBody
	public ArrayList<ServerParkingSlot> getParkingSlots() {
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		parkingList = new ArrayList<ServerParkingSlot>();
		try {
			final List<ParseObject> result = query.find();
			if (result == null)
				System.out.println("empty");
			for (final ParseObject park : result)
				parkingList.add(new ServerParkingSlot(new ParkingSlot(park)));
		} catch (final Exception e) {
			System.out.println("exception...");
		}
		return parkingList;
	}

	@RequestMapping(value = "/ParkingSlots/{name}", produces = "application/json")
	@ResponseBody
	public ServerParkingSlot getPark(@PathVariable String name) {
		parkingList = new ArrayList<ServerParkingSlot>();
		try {
			if (name == "")
				System.out.println("bad name");
			return new ServerParkingSlot(new ParkingSlot(name));
		} catch (final Exception e) {
			System.out.println("exception...");
		}
		return null;
	}
}