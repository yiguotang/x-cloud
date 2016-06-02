package org.waddys.xcloud;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.waddys.xcloud.event.type.ConnectionInfo;
//import org.waddys.xcloud.vijava.util.CloudVMConfig.ConnectionInfo;

@SpringBootApplication
@EnableConfigurationProperties({ ConnectionInfo.class })
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * 主入口
     * 
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        System.out.println("=== STARTUP App NOW ===");
    }

}
