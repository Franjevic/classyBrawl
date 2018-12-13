package core;

import core.actionlisteners.KeyStrokes;
import core.actionlisteners.MouseStrikes;
import core.entities.Damage;
import core.entities.resources.Resources;
import core.entities.tools.Style;
import core.entities.tools.UseHandler;
import core.entities.windows.Inventory;
import core.entities.workstations.WorkStation;
import core.entities.workstations.WorkStations;
import core.gfx.InitializeWindow;
import core.gfx.LoadingScreen;
import core.gfx.Map;
import core.gfx.NeedsFocus;
import core.gfx.Player;
import core.gfx.Sprites;
import core.gfx.Text;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;



import javax.imageio.ImageIO;



public class Runner extends Canvas implements Runnable {
	public static final long serialVersionUID = 1L;
	public static final int WIDTH = 720;
	public static final int HEIGHT = WIDTH / 12 * 9;
	

	private Thread thread;
	private boolean running = false;
	private int seconds = 0;
	private static Map map;
	private static Player player;
	private static Inventory inv;
	private static NeedsFocus focus;
	private static UseHandler uses;
	private static Resources resources;
	private static ArrayList<Object> instances = new ArrayList<>(); 

	public Runner() {
		this.setSize(WIDTH, HEIGHT);
		new InitializeWindow(this);
		this.start();
	}

	@Override
	public final void run() {
		
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			if (running) {
				render();
				frames++;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(frames + " " + seconds);
				seconds++;
				frames = 0;
				ticks = 0;
			}
		}
		this.stop();
	}

	BufferStrategy bs;
	static Graphics2D g;
	public static int ticks = 0;

	private final void init() {
		new LoadingScreen();
		this.createBufferStrategy(3);
		bs = this.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();
		try {
			new Text(ImageIO.read(getClass().getResource("/resources/Font.png")));
			new Sprites(ImageIO.read(getClass().getResource("/resources/MySheet.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addKeyListener(new KeyStrokes());
		this.addMouseListener(new MouseStrikes());
		
		map = new Map();
		player = new Player();
		inv = new Inventory();
		focus = new NeedsFocus();
		uses = new UseHandler();
		resources = new Resources();
		instances.add(map);
		instances.add(player);
		instances.add(inv);
		instances.add(focus);
		instances.add(uses);
		instances.add(resources);
		new WorkStation("crafter", new int[] {Player.loc[0],Player.loc[1]}, new int[] {6,2,2,2}, false);

		calculatePlayerSpawn();
	}
	
	
	private void calculatePlayerSpawn() {
		while(Map.calcTileCollision(Player.playerHB) != null) {
			Map.calcTileCollision(Player.playerHB).doHurt(4000, Style.Axe);
		}
	}

	private final void tick() {
		if (this.hasFocus()) {
			map.tick();
			player.tick();
			uses.tick();
			resources.tick();
			Damage.tick();
			WorkStations.tick();
		}	
	}

	private final void render() {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, WIDTH + 20, HEIGHT + 20);
		// if(loading) loadscreen.render(g);
		g.setColor(Color.WHITE);
		
		
		map.render(g);
		WorkStations.render(g);
		player.render(g);
		inv.render(g);
		uses.render(g);
		resources.render(g);
		Damage.render(g);
		if (!this.hasFocus()) {
			focus.render(g);
		}
		g.setColor(Color.RED);
		g.drawRect(Runner.WIDTH / 2, Runner.HEIGHT / 2, 1, 1);
		// l.render(g);

		bs.show();
	}

	public final synchronized void start() {
		thread = new Thread(this);
		init();
		thread.start();
		running = true;
	}

	public final synchronized void stop() {
		try {
			running = false;
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static double clamp(double value, double desired, double min, double max) {
		if (Math.signum(desired - value) == -1.0) {
			return value = desired > min ? desired : min;
		} else
			return value = desired < max ? desired : max;
	}
	
	public static Object getInstance(Class<?> pass) {
		for(int i = 0; i < instances.size();i++) {
			if(instances.get(i).getClass() == pass) {
				return instances.get(i);
			}
		}
		return null;
	}

}
