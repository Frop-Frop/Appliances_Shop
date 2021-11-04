package com.foxminded.appliancesshop.bootstrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.foxminded.appliancesshop.domain.Address;
import com.foxminded.appliancesshop.domain.Cart;
import com.foxminded.appliancesshop.domain.Category;
import com.foxminded.appliancesshop.domain.Customer;
import com.foxminded.appliancesshop.domain.Product;
import com.foxminded.appliancesshop.repositories.AddressRepository;
import com.foxminded.appliancesshop.repositories.CartRepository;
import com.foxminded.appliancesshop.repositories.CategoryRepository;
import com.foxminded.appliancesshop.repositories.CustomerRepository;
import com.foxminded.appliancesshop.repositories.ItemRepository;
import com.foxminded.appliancesshop.repositories.ProductRepository;

@Component
public class Bootstrap implements CommandLineRunner {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void run(String... args) throws Exception {
		loadAdsresses();
		System.out.println("Addresses loaded");
		loadCustomers();
		System.out.println("Customers loaded");
		loadCategories();
		System.out.println("Categories loaded");
		loadProducts();
		System.out.println("Products loaded");
		List<Category> subCategories = categoryRepository.findSubCategories(1L);
		subCategories.stream().forEach(System.out::println);
	}

	private void loadAdsresses() {
		List<String> countries = Arrays.asList("China", "Liberia", "China", "Spain", "Indonesia", "Poland", "Brazil",
				"China", "France", "China");
		List<String> regions = Arrays.asList("Guangdong", "Lofa", "Jiangmen", "Baleares", "Manjung", "Bilgoraj",
				"Ceara", "Fengnan", "Pays de la Loire", "Luzhu");
		List<String> cities = Arrays.asList("Yixi", "Voinjama", "Hongguang", "Palma De Mallorca", "Pongkor", "Goraj",
				"Quixeramobim", "Qianying", "Saumur", "Shanjiao");
		List<String> streets = Arrays.asList("Hoepker", "Transport", "Mariners Cove", "Bobwhite", "Old Gate", "Shasta",
				"Jay", "Laurel", "American Ash", "Stone Corner");
		List<String> houseNumbers = Arrays.asList("991", "393", "10/4", "3 A", "337", "83153", "230", "70062/2",
				"82063", "9234");
		Address address;
		for (int i = 0; i < 10; i++) {
			address = new Address();
			address.setCountry(countries.get(i));
			address.setRegion(regions.get(i));
			address.setCity(cities.get(i));
			address.setStreet(cities.get(i));
			address.setHouseNumber(houseNumbers.get(i));
			addressRepository.save(address);
		}
	}

	private void loadCustomers() {
		List<String> firstNames = Arrays.asList("Marylin", "Ivy", "Duke", "Derrek", "Marcus", "Lynna", "Sandor",
				"Dimitry", "Ernest", "Elsworth");
		List<String> lastNames = Arrays.asList("Bett", "Spieck", "Mabee", "Schuck", "Roderick", "Briatt", "Skipping",
				"Wadlow", "Ragat", "Elsworth Greger");
		List<String> emails = Arrays.asList("bfarfalameev0@gizmodo.com", "tbernardot1@wikipedia.org",
				"tscandrett2@youtube.com", "cizhaky3@wunderground.com", "dchipps4@dropbox.com", "ljayne5@webmd.com",
				"hcapron6@sphinn.com", "epatzelt7@hostgator.com", "xsummons8@nydailynews.com", "pstirtle9@weebly.com");

		Customer customer;
		for (int i = 0; i < 10; i++) {
			customer = new Customer();
			customer.setFirstName(firstNames.get(i));
			customer.setLastName(lastNames.get(i));
			customer.setPassword("password");
			customer.setEmail(emails.get(i));
			Address address = addressRepository.findById(Long.valueOf(i + 1)).get();
			customer.setAddress(address);
			Cart cart = cartRepository.save(new Cart());
			customer.setCart(cart);
			Customer savedCustomer = customerRepository.save(customer);

			address.setCustomer(savedCustomer);
			addressRepository.save(address);

			cart.setCustomer(savedCustomer);
			cartRepository.save(cart);
		}
	}

