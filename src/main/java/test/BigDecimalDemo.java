package test;

import java.math.BigDecimal;

public class BigDecimalDemo {

	public static void main(String[] args) {
		// 327488.545
		BigDecimal bg = new BigDecimal("203212.765").setScale(2, BigDecimal.ROUND_HALF_UP);
		System.out.println(bg);
		System.out.println(new BigDecimal("327488.545").setScale(2, BigDecimal.ROUND_HALF_UP));
	}

}
