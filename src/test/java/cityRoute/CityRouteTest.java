package cityRoute;

import org.junit.Assert;
import org.junit.Test;

import com.cityRoute.CityPath;

import java.util.Set;

public class CityRouteTest {

    @Test
    public void build() {
        CityPath city = CityPath.build("Montreal");
        Assert.assertEquals("MONTREAL", city.getName());
    }

    @Test
    public void buildWithNeighbours() {
        CityPath city = CityPath.build("Montreal");
        city.addNearby(CityPath.build("Laval"))
                .addNearby(CityPath.build("Lachine"));

        Set<CityPath> nearby = city.getNearby();
        Assert.assertEquals(2, nearby.size());
        Assert.assertTrue(nearby.contains(CityPath.build("Laval")));
    }


    @Test
    public void addNearby() {
        CityPath city = CityPath.build("Montreal");
        city.addNearby(CityPath.build("Laval"))
                .addNearby(CityPath.build("Lachine"));

        Assert.assertEquals(2, city.getNearby().size());
    }

    @Test
    public void addNearbyDuplicates() {
        CityPath city = CityPath.build("Montreal");
        city.addNearby(CityPath.build("Laval"))
                .addNearby(CityPath.build("LAVAL"))
                .addNearby(CityPath.build("  LaVal"))
                .addNearby(CityPath.build("  LaVaL "));

        Assert.assertEquals(1, city.getNearby().size());
    }


}