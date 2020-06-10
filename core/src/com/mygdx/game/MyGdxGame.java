package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Button;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
    BitmapFont font;
	Texture img;//play game image
	Texture img1;//score image

    int x;
    int y;

    int scene=0;//The default is to start the game interface

    //player
    Texture playimg;
    int score=0;
    int scoreed=0;
    int hp=5;


    class zhadan{
        Texture zhadanimg;
        int id=0;

        zhadan(){
            super();
            zhadanimg=new Texture("play.png");
        }
    }

    class jinpi {
        Texture jinpiimg;
        int id=1;

        jinpi(){
            super();
            jinpiimg=new Texture("play.png");
        }
    }

    ArrayList zhadanlis=new ArrayList();


	@Override
	public void create () {
		batch = new SpriteBatch();
        font = new BitmapFont();
		img = new Texture("play.png");
		img1=new Texture("score.png");

		x=Gdx.graphics.getWidth();
		y=Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		switch (scene){
            case 0:{
                batch.draw(img, x/2-50, y/2+50);
                batch.draw(img1, x/2-50, y/2-20);

                boolean pan = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
                if(pan){
                    float mx=Gdx.input.getX();
                    float my=Gdx.input.getY();

                    my=y-my;

                    //test
//                    font.draw(batch, "Mouse/Touch: x=" + mx + "  y=" + my, 20.0f,
//                            y - 20.0f);
//                    font.draw(batch, "Mouse/Touch: x=" + (x/2-50) + "  y=" + (y/2+50), 20.0f,
//                            y - 40.0f);

                    if(mx>x/2-50&&mx<x/2+34&&
                    my>y/2+50&&my<y/2+98){
                        //play game
                        scene=1;
                    }else if(mx>x/2-50&&mx<x/2+34&&
                            my>y/2-20&&my<y/2+28){
                        scene=2;
                    }
                }
            }break;

            case 1:{

                //Draw player, player score, player HP
                //Randomly draw the coins and bombs falling from the sky to get the gold score +1 to get the bomb HP-1 Player HP<=0 The end of the game The more difficult the game is, the greater the difficulty will be. The game will record the score

            }break;

            case 2:{
                //View results

                font.draw(batch,"results:"+scoreed,x/2-50,
                        y/2+20);

                font.draw(batch,"return",0,y-20);

                boolean pan = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
                int mx=Gdx.input.getX();
                int my=Gdx.input.getY();
                my=y-my;
                if(pan){
                    if(mx>0&&mx<40
                    &&my>y-20&&my<y){
                        scene=0;
                    }
                }

            }break;
        }
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		img1.dispose();
		font.dispose();
	}
}
