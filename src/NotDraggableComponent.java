import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Cursor;

public class NotDraggableComponent extends Component {
	private static final long serialVersionUID = 1L;
	//DIMENSIONS
	protected int origx;
	protected int origy;
	protected int width;
	protected int height;
	//INFORMATIONS
    protected boolean isInMenu=false;
	//LISTENERS
	MenuComponentListener listener;
	
	/*Here is the builder
	Each component has a name, an initial (x,y) position, a size.
	You can drag it in his original window (componentOriginWindow) : The boolean componentOut sets to false.
	Or make it draggable in a larger window (componentWindow) and have a signal when the component is leaving his original window : The boolean componentOut sets to true.
	*/
  public NotDraggableComponent(String componentName,int componentX, int componentY,int componentWidth, int componentHeight, int componentIndex,boolean isMenu) {
	  	super(componentName,componentIndex);
		this.origx=componentX;
		this.origy=componentY;
		this.width=componentWidth;
		this.height=componentHeight;
	  	this.isInMenu=isMenu;
	  	this.setBounds(origx,origy,width,height);
    	//We create the component
    addMouseListener(new MouseListener() {
      @Override public void mouseClicked(MouseEvent e) {}
      @Override public void mousePressed(MouseEvent e) {
    	  if(isInMenu){
    		  setImage(true);
    		  setClicked(!Clicked);
    	  }
      }
      @Override public void mouseReleased(MouseEvent e) {}
      @Override public void mouseEntered(MouseEvent e) { 
    	  setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	  inItem(true);  
      }
      @Override public void mouseExited(MouseEvent e){inItem(false);}
    });
    
  }
  protected void notDraggableListener(MenuComponentListener l){
	  this.listener = l;
  }
  public void changeInformationPanel(){
	  listener.printInformations(this);
  }
  protected void setImage(boolean clicked){}
  protected void inItem(boolean in){}
  protected void setAttachedComponent(Component d,int number){}
  protected Component getAttachedComponent(int number){return null;}
}
