package com.mbl111.ld26.ai;

import com.mbl111.ld26.Game;
import com.mbl111.ld26.Resource;
import com.mbl111.ld26.entity.Unit;
import com.mbl111.ld26.util.MathHelper;
import com.mbl111.ld26.util.SyncedRandom;
import com.mbl111.ld26.world.tile.Tile;
import com.mbl111.ld26.world.tile.WindmillTile;

public class FarmJob extends Job {

	public int tileX, tileY;
	public Job subJob = null;
	public boolean depositing = false;
	public boolean movingToWheat = true;
	private int closeX = -1;
	private int closeY = -1;
	private Resource resourceToCollect;

	public FarmJob(int x, int y, Resource resource) {
		this.tileX = x;
		this.tileY = y;
		this.resourceToCollect = resource;
	}

	@Override
	public boolean finished() {
		return false;
	}

	@Override
	public void tick() {
		if (unit.currentResource != resourceToCollect) {
			unit.setCollect(resourceToCollect);
		}
		if (movingToWheat) {
			if (subJob == null) {
				this.subJob = new MoveJob((tileX * Tile.WIDTH) + (Tile.WIDTH / 2) + SyncedRandom.r.nextInt(8) - 3, (tileY * Tile.HEIGHT) + (Tile.HEIGHT / 2) + SyncedRandom.r.nextInt(8) - 3);
				this.subJob.init(unit);
			} else {
				if (subJob.finished() || (MathHelper.distance(unit.x, unit.y, (tileX * Tile.WIDTH) + (Tile.WIDTH / 2), (tileY * Tile.HEIGHT) + (Tile.HEIGHT / 2)) < 22 && !resourceToCollect.deposit.canPass(unit))) {
					movingToWheat = false;
					subJob = null;
				}
			}
		} else if (depositing) {
			if (closeX == -1 && closeY == -1) {
				boolean found = false;
				int rad = 1;
				while (!found) {
					if (rad > 20) {
						break;
					}
					for (int x = tileX - rad; x < tileX + rad; x++) {
						int y = tileY - rad;
						if (Game.instance.getWorld().getTile(x, y) == resourceToCollect.deposit) {
							closeX = x;
							closeY = y;
							found = true;
						}
						y = tileY + rad;
						if (Game.instance.getWorld().getTile(x, y) == resourceToCollect.deposit) {
							closeX = x;
							closeY = y;
							found = true;
						}
					}
					for (int y = tileY - rad - 1; y < tileY + rad - 1; y++) {
						int x = tileY - rad;
						if (Game.instance.getWorld().getTile(x, y) == resourceToCollect.deposit) {
							closeX = x;
							closeY = y;
							found = true;
						}
						x = tileY + rad;
						if (Game.instance.getWorld().getTile(x, y) == resourceToCollect.deposit) {
							closeX = x;
							closeY = y;
							found = true;
						}
					}
					rad++;
				}
			} else {
				if (MathHelper.distance(unit.x, unit.y, (closeX * Tile.WIDTH) + (Tile.WIDTH / 2), (closeY * Tile.HEIGHT) + (Tile.HEIGHT / 2)) > 5 + 16) {
					if (subJob == null) {
						this.subJob = new MoveJob((closeX * Tile.WIDTH) + (Tile.WIDTH / 2), (closeY * Tile.HEIGHT) + (Tile.HEIGHT / 2));
						this.subJob.init(unit);
					}
				} else {
					Game.instance.getPlayerView().addResouce(unit.resourceAmount, resourceToCollect);
					unit.resourceAmount = 0;
					depositing = false;
					movingToWheat = true;
					subJob = null;
				}
			}
		}
		if (subJob != null) {
			subJob.tick();
			return;
		}

		int data = Game.instance.getWorld().getData(tileX, tileY);
		int growth = data & 0xF;
		int picked = (data & 0xFF0) >> 4;

		if (picked >= 100) {
			Game.instance.getWorld().setData(tileX, tileY, 0);
		}

		if (growth < resourceToCollect.maxBuild) {
			if (SyncedRandom.r.nextInt(250) == 0) {
				int newdata = (picked << 4) | ((growth + 1));
				Game.instance.getWorld().setData(tileX, tileY, newdata);
			}
			return;
		}

		if (unit.resourceAmount == Unit.maxHold) {
			depositing = true;
			movingToWheat = false;
		} else {
			if (Game.instance.tickCount % 90 == 0) {
				if (SyncedRandom.r.nextInt(2) == 0) {
					unit.resourceAmount += 1;
					int newdata = ((picked + 1) << 4) | ((growth));
					Game.instance.getWorld().setData(tileX, tileY, newdata);
				}
			}
		}

	}

}
