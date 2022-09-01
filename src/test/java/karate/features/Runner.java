package karate.features;

import com.intuit.karate.junit5.Karate;

public class Runner {
	@Karate.Test
	Karate testUsers() {
		return Karate.run("get").relativeTo(getClass());
	}

}
