package com.hendisantika.coffeshop.entity;

import lombok.Value;
import org.springframework.data.annotation.Id;
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

@Value
@Document
public class Store {
    @Id
    UUID id = UUID.randomUUID();
    String name;
    Address address;

    /**
     * Value object to represent an {@link Address}.
     *
     * @author Oliver Gierke
     */
    @Value
    public static class Address {

        String street, city, zip;
        @GeoSpatialIndexed(type = GEO_2DSPHERE)
        Point location;

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return String.format("%s, %s %s", street, zip, city);
        }
    }
}
