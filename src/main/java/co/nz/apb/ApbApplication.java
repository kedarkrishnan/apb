package co.nz.apb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApbApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ApbApplication.class, args);
	}
	
	@GetMapping("/api")
	public String apbApi(){
		return "APB rest API";
	}
}
