package com.zc.orm;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author zhangchao
 * @Title: JDBCTest
 * @ProjectName info-price
 * @Description: TODO
 * @date 2019/4/22/02215:52
 */
public class JDBCTest {

    public static void main(String[] args) {
        Member member = new Member();
        member.setAdNumber("mobile_home");
        List<?> result =  select(member);
        System.out.println(Arrays.toString(result.toArray()));
    }
    private Member member;

    private static List<?> select(Object condition) {
        List<Object> result = new ArrayList<Object>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Class<?> entityClass = condition.getClass();
        try {
            //1、设置驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //2、建立连接
            con = DriverManager.getConnection("jdbc:mysql://192.168.0.109:3306/itm_dev?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8", "itm", "itm");

            //根据类名找属性名
            Map<String,String> columnMapper = new HashMap<String,String>();
            //根据属性名找字段名
            Map<String,String> fieldMapper = new HashMap<String,String>();
            Field[] fields =  entityClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if(field.isAnnotationPresent(Column.class)){
                    Column column = field.getAnnotation(Column.class);
                    String columnName = column.name();
                    columnMapper.put(columnName,fieldName);
                    fieldMapper.put(fieldName,columnName);
                }else {
                    //默认就是字段名属性名一致
                    columnMapper.put(fieldName, fieldName);
                    fieldMapper.put(fieldName,fieldName);
                }
            }

            //3、创建语句集
            Table table = entityClass.getAnnotation(Table.class);
            String sql = "select * from "+ table.name();
            StringBuffer where = new StringBuffer(" where 1=1 ");
            for (Field field : fields) {
                Object value =field.get(condition);
                if(null != value&&isTrue(value)){
                    where.append(" and " + fieldMapper.get(field.getName()) + " = '" + value + "'");
                }
            }
            System.out.println(sql + where.toString());
            pstm =  con.prepareStatement(sql + where.toString());
            //4、执行语句
            rs = pstm.executeQuery();

            //5、获取结果集
            int columnCounts = rs.getMetaData().getColumnCount();
            while (rs.next()){
                Object instance = entityClass.newInstance();
                for (int i = 1; i <= columnCounts; i++) {
                    //从rs中取得当前这个游标下的类名
                    String columnName = rs.getMetaData().getColumnName(i);
                    try{
                        Field field = entityClass.getDeclaredField(columnMapper.get(columnName));
                        field.setAccessible(true);
                        field.set(instance,rs.getObject(columnName));
                    }catch(NullPointerException e){
                        System.out.println("没有的字段跳过");
                    }
                }
                result.add(instance);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //6、关闭
        finally {
            try {
                rs.close();
                pstm.close();
                con.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }

    public static boolean isTrue(Object object){
        if(object instanceof Integer&&object.equals(0)){
            return false;
        }if (object instanceof String&&object.equals("")){
            return  false;
        }
        return true;
    }
}
