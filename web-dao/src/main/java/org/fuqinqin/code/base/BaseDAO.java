package org.fuqinqin.code.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.*;

/**
 * @desc 基础DAO
 * @author fuqq
 * @date 2016-09-19
 * */

public class BaseDAO {
	public static Logger logger = Logger.getLogger(BaseDAO.class);

	private static final String JDBC_DRIVER;
	private static final String JDBC_URL;
	private static final String JDBC_USERNAME;
	private static final String JDBC_PASSWD;

	static {
		ClassLoader classLoader = BaseDAO.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JDBC_DRIVER = properties.getProperty("jdbc.driver");
		JDBC_URL = properties.getProperty("jdbc.url");
		JDBC_USERNAME = properties.getProperty("jdbc.username");
		JDBC_PASSWD = properties.getProperty("jdbc.password");
	}

	private static Connection getConnection(){
		try {
			Class.forName(JDBC_DRIVER);
			return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 增删改
	 * @sql 
	 * @params 参数
	 * @contextH噢older 数据源标识
	 * */
	public int executeUpdate(String sql, Object[] params){
		int rownum = -1;
		Connection conn = null;
		PreparedStatement pstat = null;
		
		if(StringUtils.isBlank(sql)){
			logger.error("executeUpdate 参数为空");
			return rownum;
		} 
		
		try {
			conn = getConnection();
			pstat = conn.prepareStatement(sql);
			if(params!=null && params.length>0){
				for(int i=0;i<params.length;i++){
					pstat.setObject(i+1, params[i]);
				}
			}
			logger.info(sql);
			rownum = pstat.executeUpdate();
		} catch (SQLException e) {
			logger.error("增删改执行失败1");
			e.printStackTrace();
			return rownum;
		} catch (Exception e){
			logger.error("增删改执行失败2");
			e.printStackTrace();
			return rownum;
		} finally {
			this.close(conn, pstat, null);
		}
		
		return rownum;
	}
	
	/**
	 * 查询
	 * */
	public List<Object> executeQuery(String sql, Object[] params, Class cls, String contextHolder){
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet res = null;
		
		if(StringUtils.isBlank(sql) || cls==null){
			logger.error("executeQuery 参数为空");
			return null;
		} 
		
		List<Object> resultList = new ArrayList<Object>();
		
		try {
			conn = getConnection();
			pstat = conn.prepareStatement(sql);
			if(params!=null && params.length>0){
				for(int i=0;i<params.length;i++){
					pstat.setObject(i+1, params[i]);
				}
			}
			logger.info(sql);
			res = pstat.executeQuery();
			if(res != null){
				while(res.next()){
					Object obj = cls.newInstance();
					Field[] fs = cls.getDeclaredFields();
					int finalCnt = 0;
					int staticCnt = 0;
					for(int i=0;i<fs.length;i++){
						Field f = fs[i];
						f.setAccessible(true);
						//避开常量
						if(Modifier.isFinal(f.getModifiers())){
							finalCnt++;
							continue;
						}
						String type = f.getType().getName();
						if(dataTypeMap.get("int").equals(type)){
							f.set(obj, res.getInt(i+1-finalCnt));
						}else if(dataTypeMap.get("string").equals(type)){
							f.set(obj, res.getString(i+1-finalCnt));
						}else if(dataTypeMap.get("long").equals(type)){
							f.set(obj, res.getLong(i+1-finalCnt));
						}else if(dataTypeMap.get("float").equals(type)){
							f.set(obj, res.getFloat(i+1-finalCnt));
						}else if(dataTypeMap.get("double").equals(type)){
							f.set(obj, res.getDouble(i+1-finalCnt));
						}else if(dataTypeMap.get("date").equals(type)){
							java.sql.Date date = res.getDate(i+1-finalCnt);
							if(date!=null){
								f.set(obj, new java.util.Date(date.getTime()));
							}else{
								f.set(obj, null);
							}
						}else if(dataTypeMap.get("timestamp").equals(type)){
							f.set(obj, res.getTimestamp(i+1-finalCnt));
						}else if(dataTypeMap.get("boolean").equals(type)){
							f.set(obj, res.getBoolean(i+1-finalCnt));
						}
					}
					//父类属性
					Field[] pfs = cls.getSuperclass().getDeclaredFields();
					for(int j=fs.length;j<pfs.length+fs.length;j++){
						Field f = pfs[j-fs.length];
						f.setAccessible(true);
						if(Modifier.isStatic(f.getModifiers()) || "logger".equals(f.getName().trim())){
							staticCnt++;
							continue;
						}
						String type = f.getType().getName();
						if(dataTypeMap.get("int").equals(type)){
							f.set(obj, res.getInt(j+1-staticCnt-finalCnt));
						}else if(dataTypeMap.get("string").equals(type)){
							f.set(obj, res.getString(j+1-staticCnt-finalCnt));
						}else if(dataTypeMap.get("long").equals(type)){
							f.set(obj, res.getLong(j+1-staticCnt-finalCnt));
						}else if(dataTypeMap.get("float").equals(type)){
							f.set(obj, res.getFloat(j+1-staticCnt-finalCnt));
						}else if(dataTypeMap.get("double").equals(type)){
							f.set(obj, res.getDouble(j+1-staticCnt-finalCnt));
						}else if(dataTypeMap.get("date").equals(type)){
							java.sql.Date date = res.getDate(j+1-staticCnt-finalCnt);
							if(date!=null){
								f.set(obj, new java.util.Date(date.getTime()));
							}else{
								f.set(obj, null);
							}
						}else if(dataTypeMap.get("timestamp").equals(type)){
							f.set(obj, res.getTimestamp(j+1-staticCnt-finalCnt));
						}else if(dataTypeMap.get("boolean").equals(type)){
							f.set(obj, res.getBoolean(j+1-staticCnt-finalCnt));
						}
					}
					resultList.add(obj);
				}
			}
		} catch (SQLException e) {
			logger.error("查询执行失败");
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			logger.error("根据class反射实例失败");
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			logger.error("根据class反射实例失败");
			e.printStackTrace();
			return null;
		} catch (Exception e){
			logger.error("执行查询失败");
			e.printStackTrace();
			return null;
		} finally {
			this.close(conn, pstat, res);
		}
		
		
		return resultList;
	}
	
	/**
	 * 关闭连接
	 * */
	public void close(Connection conn, PreparedStatement pstat, ResultSet res){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstat != null){
			try {
				pstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(res != null){
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Map<String, String> dataTypeMap = new HashMap<String, String>();
	
	static{
		dataTypeMap.put("int", "int");
		dataTypeMap.put("string", "java.lang.String");
		dataTypeMap.put("long", "long");
		dataTypeMap.put("float", "float");
		dataTypeMap.put("double", "double");
		dataTypeMap.put("date", "java.util.Date");
		dataTypeMap.put("timestamp", "java.security.Timestamp");
		dataTypeMap.put("boolean", "boolean");
	}

}
