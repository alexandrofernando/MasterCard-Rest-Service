package com.cityRoute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class ConnectedPath {

    private static final Log LOG = LogFactory.getLog(ConnectedPath.class);

    private ConnectedPath() { }

    public static boolean commute(CityPath origin, CityPath destination) {



        LOG.info("Origin: " + origin.getName() + ", destination: " + destination.getName());

        if (origin.equals(destination)) return true;

        if (origin.getNearby().contains(destination)) return true;

        /*
         * The origin city was already visited since we have started from it
         */
        Set<CityPath> visited = new HashSet<>(Collections.singleton(origin));

        /*
         * Put all the neighboring cities into a bucket list
         */
        Deque<CityPath> bucketlist = new ArrayDeque<>(origin.getNearby());


        while (!bucketlist.isEmpty()) {


            CityPath city = bucketlist.getLast();

            if (city.equals(destination)) return true;

            // remove the city from the bucket list

            // first time visit?
            if (!visited.contains(city)) {

                visited.add(city);

                // add neighbours to the bucket list and
                // remove already visited cities from the list
                bucketlist.addAll(city.getNearby());
                bucketlist.removeAll(visited);

                LOG.info("Visiting: ["
                        + city.getName()
                        + "] , neighbours: ["
                        + (city.prettyPrint())
                        + "], bucketlist: ["
                        + bucketlist.toString()
                        + "]");
            } else {
                // the city has been visited, so remove it from the bucket list
                bucketlist.removeAll(Collections.singleton(city));
            }
        }

        return false;
    }
}

