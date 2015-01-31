package com.template.common.utils;

public class Global {
	
	/**
	 *管理根路径
	* @return
	 */
	public static String getAdminPath(){
		return SysConstant.getValue("adminPath");
	}
	
	/**
	 * web上下文
	* @return
	 */
	public static String getCtxPath(){
		return SysConstant.getValue("ctxPath");
	}
	
	/**
	 *  下载的路径
	* @param groupFile 分组文件夹名
	 */
	public static String getDownloadRootPath(String groupFile){
		String dateFile = DateUtils.getDate();
		StringBuilder builder = new StringBuilder();
		builder.append(SysConstant.getValue("download.rootPath")+"/");
		builder.append(groupFile+"/");
		builder.append(dateFile+"/");
		return builder.toString();
	}
	
	public static String getUploadRootPath(String groupFile){
		String dateFile = DateUtils.getDate();
		StringBuilder builder = new StringBuilder();
		builder.append(SysConstant.getValue("upload.rootPath")+"/");
		builder.append(groupFile+"/");
		builder.append(dateFile+"/");
		return builder.toString();
	}
	
}
