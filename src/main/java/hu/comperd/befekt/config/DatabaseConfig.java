package hu.comperd.befekt.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
/*
    @Autowired
    private Environment env;

    @Bean
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClientURI(env.getProperty("spring.data.mongodb.uri")));
    }

	@Bean
	public MongoTemplate mongoTemplate() {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;

    }
    @Primary
    @Bean("UJSZLFEJL_DS")
    @ConfigurationProperties(prefix = "spring.datasource.ujszlfejl")
    public DataSource ujszlfejlDataSource() {
    	System.out.println("ujszlfejlDataSource létrehozása!");
        return DataSourceBuilder.create().build();
    }

    @Bean("SRTESZT_DS")
    @ConfigurationProperties(prefix = "spring.datasource.srteszt")
    public DataSource srtesztDataSource() {
    	System.out.println("srtesztDataSource létrehozása!");
        return DataSourceBuilder.create().build();
    }
*/
}
