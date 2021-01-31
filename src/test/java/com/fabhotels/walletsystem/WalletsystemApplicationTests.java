package com.fabhotels.walletsystem;

import com.fabhotels.walletsystem.models.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WalletsystemApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {
		System.out.println(new ObjectMapper().writeValueAsString(new User()));
//		"String.".matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
	}

}
