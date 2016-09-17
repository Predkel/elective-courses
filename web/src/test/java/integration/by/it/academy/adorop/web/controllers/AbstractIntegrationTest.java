package integration.by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.dao.config.PersistenceConfig;
import by.it.academy.adorop.web.infrastructure.WebConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, AbstractIntegrationTest.TestPersistenceConfig.class})
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    protected MockMvc mvc;
    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Configuration
    public static class TestPersistenceConfig extends PersistenceConfig {
        @Bean
        @Override
        public DataSource dataSource() {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.H2)
                    .setScriptEncoding("UTF-8")
                    .addScripts("courses-ddl.sql", "courses-data.sql")
                    .build();
        }

        @Override
        protected boolean showSqlIsNeeded() {
            return true;
        }
    }
}
