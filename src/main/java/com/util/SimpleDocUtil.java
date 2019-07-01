package com.util;


import com.model.Constant;
import com.model.Index;
import com.model.IndexConstant;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;


/**
 * Created by Wuhaiyang on 2016/7/26.
 *简单的word操作
 */
@Component
public class SimpleDocUtil {
    private static final Logger logger = LoggerFactory.getLogger(SimpleDocUtil.class);

    /**
     * 将表信息输出到word
     * @param doc
     * @param allAttribute
     * @param key
     * @param chineseName
     * @return
     */
    public XWPFTable setTableToWord(XWPFDocument doc, Map<String ,List<String>>  allAttribute , List<String> key, Map<String ,String> chineseName){
        Object[] attrs= allAttribute.keySet().toArray();
        XWPFTable table = doc.createTable(attrs.length, 8);
        setTableAttr(table);
        List<XWPFTableRow> rows= table.getRows();

        for(int i=0;i<rows.size();i++){
            List<XWPFTableCell> tableCells = rows.get(i).getTableCells();

            List<String> zdAttr=allAttribute.get(attrs[i]);

            tableCells.get(0).setText(attrs[i].toString());

            tableCells.get(1).setText(chineseName.get(attrs[i].toString()));

            tableCells.get(2).setText(zdAttr.get(0));

            if(!(zdAttr.get(0).equals("NUMBER")||zdAttr.get(0).equals("DATE"))){
                tableCells.get(3).setText(zdAttr.get(1));
            }

            if(key.contains(attrs[i].toString())){
                tableCells.get(4).setText("是");
            }

            if("Y".equals(zdAttr.get(2))){
                tableCells.get(5).setText("是");
            }
            tableCells.get(6).setText("");
            tableCells.get(7).setText("");

        }
        return table;
    }

    /**
     * 将索引信息输出到表中
     * @param doc
     * @param indexList
     * @return
     */
    public XWPFTable setTableIndexToword(XWPFDocument doc, List<Index> indexList){
        // Object[] attrs= allAttribute.keySet().toArray();
        XWPFTable table = doc.createTable(indexList.size(), 6);
        if(indexList.size()>0){
            setTableAttr(table);
            List<XWPFTableRow> rows= table.getRows();
            for(int i=0;i<rows.size();i++){
                List<XWPFTableCell> tableCells = rows.get(i).getTableCells();
                tableCells.get(0).setText(indexList.get(i).getIndexName());
                tableCells.get(1).setText(indexList.get(i).getIndeType());
                tableCells.get(2).setText(indexList.get(i).getTabelName());
                tableCells.get(3).setText(indexList.get(i).getConstraintName());
                tableCells.get(4).setText(indexList.get(i).getColumnName());
                tableCells.get(5).setText(indexList.get(i).getUniqueness());
            //    tableCells.get(6).setText(indexList.get(i).getTableOwner());

            }

        }

        return table;
    }

    /**
     * 表头信息
     * @param doc
     * @return
     */
    public XWPFTable headerOfTable(XWPFDocument doc){
        XWPFTable table = doc.createTable(1, 8);

        setTableAttr(table);
        List<XWPFTableRow> rows= table.getRows();

        List<String> list= Constant.getAllConstant();
        for(int i=0;i<rows.size();i++){
            List<XWPFTableCell> tableCells = rows.get(i).getTableCells();
            for(int j=0;j<tableCells.size();j++){
                tableCells.get(j).setText(list.get(j));
                tableCells.get(j).setColor("4682B4");
            }
        }
        return table;
    }

    /**
     * 索引表头信息
     * @param doc
     * @return
     */
    public XWPFTable indexheaderOfTable(XWPFDocument doc){
        XWPFTable table = doc.createTable(1, 6);

        setTableAttr(table);
        List<XWPFTableRow> rows= table.getRows();

        List<String> list= IndexConstant.getAllIndexConstant();
        for(int i=0;i<rows.size();i++){
            List<XWPFTableCell> tableCells = rows.get(i).getTableCells();
            for(int j=0;j<tableCells.size();j++){
                tableCells.get(j).setText(list.get(j));
                tableCells.get(j).setColor("838B8B");
            }
        }
        return table;
    }


