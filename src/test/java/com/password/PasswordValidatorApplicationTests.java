package com.password;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PasswordValidatorApplicationTests {



	@Value("${base.url}")
	private String baseURI;

	@Test
	public void testPasswordUpdated(){

		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"Ab1@y6j8k9a0lg@lp021\"" + "}";

		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/1")
				.then().extract().response();
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(response.asString()).isEqualTo("Password updated successfully");
	}

	@Test
	public void testEmptyNewPassword(){
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"\"" + "}";
		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/2")
				.then().extract().response();
		assertThat(response.statusCode()).isEqualTo(500);
		assertThat((String) JsonPath.parse(response.asString()).read("$.description")).isEqualTo(
				"New Password can not be empty or null"
		);
	}

	@Test
	public void testSpecialCharacterExceedingPassword(){
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"Ab#@y6j8k9&0l$@lp021\"" + "}";
		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/2")
				.then().extract().response();
		assertThat(response.statusCode()).isEqualTo(500);
		assertThat((String) JsonPath.parse(response.asString()).read("$.description")).isEqualTo(
				"More than 4 special Characters"
		);
	}

	@Test
	public void testRepeatingCharacter(){
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"Ax#ny6j8a9a0l$aaa021\"" + "}";
		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/2")
				.then().extract().response();
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat((String) JsonPath.parse(response.asString()).read("$.description")).contains(
				"Password contains 5 occurrences of the character 'a', but at most 4 are allowed."
		);
	}

	@Test
	public void testNumericCharacterExceeding(){
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"A6#ay678k980l$5a1021\"" + "}";
		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/2")
				.then().extract().response();

		assertThat(response.statusCode()).isEqualTo(500);
		assertThat((String) JsonPath.parse(response.asString()).read("$.description")).isEqualTo(
				"50% of password is number"
		);
	}

	@Test
	public void testPasswordMatchSimilarity(){
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@\"" + "}";
		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/2")
				.then().extract().response();
		assertThat(response.statusCode()).isEqualTo(500);
		assertThat((String) JsonPath.parse(response.asString()).read("$.description")).isEqualTo(
				"Percentage of old and new password is equal and more than 80%"
		);
	}

	@Test
	public void testNoNumericDigitPresent(){
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"IQz@bcdefllhomnop#@\"" + "}";
		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/2")
				.then().extract().response();
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat((String) JsonPath.parse(response.asString()).read("$.description")).contains(
				"Password must contain 1 or more digit characters"
		);
	}

	@Test
	public void testNoUppercaseCharPresent(){
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"iqz@bcdefllhomnop#@\"" + "}";
		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/2")
				.then().extract().response();
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat((String) JsonPath.parse(response.asString()).read("$.description")).contains(
				"Password must contain 1 or more uppercase characters"
		);
	}

	@Test
	public void testNoSpecialCharPresent(){
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"IqzZ9cdefllhomnopLx\"" + "}";
		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/2")
				.then().extract().response();
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat((String) JsonPath.parse(response.asString()).read("$.description")).contains(
				"Password must contain 1 or more special characters."
		);
	}

	@Test
	public void testPasswordLessThan18Alphanumeric(){
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghomnop#@2\",\"newPassword\"" + ":" + "\"IqzZ9cdef@\"" + "}";
		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/2")
				.then().extract().response();
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat((String) JsonPath.parse(response.asString()).read("$.description")).contains(
				"Password must be 18 or more characters in length."
		);
	}

	@Test
	public void testOldPasswordNotMatch(){
		String request = "{" + "\"oldPassword\"" + ":" + "\"Ab1@bcdefgghoop#@\",\"newPassword\"" + ":" + "\"Ab1@y6j8k9a0lg@lp021\"" + "}";
		Response response = RestAssured.given().
				baseUri(baseURI).
				body(request).headers("Content-Type", "application/json").
				post("changepassword/2")
				.then().extract().response();
		assertThat(response.statusCode()).isEqualTo(500);
		assertThat(response.asString()).contains(
				"Password not updated"
		);
	}
}