	private void loadCategories() {
		List<String> superCategories = Arrays.asList("Kitchen Appliances", "Washers & Dryers", "Floor Care",
				"Heating, Cooling & Air Quality");
		List<String> kitchenAppliances = Arrays.asList("Refrigerators", "Dishwashers", "Ovens", "Microwaves");
		List<String> washersDryers = Arrays.asList("Front-Loading Washers", "Top-Loading Washers", "Electric Dryers");
		List<String> floorCare = Arrays.asList("Upright Vacuums", "Robot Vacuums", "Handheld & Stick Vacuums");
		List<String> airQuality = Arrays.asList("Air Conditioners", "Air Purifiers");

		List<List<String>> transfer = Arrays.asList(kitchenAppliances, washersDryers, floorCare, airQuality);
		List<List<String>> subCategories = new ArrayList<>();
		subCategories.addAll(transfer);
		superCategories.stream().forEach(category -> {
			Category superCategory = categoryRepository.save(new Category(category));
			List<String> localSubCategory = subCategories.remove(0);
			localSubCategory.stream().forEach(lsc -> {
				categoryRepository.save(new Category(lsc, superCategory));
			});
		});
		List<String> ovens = Arrays.asList("Cooktops", "Wall Ovens", "Range Hoods & Ventilation");
		Category ovenCategory = categoryRepository.findByName("Ovens").get();
		ovens.stream().forEach(category -> {
			categoryRepository.save(new Category(category, ovenCategory));
		});
	}

	private void loadProducts() {
		loadFriges();
		loadDishwashers();
		loadOvens();
		loadMicrowaves();
		loadFrontloadingWashers();
		loadTopLoadingWashers();
		loadElectricDryers();
		loadUprightVacuums();
		loadRobotVacuums();
		loadStickVacuums();
		loadAirConditioners();
		loadAirPurifiers();
		loadCooktops();
		loadWallOvens();
		loadVentilation();
	}

	private void loadFriges() {
		Category category = categoryRepository.findByName("Refrigerators").get();
		List<String> names = Arrays.asList("LG - 29 LRFWS2906S", "Samsung - 28 RF28T5021SR/AA", "LG - 20.2 LTCS20020S",
				"Insignia - 18.1  NS-RTM18WH8Q", "Whirlpool - 26.8 ", "LG - Door-in-Door 26.0");

		List<String> descriptions = Arrays.asList(
				"LG - 29 cu. Ft. 3 Door French Door with Ice Maker, and External Water Dispenser - Printproof Stainless Steel",
				"Samsung - 28 cu. ft. Large Capacity 3-Door French Door Refrigerator with AutoFill Water Pitcher - Fingerprint Resistant Stainless Steel",
				"LG - 20.2 Cu. Ft. Top-Freezer Refrigerator - Stainless Steel",
				"Insignia™ - 18.1 Cu. Ft. Top-Freezer Refrigerator - White",
				"Whirlpool - 21.4 Cu. Ft. Side-by-Side Refrigerator Fingerprint Resistant - Fingerprint Resistant Stainless Steel",
				"LG - Door-in-Door 26.0 Cu. Ft. Side-by-Side Refrigerator with Thru-the-Door Ice and Water - PrintProof Black Stainless Steel");
		generateProducts(category, names, descriptions, 100000, 300000);
	}

	private void loadDishwashers() {
		Category category = categoryRepository.findByName("Dishwashers").get();
		List<String> names = Arrays.asList("KitchenAid - KDTM404KPS", "Samsung - DW80R7060US",
				"KitchenAid - KDTM604KPS", "Samsung - 24 DW80R7061US");
		List<String> descriptions = Arrays.asList(
				"KitchenAid - 24\" Top Control Built-In Dishwasher with Stainless Steel Tub, FreeFlex™, 3rd Rack, 44dBA - Stainless Steel With PrintShield Finish",
				"Samsung - StormWash™ 24\" Top Control Built-In Dishwasher with AutoRelease Dry, 3rd Rack, 42 dBA - Stainless Steel",
				"KitchenAid - Top Control Built-In Dishwasher with Stainless Steel Tub, FreeFlex Third Rack, 44dBA - Stainless Steel With PrintShield Finish",
				"Samsung - StormWash™ 24\" Top Control Built-In Dishwasher with AutoRelease Dry, 3rd Rack, 42 dBA - Stainless Steel");
		generateProducts(category, names, descriptions, 70000, 100000);
	}

	private void loadOvens() {
		Category category = categoryRepository.findByName("Ovens").get();
		List<String> names = Arrays.asList("Amana - 4.8 AER6303MFS", "Samsung - 6.0 NX60A6511SS/AA");
		List<String> descriptions = Arrays.asList("Amana - 4.8 Cu. Ft. Freestanding Electric Range - Stainless Steel",
				"Samsung - 6.0 cu. ft. Freestanding Gas Range with WiFi, No-Preheat Air Fry & Convection - Fingerprint Resistant Stainless Steel");
		generateProducts(category, names, descriptions, 50000, 110000);
	}

