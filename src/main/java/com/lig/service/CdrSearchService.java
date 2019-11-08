package com.lig.service;

import com.lig.common.dataSource.TargetDataSource;
import com.lig.common.pojo.CdrEntity;
import com.lig.common.pojo.HeaderData;
import com.lig.dao.CdrDataMapper;
import com.lig.dao.CdrTreeMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author：ligen
 * @Date: Created:10:52  2019/11/1
 * @Description: CDR查询服务
 **/
@Service
public class CdrSearchService {
    @Value(value="${current.cdr.isbigdata}")
    private boolean isBigdata; //是否为大数据环境

    @Autowired
    private CdrTreeMapper cdrTreeMapper;

    @Autowired
    private CdrDataMapper cdrDataMapper;


    @TargetDataSource(name="cdr")
    public List<CdrEntity.CdrTree> listTree(){
        List<CdrEntity.CdrTree> trees = new ArrayList<>();
        if(isBigdata){
            /**大数据环境**/
            List<CdrEntity> domains = cdrTreeMapper.listDomain();
            for(CdrEntity domain:domains){
                List<CdrEntity> listTb = cdrTreeMapper.listTbByDomain(domain.getName());
                CdrEntity.CdrTree tree = new CdrEntity.CdrTree();
                tree.setName(domain.getName());
                tree.setChildren(listTb);
                trees.add(tree);
            }
        }else {
            /**传统数据库**/

        }

        return trees;
    }

    @TargetDataSource(name = "cdr")
    public List<CdrEntity> listColumnsByTb(String tb){
        if(isBigdata){
            List<CdrEntity> columns = cdrTreeMapper.listColumnsByTb(tb);
            return columns;
        }
        return null;
    }

    @TargetDataSource(name = "cdr")
    public List<HashMap> listPageData(String tb,String filter,int offset,int limit){
        if(isBigdata){
            String col = cdrDataMapper.selectCol(tb);
            if(StringUtils.isEmpty(col)){
                return null;
            }
            PageHelper.startPage(offset, limit,true);
            List<Map<String,Object>> list = cdrDataMapper.listData(tb,col,filter);
            for(Map<String,Object> m:list){
                Class<? extends Object> keyClass = null;
                for(Map.Entry<String,Object> k : m.entrySet()){
                    keyClass =  k.getValue().getClass();
                    if(keyClass.equals(Timestamp.class)){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        m.put(k.getKey(),sdf.format(k.getValue()));
                    }
                }
            }
            Page<HashMap> pages = (Page)list;
            return pages;
        }
        return null;
    }
    @TargetDataSource(name = "cdr")
    public List<HeaderData> listHeader(String tb){
        if(isBigdata){
            return cdrDataMapper.listColumns(tb);
        }
        return null;
    }

}
