package com.clinbrain.common.dataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.*;


/**
 *  实现一个实现ApplicationContextAware和ApplicationListener接口的类DynamicDataSourceC3p0，
 * 实现ApplicationContextAware是为了得到ApplicationContext，
 * 实现了ApplicationListener是为了配置spring的加载事件。
 *
 */
public class DynamicCreateDataSourceBean implements ApplicationContextAware {
    private ConfigurableApplicationContext app;
    private JdbcTemplate jdbcTemplate;
    private DynamicDataSource dynamicDataSource;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

    public void setApplicationContext(ApplicationContext app) throws BeansException {
        this.app = (ConfigurableApplicationContext)app;
    }


    public void regDynamicBean() throws IOException {
        // 解析属性文件，得到数据源Map
        Map<String, DataSourceInfo> mapCustom = parsePropertiesFile();
        // 把数据源bean注册到容器中
        addSourceBeanToApp(mapCustom);
    }

    /**
     * 功能说明：根据DataSource创建bean并注册到容器中
     *
     * @param mapCustom
     */
    private void addSourceBeanToApp(Map<String, DataSourceInfo> mapCustom) {
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) app.getAutowireCapableBeanFactory();
        String DATASOURCE_BEAN_CLASS = "com.alibaba.druid.pool.DruidDataSource";
        BeanDefinitionBuilder bdb;
        Iterator<String> iter = mapCustom.keySet().iterator();

        Map<Object,Object> targetDataSources = new LinkedHashMap<Object, Object>();

        //ChildBeanDefinition baseBeanComm = new ChildBeanDefinition("dataSource");
        //	将默认数据源放入 targetDataSources map中
        //targetDataSources.put("dataSource", app.getBean("dataSource"));

        // 根据数据源得到数据，动态创建数据源bean 并将bean注册到applicationContext中去
        while (iter.hasNext()) {
            //	bean ID
            String beanKey = iter.next();
            //	创建bean
            bdb = BeanDefinitionBuilder.rootBeanDefinition(DATASOURCE_BEAN_CLASS);
            bdb.getBeanDefinition().setAttribute("id", beanKey);
            bdb.addPropertyValue("driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
            bdb.addPropertyValue("url", mapCustom.get(beanKey).connUrl);
            bdb.addPropertyValue("username", mapCustom.get(beanKey).userName);
            bdb.addPropertyValue("password", mapCustom.get(beanKey).password);
            bdb.addPropertyValue("timeBetweenEvictionRunsMillis", 3600000);
            bdb.addPropertyValue("minEvictableIdleTimeMillis", 3600000);


            //	注册bean
            acf.registerBeanDefinition(beanKey, bdb.getBeanDefinition());

            //	放入map中，注意一定是刚才创建bean对象
            targetDataSources.put(beanKey, app.getBean(beanKey));

        }
        dynamicDataSource.setTargetDataSources(targetDataSources);
        //	必须执行此操作，才会重新初始化AbstractRoutingDataSource 中的 resolvedDataSources，也只有这样，动态切换才会起效
        dynamicDataSource.afterPropertiesSet();

    }

    /**
     * 功能说明：GET ALL SM_STATIONS FROM DB1
     *
     * @return
     * @throws IOException
     */
    private Map<String, DataSourceInfo> parsePropertiesFile()throws IOException {

        String sql = "SELECT *  FROM monitor_instance_info ";

        List list = jdbcTemplate.queryForList(sql);
        Iterator iterator = list.iterator();
        Map<String, DataSourceInfo> mds = new HashMap<String, DataSourceInfo>();
        while (iterator.hasNext()) {

            Map map4station = (Map) iterator.next();
            DataSourceInfo dsi  = new DataSourceInfo();

            String username = "sa";
            String password = "Aa123456";
            String url_1 = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=";
            //String dbName = map4station.get("instance_name")+"";
            String dbName = "shca-monitor";
            String url2 = "";


            String key = "dataSource_";
            String sid = map4station.get("ID")+"";
            key = key.concat(sid);

            String url = url_1.concat(dbName).concat(url2);

            dsi.connUrl = url;
            dsi.userName = username;
            dsi.password = password;
            mds.put(key, dsi);
        }

        return mds;
    }


    //	自定义数据结构
    private class DataSourceInfo{

        public String connUrl;
        public String userName;
        public String password;

        public String toString() {
            return "(url:" + connUrl + ", username:" + userName + ", password:"
                    + password + ")";
        }
    }



}

