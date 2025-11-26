package blog.natta.santa.cors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorsApplication {

  private CorsApplication() {}

	static void main(String[] args) {
		SpringApplication.run(CorsApplication.class, args);
	}

}
