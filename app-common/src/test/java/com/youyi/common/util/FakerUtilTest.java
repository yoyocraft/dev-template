package com.youyi.common.util;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/10
 */
class FakerUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(FakerUtilTest.class);
    private static final Faker faker = new Faker();

    @Test
    void testFakerCode() {
        logger.info("code: {}", faker.code().asin());
    }

    @Test
    void testFakerContent() {
        logger.info("paragraphs: {}", faker.lorem().paragraph(100));
    }
}
