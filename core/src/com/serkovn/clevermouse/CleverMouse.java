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

import java.util.HashMap;
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
	int answerSpeed = 5;
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
	BitmapFont questionText;
	BitmapFont trueAnswer;
	int highScore;
	int falseAnswer;
	int rightAnswer;
	Preferences preferences;
	HashMap question;
	HashMap	answerQuestion;
	
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
		playQuestion();
		falseAnswer = mRandom.nextInt(answerQuestion.size()) + 1;
		rightAnswer = mRandom.nextInt(question.size());
		mRectangleMouse = new Rectangle();
		answerTrue = new Rectangle();
		tubeY = Gdx.graphics.getHeight()/2 + Gdx.graphics.getHeight()/4;
		scoreFont = new BitmapFont();
		scoreFont.setColor(Color.BLACK);
		scoreFont.getData().setScale(7);
		questionText = new BitmapFont();
		questionText.setColor(Color.BLACK);
		questionText.getData().setScale(7);
		trueAnswer = new BitmapFont();
		trueAnswer.setColor(Color.BLACK);
		trueAnswer.getData().setScale(5);
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
				falseAnswer = mRandom.nextInt(answerQuestion.size()) + 1;
				rightAnswer = mRandom.nextInt(question.size());

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
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + rightAnswer), Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs(rightAnswer - falseAnswer)%question.size())), Gdx.graphics.getWidth() / 4 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs(rightAnswer + falseAnswer)%question.size())), Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs((rightAnswer +1) * falseAnswer)%question.size())), Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					break;
				case 1:
					answerTrue = new Rectangle(Gdx.graphics.getWidth() / 4, (int) tubeY, answer.getWidth(), answer.getHeight());
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs(rightAnswer - falseAnswer)%question.size())), Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + rightAnswer), Gdx.graphics.getWidth() / 4 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs(rightAnswer + falseAnswer)%question.size())), Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs((rightAnswer +1) * falseAnswer)%question.size())), Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					break;
				case 2:
					answerTrue = new Rectangle(Gdx.graphics.getWidth() / 2, (int) tubeY, answer.getWidth(), answer.getHeight());
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs(rightAnswer - falseAnswer)%question.size())), Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs(rightAnswer + falseAnswer)%question.size())), Gdx.graphics.getWidth() / 4 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + rightAnswer), Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs((rightAnswer +1) * falseAnswer)%question.size())), Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					break;
				case 3:
					answerTrue = new Rectangle(Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4, (int) tubeY, answer.getWidth(), answer.getHeight());
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs(rightAnswer - falseAnswer)%question.size())), Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs(rightAnswer + falseAnswer)%question.size())), Gdx.graphics.getWidth() / 4 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + (Math.abs((rightAnswer +1) * falseAnswer)%question.size())), Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
					trueAnswer.draw(batch,"" +  answerQuestion.get("" + rightAnswer), Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 4 + Gdx.graphics.getWidth() / 16, (int) tubeY + Gdx.graphics.getWidth() / 8);
			}
			questionText.draw(batch,"" +  question.get("" + rightAnswer), Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight() - 120);
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
	public void playQuestion(){
		question = new HashMap();
		answerQuestion = new HashMap();
		question.put("0","57 + 29 = ?");
		answerQuestion.put("0", "86");
		question.put("1","38 - 19 = ?");
		answerQuestion.put("1", "19");
		question.put("2","7 * 20 = ?");
		answerQuestion.put("2", "140");
		question.put("3","114 + 8 = ?");
		answerQuestion.put("3", "122");
		question.put("4","270 - 5 = ?");
		answerQuestion.put("4", "265");
		question.put("5","0 * 12 = ?");
		answerQuestion.put("5", "0");
		question.put("6","1065 + 0 = ?");
		answerQuestion.put("6", "1065");
		question.put("7","260 - 37 = ?");
		answerQuestion.put("7", "223");
		question.put("8","7 * 19 = ?");
		answerQuestion.put("8", "133");
		question.put("9","38 + 253 = ?");
		answerQuestion.put("9", "291");
		question.put("10","232 - 140 = ?");
		answerQuestion.put("10", "92");
		question.put("11","48 : 3 = ?");
		answerQuestion.put("11", "16");
		question.put("12","317 - 0 = ?");
		answerQuestion.put("12", "317");
		question.put("13","182 + 74 = ?");
		answerQuestion.put("13", "256");
		question.put("14","9 * 14 = ?");
		answerQuestion.put("14", "126");
		question.put("15","1000 + 67 = ?");
		answerQuestion.put("15", "1067");
		question.put("16","400 - 34 = ?");
		answerQuestion.put("16", "366");
		question.put("17","8100 : 9 = ?");
		answerQuestion.put("17", "900");
		question.put("18","1372 + 500 = ?");
		answerQuestion.put("18", "1872");
		question.put("19","150 - 162 = ?");
		answerQuestion.put("19", "-12");
		question.put("20","184 * 0 = ?");
		answerQuestion.put("20", "0");
		question.put("21","42 + 70 = ?");
		answerQuestion.put("21", "112");
		question.put("22","4000 - 29 = ?");
		answerQuestion.put("22", "3971");
		question.put("23","0 : 37 = ?");
		answerQuestion.put("23", "0");
		question.put("24","805 + 26 = ?");
		answerQuestion.put("24", "831");
		question.put("25","1000 - 123 = ?");
		answerQuestion.put("25", "877");
		question.put("26","19 * 90 = ?");
		answerQuestion.put("26", "1710");
		question.put("27","2009 + 54 = ?");
		answerQuestion.put("27", "2063");
		question.put("28","117 - 48 = ?");
		answerQuestion.put("29", " 69");
		question.put("29","460 : 20 = ?");
		answerQuestion.put("29", "23");



		question.put("30","0,68 * 2 = ?");
		answerQuestion.put("30", "1,36");
		question.put("31","5,2 - 3,35 = ?");
		answerQuestion.put("31", "1,85");
		question.put("32","20,7 : 0,1 = ?");
		answerQuestion.put("32", "207");
		question.put("33","1(7/9) + 4 = ?");
		answerQuestion.put("33", "5(7/9)");
		question.put("34","2,52 : 3 = ?");
		answerQuestion.put("34", "0,84");
		question.put("35","4,93 - 3,1 = ?");
		answerQuestion.put("35", "1,83");
		question.put("36","5,23 * 0,1 = ?");
		answerQuestion.put("36", "0,523");
		question.put("37","4(4/7) - 3 = ?");
		answerQuestion.put("37", "1(4/7)");
		question.put("38","10,6 : 10 = ?");
		answerQuestion.put("38", "1,06");
		question.put("39","9,4 + 5,27 = ?");
		answerQuestion.put("39", "14,67");
		question.put("40","3,02 : 0,01 = ?");
		answerQuestion.put("40", "302");
		question.put("41","11 - 9(2/5) = ?");
		answerQuestion.put("41", "1,6");
		question.put("42","0,12 * 0,8 = ?");
		answerQuestion.put("42", "0,096");
		question.put("43","4,36 + 9,2 = ?");
		answerQuestion.put("43", "13,56");
		question.put("44","0,01 * 10,4 = ?");
		answerQuestion.put("44", "0,104");
		question.put("45","6(2/5) - 4(1/5) = ?");
		answerQuestion.put("45", "2(1/5)");
		question.put("46","2,22 : 6 = ?");
		answerQuestion.put("46", "0,37");
		question.put("47","15,17 - 7,1 = ?");
		answerQuestion.put("47", "8,07");
		question.put("48","2,41 : 0,001 = ?");
		answerQuestion.put("48", "2410");
		question.put("49","111 - 11(2/11) = ?");
		answerQuestion.put("49", "99(9/11)");
		question.put("50","2,4 * 0,05 = ?");
		answerQuestion.put("50", "0,12");
		question.put("51","1,3 + 4,14 = ?");
		answerQuestion.put("51", "5,44");
		question.put("52","50,7 * 0,01 = ?");
		answerQuestion.put("52", "0,507");
		question.put("53","6(5/9) + 3(4/9) = ?");
		answerQuestion.put("53", "10");
		question.put("54","3,2 : 20 = ?");
		answerQuestion.put("54", "0,16");
		question.put("55","6,2 + 4,09 = ?");
		answerQuestion.put("55", "10,29");
		question.put("56","6,21 : 0,01 = ?");
		answerQuestion.put("56", "621");
		question.put("57","9(1/9) + 7(2/9) = ?");
		answerQuestion.put("57", "16(3/9)");
		question.put("58","7,2 : 1,2 = ?");
		answerQuestion.put("58", "6");
		question.put("59","2,82 + 3,1 = ?");
		answerQuestion.put("59", "5,92");
		question.put("60","0,24 * 0,01 = ?");
		answerQuestion.put("60", "0,0024");
		question.put("61","20(7/8) - 8(3/8) = ?");
		answerQuestion.put("61", "12(1/2)");
		question.put("62","0,72 : 2,4 = ?");
		answerQuestion.put("62", "0,3");
		question.put("63","5,47 + 38 = ?");
		answerQuestion.put("63", "43,47");
		question.put("64","20,5 : 0,01 = ?");
		answerQuestion.put("64", "2050");
		question.put("65","3(3/8) + 6(7/8) = ?");
		answerQuestion.put("65", "10(1/4)");
		question.put("66","42 * 0,4 = ?");
		answerQuestion.put("66", "16,8");
		question.put("67","9,8 + 6,63 = ?");
		answerQuestion.put("67", "16,43");
		question.put("68","402,4 * 0,001 = ?");
		answerQuestion.put("68", "0,4024");
		question.put("69","5 - 2(2/7) = ?");
		answerQuestion.put("69", "2(5/7)");







		question.put("7","1/3 + 1/2 = ?");
		answerQuestion.put("7", "5/6");
		question.put("8","1/6 * 5/7 = ?");
		answerQuestion.put("8", "5/42");
		question.put("9","3,2 - 1/2 = ?");
		answerQuestion.put("9", "2,7");
		question.put("10","1/3 : 0,25 = ?");
		answerQuestion.put("10", "1(1/3)");
		question.put("18","5/22 - 2/11 = ?");
		answerQuestion.put("18", "1/22");
		question.put("19","3/4 * 1/6 = ?");
		answerQuestion.put("19", "3/24");
		question.put("20","1,4 - 1/5 = ?");
		answerQuestion.put("20", "1,2");
		question.put("21","3(1/3) * 0,3 = ?");
		answerQuestion.put("21", "1");
		question.put("29","9/14 - 3/7 = ?");
		answerQuestion.put("29", "3/14");
		question.put("30","4/7 : 9/14 = ?");
		answerQuestion.put("30", "8/9");
		question.put("31","2(1/3) + 1,2 = ?");
		answerQuestion.put("31", "3(8/15)");
		question.put("32","1(1/14) * 0,7 = ?");
		answerQuestion.put("32", "15/98");
		question.put("40","1/6 + 1(1/3) = ?");
		answerQuestion.put("40", "1,5");
		question.put("41","2(1/3) : 7/9 = ?");
		answerQuestion.put("41", "3");
		question.put("42","0,4 + 2(2/7) = ?");
		answerQuestion.put("42", "2(24/35");
		question.put("43","0,9 : 1/9 = ?");
		answerQuestion.put("43", "8,1");



	}
	@Override
	public void dispose () {
	}
	// help me
	// commit and push
}
