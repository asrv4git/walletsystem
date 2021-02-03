package com.fabhotels.walletsystem;

import com.fabhotels.walletsystem.models.dto.UserProfileDataDto;
import com.fabhotels.walletsystem.models.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
class WalletsystemApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {
		ModelMapper modelMapper = new ModelMapper();
//		modelMapper.getConfiguration().setSkipNullEnabled(true);
//		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		User user = new User();
		user.setUsername("vikash123");
		user.setMobileNumber("7067103640");
		user.setPassword("Rjit@12345");
		user.setFirstName("Vikash");
		user.setLastName("Kumar");

		System.out.println(user.getLastName());

		UserProfileDataDto userProfileDataDto = new UserProfileDataDto();
		userProfileDataDto.setFirstName("Aditya");
		userProfileDataDto.setLastName(null);

		modelMapper.map(userProfileDataDto,user);
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
	}

}
