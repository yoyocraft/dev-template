package com.youyi.common.constant;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/29
 */
public class RepositoryConstant {
    public static final int SINGLE_DML_AFFECTED_ROWS = 1;

    public static final int INIT_VERSION = 0;

    public static final String INIT_QUERY_CURSOR = "0";

    public static final String TOP_COMMENTARY_ID = "-1";

    public static final String MONGO_IGNORE_CASE_OPTION = "i";

    public static final String MONGO_FUZZY_QUERY_FORMAT = ".*%s.*";

    public static String ofFuzzyQuery(String query) {
        return String.format(MONGO_FUZZY_QUERY_FORMAT, query);
    }

}
