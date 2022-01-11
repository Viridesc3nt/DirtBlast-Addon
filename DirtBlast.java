package org.justinjaques.earthabilites;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.util.Vector;
import org.bukkit.Location;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

/**
 * A move that bends dirt at an enemy, temporarily blinding them and dealing damage
 * @author Justin Jaques (alias Viridescent_)
 *
 */


public class DirtBlast extends EarthAbility implements AddonAbility {
	private static final String AUTHOR = "Viridescent_";
	private static final String VERSION = "1.0.0";
	private static final String NAME = "DirtBlast";
	private static final long COOLDOWN = 2000;
	
	private static final double DAMAGE = 1;
	private static final double RANGE = 25;
	private double distanceTravelled;
	
	
	
	private DirtBlastListener listener;
	private Permission perm;
	
	private Location location;
	private Vector direction;

	public DirtBlast(Player player) {
		super(player);
		
		location = player.getEyeLocation();
		direction = player.getLocation().getDirection();
		direction.multiply(0.8);
		bPlayer.addCooldown(this);
		distanceTravelled = 0;
		
		start();
	}
	

	@Override
	public long getCooldown() {
		// TODO Auto-generated method stub
		return COOLDOWN;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return location;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void progress() {
		if(bPlayer.canBend(this)) {
			remove();	
			return;
		}
		
		if(location.getBlock().getType().isSolid()) {
			remove();
			return;
		}
		
		if(distanceTravelled > RANGE) {
			remove();
			return;
		}
		affectTargets();
		ParticleEffect.CRIT_MAGIC.display(location, 0, direction.getX(), direction.getY(), direction.getZ());
		location.add(direction);
		distanceTravelled += direction.length();
	}
	
	private void affectTargets() {
		List<Entity> targets = GeneralMethods.getEntitiesAroundPoint(location, 1);
		for (Entity target : targets) {
			if(target.getUniqueId() == player.getUniqueId()) {
				continue;
			}
			target.setVelocity(direction);
			DamageHandler.damageEntity(target, DAMAGE, this);
			target.setFireTicks(0);
		}
		
		
	}	

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return AUTHOR;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return VERSION;
	}

	@Override
	public void load() {
		ProjectKorra.log.info(this.getName() + " by " + this.getAuthor() + " " + this.getVersion() + " has been loaded!");
		listener = new DirtBlastListener();
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(listener, ProjectKorra.plugin);
		perm = new Permission("bending.ability.DirtBlast");
		ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
		
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(listener);
		ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
		
	}

}
