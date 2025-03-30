package com.youyi.infra.localmsg.core;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/16
 */
public class InvokeStatusHolder {
    private static final ThreadLocal<Integer> INVOKE_COUNTER = ThreadLocal.withInitial(() -> 0);

    public static boolean inInvoke() {
        return INVOKE_COUNTER.get() > 0;
    }

    public static void startInvoke() {
        INVOKE_COUNTER.set(INVOKE_COUNTER.get() + 1);
    }

    public static void endInvoke() {
        int count = INVOKE_COUNTER.get();
        if (count > 0) {
            INVOKE_COUNTER.set(count - 1);
        }
        if (INVOKE_COUNTER.get() == 0) {
            INVOKE_COUNTER.remove();
        }
    }
}
