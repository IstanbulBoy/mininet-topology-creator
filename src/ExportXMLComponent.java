import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ExportXMLComponent extends NotDraggableComponent{

	private static final long serialVersionUID = 1L;
	public ExportXMLComponent(String componentName, int componentX,int componentY, int componentWidth, int componentHeight,boolean componentOut,int componentIndex,boolean isMenu) {
		super(componentName, componentX, componentY, componentWidth, componentHeight,componentIndex,isMenu);
    	this.ComponentType="ExportXML";
		setImage(false);
    	
	  addMouseListener(new MouseListener() {
	      @Override public void mouseClicked(MouseEvent e) {}
	      @Override public void mousePressed(MouseEvent e) {
	    	 listener.exportXML();
	    	  
	      }
	      @Override public void mouseReleased(MouseEvent e) {}
	      @Override public void mouseEntered(MouseEvent e) { 
	    	  
	      }
	      @Override public void mouseExited(MouseEvent e){inItem(false);}
	    });
    	
	}
	
	  //The paint function which is automatically called
	  @Override protected void paintComponent(Graphics g){
		  super.paintComponent(g);
		  if(isInMenu){g.drawImage(ComponentImage, 0, 0,40, 40,null);}
		  else{g.drawImage(ComponentImage, 10, 0,40, 40,null);}   
	  }   
	 @Override protected void setImage(boolean b){
		 String image;
		 if(b){image="exportXML_clicked.png";}
		 else{image="exportXML_not_clicked.png";}
		 try{ComponentImage = ImageIO.read(new File(image));}
		 catch (IOException e) { e.printStackTrace();} 
		 this.repaint();
		
	}


	  protected void componentListener(MenuComponentListener l){
		  this.listener = l;
	  }
	  protected void inItem(boolean in){
		  if(in){
			  listener.changeIndication("Click to export the project to a .xml file",0);
			  this.setImage(true);}
		  else{
			  this.setImage(false);
		  }
	  }
	  protected void setClicked(boolean b){
		  this.Clicked=b;
	  }
}
