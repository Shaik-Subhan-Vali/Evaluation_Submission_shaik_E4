package My_Package;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CryptocurrencyWalletAPI {
	String token ;
	
	@Test
	public void Register_user() {
		JSONObject json = new JSONObject();
		json.put("username", "user123");
		json.put("password", "securepassword");
		json.put("email", "user@example.com");
		Response Res = given().contentType(ContentType.JSON).body(json.toJSONString()).when().post("https://crypto-wallet-server.mock.beeceptor.com/api/v1/register");
			
		Res.then().statusCode(200).log().all();
		Res.jsonPath().getString("username").equals("user123");
		Res.jsonPath().getString("password").equals("securepassword");
		
			
	}
	
	@Test
	public void Login_user() {
		JSONObject json = new JSONObject();
		json.put("username", "user123");
		json.put("password", "securepassword");
		Response Res = given().contentType(ContentType.JSON).body(json.toJSONString()).when().post("https://crypto-wallet-server.mock.beeceptor.com/api/v1/login");
		
		token = Res.jsonPath().getString("access_token");
		 
		Res
		.then().statusCode(200).log().all();
		
		Res.jsonPath().getString("token_type").equals("bearer");
	
		
	}

	
}
