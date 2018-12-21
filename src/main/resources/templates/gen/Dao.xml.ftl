<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${package}.dao.${className}Dao">


    <resultMap type="${package}.entity.${className}Entity" id="${classname}Map">
		<#list  columns as column>
        <result property="${column.attrname}" column="${column.columnName}"/>
		</#list>
    </resultMap>
<#--#*基础列*#-->
    <sql id="Base_Column_List" >
	<#list  columns as column>
		${column.columnName} <#if column_has_next>,</#if>
	</#list>
    </sql>

	<select id="queryObject" resultType="${package}.entity.${className}Entity" resultMap="${classname}Map">
		select
		<include refid="Base_Column_List" />
		from ${tableName}
		where ${pk.columnName} = ${r"#{value}"}

	</select>

	<select id="queryList" resultType="${package}.entity.${className}Entity" resultMap="${classname}Map">
		select
		<include refid="Base_Column_List" />
		from ${tableName}
        <choose>
            <when test="sidx != null and sidx.trim() != ''">
                order by  ${r"${sidx}"} ${r"${order}"}
            </when>
			<otherwise>
                order by ${pk.columnName} desc
			</otherwise>
        </choose>
		<if test="offset != null and limit != null">
			limit  ${r"#{offset}"}, ${r"#{limit}"}
		</if>
	</select>

    <select id="queryListByBean" resultType="${package}.entity.${className}Entity" resultMap="${classname}Map">
        select
        <include refid="Base_Column_List" />
        from ${tableName}
        WHERE 1=1
    </select>
	
 	<select id="queryTotal" resultType="int">
		select count(*) from ${tableName}
	</select>
	 
	<insert id="save" parameterType="${package}.entity.${className}Entity"<#if pk.extra == 'auto_increment'> useGeneratedKeys="true" keyProperty="${pk.attrname}"</#if> >
		insert into ${tableName}
		(
		<#list  columns as column>
		<#if column.columnName != pk.columnName || pk.extra != 'auto_increment'>
					`${column.columnName}`<#if column_has_next>,</#if>
		</#if>

		</#list>
				)
				values
				(
		<#list columns as column>
		<#if column.columnName != pk.columnName || pk.extra != 'auto_increment'>

				${r"#{"}${column.attrname}${r"}"}<#if column_has_next>,</#if>
		</#if>
		</#list>

		)
	</insert>
	 
	<update id="update" parameterType="${package}.entity.${className}Entity">
		update ${tableName}
		<set>
		<#list  columns as column>
			<#if column.columnName != pk.columnName>
                <if test="${column.attrname} != null">`${column.columnName}` = ${r"#{"}${column.attrname}${r"}"}<#if column_has_next>,</#if></if>
			</#if>
		</#list>
		</set>
		where ${pk.columnName} = ${r"#{"}${pk.attrname}${r"}"}
	</update>

	<delete id="delete">
		delete from ${tableName} where ${pk.columnName} = ${r"#{value}"}
	</delete>

	<delete id="deleteBatch">
		delete from ${tableName} where ${pk.columnName} in
		<foreach item="${pk.attrname}" collection="array" open="(" separator="," close=")">
		${r"#{"}${pk.attrname}${r"}"}
		</foreach>
	</delete>

</mapper>