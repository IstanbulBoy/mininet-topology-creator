import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.util.LinkedList;
import javax.swing.JComponent;


public abstract class Component extends JComponent {
	



	private static final long serialVersionUID = 1L;
	protected int index;
	protected String ComponentName;
	protected WindowPanel ComponentInformations;	
	protected String ComponentType;	
	protected boolean Clicked=false;
	protected BufferedImage ComponentImage;

	Component(String componentName,int componentIndex){
		this.ComponentName=componentName;
	  	this.index=componentIndex;
	  	this.ComponentInformations = new WindowPanel(0,0,500,200,null,null);
	}
	public void finalize(){}
	
	public WindowPanel getComponentInformations() {return ComponentInformations;}
	public void setComponentInformations(WindowPanel componentInformations) {ComponentInformations = componentInformations;}
	
	public BufferedImage getComponentImage() {return ComponentImage;}
	protected void setComponentImage(BufferedImage componentImage) {ComponentImage = componentImage;}
	protected void setImage(boolean b) {}
	
	protected int getIndex(){return this.index;}
	public void setIndex(int index) {this.index = index;}
	
	protected String getComponentType(){return this.ComponentType;}
	public void setComponentType(String componentType) {ComponentType = componentType;}
	
	protected String getComponentName(){return this.ComponentName;}
	protected void setComponentName(String newName){this.ComponentName=newName;}
	
	protected boolean getClicked() {return this.Clicked;}
	protected void setClicked(boolean isClicked){this.Clicked=isClicked;}
	
	protected LinkedList<PortComponent> getPortsList(){return null;}
	
	protected void exportXML(FileWriter fw){}
	
	
	
}
