package com.Service;

import com.model.Index;
import com.model.PageModel;
import com.util.JdbcOracleUtil;
import com.util.SimpleDocUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/24.
 */

@Service("flowService")
public class FlowService {

    private static final Logger logger = LoggerFactory.getLogger(FlowService.class);

    /**
     * 文件保存的路径
     **/
    public static String PATH = "F:\\workspace\\exportoracle\\src\\main\\resources\\exportfile";

    @Autowired
    private JdbcOracleUtil jdbcOracleUtil;

    @Autowired
    private SimpleDocUtil simpleDoc;

    private XWPFDocument document ;


    /**
     * 入口
     *
     * @param pageModel
     */
    public String operater(PageModel pageModel) {
        document = new XWPFDocument();
        String path1 = allTableToWord(pageModel);
        return path1;
    }

    /**
     * 根据不同的类型进行进行选择
     *
     * @param pageModel
     */
    private String allTableToWord(PageModel pageModel) {

        String str = "";
        if (pageModel.getTablesLike() == null) {
            if (null != pageModel.getTableName()) {
                str = printSingle(pageModel);
            }
        } else {
            str = printAll(pageModel);
        }
        return str;
    }

    /**
     * 打印单个表
     *
     * @param pageModel
     * @return
     */
    private String printSingle(PageModel pageModel) {

        List<String> tables = jdbcOracleUtil.getAllTables(pageModel.getTableName());
        String path = "";
        if (tables == null || tables.isEmpty()) {
            logger.info("未查询到表信息");
            return path;
        }
        if (pageModel.getWithIndex() == 1) {
            path = PATH + "\\\\" + pageModel.getTableName() + "_table_hasindex.docx";
            for (int i = 0; i < tables.size(); i++) {
                String tableName = tables.get(i);
                try {
                    oracleToWord0(tableName, path, i);
                    oracleIndexToWord(tableName, path, i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            path = PATH + "\\\\" + pageModel.getTableName() + "_table_noindex.docx";

            for (int i = 0; i < tables.size(); i++) {
                String tableName = tables.get(i);
                try {
                    oracleToWord0(tableName, path, i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("EXPORT----OVER   tablename={}", pageModel.getTableName());
        return path;
    }

    /**
     * 所有表
     *
     * @param pageModel
     * @return
     */
    private String printAll(PageModel pageModel) {
        document.setTable(0, simpleDoc.Top(document, pageModel.getTableName()));
        List<String> all = pageModel.getTablesLike();
        String path = "";
        for (int j = 0; j < all.size(); j++) {
            List<String> tables = jdbcOracleUtil.getAllTables(all.get(j));
            path = PATH + "\\\\" + "alltable.docx";
            document.setParagraph(simpleDoc.topTitle(document, new HashMap<String, String>(), j), 0);

            for (int i = 0; i < tables.size(); i++) {
                String tableName = tables.get(i);
                try {
                   oracleToWord(tableName, path, j, i);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            logger.info("over for--->{}", all.get(j));
        }
        return path;
    }


    /**
     * @param tableName
     * @param path
     * @param order
     * @throws IOException
     */
    private void oracleToWord0(String tableName, String path, int order) throws IOException {
        //获取表名对应中文名
        Map<String, String> tablename = jdbcOracleUtil.getTableName(tableName);
        //字段名对应中文名
        Map<String, String> chineseName = jdbcOracleUtil.getChineseName(tableName);
        //返回指定表的所有主键，没有为空
        List<String> key = jdbcOracleUtil.getTableKey(tableName);
        //获取指定表的所有属性
        Map<String, List<String>> allAttribute = jdbcOracleUtil.getOracleColumn_info(tableName);
        //一级标题
        document.setParagraph(simpleDoc.topTitle(document, tablename, order), 0);
        //标题表名字和中文名字
        document.setParagraph(simpleDoc.titleTable(document, tablename, order), 0);

        //创建表的第二行并合并
        document.insertTable(0, simpleDoc.headerOfTable(document));
        //将总的表插入
        document.insertTable(0, simpleDoc.setTableToWord(document, allAttribute, key, chineseName));
        //插入空行
        document.setParagraph(simpleDoc.addParagraph(document), 0);
        simpleDoc.writeToWord(document, path,tableName);


    }

    /**
     * @param tableName
     * @param path
     * @param order
     * @throws IOException
     */
    private void oracleToWord(String tableName, String path, int order, int order2) throws IOException {
        //获取表名对应中文名
        Map<String, String> tablename = jdbcOracleUtil.getTableName(tableName);
        //字段名对应中文名
        Map<String, String> chineseName = jdbcOracleUtil.getChineseName(tableName);
        //返回指定表的所有主键，没有为空
        List<String> key = jdbcOracleUtil.getTableKey(tableName);
        //获取指定表的所有属性
        Map<String, List<String>> allAttribute = jdbcOracleUtil.getOracleColumn_info(tableName);
        //一级标题
        //   document.setParagraph(simpleDoc.topTitle(document,tablename,order),0);
        //标题表名字和中文名字
        document.setParagraph(simpleDoc.titleTable(document, tablename, order, order2), 0);

        //创建表的第二行并合并
        document.insertTable(0, simpleDoc.headerOfTable(document));
        //将总的表插入
        document.insertTable(0, simpleDoc.setTableToWord(document, allAttribute, key, chineseName));
        //插入空行
        document.setParagraph(simpleDoc.addParagraph(document), 0);
        simpleDoc.writeToWord(document, path,tableName);


    }

    /**
     * 为获取每个表的索引表
     *
     * @param tableName
     * @param path
     * @param order
     */
    private void oracleIndexToWord(String tableName, String path, int order) throws IOException {
        //标题信息
        document.setParagraph(simpleDoc.indexTitleTable(document, tableName, order), 0);
        //表头信息
        document.insertTable(0, simpleDoc.indexheaderOfTable(document));
        //获取数据并将 数据插入
        List<Index> indexList = jdbcOracleUtil.getAllIndexInfo(tableName);
        document.insertTable(0, simpleDoc.setTableIndexToword(document, indexList));
        //插入空行
        document.setParagraph(simpleDoc.addParagraph(document), 0);
        simpleDoc.writeToWord(document, path,tableName);

    }

}
