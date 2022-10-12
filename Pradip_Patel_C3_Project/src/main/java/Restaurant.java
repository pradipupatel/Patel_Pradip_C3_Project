import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
	private String name;
	private String location;
	public LocalTime openingTime;
	public LocalTime closingTime;
	private List<Item> menu = new ArrayList<Item>();

	public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
		this.name = name;
		this.location = location;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
	}

	public boolean isRestaurantOpen() {
		if (getCurrentTime().compareTo(openingTime) >= 0 && getCurrentTime().compareTo(closingTime) <= 0) {
			return true;
		}
		return false;
	}

	public LocalTime getCurrentTime() {
		return LocalTime.now();
	}

	public List<Item> getMenu() {
		return menu;
	}

	private Item findItemByName(String itemName) {
		for (Item item : menu) {
			if (item.getName().equals(itemName))
				return item;
		}
		return null;
	}

	public void addToMenu(String name, int price) {
		Item newItem = new Item(name, price);
		menu.add(newItem);
	}

	public void removeFromMenu(String itemName) throws ItemNotFoundException {

		Item itemToBeRemoved = findItemByName(itemName);
		if (itemToBeRemoved == null)
			throw new ItemNotFoundException(itemName);

		menu.remove(itemToBeRemoved);
	}

	public void displayDetails() {
		System.out.println("Restaurant:" + name + "\n" + "Location:" + location + "\n" + "Opening time:" + openingTime
				+ "\n" + "Closing time:" + closingTime + "\n" + "Menu:" + "\n" + getMenu());

	}

	public String getName() {
		return name;
	}

	public int calculateTotalSelectedItemPrice(List<String> itemList) throws NullItemListFoundException {
		if(itemList == null)
			throw new NullItemListFoundException("We can not calulate total price of null item List");
		if(itemList.size()==0)
			return 0;
		int totalItemPrice = 0;
		Item item = null;
		for (String itemName : itemList) {
			item = findItemByName(itemName);
			totalItemPrice = totalItemPrice + item.getPrice();
		}
		return totalItemPrice;
	}

	
}
