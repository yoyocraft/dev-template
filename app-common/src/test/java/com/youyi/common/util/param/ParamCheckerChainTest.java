package com.youyi.common.util.param;

import com.youyi.common.exception.AppBizException;
import com.youyi.common.util.seq.IdSeqUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.youyi.common.constant.ErrorCodeConstant.INVALID_PARAM;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/28
 */
class ParamCheckerChainTest {

    ParamCheckerChain paramCheckerChain = null;

    @BeforeEach
    void setUp() {
        paramCheckerChain = ParamCheckerChain.newCheckerChain();
    }

    @Test
    void testNewCheckerChain() {
        ParamCheckerChain result = ParamCheckerChain.newCheckerChain();
        Assertions.assertNotNull(result);
    }

    @Test
    void testPut() {
        ParamCheckerChain result = paramCheckerChain.put(null, null);
        Assertions.assertNotNull(result);
    }

    @Test
    void testValidateWithThrow() {
        Assertions.assertDoesNotThrow(() -> paramCheckerChain.validateWithThrow());
    }

    @Test
    void testParamCheckerChain() {
        long param = 1L;
        Assertions.assertDoesNotThrow(
            () -> ParamCheckerChain.newCheckerChain()
                .put(ParamChecker.greaterThanChecker(0L, INVALID_PARAM), param)
                .put(ParamChecker.lessThanChecker(100L, INVALID_PARAM), param)
                .put(ParamChecker.rangeCloseChecker(0L, 100L, INVALID_PARAM), param)
                .validateWithThrow()
        );

        long exParam = -1L;

        AppBizException exception = Assertions.assertThrows(
            AppBizException.class,
            () -> ParamCheckerChain.newCheckerChain()
                .put(ParamChecker.greaterThanChecker(0L, INVALID_PARAM), exParam)
                .put(ParamChecker.lessThanChecker(100L, INVALID_PARAM), exParam)
                .validateWithThrow()
        );
        Assertions.assertEquals(INVALID_PARAM, exception.getCode());
    }

    @Test
    void testSnowflakeIdChecker() {
        Assertions.assertDoesNotThrow(
            () -> ParamCheckerChain.newCheckerChain()
                .put(ParamChecker.snowflakeIdChecker(), IdSeqUtil.genSysTaskId())
                .validateWithThrow()
        );
    }

    @Test
    void testEmailChecker() {
        Assertions.assertDoesNotThrow(
            () -> ParamCheckerChain.newCheckerChain()
                .put(ParamChecker.emailChecker(), "test@test.com")
                .validateWithThrow()
        );
    }
}

// Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme