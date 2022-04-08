package com.snowalker.shardingjdbc.snowalker.demo.common.enums;

import lombok.Getter;

/**
 * 排序
 *
 * @author shb
 * @since 2021-04-15
 */
public enum SortEnum {
    ASC("ascend", "升序"),
    DESC("descend", "降序");

    @Getter
    private String key;
    @Getter
    private String desc;

    SortEnum(String key, String desc) {
        this.desc = desc;
        this.key = key;
    }
}
