package weapon;

public class Deagle extends Weapon {

	public Deagle() {
		super(7, 35, 225, 15f, 1500f, 2f, 45, 7f, 0.10f, 1000); //TODO fix values

		this.gunXOffset = 0.13f;
		this.gunYOffset = -0.15f;
		this.gunZOffset = -0.55f;

		this.gunXRotRecoilScale = 1f;

		this.gunYOffsetRecoilScale = 0.4f;
		
		this.description = "As expensive as it is powerful, the Desert Eagle is an iconic pistol that is difficult to master but surprisingly accurate at long range.";
	}

	@Override
	public String getModelName() {
		return "deagle";
	}

}