	private void loadMicrowaves() {
		Category category = categoryRepository.findByName("Microwaves").get();
		List<String> names = Arrays.asList("Frigidaire - 1.8 FFMV1846VS", "Samsung - 2.1 ME21K7010DG",
				"Samsung - 1.6 ME16A4021AS/AA");
		List<String> descriptions = Arrays.asList("Frigidaire - 1.8 Cu. Ft. Over-the-Range Microwave - Stainless Steel",
				"Samsung - 2.1 Cu. Ft. Over-the-Range Microwave with Sensor Cook - Fingerprint Resistant Black Stainless Steel",
				"Samsung - 1.6 cu. ft. Over-the-Range Microwave with Auto Cook - Stainless Steel");
		generateProducts(category, names, descriptions, 20000, 50000);
	}

	private void loadFrontloadingWashers() {
		Category category = categoryRepository.findByName("Front-Loading Washers").get();
		List<String> names = Arrays.asList("Samsung - 4.5 WF45T6000AW", "Samsung - 5.0 WF50A8500AV/A5",
				"LG - 4.5 WM4000HWA");
		List<String> descriptions = Arrays.asList(
				"Samsung - 4.5 cu. ft. 5-Cycle Front Load Washer with Vibration Reduction Technology+ - White",
				"New!Samsung - 5.0 cu. ft. Extra-Large Capacity Smart Front Load Washer with Super Speed Wash - Brushed Black",
				"LG - 4.5 Cu. Ft. High Efficiency Stackable Smart Front-Load Washer with Steam and Built-In Intelligence - White");
		generateProducts(category, names, descriptions, 70000, 120000);
	}

	private void loadTopLoadingWashers() {
		Category category = categoryRepository.findByName("Top-Loading Washers").get();
		List<String> names = Arrays.asList("Samsung - 5.0 WA50R5200AW/US", "Samsung - 5.2  WA52A5500AC/US");
		List<String> descriptions = Arrays.asList(
				"Samsung - 5.0 Cu. Ft. High Efficiency Top Load Washer with Active WaterJet - White",
				"Samsung - 5.2 cu. ft. Large Capacity Smart Top Load Washer with Super Speed Wash - Champagne");
		generateProducts(category, names, descriptions, 70000, 120000);
	}

	private void loadElectricDryers() {
		Category category = categoryRepository.findByName("Electric Dryers").get();
		List<String> names = Arrays.asList("GE - 7.2  GTD42EASJWW", "Samsung - 7.4 DVE52A5500V/A3",
				"Samsung - 7.5 DVE45A6400W/A3");
		List<String> descriptions = Arrays.asList("GE - 7.2 Cu. Ft. Electric Dryer - White",
				"New!Samsung - 7.4 cu. ft. Smart Electric Dryer with Steam Sanitize+ - Brushed Black",
				"New!Samsung - 7.5 cu. ft. Smart Dial Electric Dryer with Super Speed Dry - White");
		generateProducts(category, names, descriptions, 60000, 110000);
	}

	private void loadUprightVacuums() {
		Category category = categoryRepository.findByName("Upright Vacuums").get();
		List<String> names = Arrays.asList("BISSELL - 3059", "Hoover - UH71250", "Dyson - 227635-01/334176-01");
		List<String> descriptions = Arrays.asList(
				"BISSELL - CleanView Allergen Lift-Off Pet Vacuum - Black/ Electric Green",
				"Hoover - WindTunnel 2 Whole House Rewind Upright Vacuum - Blue",
				"Dyson - Ball Animal 2 Upright Vacuum - Iron/Purple");
		generateProducts(category, names, descriptions, 11000, 50000);
	}

	private void loadRobotVacuums() {
		Category category = categoryRepository.findByName("Robot Vacuums").get();
		List<String> names = Arrays.asList("iRobot - S955020", "Shark -  RV912S", "iRobot - j755020");
		List<String> descriptions = Arrays.asList(
				"iRobot - iRobot® Roomba® s9+ (9550) Wi-Fi® Connected Self-Emptying Robot Vacuum - Java Black",
				"Shark - EZ Robot Vacuum RV912S with Self-Empty Base, Bagless, Wi-Fi - Dark Grey",
				"iRobot - Robot Roomba j7+ (7550) Self-Emptying Robot Vacuum – Identifies and avoids obstacles like pet waste & cords - Graphite");
		generateProducts(category, names, descriptions, 30000, 120000);
	}

