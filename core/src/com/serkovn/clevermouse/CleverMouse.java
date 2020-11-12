package com.serkovn.clevermouse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


public class  CleverMouse extends ApplicationAdapter {
	SpriteBatch batch;
	Texture mouse;
	Texture cheese1;
	Texture cross;
	Texture answer;
	Texture background;
	Texture gameOver;
	Texture cat;
	Texture play;
	int locationClick;
	int locationMouse;
	int answerSpeed = 8;
	float tubeY;
	float distanceBetweenAnswer;
	Rectangle answerTrue;
	int oneIsFour;
	Rectangle mRectangleMouse;
	ShapeRenderer shapeRenderer;
	Random mRandom;
	int start = 0;
	int amount;
	int notAmount;
	BitmapFont scoreFont;
	int highScore;
	Preferences preferences;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("room.png");
		shapeRenderer = new ShapeRenderer();
		mRandom = new Random();
		mouse = new Texture("mouse.png");
		cheese1 = new Texture("cheese.png");
		gameOver = new Texture("gameover.png");
		cat = new Texture("cat.png");
		cross = new Texture("cross.png");
		answer = new Texture("wood.png");
		play = new Texture("play.png");
		locationMouse = Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/10;
		distanceBetweenAnswer = Gdx.graphics.getHeight();
		oneIsFour = mRandom.nextInt(4);
		mRectangleMouse = new Rectangle();
		answerTrue = new Rectangle();
		tubeY = Gdx.graphics.getHeight()/2 + Gdx.graphics.getHeight()/4;
		scoreFont = new BitmapFont();
		scoreFont.setColor(Color.BLACK);
		scoreFont.getData().setScale(7);
		preferences = Gdx.app.getPreferences("record");
		highScore = preferences.getInteger("highScore");
	}

	@Override
	public void render () {
		if (start == 1) {
			batch.begin();
			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(mouse, locationMouse, 0, Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 6);
			if (notAmount == 0) {
				batch.draw(cheese1, 20, Gdx.graphics.getHeight() - 100, 80, 80);
				batch.draw(cheese1, 120, Gdx.graphics.getHeight() - 100, 80, 80);
				batch.draw(cheese1, 220, Gdx.graphics.getHeight() - 100, 80, 80);
			} else if (notAmount == 1) {
				batch.draw(cheese1, 20, Gdx.graphics.getHeight() - 100, 80, 80);
				batch.draw(cheese1, 120, Gdx.graphics.getHeight() - 100, 80, 80);
				batch.draw(cross, 220, Gdx.graphics.getHeight() - 100, 80, 80);
			} else if (notAmount == 2) {
				batch.draw(cheese1, 20, Gdx.graphics.getHeight() - 100, 80, 80);
				batch.draw(cross, 120, Gdx.graphics.getHeight() - 100, 80, 80);
				batch.draw(cross, 220, Gdx.graphics.getHeight() - 100, 80, 80);
			} else {
				batch.draw(cross, 20, Gdx.graphics.getHeight() - 100, 80, 80);
				batch.draw(cross, 120, Gdx.graphics.getHeight() - 100, 80, 80);
				batch.draw(cross, 220, Gdx.graphics.getHeight() - 100, 80, 80);
				start =2;
			}
			mRectangleMouse = new Rectangle(locationMouse + 10, 0, Gdx.graphics.getWidth() / 5 - 30, Gdx.graphics.getHeight() / 6);

			if (Gdx.input.justTouched()) {
				locationClick = Gdx.input.getX();
				if ((locationClick > 0) && (locationClick < Gdx.graphics.getWidth() / 4))
					locationMouse = Gdx.graphics.getWidth() / 8 - Gdx.graphics.getWidth() / 10;
				if ((locationClick >= Gdx.graphics.getWidth() / 4) && (locationClick < Gdx.graphics.getWidth() / 2))
					locationMouse = Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8 - Gdx.graphics.getWidth() / 10;
				if ((locationClick >= Gdx.graphics.getWidth() / 2) && (locationClick < Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4))
					locationMouse = Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4 - Gdx.graphics.getWidth() / 8 - Gdx.graphics.getWidth() / 10;
				if ((locationClick >= Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4))
					locationMouse = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 8 - Gdx.graphics.getWidth() / 10;
			}

			if (tubeY < (Gdx.graphics.getHeight() / 6)) {
				if (Math.abs(mRectangleMouse.getX() - answerTrue.getX()) < 40) {
					Gdx.app.log("intersector", "Yes");
					amount++;
					if (amount > highScore) {
						highScore = amount;
					}
				} else {
					notAmount++;
					Gdx.app.log("intersector", "Not");
				}
				tubeY += distanceBetweenAnswer;
				oneIsFour = mRandom.nextInt(4);

			} else {
				tubeY -= answerSpeed;
			}
			batch.draw(answer, 0, tubeY, Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4);
			batch.draw(answer, Gdx.graphics.getWidth() / 4, tubeY, Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4);
			batch.draw(answer, Gdx.graphics.getWidth() / 2, tubeY, Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4);
			batch.draw(answer, Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4, tubeY, Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4);
			switch (oneIsFour) {
				case 0:
					answerTrue = new Rectangle(0, (int) tubeY, answer.getWidth(), answer.getHeight());
					break;
				case 1:
					answerTrue = new Rectangle(Gdx.graphics.getWidth() / 4, (int) tubeY, answer.getWidth(), answer.getHeight());
					break;
				case 2:
					answerTrue = new Rectangle(Gdx.graphics.getWidth() / 2, (int) tubeY, answer.getWidth(), answer.getHeight());
					break;
				case 3:
					answerTrue = new Rectangle(Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4, (int) tubeY, answer.getWidth(), answer.getHeight());
			}

			scoreFont.draw(batch, String.valueOf(amount), Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight() - 20);
			batch.end();
		}
		else if (start == 2) {
			batch.begin();
			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(cat, Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, 10, Gdx.graphics.getWidth()/3 , Gdx.graphics.getHeight()/2 - Gdx.graphics.getWidth()/4 -20);
			batch.draw(cross, 20, Gdx.graphics.getHeight() - 100, 80, 80);
			batch.draw(cross, 120, Gdx.graphics.getHeight() - 100, 80, 80);
			batch.draw(cross, 220, Gdx.graphics.getHeight() - 100, 80, 80);
			batch.draw(gameOver, 0, Gdx.graphics.getHeight()/2 - Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth(), Gdx.graphics.getWidth()/2);
			scoreFont.draw(batch, String.valueOf(amount), Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight() - 20);
			batch.end();
			preferences.putInteger("highScore", highScore);
			preferences.flush();
			if (Gdx.input.justTouched()) {
				start = 1;
				amount = 0;
				notAmount = 0;
			}
		} else {
			batch.begin();
			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(play, Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4 , Gdx.graphics.getHeight()/2 - Gdx.graphics.getWidth()/4, Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth()/2);
			scoreFont.draw(batch, "High Score " + highScore, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight() - 50);
			batch.end();
			if (Gdx.input.justTouched()) {
				start = 1;
				amount = 0;
				notAmount = 0;
			}
		}
		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(locationMouse, 0,Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/6);
		switch (oneIsFour) {
			case 0:
				shapeRenderer.rect(0, (int) tubeY, answer.getWidth() / 4, answer.getHeight() / 4);
				break;
			case 1:
				shapeRenderer.rect(Gdx.graphics.getWidth() / 4, (int) tubeY, answer.getWidth() / 4, answer.getHeight() / 4);
				break;
			case 2:
				shapeRenderer.rect(Gdx.graphics.getWidth() / 2, (int) tubeY, answer.getWidth() / 4, answer.getHeight() / 4);
				break;
			case 3:
				shapeRenderer.rect(Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4, (int) tubeY, answer.getWidth() / 4, answer.getHeight() / 4);
				break;
		}


		shapeRenderer.end();*/
	}

	@Override
	public void dispose () {
	}
	// help me
	// commit and push
}
