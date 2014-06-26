import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import java.awt.Cursor;
import java.util.LinkedList;


public abstract class DraggableComponent extends Component {
	private static final long serialVersionUID = 1L;
	
	//DIMENSIONS
	protected int origx;
	protected int origy;
	protected int width;
	protected int height;
	//DEFINE DROP SPACE AND DRAG POSSIBILITIES
	private Point wpos;
	private Dimension wsize;
	private Point owpos;
	private Dimension owsize;
	private boolean out;
	private boolean dropable=false;
	private boolean dropped=false;
	//DROPPING
	private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;
    private volatile int deltaX=0;
    private volatile int deltaY=0;
	//INFORMATIONS
    protected boolean isInMenu=false;
	//LISTENERS
	MenuComponentListener listener;
	
	protected LinkedList<PortComponent> PortsList;
	
	/*Here is the builder
	Each component has a name, an initial (x,y) position, a size.
	You can drag it in his original window (componentOriginWindow) : The boolean componentOut sets to false.
	Or make it draggable in a larger window (componentWindow) and have a signal when the component is leaving his original window : The boolean componentOut sets to true.
	*/

	public DraggableComponent(String componentName,int componentX, int componentY,int componentWidth, int componentHeight, 
		  JPanel componentWindow, JPanel componentOriginWindow, boolean componentOut, 
		  int componentIndex,boolean isMenu) {
	  	super(componentName,componentIndex);
		this.origx=componentX;
		this.origy=componentY;
		this.width=componentWidth;
		this.height=componentHeight;
	  	this.isInMenu=isMenu;
	  	this.out=componentOut;
	  	this.setBounds(origx,origy,width,height);
	  	
	  	PortsList = new LinkedList<PortComponent>();
	  	
    	//We link the component to the window
	  	setOriginWindow(componentOriginWindow);
	  	if(out){setWindow(componentWindow);}
	  	else{setWindow(componentOriginWindow);}

	  	addMouseListener(new MouseListener() {
		  @Override public void mouseClicked(MouseEvent e) {}
	      @Override public void mousePressed(MouseEvent e) {
	    	  
			if(dropped){updateInformations();
				changeInformationPanel();}
			if(isInMenu){listener.unselectAllNonDraggableComponent();}
			screenX = e.getXOnScreen();
			screenY = e.getYOnScreen();
			myX = getX();
			myY = getY();
	      }
	      @Override public void mouseReleased(MouseEvent e) { 
	    	  Point dropPosition=new Point();
	    	  dropPosition.x=myX+deltaX;
	    	  dropPosition.y=myY+deltaY;
	    	  if(dropable && out){
	    	  dropItem(dropPosition);
	    	  setLocation(origx, origy);
	    	  dropable=false;}
	    	  if(out && !dropable){setLocation(origx, origy);}
	      }
	      @Override public void mouseEntered(MouseEvent e){ 
	    	  setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    	  inItem(true);  
	      }
	      @Override public void mouseExited(MouseEvent e){inItem(false);}
	  	});
    
    addMouseMotionListener(new MouseMotionListener() {
    	
      @Override
      public void mouseDragged(MouseEvent e) {
    	listener.actualize();  
    	deltaX = e.getXOnScreen() - screenX; 
        deltaY = e.getYOnScreen() - screenY;
	    	//The component can't get out of the specified window
	        if((myX + deltaX + width) > wsize.getWidth()+wpos.x){
	  
	        	if((myY + deltaY)<0){
	        		setLocation((int)wsize.getWidth()-width+wpos.x, 0);
	        	}
	        	else if((myY + deltaY + height) > wsize.getHeight()){
	        		setLocation((int)wsize.getWidth()-width+wpos.x, (int)wsize.getHeight()-height);
	        	}
	        	else{
	        		setLocation((int)wsize.getWidth()-width+wpos.x, myY + deltaY);
	        	}
	        }
	        else if((myX + deltaX-wpos.x)<0){
	        	if((myY + deltaY)<0){
	        		setLocation(wpos.x, 0); 
	        	}
	        	else if((myY + deltaY + height) > wsize.getHeight()){
	        		if(!out){
	        		setLocation(wpos.x, (int)wsize.getHeight()-height); }
	        	}
	        	else{
	        		setLocation(wpos.x, myY + deltaY); 
	        	}
	        
	        }
	        else if((myY + deltaY)<0){
	        	setLocation(myX + deltaX, 0); 
	    
	        }
	        else if((myY + deltaY + height) > wsize.getHeight()){
	        	setLocation(myX + deltaX, (int)wsize.getHeight()-height);
	    
	        }
	        else{
	        	setLocation(myX + deltaX,myY + deltaY);
	        }
        
        //Is the component out of his original drag window ?
	    //It's not saying OUT if the component (at least the mouse) is out of the drag window, which is larger than the original
	        if(out){listener.changeColor(dropable);}
	        if(myY + deltaY-wpos.y>0 && myX + deltaX>wpos.x && myX + deltaX<wpos.x+wsize.width-width && (myY + deltaY)<wpos.y+wsize.height)
	        {		        
		        if((myX + deltaX+width<owpos.x && myX + deltaX>0)){
		        	dropable=true;}
		        else if(myX + deltaX>(int)owsize.getWidth()+owpos.x-width){
		        	dropable=true;}
		        else if((myY + deltaY)<owpos.y-height){
		        	dropable=true;}
		        else if((myY + deltaY)>owpos.y+owsize.height){
		        	dropable=true;}
		        else{
		        	dropable=false;}
	        }
	        else{
	        	dropable=false;
	        }
        }

      @Override
      public void mouseMoved(MouseEvent e) { }

    });
  
  }
  public void finalize(){}
  
