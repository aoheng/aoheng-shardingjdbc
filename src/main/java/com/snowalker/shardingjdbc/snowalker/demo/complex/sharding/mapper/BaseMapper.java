package com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.mapper;



import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper<T>, MySqlMapper<T>,ExampleMapper<T> ,Marker{

    @Select("SELECT found_rows()")
    @ResultType(Long.class)
    Long getFoundRows();

}
