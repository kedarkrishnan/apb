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

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@MockBean
	UserService userService;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void userDetailIsEmpty_When_deviceIdIsNotFound() throws Exception{
		given(this.userService.getUserByUserId("alanRu")).willReturn(null);
		this.mvc.perform(get("/api/user/alanRu"))
		.andExpect(status().isOk());
		verify(this.userService).getUserByUserId("alanRu");		
	}
	
	@Test
	public void userDetailIsCorrect_When_deviceIdIsFound() throws Exception{
		User user = new User();	
		given(this.userService.getUserByUserId("samPaul")).willReturn(user);
		this.mvc.perform(get("/api/user/samPaul"))
		.andExpect(status().isOk());
		verify(this.userService).getUserByUserId("samPaul");		
	}
	
}
