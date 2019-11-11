package com.blog.utils;

import com.blog.model.Role;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class FilePortUtil {
    private static final Logger log = LoggerFactory.getLogger(FilePortUtil.class);



    /**
     * * 导出功能
     * * 注意：泛型T类字段名和containBean集合里字段名字的一致性
     * *     * @param response
     * * @param title       表名
     * * @param headers     表头
     * * @param list        数据集
     * * @param containBean 数据集类型字段
     * * @param <T>
     *     * @throws Exception
     * */

    public static <T> void exportExcel(HttpServletResponse response, String title, String[] headers, List<T> list, List<String> containBean) throws Exception {
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title);
            HSSFRow row = sheet.createRow(0);
            /*创建第一行表头*/
            for (short i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            Iterator<T> it = list.iterator();
            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                T t = (T) it.next();
                /*反射得到字段*/
                Field[] fields = t.getClass().getDeclaredFields();
                /*如果需要匹配*/
                if (CollectionUtils.isNotEmpty(containBean)) {
                    for (int j = 0; j < containBean.size(); j++) {
                        for (int i = 0; i < fields.length; i++) {
                            Field field = fields[i];
                            if (!field.getName().equals(containBean.get(j)))
                                continue;
                            /*给每一列set值*/
                            setCellValue(t, field, row, j);
                        }
                    }
                } else {
                    for (int i = 0; i < fields.length; i++) {
                        Field field = fields[i];
                        setCellValue(t, field, row, i);
                    }
                }
            }
            /*application/vnd.ms-excel告诉浏览器要下载的是个excel*/
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            /*请求头设置，Content-Disposition为下载标识，attachment标识以附件方式下载*/
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((title).getBytes(), "ISO8859-1") + ".xls");
            workbook.write(response.getOutputStream());
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    /**
     * 设置每一行中的列
     *
     * @param t
     * @param field
     * @param row
     * @param index
     * @param <T>
     */
    private static<T> void setCellValue(T  t, Field field, HSSFRow row, int index) {
        HSSFCell cell = row.createCell(index);
        Object value = invoke(t, field);
        String textValue = null;
        if (value != null) {
            if (value instanceof Date) {
                Date date = (Date) value;
                textValue = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
            } else {
                textValue = value.toString();
            }
        }
        if (textValue != null) {
            cell.setCellValue(textValue);
        }
    }

    /**
      * 反射映射数据集字段
     ** @param t
     * * @param field
     * * @param <T>
     * @return
     * */
    private static <T> Object invoke(T t, Field field) {
        try {
            String fieldName = field.getName();
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, t.getClass());
            Method method = pd.getReadMethod();
            return method.invoke(t);
        } catch (Exception e) {
            return null;
        }
    }

}
