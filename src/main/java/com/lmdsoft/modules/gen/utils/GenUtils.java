package com.lmdsoft.modules.gen.utils;

import com.lmdsoft.modules.common.common.Constant;
import com.lmdsoft.modules.common.common.RRException;
import com.lmdsoft.modules.common.utils.DateUtils;
import com.lmdsoft.modules.common.utils.Utils;
import com.lmdsoft.modules.gen.entity.ColumnEntity;
import com.lmdsoft.modules.gen.entity.TableEntity;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
//import org.apache.velocity.Template;
//import org.apache.velocity.VelocityContext;
//import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 类GenUtils的功能描述:
 * 代码生成器   工具类
 * @Auther lmdsoft
 * @Date 2018-08-25 16:12:47
 */
public class GenUtils {
//	//配置信息
    private final static Configuration config = getConfig();

    private static Logger log = LoggerFactory.getLogger(GenUtils.class);

    public static List<String> getTemplates(){
        List<String> templates = new ArrayList<String>();
        templates.add("Entity.java.ftl");
        templates.add("Dao.java.ftl");
        templates.add("Dao.xml.ftl");
        templates.add("Service.java.ftl");
        templates.add("ServiceImpl.java.ftl");
        templates.add("Controller.java.ftl");
        templates.add("list.html.ftl");
        templates.add("list.js.ftl");
        templates.add("menu.sql.ftl");
        return templates;
    }


	/**
	 * 生成代码
	 * @param table
	 * @param columns
	 * @param zip
	 * @param genType 生成方式
	 */
	public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip, int genType){
		//配置信息
		//Configuration config = getConfig();

		//表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		//表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
		tableEntity.setClassName(className);
		tableEntity.setClassname(StringUtils.uncapitalize(className));

		//列信息
		List<ColumnEntity> columsList = new ArrayList<ColumnEntity>();
		for(Map<String, String> column : columns){
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));

			//列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

			//列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);

			//是否主键
			if("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null){
				tableEntity.setPk(columnEntity);
			}

			columsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);

		//没主键，则第一个字段为主键
		if(tableEntity.getPk() == null){
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}

		//设置velocity资源加载器
//		Properties prop = new Properties();
//		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//		Velocity.init(prop);

		//封装模板数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", Utils.uuid());
		map.put("rid", Utils.uuid());
		map.put("cid", Utils.uuid());
		map.put("uid", Utils.uuid());
		map.put("did", Utils.uuid());
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("pathName", tableEntity.getClassname().toLowerCase());
		map.put("columns", tableEntity.getColumns());
		map.put("package", config.getString("package"));
		map.put("author", config.getString("author"));
		map.put("module", config.getString("module"));
		map.put("email", config.getString("email"));
		map.put("baseDao", config.getString("baseDao"));
		map.put("utils", config.getString("utils"));
		map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
       // VelocityContext context = new VelocityContext(map);

        //获取模板列表
		List<String> templates = getTemplates();
		for(String template : templates){
			//渲染模板
            Template tpl = null;
            try {
                tpl = FreeMarkerTemplateUtils.getTemplate(template);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //本地项目生成
			if(genType == Constant.genType.local.getValue()){
                local(template,tableEntity,tpl,map);
			}
			//web页面生成
			if(genType == Constant.genType.webDown.getValue()){
                try {
                    webDown(zip,template,tableEntity,tpl,map);
                } catch (IOException e) {
                    throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
                }
            }
		}
	}

	/**
	 * 方法webDown的功能描述:
	 * 在web页面中生成代码，并打包zip文件下载
	 * @params [zip, template, tableEntity, tpl, context]
	 * @return void
	 * @Auther lmdsoft
	 * @Date 2018-06-28 16:24:33
	 */
	public static void webDown(ZipOutputStream zip, String template, TableEntity tableEntity, Template tpl, Map context) throws IOException {
		StringWriter sw = new StringWriter();
        try {
            tpl.process(context, sw);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        //添加到zip
		zip.putNextEntry(new ZipEntry(getWebFileName(template, tableEntity.getClassName(), config.getString("package"))));
		IOUtils.write(sw.toString(), zip, "UTF-8");
		IOUtils.closeQuietly(sw);
		zip.closeEntry();
	}

	/**
	 * 在本地工程中生成代码
	 * @param template
	 * @param tableEntity
	 * @param tpl
	 * @param context
	 */
	public static void local(String template, TableEntity tableEntity, Template tpl, Map context){
		try {
			String filePath=getLocalFileName(template, tableEntity.getClassName(), "");
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fos=new FileOutputStream(filePath);
			BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(
					fos,"UTF-8"));
			tpl.process(context, writer);
			log.info("生成文件"+tableEntity.getClassName()+"生成成功！");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
	}

	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if(StringUtils.isNotBlank(tablePrefix)){
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}

	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig(){
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new RRException("获取配置文件失败，", e);
		}
	}


    /**
     * 获取文件名 本地项目中生成
     */
    public static String getLocalFileName(String template, String className, String packageName){

        if(template.contains("Entity.java.ftl")){
            return config.getString("entity") + File.separator + className + "Entity.java";
        }

        if(template.contains("Dao.java.ftl")){
            return config.getString("dao") + File.separator + className + "Dao.java";
        }

        if(template.contains("Dao.xml.ftl")){
            return config.getString("mapping") + File.separator + className + "Mapper.xml";
        }

        if(template.contains("Service.java.ftl")){
            return config.getString("service") + File.separator + className + "Service.java";
        }

        if(template.contains("ServiceImpl.java.ftl")){
            return config.getString("serviceImpl") + File.separator+className+ "ServiceImpl.java";
        }

        if(template.contains("Controller.java.ftl")){
            return config.getString("controller") + File.separator+className+ "Controller.java";
        }

        if(template.contains("list.html.ftl")){
            return config.getString("view")+ File.separator + className.toLowerCase() + ".ftl";
        }

        if(template.contains("list.js.ftl")){
            return config.getString("js") + File.separator + className.toLowerCase() + ".js";
        }
        if(template.contains("menu.sql.ftl")){
            return config.getString("sql") + File.separator + className.toLowerCase() + "_menu.sql";
        }
        return null;
    }
    /**
     * 获取文件名 web中生成代码使用
     */
    public static String getWebFileName(String template, String className, String packageName){
        String packagePath = "main" + File.separator + "java" + File.separator;
        if(StringUtils.isNotBlank(packageName)){
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if(template.contains("Entity.java.ftl")){
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if(template.contains("Dao.java.ftl")){
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if(template.contains("Dao.xml.ftl")){
            return packagePath + "dao" + File.separator + className + "Dao.xml";
        }

        if(template.contains("Service.java.ftl")){
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if(template.contains("ServiceImpl.java.ftl")){
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if(template.contains("Controller.java.ftl")){
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if(template.contains("list.html.ftl")){
            return "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "page"
                    + File.separator + "generator" + File.separator + className.toLowerCase() + ".ftl";
        }

        if(template.contains("list.js.ftl")){
            return "main" + File.separator + "webapp" + File.separator + "js" + File.separator + "generator" + File.separator + className.toLowerCase() + ".js";
        }

        if(template.contains("menu.sql.ftl")){
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }
}
