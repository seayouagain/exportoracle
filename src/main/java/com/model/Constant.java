package com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhaiyang on 2016/7/27.
 *
 * 存放常量
 */
public class Constant {
    private static final String ZDID="字段ID";//字段Id
    private static final String ZDZWM="中文名称";//字段中文名
    private static final String LX="数据类型";//字段类型
    private static final String CD="长度";//长度
    private static final String KEY="主键";//是否主键
    private static final String ISNULL="可空";//是否为空
    private static final String ZKJ="值空间";//值空间
    private static final String JSJL="解释/举例";//解释举例

    /**
     * 获取常量参数表
     * @return
     */
    public static List<String> getAllConstant(){
        List<String> list=new ArrayList<String>();
        list.add(0, Constant.ZDID);
        list.add(1, Constant.ZDZWM);
        list.add(2, Constant.LX);
        list.add(3, Constant.CD);
        list.add(4, Constant.KEY);
        list.add(5, Constant.ISNULL);
        list.add(6, Constant.ZKJ);
        list.add(7, Constant.JSJL);
        return list;
    }
}
