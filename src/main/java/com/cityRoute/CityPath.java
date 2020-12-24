package com.cityRoute;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public class CityPath {
    private String name;

    private Set<CityPath> nearby = new HashSet<>();

    private CityPath(String name) {
        Objects.requireNonNull(name);
        this.name = name.trim().toUpperCase();
    }

    private CityPath() {
    }

    public static CityPath build(String name) {
        return new CityPath(name);
    }

    @Override
    public String toString() {

        return "City{" +
                "name='" + name + "'" +
                ", nearby='" + prettyPrint() +
                "'}";
    }

    public String prettyPrint() {
        return nearby
                .stream()
                .map(CityPath::getName)
                .collect(Collectors.joining(","));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityPath addNearby(CityPath city) {
        nearby.add(city);
        return this;
    }

    public Set<CityPath> getNearby() {
        return nearby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityPath)) return false;
        CityPath city = (CityPath) o;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
