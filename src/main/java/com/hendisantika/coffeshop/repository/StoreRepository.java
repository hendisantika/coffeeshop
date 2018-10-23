package com.hendisantika.coffeshop.repository;

import com.hendisantika.coffeshop.entity.Store;
import com.mongodb.client.model.geojson.Point;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
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


public interface StoreRepository extends PagingAndSortingRepository<Store, UUID>, QuerydslPredicateExecutor<Store> {
    @RestResource(rel = "by-location")
    Page<Store> findByAddressLocationNear(Point location, Distance distance, Pageable pageable);

    /**
     * Tweak the Querydsl binding if collection resources are filtered.
     *
     * @see org.springframework.data.web.querydsl.QuerydslBinderCustomizer#customize(org.springframework.data.web.querydsl.QuerydslBindings,
     * com.mysema.query.types.EntityPath)
     */
    default void customize(QuerydslBindings bindings, QStore store) {

        bindings.bind(store.address.city).first((path, value) -> path.endsWith(value));
        bindings.bind(String.class).first((StringPath path, String value) -> path.contains(value));
    }
}
