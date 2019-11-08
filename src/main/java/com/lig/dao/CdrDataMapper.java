package com.lig.dao;

import com.lig.common.pojo.HeaderData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author：ligen
 * @Date: Created:14:05  2019/11/4
 * @Description:
 **/
public interface CdrDataMapper   {
    List<Map<String,Object>> listData(@Param("tb") String tb, @Param("col") String col, @Param("filter") String filter);
    List<HeaderData> listColumns(String tb);
    String selectCol(String tb);
}
