package api.spring.ioc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class DataSource {
	@Value("123456")
	private String user;

	@Override
	public String toString() {
		return "DataSource [user=" + user + "]";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
