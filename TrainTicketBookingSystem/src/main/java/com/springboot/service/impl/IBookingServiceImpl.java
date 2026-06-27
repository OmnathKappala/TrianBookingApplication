package com.springboot.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.dto.request.BookingRequest;
import com.springboot.dto.response.BookingResponse;
import com.springboot.entity.Booking;
import com.springboot.entity.Train;
import com.springboot.entity.User;
import com.springboot.enums.BookingStatus;
import com.springboot.exception.BookingNotFound;
import com.springboot.exception.SeatsAvabliable;
import com.springboot.exception.TrainAlreadyExist;
import com.springboot.exception.UserNotFound;
import com.springboot.repository.IBookingRepo;
import com.springboot.repository.ITrainRepo;
import com.springboot.repository.UserRepository;
import com.springboot.service.EmailService;
import com.springboot.service.IBookingService;
import com.springboot.service.JwtService;
@Service
public class IBookingServiceImpl implements IBookingService {
	
	@Autowired
	private IBookingRepo repo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ITrainRepo TrainRepo;
	
	@Autowired
	private EmailService service;
	
	@Autowired
	private JwtService jwtService;

	@Override
	public BookingResponse cancelTrainTicket(Long bookingSeatId) {
		Booking updateBooking = repo.findById(bookingSeatId).orElseThrow(()-> new BookingNotFound("No seat booked"));
		if(updateBooking.getStatus().equals(BookingStatus.CANCELLED)) {
			throw new BookingNotFound("Booking is already cancelled");
		}
		updateBooking.setStatus(BookingStatus.CANCELLED);
		Train train = updateBooking.getTrain();
		train.setAvailableSeats(train.getAvailableSeats()+1);
		
		Booking booking = repo.save(updateBooking);
		
		BookingResponse br= new BookingResponse();
		br.setId( booking.getId());
		br.setTrainName( booking.getTrain().getTrainName());
		br.setDestination( booking.getTrain().getDestination());
		br.setSource( booking.getTrain().getSource());
		br.setSeatNumber( booking.getSeatNumber());
		br.setFare( booking.getTotalFare());
		br.setUserName( booking.getUser().getName());
		br.setStatus( booking.getStatus());
		br.setBookingDateTime(LocalDateTime.now());
		
		
		 return  br ;
		
	    
	}


	@Override
	public List<BookingResponse> getAllBookingsByUserId(Long Id) {
		 User user = userRepo.findById(Id).orElseThrow(()-> new UserNotFound("No user with this id") );
		 
		 List<Booking> getAll = repo.findByUserId(user.getId());
		 
		 if(getAll.isEmpty()) {
			 throw new BookingNotFound("No bookings found");
		 }
		 
		 List<BookingResponse>br=new ArrayList<>();
		 for(Booking book:getAll) {
			 BookingResponse bookResponse= new BookingResponse();
			 bookResponse.setUserName(book.getUser().getName());
			 bookResponse.setId(book.getId());
			 bookResponse.setTrainName(book.getTrain().getTrainName());
			 bookResponse.setSource(book.getTrain().getSource());
			 bookResponse.setDestination(book.getTrain().getDestination());
			 bookResponse.setFare(book.getTotalFare());
			 bookResponse.setSeatNumber(book.getSeatNumber());
			 bookResponse.setStatus(book.getStatus());
			 bookResponse.setBookingDateTime(LocalDateTime.now());
			 br.add(bookResponse);
			 
			 
		 }
		return br;
	}


	@Override
	public BookingResponse bookTrainSeat(String token, BookingRequest bookingRequest) {
		 String email = jwtService.extractUsername(token);

		    // find user by email instead of userId
		    User user = userRepo.findByEmail(email)
		            .orElseThrow(() -> new UserNotFound("User not found"));

		    Train train = TrainRepo.findById(bookingRequest.getTrainId())
		            .orElseThrow(() -> new TrainAlreadyExist("Train not found"));

			   if(train.getTotalSeats()<=0) {
				   throw new SeatsAvabliable("No seats Avaliable to book");
				   
			   }
			   Booking book = new Booking();
			   
			   book.setUser(user);
			   book.setTrain(train);
			   book.setStatus(BookingStatus.CONFIRMED);
			   book.setTotalFare(train.getFare());
			   book.setSeatNumber(bookingRequest.getSeatNumber());
			   book.setTotalFare(train.getFare());
			   book.setBookingDate(LocalDate.now());
			   
			   train.setAvailableSeats(train.getAvailableSeats() -1);
			   TrainRepo.save(train);
			 
			   Booking savedBook = repo.save(book);
			   
			  
			   BookingResponse bookingResponse= new BookingResponse();
			   
			   bookingResponse.setBookingDateTime(LocalDateTime.now());
			   bookingResponse.setId(savedBook.getId());
			   bookingResponse.setTrainName(savedBook.getTrain().getTrainName());
			   bookingResponse.setUserName(savedBook.getUser().getName());
			   bookingResponse.setStatus(savedBook.getStatus());
			   bookingResponse.setSource(savedBook.getTrain().getSource());
				bookingResponse.setDestination(savedBook.getTrain().getDestination());
				bookingResponse.setFare(savedBook.getTotalFare());
				bookingResponse.setSeatNumber(savedBook.getSeatNumber());
				
				 
				 
				return  bookingResponse;
	}

}
