package com.blog.utils;

public class RedisCommonConf {
	
	// redis key 维护类，常量 
	// 常量定义规则：
	//       公共的 静态的 最终的 数据类型 变量名 = 值;
	//   变量名定义：统一是大写，两个单词用下划线分割
	//          值：统一是小写

	public static final String QUERY_COMMENT_LIST = "query_comment_list"; //用户列表
	public static final String QUERY_BLOG_TYPE_LIST = "query_blog_type_list"; //文章类型列表
	public static final String QUERY_BLOG_LIST = "query_blog_list"; //文章列表

	public static final String QUERY_BLOG_EXAMINE = "query_blog_examine";//审核列表


}
