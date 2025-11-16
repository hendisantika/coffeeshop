package com.hendisantika.coffeshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import static org.springframework.data.mongodb.core.index.GeoSpatialIndexType.GEO_2DSPHERE;

/**
 * Created by IntelliJ IDEA.
 * Project : coffeeshop
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/12/17
 * Time: 06.30
 * To change this template use File | Settings | File Templates.
 */

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@Document
public class Store {
    @Id
    private UUID id;
    private String name;
    private Address address;

    @PersistenceCreator
    public Store(String name, Address address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
    }

    /**
     * Value object to represent an {@link Address}.
     *
     * @author Oliver Gierke
     */
    @Getter
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @ToString
    public static class Address {

        private String street;
        private String city;
        private String zip;
        @GeoSpatialIndexed(type = GEO_2DSPHERE)
        private Point location;
    }
}
