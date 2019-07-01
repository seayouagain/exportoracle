package com.util;

import com.model.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.*;

/**
 * Created by Administrator on 2016/9/24.
 */
@Repository
@Transactional
public class JdbcOracleUtil {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /***
     * 获取oracle数据字段信息(字段名，字段类型，字段长度，数字精度，是否为空)
     *
     * @param table  表名
     * @return
     */
    public Map<String, List<String>> getOracleColumn_info(String table) {
        String sql = "select COLUMN_NAME,DATA_TYPE,DATA_LENGTH,DATA_PRECISION,DATA_SCALE,NULLABLE from user_tab_columns where table_name =UPPER('" + table + "')";
        Map<String, List<String>> map = new ManagedMap<String, List<String>>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        Iterator<Map<String, Object>> iterator = maps.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> next = iterator.next();
            List<String> val = new ArrayList<String>();
            String key = (String) next.get("COLUMN_NAME");  //获取字段名
            val.add((String) next.get("DATA_TYPE"));  //获取数据类型
            val.add(String.valueOf(next.get("DATA_LENGTH")));  //获取数据长度
            //  val.add(rs.getString("DATA_PRECISION"));  //获取数据长度
            //  val.add(rs.getString("DATA_SCALE"));  //获取数据精度
            val.add((String) next.get("NULLABLE"));  //获取是否为空
            map.put(key, val);
        }
        return map;
    }

    /**
     * 获取指定表的主键
     *
     * @param table
     * @return
     */
    public List<String> getTableKey(String table) {
        List<String> result = new ArrayList<String>();
        ResultSet set = null;
        DatabaseMetaData metadata = null;
        Connection connection = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            metadata = connection.getMetaData();
            set = metadata.getPrimaryKeys(null, null, table);

            while (set.next()) {
                /**
                 * 获取主键信息
                 */
                result.add(set.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                JdbcUtils.closeResultSet(set);
                JdbcUtils.closeConnection(connection);
        }

        return result;
    }

    /**
     * 获取字段对应的中文解释   键-值对形式存储
     *
     * @param table
     * @return
     */
    public Map<String, String> getChineseName(String table) {
        String sql = "select * from user_col_comments where table_name=UPPER('" + table + "')";
        List<Map<String, Object>> query = jdbcTemplate.query(sql, new RowMapper<Map<String, Object>>() {
            public Map<String, Object> mapRow(ResultSet resultSet, int i) throws SQLException {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(resultSet.getString("COLUMN_NAME"), resultSet.getString("COMMENTS"));
                return map;
            }
        });
        Map<String, String> chineseNameObject = new HashMap<String, String>();
        for (Map<String, Object> m : query) {
            Iterator<Map.Entry<String, Object>> iterator = m.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                chineseNameObject.put(next.getKey(), (String) next.getValue());
            }

        }
        return chineseNameObject;
    }

    /**
     * 查询表的中文名称
     *
     * @param table
     * @return
     */
    public Map<String, String> getTableName(String table) {
        String sql = "select * from user_tab_comments where table_name=UPPER('" + table + "') ";
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap(sql);
        Iterator<Map.Entry<String, Object>> iterator = stringObjectMap.entrySet().iterator();
        Map<String, String> res = new HashMap<String, String>();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            if (!"TABLE_NAME".equals(next.getKey()) && !"COMMENTS".equals(next.getKey())) {

                iterator.remove();
                stringObjectMap.remove(next.getKey());
            } else {
                res.put(next.getKey(), (String) next.getValue());
            }
        }
        return res;
    }

    /**
     * 从数据库中获取指定条件的表名
     *
     * @param table
     * @return
     */
    public List<String> getAllTables(String table) {
        String table1 = table + "%";
        String sql = "select table_name from user_tables where table_name like UPPER('" + table1 + "')";
        List<String> tables = jdbcTemplate.query(sql, new RowMapper<String>() {
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                String string = resultSet.getString("TABLE_NAME");
                return string;
            }
        });
        return tables;


    }

    /**
     * 获取指定表的所有的索引信息
     *
     * @param table
     * @return
     */
    public List<Index> getAllIndexInfo(String table) {
        String sql = "SELECT u.INDEX_NAME,u.INDEX_TYPE,s.TABLE_NAME,s.CONSTRAINT_NAME,s.COLUMN_NAME,u.UNIQUENESS,u.TABLE_OWNER\n" +
                "FROM SYS.USER_CONS_COLUMNS s\n" +
                "LEFT JOIN SYS.USER_INDEXES u\n" +
                "ON s.TABLE_NAME=u.TABLE_NAME \n" +
                "WHERE u.TABLE_NAME=UPPER('" + table + "') ";

        List<Index> indexList = jdbcTemplate.query(sql, new RowMapper<Index>() {
            public Index mapRow(ResultSet resultSet, int i) throws SQLException {
                Index index = new Index();
                index.setIndexName(resultSet.getString("INDEX_NAME"));
                index.setIndeType(resultSet.getString("INDEX_TYPE"));
                index.setTabelName(resultSet.getString("TABLE_NAME"));
                index.setConstraintName(resultSet.getString("CONSTRAINT_NAME"));
                index.setColumnName(resultSet.getString("COLUMN_NAME"));
                index.setUniqueness(resultSet.getString("UNIQUENESS"));
                index.setTableOwner(resultSet.getString("TABLE_OWNER"));
                return index;
            }
        });


        return indexList;
    }
}
