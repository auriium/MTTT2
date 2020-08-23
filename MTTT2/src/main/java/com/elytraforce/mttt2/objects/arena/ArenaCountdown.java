package main.java.com.elytraforce.mttt2.objects.arena;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.com.elytraforce.mttt2.Main;
import main.java.com.elytraforce.mttt2.enums.GameStateEnum;

public class ArenaCountdown extends BukkitRunnable{
	private int time;
	private final Arena arena;

	public ArenaCountdown(Arena arena) {
		this.arena = arena;
		this.time = 0;
	}
	
	public void start(int time)  {

		arena.setArenaState(GameStateEnum.COUNTDOWN);
		this.time = time;
		this.runTaskTimer(Main.getMain(), 0L, 20L);

	}
	
	public boolean isRunning() {
		return arena.getArenaState() == GameStateEnum.COUNTDOWN;
	}
	
	public int getCountdownTime() {
		return this.time;
	}
	
	@Override
	public void run() {

		if (arena.getArenaPlayers().size() < arena.getRequiredPlayers()) {
			cancel();
			arena.setArenaState(GameStateEnum.WAITING);
			arena.broadcastMessage(ChatColor.RED + "There are too few players. Countdown stopped.");
			return; 
		}
		// If time is 0 start the round!
		if (time == 0) {
			cancel();
			
			//start the Arena Preparation Countdown Phase
			
			//TODO: This 20 here represents the arena preparation time. In the future, get this from a config.
			arena.getArenaPreparationCountdown().start(20);
			return;
		}
		
		// If the time is divisible by 15 then broadcast a countdown
		// message.
		
		if (time % 15 == 0 || time <= 10) {
				if (time != 1) {
					arena.broadcastMessage(ChatColor.AQUA + "Game will start in " + time + " seconds.");
				} else {
					arena.broadcastMessage(ChatColor.AQUA + "Game will start in " + time + " second.");
				}
		}
		
	time--;
	}
	
	
}