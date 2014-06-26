import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class LinkComponent extends NotDraggableComponent{

	private static final long serialVersionUID = 1L;
	private Component compA;
	private Component compB;
	/*private int portA;
	private int portB;*/
	
	public LinkComponent(String componentName, int componentX,int componentY, int componentWidth, int componentHeight,boolean componentOut,int componentIndex,boolean isMenu) {
		super(componentName, componentX, componentY, componentWidth, componentHeight,componentIndex,isMenu);
    	this.ComponentType="Link";
		setImage(false);
		this.setClicked(false);
    	
	  addMouseListener(new MouseListener() {
	      @Override public void mouseClicked(MouseEvent e) {}
	      @Override public void mousePressed(MouseEvent e) {
	    	  listener.createLinkComponent();
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
		 if(b){image="link_clicked.png";}
		 else{image="link_not_clicked.png";}
		 try{ComponentImage = ImageIO.read(new File(image));}
		 catch (IOException e) { e.printStackTrace();} 
		 this.repaint();
		
	}

	  protected void componentListener(MenuComponentListener l){
		  this.listener = l;
	  }
	  public void updateInformations(){
		  this.setComponentName(((JTextField)this.ComponentInformations.getComponent(3)).getText());
		  ((JLabel)this.getComponent(0)).setText(((JTextField)this.ComponentInformations.getComponent(3)).getText());
	  }
	  protected void inItem(boolean in){
		  if(in){
			  if(!Clicked){
			  listener.changeIndication("Click and select two components to make a link between them",0);
			  this.setImage(true);}}
		  else{
			  if(!Clicked){listener.changeIndication("",0);}
			  this.setImage(Clicked);
		  }
	  }
	  protected void setAttachedComponent(Component d,int number){
		  if(number==0){this.compA=d;}
		  else if(number==1){this.compB=d;}
	  }
	  protected Component getAttachedComponent(int number){
		  if(number==0){return this.compA;}
		  else if(number==1){return this.compB;}
		  else{return null;}
	  }
	  protected void setClicked(boolean b){
		  this.Clicked=b;
		  this.compA=null;
		  this.compB=null;
	  }
	  
}
