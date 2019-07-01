package com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhaiyang on 2016/9/3.
 * Ϊ��ͷ��׼��
 */
public class IndexConstant {
    private static final String indexName="INDEX_NAME";
    private static final String indeType="INDEX_TYPE";
    private static final String tabelName="TABLE_NAME";
    private static final String constraintName="CONSTRAINT_NAME";
    private static final String columnName="COLUMN_NAME";
    private static final String uniqueness="UNIQUENESS";
 //   private static final String tableOwner="TABLE_OWNER";

    /**
     * ��ͷ��Ϣ
     * @return
     */
    public static List<String> getAllIndexConstant(){
        List<String> list=new ArrayList<String>();
        list.add(0, IndexConstant.indexName);
        list.add(1, IndexConstant.indeType);
        list.add(2, IndexConstant.tabelName);
        list.add(3, IndexConstant.constraintName);
        list.add(4, IndexConstant.columnName);
        list.add(5, IndexConstant.uniqueness);
       // list.add(6,IndexConstant.tableOwner);
        return list;
    }
}
