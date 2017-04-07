package com.jk.demo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.*;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by zengping on 2017/3/7.
 */
public class GuavaDemo {

    /**
     * 使用和避免null
     */
    @Test
    public void useAndAvoidNull(){
        /*Optional<Boolean> bo = Optional.of(true);
        assertNotNull(bo.get());
        System.out.println(bo.isPresent());*/
        /*Optional<Boolean> bo = Optional.of(null);
        Optional<Object> absent = Optional.absent();
        Optional<Object> objectOptional = Optional.fromNullable(null);
        Set<Object> objects = objectOptional.asSet();*/
        checkArgument( 5 > 3, "compare params error", 5, 3);
        Objects.hashCode("ccc","vvv","kkk");
        System.out.println(MoreObjects.toStringHelper("CCCCC").add("CCC", "DDD").toString());
        ComparisonChain.start().compare("ccc", "dddd").compare("vvv", "fff", Ordering.natural().nullsFirst());
        ImmutableList<String> of = ImmutableList.of("aaa", "bbb", "ccc");
        ImmutableSet<Object> objects = ImmutableSet.builder().addAll(of).add("super").build();
        ImmutableSet<String> set = ImmutableSet.copyOf(of);
        Multiset<Object> multiset = HashMultiset.create(5);
        ImmutableMultiset<Object> cc_set = ImmutableMultiset.of();
        cc_set.forEach((object)-> multiset.add(object));
        ByteSource byteSource = Files.asByteSource(new File("/ccc"));

    }
}
