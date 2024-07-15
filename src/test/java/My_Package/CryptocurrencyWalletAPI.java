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
		//created a json obj to store request paylod
		JSONObject json = new JSONObject();
		json.put("username", "user123");
		json.put("password", "securepassword");
		json.put("email", "user@example.com");
		Response Res = given().contentType(ContentType.JSON).body(json.toJSONString()).when().post("https://crypto-wallet-server.mock.beeceptor.com/api/v1/register");
		//validating the response payload	
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
		
		//created a token vraible to store generated token
		token = Res.jsonPath().getString("access_token");
		 
		Res
		.then().statusCode(200).log().all();
		
		Res.jsonPath().getString("token_type").equals("bearer");
		
	}
	
	@Test
	public void Retrieve_wallet_balance() {
		//using get method to retrieve response payload
		Response Res = given().get("https://crypto-wallet-server.mock.beeceptor.com/api/v1/balance");
		Res.then().statusCode(200).log().all();
		Res.jsonPath().getString("balance").equals("100.25");
		Res.jsonPath().getString("currency").equals("BTC");
		
	}
	@Test
	public void List_transactions() {
		//using get method to retrieve response payload
		Response Res = given().get("https://crypto-wallet-server.mock.beeceptor.com/api/v1/transactions");
		Res.then().statusCode(200).log().all();
		//validating the response payload	
		Res.jsonPath().getString("transactions[0].id").equals("12345");
		Res.jsonPath().getString("transactions[0].amount").equals("10.5");
		

	}		
	@Test
	public void Transfer_ETH() {
		JSONObject json = new JSONObject();
		json.put("recipient_address", "0x1234567890ABCDEF");
		json.put("amount", "5.0");
		json.put("currency", "ETH");
		Response Res = given().contentType(ContentType.JSON).body(json.toJSONString()).when().post("https://crypto-wallet-server.mock.beeceptor.com/api/v1/transactions");
		//validating the response payload		
		Res.then().statusCode(200).log().all();
		Res.jsonPath().getString("id").equals("98765");
		Res.jsonPath().getString("currency").equals("ETH");

	}
	@Test
	public void Calculate_transaction_fees() {
		JSONObject json = new JSONObject();
		json.put("recipient_address", "0x1234567890ABCDEF");
		json.put("amount", "2.5");
		json.put("currency", "BTC");
		Response Res = given().contentType(ContentType.JSON).body(json.toJSONString()).when().post("https://crypto-wallet-server.mock.beeceptor.com/api/v1/transaction_fee");
		//validating the response payload		
		Res.then().statusCode(200).log().all();
		Res.jsonPath().getString("fee").equals("0.0005");
		Res.jsonPath().getString("currency").equals("BTC");

	}
	
	@Test
	public void Get_available_exchange_rates() {
		Response Res = given().get("https://crypto-wallet-server.mock.beeceptor.com/api/v1/exchange_rates");
		Res.then().statusCode(200).log().all();
		Res.jsonPath().getString("BTC").equals("42345.67");
		Res.jsonPath().getString("ETH").equals("2567.89");
		Res.jsonPath().getString("USD").equals("1.0");

	}
	
}









