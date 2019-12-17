package api.spring.ioc;

public class StoneAxe implements Axe{

	@Override
	public String chop() {
		return "stone axe is slowly";
	}

}
