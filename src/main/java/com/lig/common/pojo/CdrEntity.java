package com.lig.common.pojo;

import lombok.Data;

import java.util.List;

/**
 * @Author：ligen
 * @Date: Created:11:00  2019/11/1
 * @Description:
 **/
@Data
public class CdrEntity {

    private String name;

    @Data
    public static class CdrTree{
        private String name;
        private List<CdrEntity> children;
    }



}
