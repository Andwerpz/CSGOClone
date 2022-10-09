package weapon;

public class Usps extends Weapon {

	public Usps() {
		super(12, 24, 100, 6f, 1000f, 2f, 30, 7f, 0.04f, 1000); //TODO fix values
	}

	@Override
	public String getModelName() {
		return "usps";
	}

}
