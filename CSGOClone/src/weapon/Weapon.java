package weapon;

import main.Main;
import model.Model;
import player.Player;
import util.Mat4;
import util.Pair;
import util.Vec3;

public abstract class Weapon {

	private int magazineAmmoSize, magazineAmmo;
	private int reserveAmmoSize, reserveAmmo;

	private long fireDelayMillis;
	private long fireMillisCounter = 0;

	private float recoilRecoverySpeedPercent = 0.025f;
	private float recoilRecoverySpeedLinear = 0.4f;
	private float recoilVerticalRot = 0f;
	private float recoilHorizontalRot = 0f;
	private float recoilScale = 0.01f;
	private float recoilScreenScale = 0.5f; //a value of 1 means that the bullet will land on the crosshair always

	private float recoilVerticalImpulse;
	private float recoilHorizontalImpulse;

	private float movementInaccuracyMinimum = 0.05f; //minimum movement speed required to trigger movement inaccuracy
	private float movementInaccuracyScale; //500 is smg, 1000 is rifle

	private float weaponInaccuracy; //2 is rifle

	private int weaponDamage;
	private float weaponDamageFalloffDist; //7 is rifle
	private float weaponDamageFalloffPercent; //0.04 is rifle

	private boolean reloading = false;
	private long reloadStartMillis;
	private long reloadTimeMillis;

	private float bulletXRot, bulletYRot; //after shooting, this is the computed deviation for the bullet. 

	public Weapon() {

	}

	private boolean canShoot() {
		return this.fireMillisCounter > this.fireDelayMillis && this.magazineAmmo > 0 && !this.reloading;
	}

	private void reload() {
		if (System.currentTimeMillis() - this.reloadStartMillis >= this.reloadTimeMillis) {
			this.reserveAmmo += this.magazineAmmo;
			int transferAmmo = this.magazineAmmoSize + Math.min(this.reserveAmmo - this.magazineAmmoSize, 0);
			this.reserveAmmo = Math.max(0, this.reserveAmmo - this.magazineAmmoSize);
			this.magazineAmmo = transferAmmo;
			this.reloading = false;
		}
	}

	public void startReloading() {
		if (this.magazineAmmo != this.magazineAmmoSize && this.reserveAmmo != 0 && !this.reloading) {
			this.reloading = true;
			this.reloadStartMillis = System.currentTimeMillis();
		}
	}

	//return null if the shot was unsuccessful
	//returns a direction vector if the shot was successful
	public Vec3 shoot(Player player) {
		if (this.canShoot()) {
			this.fireMillisCounter %= this.fireDelayMillis;

			float spread = this.weaponInaccuracy;
			float movementSpeed = player.vel.length();
			if (movementSpeed > this.movementInaccuracyMinimum) {
				spread += movementSpeed * this.movementInaccuracyScale;
			}

			float totalYRot = player.camYRot - (this.recoilHorizontalRot + (float) (Math.random() * spread - spread / 2)) * this.recoilScale;
			float totalXRot = player.camXRot - (this.recoilVerticalRot + (float) (Math.random() * spread - spread / 2)) * this.recoilScale;
			Vec3 ray_dir = new Vec3(0, 0, -1).rotateX(totalXRot).rotateY(totalYRot);

			this.magazineAmmo--;

			if (this.magazineAmmo == 0) {
				this.startReloading();
			}

			//apply recoil
			this.recoilVerticalRot += this.recoilVerticalImpulse;
			this.recoilHorizontalRot += this.recoilHorizontalImpulse;

			return ray_dir;
		}
		return null;
	}

	//xRot, yRot, yOffset, zOffset
	public float[] getGunRecoilOffset() {

		float yOffset = this.recoilVerticalRot * this.recoilScale * 0.1f;
		float zOffset = this.recoilVerticalRot * this.recoilScale * 1f;

		float xRot = -this.recoilVerticalRot * this.recoilScale * 0.7f;
		float yRot = -this.recoilHorizontalRot * this.recoilScale * 0.7f;

		return new float[] { xRot, yRot, yOffset, zOffset };
	}

	public void update() {
		if (this.reloading) {
			this.reload();
		}

		this.fireMillisCounter = Math.min(this.fireMillisCounter + Main.main.deltaMillis, this.fireDelayMillis);

		this.recoilVerticalRot -= this.recoilVerticalRot * this.recoilRecoverySpeedPercent + this.recoilRecoverySpeedLinear;
		this.recoilHorizontalRot -= this.recoilHorizontalRot * this.recoilRecoverySpeedPercent;

		this.recoilVerticalRot = Math.max(this.recoilVerticalRot, 0);
		this.recoilHorizontalRot = Math.max(this.recoilHorizontalRot, 0);
	}
}
