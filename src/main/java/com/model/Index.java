package com.model;

/**
 * Created by wuhaiyang on 2016/9/3.
 * ORACLE数据库的部分索引信息
 */
public class Index {
    private String indexName;
    private String indeType;
    private String tabelName;
    private String constraintName;
    private String columnName;
    private String uniqueness;
    private String tableOwner;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndeType() {
        return indeType;
    }

    public void setIndeType(String indeType) {
        this.indeType = indeType;
    }

    public String getTabelName() {
        return tabelName;
    }

    public void setTabelName(String tabelName) {
        this.tabelName = tabelName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getUniqueness() {
        return uniqueness;
    }

    public void setUniqueness(String uniqueness) {
        this.uniqueness = uniqueness;
    }

    public String getTableOwner() {
        return tableOwner;
    }

    public void setTableOwner(String tableOwner) {
        this.tableOwner = tableOwner;
    }
}
