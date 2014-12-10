package com.template.common.mybatis.test;

import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Collection;
import java.util.Properties;

/**
 * 和Spring集成
 *
 */
public class MapperSpring implements BeanPostProcessor {

    private final MapperHelper mapperHelper = new MapperHelper();

    private boolean runed = false;

    public void setProperties(Properties properties) {
        String UUID = properties.getProperty("UUID");
        if (UUID != null && UUID.length() > 0) {
            mapperHelper.setUUID(UUID);
        }
        String IDENTITY = properties.getProperty("IDENTITY");
        if (IDENTITY != null && IDENTITY.length() > 0) {
            mapperHelper.setIDENTITY(IDENTITY);
        }
        String seqFormat = properties.getProperty("seqFormat");
        if (seqFormat != null && seqFormat.length() > 0) {
            mapperHelper.setSeqFormat(seqFormat);
        }
        String ORDER = properties.getProperty("ORDER");
        if (ORDER != null && ORDER.length() > 0) {
            mapperHelper.setBEFORE(ORDER);
        }
        String cameHumpMap = properties.getProperty("cameHumpMap");
        if (cameHumpMap != null && cameHumpMap.length() > 0) {
            mapperHelper.setCameHumpMap(cameHumpMap);
        }
        int mapperCount = 0;
        String mapper = properties.getProperty("mappers");
        if (mapper != null && mapper.length() > 0) {
            String[] mappers = mapper.split(",");
            for (String mapperClass : mappers) {
                if (mapperClass.length() > 0) {
                    mapperHelper.registerMapper(mapperClass);
                    mapperCount++;
                }
            }
        }
        if (mapperCount == 0) {
            throw new RuntimeException("通用Mapper没有配置任何有效的通用接口!");
        }
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    	System.out.println(beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!runed && bean instanceof SqlSessionTemplate) {
            SqlSessionTemplate sqlSessionTemplate = (SqlSessionTemplate)bean;
            Collection<MappedStatement> collection = sqlSessionTemplate.getConfiguration().getMappedStatements();
            for (Object object : collection) {
                if (object instanceof MappedStatement) {
                    MappedStatement ms = (MappedStatement)object;
                    if (mapperHelper.isMapperMethod(ms.getId())) {
                        if (ms.getSqlSource() instanceof ProviderSqlSource) {
                            mapperHelper.setSqlSource(ms);
                        }
                    }
                }
            }
            runed = true;
        }
        return bean;
    }
}
