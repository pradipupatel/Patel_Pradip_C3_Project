import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RestaurantTest {
	static Restaurant restaurant;
	// >>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	// -------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN
	// INTO ANY TROUBLE
	@BeforeAll
	public static void init() {
		LocalTime openingTime = LocalTime.parse("10:30:00");
		LocalTime closingTime = LocalTime.parse("22:00:00");
		restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
		restaurant.addToMenu("Sweet corn soup", 119);
		restaurant.addToMenu("Vegetable lasagne", 269);
	}
	
	@Test
	public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time()
			throws RestaurantNotFoundException {
		// WRITE UNIT TEST CASE HERE
		String resturantName = "Amelie's cafe";
		RestaurantService restaurantService = mock(RestaurantService.class);
		when(restaurantService.findRestaurantByName(resturantName))
				.thenReturn(new Restaurant(null, null, LocalTime.parse("10:30:00"), LocalTime.parse("23:50:00")));
		assertEquals(true, restaurantService.findRestaurantByName(resturantName).isRestaurantOpen());
	}
	@Test
	public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() throws RestaurantNotFoundException {
		String resturantName = "Amelie's cafe";
		RestaurantService restaurantService = mock(RestaurantService.class);
		when(restaurantService.findRestaurantByName(resturantName))
				.thenReturn(new Restaurant(null, null, LocalTime.parse("10:30:00"), LocalTime.parse("20:00:00")));
		assertEquals(false, restaurantService.findRestaurantByName(resturantName).isRestaurantOpen());
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	@Test
	public void adding_item_to_menu_should_increase_menu_size_by_1() {
		int initialMenuSize = restaurant.getMenu().size();
		restaurant.addToMenu("Sizzling brownie", 319);
		assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
	}

	@Test
	public void removing_item_from_menu_should_decrease_menu_size_by_1() throws ItemNotFoundException {
		int initialMenuSize = restaurant.getMenu().size();
		restaurant.removeFromMenu("Vegetable lasagne");
		assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
	}

	@Test
	public void removing_item_that_does_not_exist_should_throw_exception() {
		assertThrows(ItemNotFoundException.class, () -> restaurant.removeFromMenu("French fries"));
	}
	// <<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<< ITEM TOTAL >>>>>>>>>>>>>>>
		@Test
		public void calculate_total_item_price_for_the_more_then_1_item_list() throws NullItemListFoundException {
			List<String> itemList = List.of("Sweet corn soup", "Vegetable lasagne");
			assertEquals(388,restaurant.calculateTotalSelectedItemPrice(itemList));
		}
		
		@Test
		public void calculate_total_item_price_for_the_0_item_list() throws NullItemListFoundException {
			List<String> itemList = List.of();
			assertEquals(0,restaurant.calculateTotalSelectedItemPrice(itemList));
		}
		
		@Test
		public void calculate_total_item_price_for_the_null_item_list() {
			List<String> itemList = null;
			assertThrows(NullItemListFoundException.class, () -> restaurant.calculateTotalSelectedItemPrice(itemList));
		}
		// <<<<<<<<<<<<< ITEM TOTAL >>>>>>>>>>>>>>>
}