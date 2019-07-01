package com.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class PageModel {

    private String tableName;

    private int withIndex;

    private String tablePath;

    // 多个表like
    private List<String>  tablesLike;


    public PageModel(){}


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getWithIndex() {
        return withIndex;
    }

    public void setWithIndex(int withIndex) {
        this.withIndex = withIndex;
    }

    public String getTablePath() {
        return this.tableName+"_"+String.format("%tF", new Date())+".docx";
    }

    public void setTablePath(String tablePath) {
        this.tablePath = tablePath;
    }

    public List<String> getTablesLike() {
        return tablesLike;
    }

    public void setTablesLike(List<String> tablesLike) {
        this.tablesLike = tablesLike;
    }
}
