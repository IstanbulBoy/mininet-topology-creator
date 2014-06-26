
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class PhysicalInterfaceComponent extends DraggableComponent{

	private static final long serialVersionUID = 1L;

	//private LinkedList<PortComponent> PortsList;

	public PhysicalInterfaceComponent(String componentName, int componentX,int componentY, int componentWidth, int componentHeight,JPanel componentWindow,JPanel componentOriginWindow,boolean componentOut,int componentIndex,boolean isMenu) {
		super(componentName, componentX, componentY, componentWidth, componentHeight,componentWindow,componentOriginWindow,componentOut,componentIndex,isMenu);
    	this.ComponentType="PhysicalInterface";
		setImage(false);
    	PortsList = new LinkedList<PortComponent>();

    	
    	this.createInformations();
    	if(!this.isInMenu){
    	Font font = new Font("Arial",Font.BOLD,11);
    	JLabel componentNetworkLabelName = new JLabel();
    	componentNetworkLabelName.setBounds(22,42, 60, 10);
    	componentNetworkLabelName.setFont(font);
    	componentNetworkLabelName.setText(this.ComponentName);
    	this.add(componentNetworkLabelName);}
	}
	
	  //The paint function which is automatically called
	  @Override protected void paintComponent(Graphics g){
		  super.paintComponent(g);
		  if(isInMenu){g.drawImage(ComponentImage, 0, 0,40, 40,null);}
		  else{g.drawImage(ComponentImage, 10, 0,40, 40,null);}   
	  }   
	 @Override protected void setImage(boolean clicked){
		 String image;
		 if(clicked){image="interface_clicked.png";}
		 else{image="interface_not_clicked.png";}
		 try{ComponentImage = ImageIO.read(new File(image));}
		 catch (IOException e) { e.printStackTrace();} 
		 this.repaint();
	}

		
	public LinkedList<PortComponent> getPortsList() {return PortsList;}
	public void setPortsList(LinkedList<PortComponent> portsList) {PortsList = portsList;}
	 
	public void createInformations()
	{
		int SpaceHeight=25;
		int yPos=0;
		//Component type
		JLabel componentLabelType = new JLabel();
		componentLabelType.setBounds(0,yPos, 50, 15);
		componentLabelType.setText("Type :");
		JLabel componentTextType = new JLabel();
    	componentTextType.setBounds(50, yPos, 450, 15);
    	componentTextType.setText(this.getComponentType());
    	this.ComponentInformations.add(componentLabelType);
    	this.ComponentInformations.add(componentTextType);
    	
    	yPos+=SpaceHeight;
		//Component name
		JLabel componentLabelName = new JLabel();
    	componentLabelName.setBounds(0,yPos, 50, 15);
    	componentLabelName.setText("Name :");
    	JTextField componentTextName = new JTextField();
    	componentTextName.setBounds(50, yPos, 450, 15);
    	componentTextName.setText(this.getComponentName());
    	this.ComponentInformations.add(componentLabelName);
    	this.ComponentInformations.add(componentTextName);
    	
    	yPos+=SpaceHeight;
    	//Ports description
    	final JLabel componentLabelPortsList = new JLabel();
    	componentLabelPortsList.setBounds(0,yPos, 50, 15);
    	componentLabelPortsList.setText("Ports :");
    	final JComboBox<String> componentPortsList = new JComboBox<String>();
    	componentPortsList.setBounds(50,yPos,450,20);
    	for(int i=0;i<this.PortsList.size();i++){
    		componentPortsList.addItem("Port " + PortsList.get(i).getPortNumber());
    	}
    	this.ComponentInformations.add(componentLabelPortsList);
    	this.ComponentInformations.add(componentPortsList);
    	
    	
    	
    	yPos+=SpaceHeight;
    	JLabel componentLabelPortsName = new JLabel();
    	componentLabelPortsName.setBounds(0,yPos,500,15);
    	if(PortsList.size()>0){
    		componentLabelPortsName.setText("Port " + componentPortsList.getSelectedIndex() + " linked to "  + PortsList.get(componentPortsList.getSelectedIndex()).getAttachement().ComponentName);
    	}
    	else{
    		componentLabelPortsName.setText("No port");
    	}
    	this.ComponentInformations.add(componentLabelPortsName);

    	yPos=5*SpaceHeight;
    	
    	//Button port modification
    	JButton modifyPort = new JButton();
    	modifyPort.setBounds(0,yPos-5,200,15);
    	modifyPort.setText("Change port");
    	this.ComponentInformations.add(modifyPort);
    	
    	//Button apply
    	JButton apply = new JButton();
    	apply.setBounds(250,yPos-5,200,15);
    	apply.setText("Apply");
    	this.ComponentInformations.add(apply);
    	
    	//Information listener
    	componentTextName.getDocument().addDocumentListener(new DocumentListener() {
    	    @Override
    	    public void changedUpdate(DocumentEvent e) {}
    	    @Override
    	    public void insertUpdate(DocumentEvent e) {}
    	    @Override
    	    public void removeUpdate(DocumentEvent e)  {}	 
    	});
    	//Information listener for PORTS
    	componentPortsList.addActionListener(new ActionListener(){
    		  public void actionPerformed(ActionEvent e) {
    			  updatePortInformations();
    		    }
    	});
    	
    	modifyPort.addActionListener(new ActionListener(){
  		  	public void actionPerformed(ActionEvent e) {
  		  		showPortParametersWindow(PortsList.get(0).getPortNumber(), componentPortsList, componentLabelPortsList);
  		  		}
    	});
    	
    	apply.addActionListener(new ActionListener(){
  		  	public void actionPerformed(ActionEvent e) {
  	    		if (!errors()){updateSmallInformations();}
  	    		updateInformations();
  		  		}
    	});
	}
	
	public void showPortParametersWindow(int port, JComboBox<String> componentPortsList, JLabel componentLabelPortsName){
		new ModifyPortsWindow(250,115,null,(DraggableComponent)this,port,componentPortsList, componentLabelPortsName);
	}
	protected boolean errors() {
		if((((JTextField)this.ComponentInformations.getComponent(3)).getText().length()<1)
				|| Window.componentNamed(((JTextField)this.ComponentInformations.getComponent(3)).getText())!=null&& !((JTextField)this.ComponentInformations.getComponent(3)).getText().equals(this.getComponentName())){
			  
			new JOptionPane();;
			JOptionPane.showMessageDialog(null,
					"Error!" + ((((JTextField)this.ComponentInformations.getComponent(3)).getText().length()<1&& !((JTextField)this.ComponentInformations.getComponent(3)).getText().equals(this.getComponentName()))?"\nIncorrect name!":"")
					+ ((Window.componentNamed(((JTextField)this.ComponentInformations.getComponent(3)).getText())!=null)?"Name already used!":"")
					, "Export", JOptionPane.WARNING_MESSAGE);
			return true;
		}
		else {return false;}
		
	}
	  protected void componentListener(MenuComponentListener l){
		  this.listener = l;
	  }
	  public void updatePortInformations(){
		 	if((this).getPortsList().size()>0){
		 		if (((JComboBox<?>)ComponentInformations.getComponent(5)).getSelectedIndex()==this.getPortsList().size()) {
		 			((JLabel)ComponentInformations.getComponent(6)).setText("Port " + this.getPortsList().get(((JComboBox<?>)ComponentInformations.getComponent(5)).getSelectedIndex()-1).getPortNumber() + 
	    				" linked to "+ this.getPortsList().get(((JComboBox<?>)ComponentInformations.getComponent(5)).getSelectedIndex()-1).getAttachement().getComponentName());}
		 		else {
		 			((JLabel)ComponentInformations.getComponent(6)).setText("Port " + this.getPortsList().get(((JComboBox<?>)ComponentInformations.getComponent(5)).getSelectedIndex()).getPortNumber() + 
	    				" linked to "+ this.getPortsList().get(((JComboBox<?>)ComponentInformations.getComponent(5)).getSelectedIndex()).getAttachement().getComponentName());
	    	
		 		}
		 	}
		}
		  public void updateInformations(){
			  this.ComponentInformations.removeAll();
			  this.createInformations();
		  }
		  public void updateSmallInformations(){
			  this.setComponentName(((JTextField)this.ComponentInformations.getComponent(3)).getText());
			  ((JLabel)this.getComponent(0)).setText(((JTextField)this.ComponentInformations.getComponent(3)).getText());
		  }
	  public int requestPort(){
		  this.createPort();
		  return (this.PortsList.size()-1);
	  }
	  public void createPort(){
		  PortComponent newport = new PortComponent(PortsList.size());
		  PortsList.add(newport);
	  }
	  public void listAllPorts(){
		  if(PortsList.size()>0){
		  for(int i=0;i<PortsList.size();i++){
			  System.out.print("\nPort " +i+ "::");
			  if(!PortsList.get(i).isUsed()){System.out.print("unused");}
			  else{System.out.print("used");}
		  }
		  }
	  }
	  public void setPort(int index,Component p, DrawedLinkComponent d){
		  PortsList.get(index).setAttachement(p);
		  PortsList.get(index).setLink(d);
		  PortsList.get(index).setUsed(true);
		  this.updateInformations();
	  }
	  protected void updatePort(int index){
		  System.out.println("trying to delete port "+index);
			if (PortsList.size()>=1){PortsList.remove(index);System.out.println("port "+index +" deleted");}
			else {PortsList=new LinkedList<PortComponent>();}
	  }
	  public void exportXML(FileWriter fw) {
		  try {
			  fw.write("\t<PhysicalInterface>\n");
			  fw.write("\t\t<Position>\n\t\t\t<X>"+this.getX()+"</X>\n\t\t\t<Y>"+this.getY()+"</Y>\n\t\t</Position>\n");
			  fw.write("\t\t<ComponentName>"+this.getComponentName()+"</ComponentName>\n");
			  fw.write("\t</PhysicalInterface>\n");
		  } catch (IOException e) {
				e.printStackTrace();
		  }
	  }
	
}
