package com.alb.dao.sql;

import com.alb.model.po.Content;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

@SuppressWarnings("unused")
public class ContentFilterSqlBuilder {

    private final String ALL_RECORDS;
    private final String FROM_CONTENT_AND_IT_GROUP;
    private final String CONTENT_FIELDS;
    private final String ORDER_AND_LIMIT;

    public ContentFilterSqlBuilder() {

        ALL_RECORDS = "count(*)";
        FROM_CONTENT_AND_IT_GROUP = "content, content_group";
        CONTENT_FIELDS = "content.id, content.name, "
                + "author, content_group.name as \"group\" ";
        ORDER_AND_LIMIT = "groupId, content.name "
                + "offset #{offset} rows fetch next #{limit} rows only";
    }

    public String buildSelectCountAfterFilter(@Param("content") final Content content) {
        return new SQL(){{

            SELECT(ALL_RECORDS);
            FROM(FROM_CONTENT_AND_IT_GROUP);
            WHERE(getWhereQuery(content.getGroupId()));

        }}.toString();
    }

    public String buildFindAllWithFilter(
            @Param("content") final Content content) {
        return new SQL(){{

            SELECT(CONTENT_FIELDS);
            FROM(FROM_CONTENT_AND_IT_GROUP);
            WHERE(getWhereQuery(content.getGroupId()));
            ORDER_BY(ORDER_AND_LIMIT);
        }}.toString();
    }

    private String getWhereQuery(Long groupId) {
        StringBuilder where =
                new StringBuilder("groupId=content_group.id ");

        if (groupId != null)
            where.append("and groupId = #{content.groupId} ");

        where.append("and content.name like #{content.name} ");
        where.append("and author like #{content.author} ");

        return where.toString();
    }
}
