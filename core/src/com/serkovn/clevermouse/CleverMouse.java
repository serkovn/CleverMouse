package com.serkovn.clevermouse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Rectangle;

public class  CleverMouse extends ApplicationAdapter {
	SpriteBatch batch;
	Texture mouse;
	Texture cheese1;
	Texture cheese2;
	Texture cheese3;
	Texture answer1;
	Texture answer2;
	Texture answer3;
	Texture answer4;
	Texture background;
	int locationClick;
	int locationMouse;
	int answerSpeed = 5;
	int answerNumber = 3;
	float tubeY[]= new float[answerNumber];
	float distanceBetweenAnswer;
	Rectangle[] answerOne;
	Rectangle[] answerTwo;
	Rectangle[] answerThree;
	Rectangle[] answerFour;
	Rectangle mRectangleMouse;
	ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("room1.png");
		shapeRenderer = new ShapeRenderer();
		
		mouse = new Texture("mouse.png");
		cheese1 = new Texture("cheese.png");
		cheese2 = new Texture("cheese.png");
		cheese3 = new Texture("cheese.png");
		answer1 = new Texture("wood.png");
		answer2 = new Texture("wood.png");
		answer3 = new Texture("wood.png");
		answer4 = new Texture("wood.png");
		locationMouse = Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/10;
		distanceBetweenAnswer = Gdx.graphics.getHeight()/2 + Gdx.graphics.getHeight()/4;

		mRectangleMouse = new Rectangle();
		answerOne = new Rectangle[answerNumber];
		answerTwo = new Rectangle[answerNumber];
		answerThree = new Rectangle[answerNumber];
		answerFour = new Rectangle[answerNumber];
		for (int i = 0; i < answerNumber;  i++) {
			tubeY[i] = Gdx.graphics.getHeight()/2 + Gdx.graphics.getHeight()/4 + i * distanceBetweenAnswer;
			answerOne[i] = new Rectangle();
			answerTwo[i] = new Rectangle();
			answerThree[i] = new Rectangle();
			answerFour[i] = new Rectangle();
		}
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(mouse, locationMouse, 0,Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/6);
		batch.draw(cheese1, 20, Gdx.graphics.getHeight() - 80,60,60);
		batch.draw(cheese2, 100, Gdx.graphics.getHeight() - 80,60,60);
		batch.draw(cheese2, 180, Gdx.graphics.getHeight() - 80,60,60);
		mRectangleMouse = new Rectangle(locationMouse, 0,Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/6);

		if (Gdx.input.justTouched()) {
			locationClick= Gdx.input.getX();
			if ((locationClick > 0) && (locationClick < Gdx.graphics.getWidth()/4))
				locationMouse = Gdx.graphics.getWidth()/8 - Gdx.graphics.getWidth()/10;
			if ((locationClick >= Gdx.graphics.getWidth()/4) && (locationClick < Gdx.graphics.getWidth()/2))
				locationMouse = Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 - Gdx.graphics.getWidth()/10;
			if ((locationClick >= Gdx.graphics.getWidth()/2) && (locationClick < Gdx.graphics.getWidth()/2 + Gdx.graphics.getWidth()/4 ))
				locationMouse = Gdx.graphics.getWidth()/2 + Gdx.graphics.getWidth()/4 - Gdx.graphics.getWidth()/8 - Gdx.graphics.getWidth()/10;
			if ((locationClick >= Gdx.graphics.getWidth()/2 + Gdx.graphics.getWidth()/4 ))
				locationMouse = Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/8 - Gdx.graphics.getWidth()/10;
		}
		for (int i = 0; i < answerNumber;  i++) {
			if (tubeY[i] < Gdx.graphics.getHeight()/6) {
				tubeY[i] = answerNumber * distanceBetweenAnswer;
			} else {
				tubeY[i] -= answerSpeed;
			}

			batch.draw(answer1, 0, tubeY[i], Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4);
			batch.draw(answer2, Gdx.graphics.getWidth() / 4, tubeY[i], Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4);
			batch.draw(answer3, Gdx.graphics.getWidth() / 2, tubeY[i], Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4);
			batch.draw(answer4, Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4, tubeY[i], Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4);

			answerOne[i] = new Rectangle( 0, (int) tubeY[i],answer1.getWidth(),answer1.getHeight());
			answerTwo[i] = new Rectangle( Gdx.graphics.getWidth() / 4, (int) tubeY[i],answer2.getWidth(), answer2.getHeight());
			answerThree[i] = new Rectangle( Gdx.graphics.getWidth() / 2, (int) tubeY[i], answer3.getWidth(), answer3.getHeight());
			answerFour[i] = new Rectangle( Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4, (int) tubeY[i],answer4.getWidth(),answer4.getHeight());
		}

		batch.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(locationMouse, 0,Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/6);
		for (int i = 0; i < answerNumber;  i++) {

			shapeRenderer.rect(0, (int) tubeY[i],answer1.getWidth()/4,answer1.getHeight()/4);
			shapeRenderer.rect(Gdx.graphics.getWidth() / 4, (int) tubeY[i],answer2.getWidth()/4, answer2.getHeight()/4);
			shapeRenderer.rect(Gdx.graphics.getWidth() / 2, (int) tubeY[i], answer3.getWidth()/4, answer3.getHeight()/4);
			shapeRenderer.rect(Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4, (int) tubeY[i],answer4.getWidth()/4,answer4.getHeight()/4);

		}
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
	}
	// help me
	// commit and push
}
