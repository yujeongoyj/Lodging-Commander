package com.hotel.lodgingCommander;

import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.repository.CartRepository;
import com.hotel.lodgingCommander.repository.HotelRepository;
import com.hotel.lodgingCommander.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LodgingCommanderApplicationTests {


	@Test
	void contextLoads() {

	}

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testFindAvailableHotels() {
		Optional<User> a = userRepository.findByEmail("a");
		System.out.println("a = " + a);
	}
}
