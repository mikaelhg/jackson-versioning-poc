package io.mikael.poc.test;

import io.mikael.poc.Person;
import io.mikael.poc.jackson.VersioningObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class VersioningTest {

    @Test
    public void versioningTest() throws Exception {
        final var person = new Person("Muffin Man", "Drury Lane", "Boxing", "Match Fixing");

        final var om100 = new VersioningObjectMapper(1, 0, 0);
        final var s100 = om100.writeValueAsString(person);
        System.out.printf("p -> om(1, 0, 0) -> json: %s%n", s100);
        JSONAssert.assertEquals(
                "{\"name\":\"Muffin Man\",\"address\":\"Drury Lane\"}",
                s100, true);

        final var om110 = new VersioningObjectMapper(1, 1, 0);
        final var s110 = om110.writeValueAsString(person);
        System.out.printf("p -> om(1, 1, 0) -> json: %s%n", s110);
        JSONAssert.assertEquals(
                " {\"name\":\"Muffin Man\",\"address\":\"Drury Lane\",\"sport\":\"Boxing\"}",
                s110, true);

        final var om120 = new VersioningObjectMapper(1, 2, 0);
        final var s120 = om120.writeValueAsString(person);
        System.out.printf("p -> om(1, 2, 0) -> json: %s%n", s120);
        JSONAssert.assertEquals(
                "{\"name\":\"Muffin Man\",\"address\":\"Drury Lane\",\"sport\":\"Boxing\",\"claimToFame\":\"Match Fixing\"}",
                s120, true);

        final var p_120_110 = om110.readValue(s120, Person.class);
        System.out.printf("p -> om(1, 2, 0) -> om(1, 1, 0) -> p: %s%n", p_120_110);
        Assert.assertNull(p_120_110.claimToFame);

        final var p_120_100 = om100.readValue(s120, Person.class);
        System.out.printf("p -> om(1, 2, 0) -> om(1, 0, 0) -> p: %s%n", p_120_100);
        Assert.assertNull(p_120_100.sport);
        Assert.assertNull(p_120_100.claimToFame);

    }

}

