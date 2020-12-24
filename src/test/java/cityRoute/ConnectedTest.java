package cityRoute;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cityRoute.CityPath;
import com.cityRoute.ConnectedPath;
import com.cityRoute.CityMap;

import java.util.HashMap;
import java.util.Map;



import com.cityRoute.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("test")

public class ConnectedTest {

	@Autowired
	CityMap county;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void fileLoad() {
		Assert.assertFalse("File load failed", county.getCityMap().isEmpty());
	}

	@Test
	public void sameCity() {
		CityPath city = CityPath.build("A");
		Assert.assertTrue(ConnectedPath.commute(city, city));
	}

	@Test
	public void neighbours() {
		CityPath cityA = county.getCity("A");
		CityPath cityB = county.getCity("B");

		Assert.assertNotNull("Invalid test data. City not found: A", cityA);
		Assert.assertNotNull("Invalid test data. City not found: B", cityB);
   
		Assert.assertTrue(ConnectedPath.commute(cityA, cityB));
	}

	@Test
	public void distantConnected() {
		CityPath cityA = county.getCity("F");
		CityPath cityB = county.getCity("A");

		Assert.assertNotNull("Invalid test data. City not found: F", cityA);
		Assert.assertNotNull("Invalid test data. City not found: A", cityB);
        System.out.println(ConnectedPath.commute(cityA, cityB));
		Assert.assertTrue(ConnectedPath.commute(cityA, cityB));
	}

	@Test
	public void restConnectedIT() {

		Map<String, String> params = new HashMap<>();
		params.put("origin", "A");
		params.put("destination", "F");

		String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class,
				params);
		Assert.assertEquals("Yes", body);
	}

	@Test
	public void restNotConnectedIT() {

		Map<String, String> params = new HashMap<>();
		params.put("origin", "a");
		params.put("destination", "l");

		String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class,
				params);
		Assert.assertEquals("No", body);
	}

	@Test
	public void badRequestIT() {
		String body = restTemplate.getForObject("/connected", String.class);
		ResponseEntity<String> response = restTemplate.exchange("/connected?origin=none&destination=none",
				HttpMethod.GET, HttpEntity.EMPTY, String.class);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

}
