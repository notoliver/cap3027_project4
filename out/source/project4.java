import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class project4 extends PApplet {



ControlP5 cp5;
int rows;
int columns;
Camera cam = new Camera();
public void mouseWheel(MouseEvent event) {
  float e = event.getCount();
  cam.Zoom(e);
}
public void keyReleased(){
  if(key==ENTER){
    print("run the shit");
  }
}
public void mouseDragged(){
  print(p5.isMouseOver());
  print("what");  
}

public void setup() {
  background(0);
  
  
  perspective(radians(90), (float)width/(float)height, 0.1f, 500);
  gui();
}

public void gui() {
  cp5 = new ControlP5(this);
  cp5.addSlider("rows")
     .setPosition(10,10)
     .setSize(150,10)
     .setRange(1,100)
     .setValue(10)
  ;
  cp5.addSlider("columns")
     .setPosition(10,30)
     .setSize(150,10)
     .setRange(1,100)
     .setValue(10)
  ;
  cp5.addSlider("terrainSize")
     .setPosition(10,50)
     .setSize(150,10)
     .setRange(1,100)
     .setValue(10)
     .setCaptionLabel("terrain size")
  ;
  cp5.addButton("generate")
     .setPosition(10,80)
     .setSize(80,20)
  ;
  cp5.addTextfield("filename")
     .setCaptionLabel("load from file")
     .setPosition(10,110)
     .setSize(250,20)
     .setValue("")
     .setAutoClear(false)
  ;
  cp5.addToggle("stroke")
     .setPosition(300,10)
     .setSize(40,20)
     .setValue(true);
  ;
  cp5.addToggle("colour")
     .setPosition(350,10)
     .setSize(40,20)
     .setCaptionLabel("color")
  ;
  cp5.addToggle("blend")
     .setPosition(400,10)
     .setSize(40,20)
  ;
  cp5.addSlider("heightMod")
     .setPosition(300,60)
     .setSize(100,10)
     .setRange(-5,5)
     .setValue(1)
     .setCaptionLabel("height modifier")
  ;
  cp5.addSlider("snowThresh")
     .setPosition(300,80)
     .setSize(100,10)
     .setRange(1,5)
     .setValue(5)
     .setCaptionLabel("snow threshold")
  ;
}
public void rows(int theValue) {rows = theValue;}
public void columns(int theValue) {columns = theValue;}

public void draw() {
  background(0);
  cam.Update();
  camera(cam.camX, cam.camY, cam.camZ, cam.lookAtX, cam.lookAtY, cam.lookAtZ, 0, 1, 0);
  
  for(int i=-100;i<=100;i+=10){
    if(i==0){
       stroke(0,0,255); 
       line(i,0,100,i,0,-100);
       stroke(255,0,0); 
       line(-100,0,i,100,0,i);
    }
    else{
      stroke(255);
      line(i,0,100,i,0,-100);
      line(-100,0,i,100,0,i);
    }
  }
  
  camera();
  perspective();
}

class Camera{
  int currentTarget;
  float camX;
  float camY;
  float camZ;
  float radius;
  float theta;
  float phi;
  float lookAtX;
  float lookAtY;
  float lookAtZ;
  ArrayList<PVector> targets;
  public void Update(){
    camX = radius*cos(phi)*sin(theta)+lookAtX;
    camY = radius*cos(theta)+lookAtY;
    camZ = radius*sin(theta)*sin(phi)+lookAtZ;
    theta = radians(map(mouseY,0,height-1,1,179));
    phi = radians(map(mouseX,0,width-1,0,360));
  }
  public void AddLookAtTarget(PVector target){
    targets.add(target);
  }
  public void CycleTarget(){
    if(currentTarget==3){
      currentTarget=0;
      return;
    }
    currentTarget++;
  }
  public void Zoom(float zoomFac){
    if(zoomFac>0){//scrolling down
      if(radius<200){
        radius+=5;
      }
    }else if(zoomFac<0){
      if(radius>30){
        radius-=5;
      }
    }
  }
  Camera(){
    currentTarget = 0;
    targets = new ArrayList<PVector>();
    theta = 5*PI/4;
    phi = PI/2;
    radius = 100;//30-200
    lookAtX = 0;
    lookAtY = 0;
    lookAtZ = 0;
  }
}
  public void settings() {  size(1200, 800, P3D);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "project4" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
