package com.lig.controller;

import com.lig.common.mapper.ResponsePageData;
import com.lig.common.pojo.CdrEntity;
import com.lig.common.pojo.HeaderData;
import com.lig.service.CdrSearchService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author：ligen
 * @Date: Created:16:34  2019/11/1
 * @Description: CDR查询
 **/
@Controller
@RequestMapping("/cdr")
public class CdrController {

    @Autowired
    private CdrSearchService cdrSearchService;

    @RequestMapping("left")
    @ResponseBody
    public List<CdrEntity.CdrTree> left(){
        return cdrSearchService.listTree();
    }

    @RequestMapping("columns/{tb}")
    @ResponseBody
    public List<CdrEntity> columns(@PathVariable("tb") String tb){
        return cdrSearchService.listColumnsByTb(tb);
    }

    @PostMapping("data")
    @ResponseBody
    public ResponsePageData listData(@RequestParam() String tb,
                                  @RequestBody() Map<String,String> filter,
                                  @RequestParam(value = "offset",defaultValue = "0") int offset,
                                  @RequestParam(value = "limit",defaultValue = "10") int limit){
        StringBuilder sb = new StringBuilder();
        if(filter!=null && !filter.isEmpty()){
            Set<String> keySet = filter.keySet();
            String[] keyArray = keySet.toArray(new String[keySet.size()]);
            Arrays.sort(keyArray);
            for (int i = 0; i < keyArray.length; i++) {
                if (filter.get(keyArray[i]).length()> 0) {
                    sb.append(keyArray[i]).append(" like ").append("'%"+filter.get(keyArray[i])+"%'");
                }
                if(i != keyArray.length-1){
                    sb.append(" and ");
                }
            }
        }
        Page<HashMap>  page = (Page<HashMap>)cdrSearchService.listPageData(tb,sb.toString(),offset,limit);
        return new ResponsePageData(page.getTotal(),page);
    }

    @RequestMapping("header")
    @ResponseBody
    public List<HeaderData> listHeader(@RequestParam() String tb){
        return cdrSearchService.listHeader(tb);
    }
}
