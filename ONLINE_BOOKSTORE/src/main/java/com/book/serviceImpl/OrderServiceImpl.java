package com.book.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.entity.BookModule;
import com.book.entity.Orders;
import com.book.entity.UserRegister;
import com.book.model.OrderModuleDto;
import com.book.repository.OrderModuleRepository;
import com.book.repository.UserRegisterRepository;
import com.book.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private UserRegisterRepository userRegisterRepo;

	@Autowired
	private OrderModuleRepository orderModuleRepo;

	@Override
	public String saveOrders(OrderModuleDto orderModuleDto) {

		// 1. check if the request body or titles are empty
		if (orderModuleDto == null || orderModuleDto.getTitle() == null || orderModuleDto.getTitle().isEmpty()) {
			return "No books selected. Please select at least one book to proceed.";
		}

		// 2. Extract customer ID and selected book titles
		Long custmerId = orderModuleDto.getCustomerId();
		List<String> seletedBooks = orderModuleDto.getTitle();

		// 3. Check whether the user is a Prime member or not
		Boolean ifPrimeUser = checkPrimeUser(custmerId);

		// 4. Apply rules for Non-Prime users
		if (!ifPrimeUser) {

			// Non-prime users cannot order more than one book at a time
			if (seletedBooks.size() > 1) {
				return "Non-prime users can select only one book.";
			}

			// Non-prime users can place only one order per week
			List<Orders> anyLastweekPlaced = orderModuleRepo.findAnyLastweekPlaced(custmerId);

			if (!anyLastweekPlaced.isEmpty()) {
				return "Non-prime users can place only one order per week.";
			}
		}

		// 5. Iterate through each selected book and validate availability
		for (String title : seletedBooks) {

			// Find book details by title
			BookModule bookName = orderModuleRepo.findByBookName(title);

			// If the book doesn't exist, return a message
			if (bookName == null) {
				return "No book found: " + title;
			}

			// Create a new order entry for the customer
			Orders order = new Orders();
			order.setBookId(bookName.getId());
			order.setCustomerId(custmerId);
			order.setStatus(false); // Default status = pending/unprocessed

			// Save order into database
			orderModuleRepo.save(order);
		}

		// 6. Return final success message
		return "Order Placed successfully. Thank you.!";
	}

	// Helper method to check whether the user is a Prime user
	private Boolean checkPrimeUser(Long custmerId) {

		// Fetch user by ID
		Optional<UserRegister> user = userRegisterRepo.findById(custmerId);

		// Return Prime status if available, else default to false
		return user.map(UserRegister::getPrime).orElse(false);
	}

}
