package com.lig.common.pojo;

public class DBConnDTO {

    /**
     * 主机
     */
    private String host;

    /**
     * 端口
     */
    private String post;

    /**
     * 数据库名
     */
    private String dbName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;


    /**
     * 链接类型 01:Sqlserver 02:oracle 03:mySql
     */
    private String serverType = "01";


    public DBConnDTO() {
    }

    public DBConnDTO(String host, String post, String dbName, String userName, String passWord, String serverType) {
        this.host = host;
        this.post = post;
        this.dbName = dbName;
        this.userName = userName;
        this.passWord = passWord;
        this.serverType = serverType;
    }

    /**
     * 获取主机ip
     * @return 主机
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置主机
     * @param host 主机
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 端口
     * @return 端口号
     */
    public String getPost() {
        return post;
    }

    /**
     * 设置端口
     * @param post 端口
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * 数据库名
     * @return 数据库名
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * 设置数据库名
     * @param dbName 数据库名
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * 用户名
     * @return 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 密码
     * @return 密码
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * 密码
     * @param passWord 密码
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * 数据库类型
     * @return 数据库类型
     */
    public String getServerType() {
        return serverType;
    }

    /**
     * 设置数据库类型
     * @param serverType 数据库类型
     */
    public void setServerType(String serverType) {
        this.serverType = serverType;
    }
}
