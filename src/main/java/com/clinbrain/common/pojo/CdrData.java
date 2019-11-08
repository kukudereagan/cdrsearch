package com.clinbrain.common.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author：ligen
 * @Date: Created:16:07  2019/11/4
 * @Description:
 **/
@Data
public class CdrData {
    List<HeaderData> headerData;
    Map tableData;
}