    /**
     *
     *
     * @param doc
     * @param chineseName
     * @return
     */
    public XWPFTable topTableRow(XWPFDocument doc, Map<String ,String> chineseName){
        XWPFTable table = doc.createTable(1, 2);
        setTableAttr(table);
        Object[] key=  chineseName.keySet().toArray();
        List<XWPFTableRow> rows= table.getRows();
        for(int i=0;i<rows.size();i++){
            List<XWPFTableCell> tableCells = rows.get(i).getTableCells();
            for(int j=0;j<tableCells.size();j++){
                if(j==0){
                    tableCells.get(j).setText(key[i].toString());
                }else{
                    tableCells.get(j).setText(chineseName.get(key[i].toString()));
                }
                tableCells.get(j).setColor("4F94CD");
            }
        }
        return table;
    }
    /**
     * 一级标题信息
     * 如 一 XXXX----xXXXX表
     * @return
     */
    public XWPFParagraph topTitle(XWPFDocument doc, Map<String ,String> chineseName , int order){
        Object[] key=  chineseName.keySet().toArray();
        XWPFParagraph para = doc.createParagraph();
        //
        XWPFRun run = para.createRun();
        run.setFontSize(16);
        run.setBold(true);
        para.setStyle("1");
        run.setColor("0A0A0A");
        String str= "2.1."+String.valueOf(order+1)+"\t"+chineseName.get(key[0]);
        run.setText(str);
        return para;
    }
    /**
     * 标题信息
     * 如1.1 XXXXXX 表结构信息
     * @return
     */
    public XWPFParagraph titleTable(XWPFDocument doc, Map<String ,String> chineseName , int order){
        Object[] key=  chineseName.keySet().toArray();
        XWPFParagraph para = doc.createParagraph();
        XWPFRun run = para.createRun();
        run.setFontSize(11);
        run.setFontFamily("宋体");
        run.setBold(true);
        para.setStyle("2");
        run.setColor("0A0A0A");
        String str="2.1"+"."+String.valueOf(order+1)+"\t"+chineseName.get(key[1].toString())+"  "+chineseName.get(key[0].toString());
        run.setText(str);
        return para;
    }
    /**
     * 标题信息
     * 如1.1 XXXXXX 表结构信息
     * @return
     */
    public XWPFParagraph titleTable(XWPFDocument doc, Map<String ,String> chineseName , int order, int order2){
        Object[] key=  chineseName.keySet().toArray();
        XWPFParagraph para = doc.createParagraph();
        XWPFRun run = para.createRun();
        run.setFontSize(11);
        run.setFontFamily("宋体");
        run.setBold(true);
        para.setStyle("2");
        run.setColor("0A0A0A");
        String str="2.1"+"."+String.valueOf(order+1)+"."+String.valueOf(order2+1)+" "+chineseName.get(key[1].toString())+"  "+chineseName.get(key[0].toString());
        run.setText(str);
        return para;
    }

    /**
     *
     * 1.2 XXX表索引信息
     * @param doc
     * @param tableName
     * @param order
     * @return
     */
    public XWPFParagraph indexTitleTable(XWPFDocument doc, String tableName , int order){
        //  Object[] key=  chineseName.keySet().toArray();
        XWPFParagraph para = doc.createParagraph();
        //
        XWPFRun run = para.createRun();
        run.setFontSize(12);
        run.setBold(true); //???
        para.setStyle("2");

        run.setColor("0D0D0D");
        String str=String.valueOf(order+1)+".2    "+tableName+"表索引信息";
        run.setText(str);
        return para;
    }

    /**
     * 表宽度
     * @param table
     */
    public  void setTableAttr(XWPFTable table){
        CTTblPr tblPr=table.getCTTbl().getTblPr();
        tblPr.getTblW().setType(STTblWidth.DXA);
        tblPr.getTblW().setW(new BigInteger("8000"));
    }
    /**
     * 换行
     * @param doc
     */
    public XWPFParagraph addParagraph(XWPFDocument doc){
        XWPFParagraph para = doc.createParagraph();
        XWPFRun run = para.createRun();
        run.setBold(true); //???
        run.setText("\r\r\r\r");
        return para;

    }

    /**
     * 总标题
     * @param doc
     * @return
     */
    public XWPFTable Top(XWPFDocument doc, String tablename){
        XWPFTable table = doc.createTable(1, 1);
        setTableAttr(table);
        List<XWPFTableRow> rows= table.getRows();
        for(int i=0;i<rows.size();i++){
            List<XWPFTableCell> tableCells = rows.get(i).getTableCells();
            for(int j=0;j<tableCells.size();j++){
                XWPFParagraph para = doc.createParagraph();
                XWPFRun run = para.createRun();
                run.setBold(true); //???
                run.setFontSize(15);
                run.setText("相关"+tablename+"所有表信息");
                run.setColor("050505");
                tableCells.get(j).setParagraph(para);
            }
        }
        return table;
    }


    /**
     *写入word
     * @param doc
     * @throws IOException
     */
    public  void writeToWord(XWPFDocument doc, String path,String tableName) throws IOException {
       FileOutputStream out = new FileOutputStream(path);
        doc.write(out);
        out.close();
        logger.info("success to {}--->{}",path,tableName);
    }

}
