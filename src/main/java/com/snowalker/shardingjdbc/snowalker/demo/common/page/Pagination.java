package com.snowalker.shardingjdbc.snowalker.demo.common.page;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.snowalker.shardingjdbc.snowalker.demo.common.enums.SortEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description: 分页Vo
 * @author: Aoheng
 * @date: 2022/4/8 15:15
 */
public class Pagination implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Pagination.class);

    private static final long serialVersionUID = -4083929594112114522L;

    /***
     * 当前页
     */
    private int current = 1;
    /***
     * 默认每页数量20
     */
    private int pageSize = 20;
    /***
     * count总数
     */
    private long total = 0;
    /**
     * 接收前端的排序信息
     */
    private String sorter;
    /**
     * 查询场景，0分页查询  1导出excel时的查询
     * 分页查询时，最大pageSize为1000
     * 导出excel查询时，最大pageSize为50000
     */
    private int lookupScene = LOOKUP_SCENE_PAGING;
    /**
     * 分页查询
     */
    public static final int LOOKUP_SCENE_PAGING = 0;
    /**
     * 导出时查询
     */
    public static final int LOOKUP_SCENE_EXPORT = 1;

    /**
     * 默认排序
     */
    private static final String DEFAULT_ORDER_BY = "1";
    /**
     * 排序
     */
    private String orderBy = DEFAULT_ORDER_BY;

    public int getLookupScene() {
        return lookupScene;
    }

    public void setLookupScene(int lookupScene) {
        this.lookupScene = lookupScene;
    }

    /**
     * 排序 - 降序标记
     */
    public static final String ORDER_DESC = "DESC";

    public Pagination() {
    }

    /***
     * 指定当前页数
     */
    public Pagination(int current) {
        setCurrent(current);
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (this.lookupScene == LOOKUP_SCENE_PAGING && pageSize > 1000) {
            log.warn("分页pageSize过大，将被调整为默认限值，请检查调用是否合理！pageSize=" + pageSize);
            pageSize = 1000;
        }
        if (this.lookupScene == LOOKUP_SCENE_EXPORT && pageSize > 50000) {
            log.warn("导出pageSize过大，将被调整为默认限值，请检查调用是否合理！pageSize=" + pageSize);
            pageSize = 50000;
        }
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long totalCount) {
        total = totalCount;
    }

    public String getSorter() {
        return sorter;
    }

    public void setSorter(String sorter) {
        this.sorter = sorter;
    }

    /***
     * 获取数据库字段的列排序
     * @return
     */
    public String getOrderBy() {
        if (StringUtils.isEmpty(getSorter())) {
            return orderBy;
        }
        JSONObject jsonObject = (JSONObject) JSONObject.parse(getSorter(), Feature.OrderedField);
        if (Objects.isNull(jsonObject)) {
            return orderBy;
        }
        StringBuilder sb = new StringBuilder();
        jsonObject.forEach((key, value) -> {
            if (!StringUtils.isEmpty(key)) {
                sb.append("`");
                sb.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key));
                sb.append("`");
                sb.append(" ");
                sb.append(orderByNameConvert(String.valueOf(value)));
                sb.append(",");
            }
        });
        if (sb.length() == 0) {
            return orderBy;
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    private String orderByNameConvert(String orderByName) {
        if (Objects.equals(SortEnum.ASC.getKey(), orderByName)) {
            return "asc";
        }
        return "desc";
    }

    /**
     * 设置排序
     *
     * @param orderBy
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /***
     * 获取总的页数
     * @return
     */
    public int getTotalPage() {
        if (total <= 0) {
            return 0;
        }
        return (int) Math.ceil((float) total / pageSize);
    }

    /**
     * 获取当前的位置，可以知道从第几条开始取
     *
     * @return
     */
    public int getCurrentPosition() {
        return (getCurrent() - 1) * getPageSize();
    }

    /**
     * 清空默认排序
     */
    public void clearDefaultOrder() {
        // 是否为默认排序
        if (V.equals(orderBy, DEFAULT_ORDER_BY)) {
            orderBy = null;
        }
    }

    /**
     * 转换为IPage
     *
     * @param <T>
     * @return
     */
    public <T> IPage<T> toIPage() {
        List<OrderItem> orderItemList = null;
        // 解析排序
        if (V.notEmpty(orderBy)) {
            orderItemList = new ArrayList<>();
            // orderBy=shortName:DESC,age:ASC,birthdate
            String[] orderByFields = V.split(orderBy);
            for (String field : orderByFields) {
                if (field.contains(":")) {
                    String[] fieldAndOrder = V.split(field, ":");
                    String columnName = V.toSnakeCase(fieldAndOrder[0]);
                    if (ORDER_DESC.equalsIgnoreCase(fieldAndOrder[1])) {
                        orderItemList.add(OrderItem.desc(columnName));
                    } else {
                        orderItemList.add(OrderItem.asc(columnName));
                    }
                } else {
                    orderItemList.add(OrderItem.asc(V.toSnakeCase(field)));
                }
            }
        }
        IPage<T> page = new Page<T>()
                .setCurrent(getCurrent())
                .setSize(getPageSize())
                // 如果前端传递过来了缓存的总数，则本次不再count统计
                .setTotal(getTotal() > 0 ? -1 : getTotal());
        if (orderItemList != null) {
            ((Page<T>) page).addOrder(orderItemList);
        }
        return page;
    }
}
