package sjsu.edu.cmpe275;



import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import sjsu.edu.cmpe275.model.VirtualTime;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean(initMethod="runAfterObjectCreated")
	public void setTime() {
		VirtualTime vTime = VirtualTime.getInstance();
		vTime.setSystemTime(LocalDateTime.now());
		System.out.println("here init: "+LocalDateTime.now());
	}
}
