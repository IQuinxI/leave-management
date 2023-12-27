package ma.emsi.leavemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class LeaveManagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(LeaveManagementApplication.class, args);


	}

	//Test Scheduled annotation

//	@Scheduled(fixedDelay = 1000L)
//	public static void  scheduletask(){
//		System.out.println("keep it up");
//	}


}
