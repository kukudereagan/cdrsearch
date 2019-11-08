package com.clinbrain.dao;

import com.clinbrain.common.mapper.MyMapper;
import com.clinbrain.common.pojo.CdrEntity;

import java.util.List;

/**
 * @Author：ligen
 * @Date: Created:10:53  2019/11/1
 * @Description:
 **/
public interface CdrTreeMapper extends MyMapper<CdrEntity> {
    List<CdrEntity> listDomain();//领域
    List<CdrEntity> listTbByDomain(String tb);//领域下的表
    List<CdrEntity> listColumnsByTb(String tb);//表里面的字段
}
