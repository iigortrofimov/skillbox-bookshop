package com.bookshop.mybookshop.util;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;

public final class LoggingUtils {

    /**
     * Lock.
     */
    private LoggingUtils() {
    }

    /**
     * Create detailed method execution message.
     *
     * @param type      a name of a called component type
     * @param joinPoint a {@link JoinPoint} from an aspect
     * @return the message
     */
    public static String createCallExecutionMessage(final String type, final JoinPoint joinPoint) {
        return String.format("%s call %s with arguments: %s.", type, joinPoint.toLongString(), Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Create short method execution message.
     *
     * @param type      a name of a called component type
     * @param joinPoint a {@link JoinPoint} from an aspect
     * @return the message
     */
    public static String createShortCallExecutionMessage(final String type, final JoinPoint joinPoint) {
        return String.format("%s call %s.", type, joinPoint.toShortString());
    }
}
