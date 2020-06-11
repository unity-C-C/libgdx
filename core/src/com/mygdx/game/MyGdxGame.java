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
    float playx=0;
    int score=0;
    int scoreed=0;
    int hp=5;


    class enemy{
        Texture enemyimg;
        int id;
        float x;
        float y;
        float speed;
        enemy(){
            super();
        }

        enemy(int id,Texture texture){
            super();
            this.id=id;
            this.enemyimg=texture;
        }

        float getSpeed(){
            return this.speed;
        }

        void setSpeed(float speed){
            this.speed=speed;
        }

        float getX(){
            return this.x;
        }

        float getY(){
            return this.y;
        }

        void setX(float x){
            this.x=x;
        }

        void setY(float y){
            this.y=y;
        }
    }



    ArrayList<enemy> enemylis=new ArrayList<enemy>();


	@Override
	public void create () {
		batch = new SpriteBatch();
        font = new BitmapFont();
		img = new Texture("play.png");
		img1=new Texture("score.png");

		playimg=new Texture("basket.png");


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
                playx=x/2-50;
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

                font.draw(batch,"return",0,y);

                //Display player information
                font.draw(batch,"HP:"+hp,x-50,y);
                font.draw(batch,"SCORE:"+score,x-80,y-20);


                //Randomly draw bombs and gold coins
                batch.draw(playimg,playx,0);//Draw player

                int N=(int)(Math.random()*100);

                if(N-score>0){
                    int dw=(int)(Math.random()*100);
                    if(dw>97){//Draw bomb
                        enemy jinp = new enemy(0,new Texture("bomb.png"));
                        float xx=(float)(Math.random()*x/2+100);
                        batch.draw(jinp.enemyimg,xx,y);
                        jinp.setSpeed((float)Math.random()*5);
                        jinp.setX(xx);
                        jinp.setY(y);
                        jinp.id=0;
                        enemylis.add(jinp);
                    }else if(dw<1){
                        enemy zhada=new enemy(1,new Texture("gold.png"));
                        float xx = (float) (Math.random()*x/2+100);
                        batch.draw(zhada.enemyimg,xx,y);
                        zhada.setY(y);
                        zhada.setX(xx);
                        zhada.setSpeed((float)Math.random()*5);
                        zhada.id=1;
                        enemylis.add(zhada);
                    }
                }

                move();
                collision();
                victory();

                int mx=Gdx.input.getX();
                int my=Gdx.input.getY();
                my=y-my;

                boolean pan = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

                if(pan){
                    if(mx>0&&mx<100
                            &&my>y-50&&my<y){

                        scene=0;
                        hp=5;
                        enemylis=new ArrayList<enemy>();
                        playx=x/2-50;
                        scoreed=score;
                    }else{
                        if((mx+60)<=x&&
                        mx+10>0){
                            playx=mx;
                        }
                    }
                }


            }break;

            case 2:{
                //View results

                font.draw(batch,"results:"+scoreed,x/2-50,
                        y/2+20);

                font.draw(batch,"return",0,y);

                boolean pan = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
                int mx=Gdx.input.getX();
                int my=Gdx.input.getY();
                my=y-my;
                if(pan){
                    if(mx>0&&mx<100
                    &&my>y-50&&my<y){
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

	//Impact checking
    void collision(){
	    for(int i=0;i<enemylis.size();++i){
	        if(enemylis.get(i).id==0){
                if(rect(i)){
                    hp--;
                    enemylis.remove(i);
                }
            }else if(enemylis.get(i).id==1){
                if(rect(i)){
                    score++;
                    enemylis.remove(i);
                }
            }
        }
    }

    boolean rect(int i){
	    if((playx+10)>=enemylis.get(i).x&&(playx+10)<=enemylis.get(i).x+53
        &&48>=enemylis.get(i).y&&48<=enemylis.get(i).y+48){
	        return true;
        }else if((playx+60)>=enemylis.get(i).x&&(playx+60)<=enemylis.get(i).x+53
        &&48>=enemylis.get(i).y&&48<=enemylis.get(i).y+48){
	        return true;
        }

	    return false;
    }

    //Victory and defeat
    boolean victory(){
	    if(hp<=0){
	        scene=0;
	        hp=5;
	        enemylis=new ArrayList<enemy>();
	        playx=x/2-50;
	        scoreed=score;

	        return true;
        }
	    return false;
    }

    //Gold coins and bombs moving down
    void move(){
       for(int i=0;i<enemylis.size();++i){
           enemylis.get(i).y-=enemylis.get(i).getSpeed();
           batch.draw(enemylis.get(i).enemyimg,enemylis.get(i).x,enemylis.get(i).y);
           if(enemylis.get(i).getY()<0){
              if(enemylis.get(i).id==1){
                  hp-=1;
              }
               enemylis.remove(i);
           }
       }
    }
}