  private void setWindow(JPanel componentWindow){
	  this.wsize=componentWindow.getSize();
	  this.wpos=componentWindow.getLocation();
  }
  private void setOriginWindow(JPanel componentWindow){
	  this.owsize=componentWindow.getSize();
	  this.owpos=componentWindow.getLocation();
  }
  protected void draggableListener(MenuComponentListener l){
	  this.listener = l;
  }
  protected void componentListener(MenuComponentListener l){
	  
  }
  public boolean isDropped(){
	  return this.dropped;
  }
  public void setDropped(boolean isDropped){
	  this.dropped=isDropped;
  }
  public void changeInformationPanel(){
	  listener.printInformations(this);
  }
  public void inItem(boolean in){
	  if(in){
		  if(this.dropped && !this.Clicked){
			  listener.changeIndication("Click on the component to get informations",0);
			  this.setImage(true);}
		  else if(!this.dropped){
			  listener.changeIndication("Drag and drop component to the network window to add a "+ this.getComponentName(),0);
			  this.setImage(true);}
		  }
	  else{
		  listener.changeIndication("",0);
		  if(this.dropped && this.Clicked)
		  {}
		  else{ this.setImage(false);}
	  }
  }
  public void dropItem(Point dropPosition){
	  listener.drop(this,dropPosition);
  }
  @Override protected void setImage(boolean isClicked){}
  protected void setPort(int index,Component p, DrawedLinkComponent d){}
  protected int requestPort(){return 0;}
  
  protected void updatePort(int index){
	  System.out.println("trying to delete port "+index);
		if (PortsList.size()>=1){PortsList.remove(index);System.out.println("port "+index +" deleted");}
		else {PortsList=new LinkedList<PortComponent>();}
  }
  
  protected void updateInformations(){}
  
  public void removeComponent(){
	  this.setComponentInformations(null);
	  this.setComponentImage(null);
	  this.setLocation(-100, -100); //TODO find a better way to get rid of the component that left after the deletion...
	  
	  repaint();
  }
  public LinkedList<PortComponent> getPortsList(){return this.PortsList;}
  
  protected void changePort(int oldPort, int newPort){
	  for (int j=0;j<getPortsList().size();j++){					
			if (getPortsList().get(j).getPortNumber()==oldPort) getPortsList().get(j).setPortNumber(newPort);					
	  }
  }
  protected boolean portUsed(int portNb){
	  for (int j=0;j<getPortsList().size();j++){					
			if (getPortsList().get(j).getPortNumber()==portNb) return true;					
		}
	  return false;
  }
  protected void updatePortInformations(){}
}
