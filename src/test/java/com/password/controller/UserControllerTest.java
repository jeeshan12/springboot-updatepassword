package com.password.controller;

import com.password.model.User;
import com.password.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void changePassword() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setOldPassword("Ab1@bcdefgghomnop#@2");
		user.setUserName("Jeeshan");
		when(userService.findByIdAndOldPassword(1L, "Ab1@bcdefgghomnop#@2")).thenReturn(user);
		
		User userStub = new User();
		userStub.setId(1L);
		userStub.setOldPassword("Ab1@bcdefgghomnop#@2");
		userStub.setNewPassword("Ab1@y6j8k9a0lg@lp021");
		userStub.setUserName("Jeeshan");
		when(userService.save(userStub)).thenReturn(userStub);
		
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"Ab1@y6j8k9a0lg@lp021\"" + "}";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/changepassword/1")
				.contentType(MediaType.APPLICATION_JSON)
                		.accept(MediaType.APPLICATION_JSON)
				.content(request)).andReturn();
		System.out.println(result.getResponse());
        assertEquals(result.getResponse().getStatus(), 200);
	}
	
	@Test
	public void changePasswordDigitIsMoreThan50Percentage() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setOldPassword("Ab1@bcdefgghomnop#@2");
		user.setUserName("Jeeshan");
		when(userService.findByIdAndOldPassword(1L, "Ab1@bcdefgghomnop#@2")).thenReturn(user);
		
		User userStub = new User();
		userStub.setId(1L);
		userStub.setOldPassword("Ab1@bcdefgghomnop#@2");
		userStub.setNewPassword("Ab1@y6j8k9a0l7h819021");
		userStub.setUserName("Jeeshan");
		when(userService.save(userStub)).thenReturn(userStub);
		
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"Ab1@y6j8k9a0l7h819021\"" + "}";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/changepassword/1")
				.contentType(MediaType.APPLICATION_JSON)
                		.accept(MediaType.APPLICATION_JSON)
				.content(request)).andReturn();
        assertEquals(result.getResponse().getStatus(), 500);
	}

	@Test
	public void emptyNewPasswordTest() throws Exception {
		User user = new User();
		user.setId(2L);
		user.setOldPassword("Ab1@bcdefgghomnop#@2");
		user.setUserName("PK");
		when(userService.findByIdAndOldPassword(1L, "Ab1@bcdefgghomnop#@2")).thenReturn(user);

		User userStub = new User();
		userStub.setId(2L);
		userStub.setOldPassword("Ab1@bcdefgghomnop#@2");
		userStub.setNewPassword("");
		userStub.setUserName("PK");
		when(userService.save(userStub)).thenReturn(userStub);

		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"\"" + "}";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/changepassword/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(request)).andReturn();
		assertEquals(result.getResponse().getStatus(), 500);
	}

	@Test
	public void specialCharacterExceedingTest() throws Exception {
		User user = new User();
		user.setId(2L);
		user.setOldPassword("Ab1@bcdefgghomnop#@2");
		user.setUserName("PK");
		when(userService.findByIdAndOldPassword(1L, "Ab1@bcdefgghomnop#@2")).thenReturn(user);

		User userStub = new User();
		userStub.setId(1L);
		userStub.setOldPassword("Ab1@bcdefgghomnop#@2");
		userStub.setNewPassword("Ab1@bcdefgghomnop#@1");
		userStub.setUserName("PK");
		when(userService.save(userStub)).thenReturn(userStub);

		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"Ab1@y6j&k9a0l7h81@01$\"" + "}";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/changepassword/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(request)).andReturn();
		assertEquals(result.getResponse().getStatus(), 500);
	}

	@Test
	public void passwordSimilarityTest() throws Exception {
		User user = new User();
		user.setId(2L);
		user.setOldPassword("Ab1@bcdefgghomnop#@2");
		user.setUserName("PK");
		when(userService.findByIdAndOldPassword(1L, "Ab1@bcdefgghomnop#@2")).thenReturn(user);

		User userStub = new User();
		userStub.setId(1L);
		userStub.setOldPassword("Ab1@bcdefgghomnop#@2");
		userStub.setNewPassword("Ab1@y6j&k9a0l7h81@02$");
		userStub.setUserName("PK");
		when(userService.save(userStub)).thenReturn(userStub);

		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"Ab1@y6j&k9a0l7h81@02$\"" + "}";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/changepassword/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(request)).andReturn();
		assertEquals(result.getResponse().getStatus(), 500);
	}

	@Test
	public void repeatingCharacterTest() throws Exception {
		User user = new User();
		user.setId(2L);
		user.setOldPassword("Ab1@bcdefgghomnop#@2");
		user.setUserName("PK");
		when(userService.findByIdAndOldPassword(1L, "Ab1@bcdefgghomnop#@2")).thenReturn(user);

		User userStub = new User();
		userStub.setId(1L);
		userStub.setOldPassword("Ab1@bcdefgghomnop#@2");
		userStub.setNewPassword("Ab1@y6b&b9b0l7b81@02");
		userStub.setUserName("PK");
		when(userService.save(userStub)).thenReturn(userStub);

		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"Ab1@y6b&b9b0l7b81@02\"" + "}";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/changepassword/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(request)).andReturn();
		assertEquals(result.getResponse().getStatus(), 500);
	}

	@Test
	public void oldPasswordNotMatchTest() throws Exception {
		User user = new User();
		user.setId(2L);
		user.setOldPassword("Ab1@bcdefgghomnop#@2");
		user.setUserName("PK");
		when(userService.findByIdAndOldPassword(1L, "Ab1@bcdefgghomnop#@2")).thenReturn(user);

		User userStub = new User();
		userStub.setId(1L);
		userStub.setOldPassword("Ab1@bcdefgghomnp#@2");
		userStub.setNewPassword("Ab1@y6b&b9b0l7b81@02");
		userStub.setUserName("PK");
		when(userService.save(userStub)).thenReturn(userStub);

		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghmnop#@2\",\"newPassword\"" + ":" + "\"Ab1@y6b&b9b0l7b81@02\"" + "}";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/changepassword/2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(request)).andReturn();
		assertEquals(result.getResponse().getStatus(), 500);
	}
}
