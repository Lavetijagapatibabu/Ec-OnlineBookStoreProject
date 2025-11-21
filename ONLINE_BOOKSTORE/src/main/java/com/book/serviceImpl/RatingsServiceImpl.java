package com.book.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.entity.BookModule;
import com.book.entity.Customer;
import com.book.entity.Ratings;
import com.book.exception.BookIdNotFoundException;
import com.book.exception.CustomerIdNotFoundException;
import com.book.model.RatingsDto;
import com.book.repository.BookModuleRepository;
import com.book.repository.CustomerRepository;
import com.book.repository.RatingsRepository;
import com.book.service.IRatingService;


@Service
public class RatingsServiceImpl implements IRatingService {

	@Autowired private CustomerRepository customerRepository;

	@Autowired private BookModuleRepository bookModuleRepository;

	@Autowired private RatingsRepository ratingsRepository;


	@Override
	public RatingsDto giveRatingReviews(RatingsDto ratingsDto) {
		//  Step 1: Check if customer exists in DB using ID
		// If not found, throw custom exception "Customer Id Not Found"
		Customer customer = customerRepository.findById(ratingsDto.getCusmerId())
				.orElseThrow(() -> new CustomerIdNotFoundException("Custmer Id Not found"));


		//  Step 2: Check if book exists in DB using ID
		// If not found, throw custom exception "Book Id not Found"
		BookModule booksModule = bookModuleRepository.findById(ratingsDto.getBookId())
				.orElseThrow(() -> new BookIdNotFoundException("Book Id not Found"));

		Ratings rrr = new Ratings();
		rrr.setBookModule(booksModule);
		rrr.setCustomer(customer);
		rrr.setRate(ratingsDto.getRate());
		rrr.setReviewText(ratingsDto.getReviewText());
		ratingsRepository.save(rrr);

		BeanUtils.copyProperties(rrr, ratingsDto);

		return ratingsDto;
	}

	@Override
	public List<RatingsDto> getAllRatings() {

		List<Ratings> listRatings = ratingsRepository.findAll();
		List<RatingsDto> listRatingsDtos = listRatings.stream().
				map(list->{
					RatingsDto ratingsDto = new RatingsDto();
					BeanUtils.copyProperties(list, ratingsDto);
					return ratingsDto;
				}).collect(Collectors.toList());

		return listRatingsDtos;
	}





}
