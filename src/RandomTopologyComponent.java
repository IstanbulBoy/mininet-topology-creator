import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import javax.imageio.ImageIO;


public class RandomTopologyComponent extends NotDraggableComponent{


	private static final long serialVersionUID = 1L;
	private String predefined;
	private int arg1, arg2;


	public RandomTopologyComponent(String componentName, int componentX,int componentY, int componentWidth, int componentHeight,boolean componentOut,int componentIndex,boolean isMenu) {
		super(componentName, componentX, componentY, componentWidth, componentHeight,componentIndex,isMenu);
    	this.ComponentType="Random topology";
    	this.predefined="simple";
		setImage(false);
    	
	  addMouseListener(new MouseListener() {
	      @Override public void mouseClicked(MouseEvent e) {}
	      @Override public void mousePressed(MouseEvent e) {
	    	  showParametersWindow();
	    	  
	      }
	      @Override public void mouseReleased(MouseEvent e) {}
	      @Override public void mouseEntered(MouseEvent e) { 
	    	  
	      }
	      @Override public void mouseExited(MouseEvent e){inItem(false);}
	    });
    	
	}
	public String getPredefined() {return predefined;}
	public void setPredefined(String predefined) {this.predefined = predefined;}
	public int getArg1(){return this.arg1;}
	public void setArg1(int arg){this.arg1=arg;}
	public int getArg2(){return this.arg2;}
	public void setArg2(int arg){this.arg2=arg;}
	
	  //The paint function which is automatically called
	 @Override protected void paintComponent(Graphics g){
		  super.paintComponent(g);
		  if(isInMenu){g.drawImage(ComponentImage, 0, 0,40, 40,null);}
		  else{g.drawImage(ComponentImage, 10, 0,40, 40,null);}   
	  }   
	 @Override protected void setImage(boolean b){
		 String image;
		 if(b){image="topo_clicked.png";}
		 else{image="topo_not_clicked.png";}
		 try{ComponentImage = ImageIO.read(new File(image));}
		 catch (IOException e) { e.printStackTrace();} 
		 this.repaint();
		
	}
	  protected void componentListener(MenuComponentListener l){
		  this.listener = l;
	  }
	  protected void inItem(boolean in){
		  if(in){
			  listener.changeIndication("Click to generate a topology",0);
			  this.setImage(true);}
		  else{
			  this.setImage(false);
		  }
	  }
	  protected void setClicked(boolean b){
		  this.Clicked=b;
	  }
	  public void showParametersWindow(){
		  @SuppressWarnings("unused")
		RandomTopologyComponentWindow params = new RandomTopologyComponentWindow(300,300,null,this);
	  }
	  public void createTopology(){
		  int j=0;
		  int k=Window.droppedComponentList.size()+1;
		  ControllerComponent newController=new ControllerComponent("c"+Window.countCompTypeDropped("Controller"),10+arg1*60/2,10,60,65,Window.leftMenu,Window.leftMenu,false,Window.droppedComponentList.size(),false);
		  listener.createDroppedComponent(newController);
		  
		  if (predefined.equals("single")){
			  SwitchComponent newSwitch=new SwitchComponent("s"+Window.countCompTypeDropped("Switch"),5+arg1*50/2,100,60,65,Window.leftMenu,Window.leftMenu,false,Window.droppedComponentList.size(),false);
			  listener.createDroppedComponent(newSwitch);
			  listener.traceLinkComponent(newSwitch,newController);
			  for(int i=0;i<arg1;i++){
				  HostComponent newHost = new HostComponent("h"+Window.countCompTypeDropped("Host"),10+i*60,200,60,65,Window.leftMenu,Window.leftMenu,false,Window.droppedComponentList.size(),false);
				  listener.createDroppedComponent(newHost);
				  listener.traceLinkComponent(newSwitch,newHost);
			  }

		  }
		  else if (predefined.equals("linear")){
			  //Components
			  for(int i=0;i<arg1;i++){
				  SwitchComponent newSwitch=new SwitchComponent("s"+Window.countCompTypeDropped("Switch"),10+j,100,60,65,Window.leftMenu,Window.leftMenu,false,Window.droppedComponentList.size(),false);
				  listener.createDroppedComponent(newSwitch);
				  HostComponent newHost = new HostComponent("h"+Window.countCompTypeDropped("Host"),10+j,200,60,65,Window.leftMenu,Window.leftMenu,false,Window.droppedComponentList.size(),false);
				  listener.createDroppedComponent(newHost);
				  listener.traceLinkComponent(newSwitch,newHost);
				  listener.traceLinkComponent(newSwitch,newController);
				  j=j+60;
			  }
		  
			  //Links
			  for(int i=1;i<arg1;i++){
				  listener.traceLinkComponent(Window.droppedComponentList.get(k),Window.droppedComponentList.get(k+4));
				  k=k+4;
			  }
		  }
		  else if (predefined.equals("tree")){
			  SwitchComponent newSwitch=new SwitchComponent("s"+Window.countCompTypeDropped("Switch"),300,10,60,65,Window.leftMenu,Window.leftMenu,false,Window.droppedComponentList.size(),false);
			  listener.createDroppedComponent(newSwitch);
			  listener.traceLinkComponent(newSwitch,newController);
			  createTree(arg1,arg2,newSwitch);
		  }
		  
	  }
	  private void createTree(int depth, int fanout, SwitchComponent father){
		  if (depth==0){
			  for (int i=0;i<fanout;i++){
				  HostComponent newHost = new HostComponent("h"+Window.countCompTypeDropped("Host"),father.getX()-60*(fanout-1)/2+i*60,father.getY()+100,60,65,Window.leftMenu,Window.leftMenu,false,Window.droppedComponentList.size(),false);
				  listener.createDroppedComponent(newHost);
				  listener.traceLinkComponent(newHost,father);
			  }
		  }
		  else{
			  for (int i=0;i<fanout;i++){
				  int power = (int)Math.pow(fanout,depth+1);
				  SwitchComponent newSwitch = new SwitchComponent("s"+Window.countCompTypeDropped("Switch"),father.getX()-(power)*60/2+(power)*60/(fanout)/2+i*(power)*60/(fanout),father.getY()+100,60,65,Window.leftMenu,Window.leftMenu,false,Window.droppedComponentList.size(),false);
				  listener.createDroppedComponent(newSwitch);
				  listener.traceLinkComponent(newSwitch,father);
				  createTree(depth-1,fanout,newSwitch);
			  }
		  }
	  }
	  
}