	private void loadStickVacuums() {
		Category category = categoryRepository.findByName("Handheld & Stick Vacuums").get();
		List<String> names = Arrays.asList("Dyson - 371024-01", "Dyson - 368341-01", "BISSELL - 2306");
		List<String> descriptions = Arrays.asList("Dyson - Outsize Total Clean cordless vacuum - Nickel/Red",
				"Dyson - Outsize Cordless Vacuum Cleaner - Nickel",
				"BISSELL - CrossWave Pet Pro All-in-One Multi-Surface Cleaner - Grapevine Purple and Sparkle Silver");
		generateProducts(category, names, descriptions, 30000, 120000);
	}

	private void loadAirConditioners() {
		Category category = categoryRepository.findByName("Air Conditioners").get();
		List<String> names = Arrays.asList("Amana - 250 AMAP061BW", "Keystone - 1500  KSTHW25A", "LG - 501 LP1419IVSM");
		List<String> descriptions = Arrays.asList("Amana - 250 Sq. Ft. Window Air Conditioner - White",
				"Keystone - 1500 Sq. Ft. 25,000 BTU Window Air Conditioner and 16,000 BTU Heater - White",
				"LG - 501 Sq. Ft. Smart Portable Air Conditioner - White");
		generateProducts(category, names, descriptions, 20000, 70000);
	}

	private void loadAirPurifiers() {
		Category category = categoryRepository.findByName("Air Purifiers").get();
		List<String> names = Arrays.asList("Dyson - 308247-01", "Shark - HE602", "Levoit - HEAPAPLVNUS0021");
		List<String> descriptions = Arrays.asList("Dyson Pure Cool™ Purifying Fan TP01, Tower - White/Silver",
				"Shark - Air Purifier 6 With Anti-Allergen HEPA Filter Advanced Odor And Fumes Lock, 1,200 sq. ft., Smart Sensing - Charcoal Gray",
				"Levoit - Aerone 129 Sq. Ft True HEPA Air Purifier with Replacement Filter - White");
		generateProducts(category, names, descriptions, 7000, 70000);
	}

	private void loadCooktops() {
		Category category = categoryRepository.findByName("Cooktops").get();
		List<String> names = Arrays.asList("Bosch - 800  NGM8056UC", "GE - 30  JP3030DJBB",
				"KitchenAid - 36  KCGS556ESS");
		List<String> descriptions = Arrays.asList(
				"Bosch - 800 Series 30\" Built-In Gas Cooktop with 5 burners - Stainless Steel",
				"GE - 30\" Built-In Electric Cooktop - Black on Black",
				"KitchenAid - 36\" Built-In Gas Cooktop - Stainless Steel");
		generateProducts(category, names, descriptions, 80000, 210000);
	}

	private void loadWallOvens() {
		Category category = categoryRepository.findByName("Wall Ovens").get();
		List<String> names = Arrays.asList("LG - 30  LWC3063ST", "Samsung - 30 NV51K6650DG",
				"Whirlpool - 27 WOS31ES7JS");
		List<String> descriptions = Arrays.asList(
				"LG - 30\" Combination Double Electric Convection Wall Oven with Built-In Microwave, Infrared Heating, and Wifi - Stainless Steel",
				"Samsung - 30\" Double Wall Oven with Steam Cook and WiFi - Black Stainless Steel",
				"Whirlpool - 27\" Built-In Single Electric Wall Oven - Stainless Steel");
		generateProducts(category, names, descriptions, 150000, 360000);
	}

	private void loadVentilation() {
		Category category = categoryRepository.findByName("Range Hoods & Ventilation").get();
		List<String> names = Arrays.asList("Samsung - 30  NK30K7000WG/A2", "Samsung - 36 NK36K7000WS/A2",
				"LG - 36 HCED3615S");
		List<String> descriptions = Arrays.asList(
				"Samsung - 30\" Convertible Range Hood with WiFi - Fingerprint Resistant Black Stainless Steel",
				"Samsung - 36\" Range Hood - Stainless Steel",
				"LG - 36\" Convertible Range Hood with WiFi - Stainless Steel");
		generateProducts(category, names, descriptions, 150000, 360000);
	}

	private void generateProducts(Category category, List<String> names, List<String> descriptions, int minPrice,
			int maxPrice) {
		List<String> localDescriptions = new ArrayList<>();
		localDescriptions.addAll(descriptions);
		names.stream().forEach(o -> {
			Product product = new Product(o, category);
			product.setDescription(localDescriptions.remove(0));
			product.setBrand(o.substring(0, o.indexOf(' ')));
			product.setPrice((int) (Math.random() * (maxPrice - minPrice)) + minPrice);
			Product savedProduct = productRepository.save(product);
			category.addProductToCategory(savedProduct);
		});
		categoryRepository.save(category);
	}
}
