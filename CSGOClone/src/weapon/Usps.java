package weapon;

public class Usps extends Weapon {

	public Usps() {
		super(12, 24, 170, 9f, 800f, 2.5f, 25, 7f, 0.08f, 1000); //TODO fix values

		this.gunXOffset = 0.13f;
		this.gunYOffset = -0.15f;
		this.gunZOffset = -0.55f;

		this.gunXRotRecoilScale = 1f;

		this.gunYOffsetRecoilScale = 0.4f;
	}

	@Override
	public String getModelName() {
		return "usps";
	}

}
