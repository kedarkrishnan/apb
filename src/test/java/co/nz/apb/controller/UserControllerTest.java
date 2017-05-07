package co.nz.apb.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import co.nz.apb.controllers.UserController;
import co.nz.apb.domain.User;
import co.nz.apb.services.UserService;
import lombok.Data;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@Data
public class UserControllerTest {

	@MockBean
	UserService userService;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void userDetailIsEmpty_When_deviceIdIsNotFound() throws Exception{
		given(this.userService.getUserFromDeviceId("AAABBBCCC")).willReturn(null);
		this.mvc.perform(get("/api/user/AAABBBCCC"))
		.andExpect(status().isOk());
		verify(this.userService).getUserFromDeviceId("AAABBBCCC");		
	}
	
	@Test
	public void userDetailIsCorrect_When_deviceIdIsFound() throws Exception{
		User user = new User();	
		given(this.userService.getUserFromDeviceId("ARD1224DDSRRR")).willReturn(user);
		this.mvc.perform(get("/api/user/ARD1224DDSRRR"))
		.andExpect(status().isOk());
		verify(this.userService).getUserFromDeviceId("ARD1224DDSRRR");		
	}
	
}
