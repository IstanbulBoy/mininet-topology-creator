import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;


public class DrawedLinkSpace extends WindowPanel{


	private static final long serialVersionUID = 1L;
	/*private PortComponent portA;
	private PortComponent portB;*/
	private Component compA;
	private Component compB;
	MenuComponentListener listener;
	private Vector<DrawedLinkComponent> linksList;
	/*private Style currentStyle;
	private int thick=5;*/
	private int touched=0;
	
	public DrawedLinkSpace(int panX, int panY, int panDX, int panDY, Color panColor, LayoutManager panLayout){
		super(panX,panY,panDX,panDY,panColor,panLayout);
		this.linksList = new Vector<DrawedLinkComponent>();
	    

		
		addMouseListener(new MouseListener() {
			  @Override public void mouseClicked(MouseEvent e) {

			  }
		      @Override public void mousePressed(MouseEvent e) {
		    	  touched=0;
		    	  int j=0;
		    	  for(int i=0;i<linksList.size();i++){
			    	  if(linksList.get(i).getLine().intersects(e.getX(),e.getY(),10,10)){
			    		  touched++;
			    		  j=i;
		    	  }
		    	  if(touched>0){
		    		  linksList.get(j).updateInformations();
		    		  listener.printInformations(linksList.get(j));
		    		  }
		    	  }
		    	  listener.actualize();
		      }
		      @Override public void mouseReleased(MouseEvent e) { 
		    	  
		      }
		      @Override public void mouseEntered(MouseEvent e){ 
		    	 

		      
		      }
		      @Override public void mouseExited(MouseEvent e){
		    	  
		    	  }
		  	});
	    
	    addMouseMotionListener(new MouseMotionListener() {
	    	
	      @Override
	      public void mouseDragged(MouseEvent e) {

	        }

	      @Override
	      public void mouseMoved(MouseEvent e) { 
	    	  touched=0;
	    	  int j=0;
	    	  for(int i=0;i<linksList.size();i++){
		    	  if(linksList.get(i).getLine().intersects(e.getX(),e.getY(),10,10)){touched++;j=i;}
	    	  }
	    	  if(touched>0){
	    		  setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    		  if(linksList.size()>0){inItem(true,j);}}  
	    	  else{
	    		  if(linksList.size()>0){inItem(false,j);}
	    		  setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    	  }
	    	  listener.actualize();
	      }
	    });
	}

	@Override public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	    super.paint(g2);
	    
	    //g2.drawLine(compA.getLocation().x+30,compA.getLocation().y+30,compB.getLocation().x+30,compB.getLocation().y+30);
	    
	    for(int i=0;i<linksList.size();i++){
	    	linksList.get(i).updateLineCoordinates();
	    	g2.setStroke(new BasicStroke(linksList.get(i).getThickness()));
	    	g2.draw(linksList.get(i).getLine());
	    	add(linksList.get(i).getDescription());
	    }
	    
	}
	  protected void notDraggableListener(MenuComponentListener l){
		  this.listener = l;
	  }
	  public Component getAttachedComponent(int index){
		  if(index==0){return compA;}
		  else{return compB;}
	  }
	  public void changeInformationPanel(){
		  //listener.printInformations(this);
	  }
	  public void addLink(DrawedLinkComponent newLink){
		  linksList.add(newLink);
		  repaint();
	  }
	  public void removeLink(DrawedLinkComponent index){
		  remove(index.getDescription());
		  linksList.remove(index);
		  repaint();
	  }
	  public void inItem(boolean in, int index){
		  if(in)
		  {
			  if(!this.linksList.get(index).Clicked){
				  listener.changeIndication("Click on the component to get informations",0);
				  linksList.get(index).setImage(true);}
		  }
		  else
		  {
			  listener.changeIndication("",0);
			  
			  for(int i=0;i<linksList.size();i++)
			  {
				  if(!linksList.get(i).getClicked()){
				  linksList.get(i).setImage(false);}
    		  }
			  
		  }
	  }

}
