package io.mikael.poc.test;

import io.mikael.poc.Version;

public class Person {

    public String name;

    @Version(major = 1, minor = 0, patch = 0)
    public String address;

    @Version(major = 1, minor = 1, patch = 0)
    public String sport;

    @Version(major = 1, minor = 2, patch = 0)
    public String claimToFame;

    public Person() {
    }

    public Person(final String name, final String address, final String sport, final String claimToFame) {
        this.name = name;
        this.address = address;
        this.sport = sport;
        this.claimToFame = claimToFame;
    }

    @Override
    public String toString() {
        return String.format(
                "Person(name=\"%s\", address=\"%s\", sport=\"%s\", claimToFame=\"%s\")",
                name, address, sport, claimToFame);
    }

}
