package com.snowalker.shardingjdbc.snowalker.demo.common.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snowalker.shardingjdbc.snowalker.demo.common.page.Pagination;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApiPageResult<T> extends ApiResult<T> {

    private long current;
    private long pageSize;
    private long total;

    public static ApiPageResult success(Pagination pagination, List data, Integer total) {
        ApiPageResult result = new ApiPageResult();
        result.setMessage("success");
        result.setCurrent(pagination.getCurrent());
        result.setPageSize(pagination.getPageSize());
        result.setData(data);
        result.setTotal(total);
        return result;
    }

    public static ApiPageResult success(IPage page) {
        ApiPageResult result = new ApiPageResult();
        result.setMessage("success");
        result.setCurrent(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setTotal(page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    public static ApiPageResult success(Pagination pagination, Object data, Integer total) {
        ApiPageResult result = new ApiPageResult();
        result.setMessage("success");
        result.setCurrent(pagination.getCurrent());
        result.setPageSize(pagination.getPageSize());
        result.setData(data);
        result.setTotal(total);
        return result;
    }

}
