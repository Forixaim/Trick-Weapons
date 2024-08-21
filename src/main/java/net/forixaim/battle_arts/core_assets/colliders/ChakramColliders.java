package net.forixaim.battle_arts.core_assets.colliders;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

public class ChakramColliders {
	public static final Collider CHAKRAM_AIRSLASH = new MultiOBBCollider(
			new OBBCollider(0.25D, 1.25D, 0.4D, 0D, 1.25D, 0D),
			new OBBCollider(0.25D, 0.625, 0.4, 0d, 0.625d, 0.8),
			new OBBCollider(0.25D, 0.625, 0.4, 0d, 0.625d, -0.8),
			new OBBCollider(0.25D, 0.3125, 0.4, 0d, 0.3125d, 1.6),
			new OBBCollider(0.25D, 0.3125, 0.4, 0d, 0.3125d, -1.6)
	);
	public static final Collider PRECISION_VERTICAL = new OBBCollider(1D, 1D, 6D, 0D, 1D, -6D);

}
