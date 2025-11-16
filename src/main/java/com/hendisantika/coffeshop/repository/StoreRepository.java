package com.hendisantika.coffeshop.repository;

import com.hendisantika.coffeshop.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : coffeeshop
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/12/17
 * Time: 06.32
 * To change this template use File | Settings | File Templates.
 */


public interface StoreRepository extends MongoRepository<Store, UUID> {
    @RestResource(rel = "by-location")
    Page<Store> findByAddressLocationNear(Point location, Distance distance, Pageable pageable);
}
