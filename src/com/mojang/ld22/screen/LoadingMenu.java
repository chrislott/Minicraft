package com.mojang.ld22.screen;

import java.io.IOException;
import java.io.StreamCorruptedException;

import android.util.Log;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.sound.Sound;

public class LoadingMenu extends Menu {
	public static final int NEWGAME = 0;
	public static final int LOADGAME = 1;
	public boolean displayed = false;
	private int intent = 0;

	public LoadingMenu(int i) {
		intent = i;
	}

	public void tick() {
		if (displayed) {
			if (intent == 0) {
				Sound.tick.play();
				game.resetGame();
				game.setMenu(null);
			} else if (intent == 1) {
				Sound.tick.play();
				boolean success = false;
				try {
					game.load();
					success = true;
					Log.w("DEBUG", "Load succeeded");
				} catch (StreamCorruptedException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (success)
					game.setMenu(null);
				else
					game.setMenu(new TitleMenu());
			}
		}
	}

	public void render(Screen screen) {
		screen.clear(0);
		String msg1 = "Loading";
		String msg2 = "Please be patient <3";
		Font.draw(msg1, screen, screen.w / 2 - (msg1.length() * 8 / 2), screen.h / 2 - 4, Color.get(0, 111, 111, 111));
		Font.draw(msg2, screen, screen.w / 2 - (msg2.length() * 8 / 2), screen.h / 2 + 4, Color.get(0, 111, 111, 111));
		displayed = true;
	}
}