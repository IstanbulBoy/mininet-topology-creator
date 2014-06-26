
import java.awt.Color;
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
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ControllerComponent extends DraggableComponent{

	private static final long serialVersionUID = 1L;

	private String ip;
	private int listeningPort;
	
	//LISTENERS
	MenuComponentListener listener;
	
	public ControllerComponent(String componentName, int componentX,int componentY, int componentWidth, int componentHeight,JPanel componentWindow,JPanel componentOriginWindow,boolean componentOut,int componentIndex,boolean isMenu) {
		super(componentName, componentX, componentY, componentWidth, componentHeight,componentWindow,componentOriginWindow,componentOut,componentIndex,isMenu);
    	this.ComponentType="Controller";
    	this.setBounds(origx,origy,width,height);
    	this.setLayout(null);
    	this.ip="127.0.0."+(Window.countCompTypeDropped("Controller")+1);
    	listeningPort=6633;
        this.setImage(false);
    	this.createInformations();
    	
    	if(!this.isInMenu){
    	Font font = new Font("Arial",Font.BOLD,11);
    	JLabel componentNetworkLabelName = new JLabel();
    	componentNetworkLabelName.setBounds(22,42, 60, 10);
    	componentNetworkLabelName.setFont(font);
    	componentNetworkLabelName.setText(this.ComponentName);
    	this.add(componentNetworkLabelName);
    	JLabel componentNetworkLabelIP = new JLabel();
    	componentNetworkLabelIP.setBounds(2,52, 63, 15);
    	componentNetworkLabelIP.setFont(font);
    	componentNetworkLabelIP.setText(this.ip);
    	componentNetworkLabelIP.setBackground(Color.black);
    	this.add(componentNetworkLabelIP);
    	JLabel componentNetworkLabelListeningPort = new JLabel();
    	componentNetworkLabelListeningPort.setBounds(5,64, 60, 15);
    	componentNetworkLabelListeningPort.setFont(font);
    	componentNetworkLabelListeningPort.setText(String.valueOf(this.listeningPort));
    	this.add(componentNetworkLabelListeningPort);
    	}
	}
	
	  //The paint function which is automatically called
	  @Override protected void paintComponent(Graphics g){
		  super.paintComponent(g);
		  if(isInMenu){g.drawImage(ComponentImage, 0, 0,40, 40,null);}
		  else{g.drawImage(ComponentImage, 10, 0,40, 40,null);}
	  }   
	 @Override protected void setImage(boolean clicked){
		 String image;
		 if(clicked){image="controller_clicked.png";}
		 else{image="controller_not_clicked.png";}
		 try{ComponentImage = ImageIO.read(new File(image));}
		 catch (IOException e) { e.printStackTrace();} 
		 this.repaint();
		 
	}
	
	public String getIP(){return this.ip;}
	public void setIP(String componentIp){this.ip=componentIp;}
	public int getListeningPort(){return this.listeningPort;}
	public void setListeningPort(int port){this.listeningPort=port;}
	//public LinkedList<PortComponent> getPortsList(){return this.PortsList;}
	
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
    	//Component IP
    	JLabel componentLabelIP = new JLabel();
    	componentLabelIP.setBounds(0,yPos, 50, 15);
    	componentLabelIP.setText("Ip :");
    	RegexFormatter ipmask = new RegexFormatter("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        ipmask.setOverwriteMode(false);
    	JFormattedTextField componentTextIP = new JFormattedTextField(ipmask);
    	componentTextIP.setBounds(50,yPos, 200, 15);
    	componentTextIP.setText(this.getIP());
    	this.ComponentInformations.add(componentLabelIP);
    	this.ComponentInformations.add(componentTextIP);
    	
    	//Component listeningPort
    	JLabel componentLabelListeningPort = new JLabel();
    	componentLabelListeningPort.setBounds(260,yPos, 120, 15);
    	componentLabelListeningPort.setText("Listening port : ");
    	RegexFormatter portmask = new RegexFormatter("\\d{0,5}");
    	portmask.setOverwriteMode(false);
    	JFormattedTextField componentTextListeningPort = new JFormattedTextField(portmask);
    	componentTextListeningPort.setBounds(380,yPos, 50, 15);
    	componentTextListeningPort.setText(String.valueOf(this.getListeningPort()));
    	this.ComponentInformations.add(componentLabelListeningPort);
    	this.ComponentInformations.add(componentTextListeningPort);
    	
    	yPos+=SpaceHeight;
    	//Ports description
    	JLabel componentLabelPortsList = new JLabel();
    	componentLabelPortsList.setBounds(0,yPos, 50, 15);
    	componentLabelPortsList.setText("Ports :");
    	JComboBox<String> componentPortsList = new JComboBox<String>();
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
    		componentLabelPortsName.setText("Port " + PortsList.get(componentPortsList.getSelectedIndex()).getPortNumber() + " linked to "  + PortsList.get(componentPortsList.getSelectedIndex()).getAttachement().ComponentName);
    	}
    	else{
    		componentLabelPortsName.setText("No port");
    	}
    	this.ComponentInformations.add(componentLabelPortsName);
    	
    	yPos+=SpaceHeight;
    	//Button informations
    	JButton modifyInformations = new JButton();
    	modifyInformations.setBounds(0,yPos-5,200,15);
    	modifyInformations.setText("More informations");
    	this.ComponentInformations.add(modifyInformations);
    	
    	//Button apply
    	JButton apply = new JButton();
    	apply.setBounds(250,yPos-5,200,15);
    	apply.setText("Apply");
    	this.ComponentInformations.add(apply);

    	apply.addActionListener(new ActionListener(){
  		  	public void actionPerformed(ActionEvent e) {
  	    		if (!errors()){updateSmallInformations();}
  	    		updateInformations();
  		  		}
    	});
    	
    	
    	
    	
    	//Information listener for NAME
    	componentTextName.getDocument().addDocumentListener(new DocumentListener() {
    	    @Override
    	    public void changedUpdate(DocumentEvent e) {}
    	    @Override
    	    public void insertUpdate(DocumentEvent e) {}
    	    @Override
    	    public void removeUpdate(DocumentEvent e)  {}	 
    	});
    	//Information listener for IP
    	componentTextIP.getDocument().addDocumentListener(new DocumentListener() {
    	    @Override
    	    public void changedUpdate(DocumentEvent e) {}
    	    @Override
    	    public void insertUpdate(DocumentEvent e) {}
    	    @Override
    	    public void removeUpdate(DocumentEvent e)  {}	 
    	});
    	//Information listener for listeningport
    	componentTextListeningPort.getDocument().addDocumentListener(new DocumentListener() {
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
    	//Information listener for button information
    	modifyInformations.addActionListener(new ActionListener(){
  		  public void actionPerformed(ActionEvent e) {
  			 showParametersWindow();

  		    }
  	});
	}
	protected boolean errors() {
		if((((JTextField)this.ComponentInformations.getComponent(3)).getText().length()<1)
				|| (((JTextField)this.ComponentInformations.getComponent(7)).getText().length()<1)
				|| ( Window.componentNamed(((JTextField)this.ComponentInformations.getComponent(3)).getText())!=null && !((JTextField)this.ComponentInformations.getComponent(3)).getText().equals(this.getComponentName())) ){
			  
			new JOptionPane();;
			JOptionPane.showMessageDialog(null,
					"Error!" + ((((JTextField)this.ComponentInformations.getComponent(3)).getText().length()<1)?"\nIncorrect name!":"")
					+ ((Window.componentNamed(((JTextField)this.ComponentInformations.getComponent(3)).getText())!=null)?"Name already used!":"")
					+ ((((JTextField)this.ComponentInformations.getComponent(7)).getText().length()<1)?"\nIncorrect port!":"")
					, "Export", JOptionPane.WARNING_MESSAGE);
			return true;
		}
		else {return false;}
		
	}

	protected void componentListener(MenuComponentListener l){
		  this.listener = l;
	  }
	@SuppressWarnings("unchecked")
	public void updatePortInformations(){
	 	if(PortsList.size()>0){
    		if (((JComboBox<String>)ComponentInformations.getComponent(9)).getSelectedIndex()==this.getPortsList().size()){
	 		((JLabel)ComponentInformations.getComponent(10)).setText("Port " 
	 				+ this.getPortsList().get(((JComboBox<String>)ComponentInformations.getComponent(9)).getSelectedIndex()-1).getPortNumber() + 
    				" linked to "+ PortsList.get(((JComboBox<?>)ComponentInformations.getComponent(9)).getSelectedIndex()-1).getAttachement().ComponentName);
    		}
    		else {
    			((JLabel)ComponentInformations.getComponent(10)).setText("Port " 
    					+ this.getPortsList().get(((JComboBox<String>)ComponentInformations.getComponent(9)).getSelectedIndex()).getPortNumber() + 
        				" linked to "+ PortsList.get(((JComboBox<String>)ComponentInformations.getComponent(9)).getSelectedIndex()).getAttachement().ComponentName);
    		}
    		int j=((JComboBox<String>)ComponentInformations.getComponent(9)).getSelectedIndex();
			for(int i=0;i<this.getPortsList().size();i++){
				((JComboBox<String>)ComponentInformations.getComponent(9)).addItem("Port " + this.getPortsList().get(i).getPortNumber());
				((JComboBox<String>)ComponentInformations.getComponent(9)).removeItemAt(0);
		    }
			((JComboBox<String>)ComponentInformations.getComponent(9)).setSelectedIndex(j);
			((JComboBox<String>)ComponentInformations.getComponent(9)).repaint();
			((JLabel)ComponentInformations.getComponent(10)).repaint();
	 	}
	}
	  public void updateInformations(){
		  this.ComponentInformations.removeAll();
		  this.createInformations();
	  }
	  public void updateSmallInformations(){
		  this.setComponentName(((JTextField)this.ComponentInformations.getComponent(3)).getText());
		  ((JLabel)this.getComponent(0)).setText(((JTextField)this.ComponentInformations.getComponent(3)).getText());
		  this.setIP(((JTextField)this.ComponentInformations.getComponent(5)).getText());
		  ((JLabel)this.getComponent(1)).setText(((JTextField)this.ComponentInformations.getComponent(5)).getText());
		  this.setListeningPort(Integer.parseInt(((JTextField)this.ComponentInformations.getComponent(7)).getText()));
		  ((JLabel)this.getComponent(2)).setText(((JTextField)this.ComponentInformations.getComponent(7)).getText());
	  }
	  public int requestPort(){
		  this.createPort();
		  return (this.PortsList.size()-1);
	  }
	  public void createPort(){
		  PortComponent newport = new PortComponent(PortsList.size());
		  PortsList.add(PortsList.size(), newport);
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
			if (PortsList.size()>=2){PortsList.remove(index);System.out.println("port "+index +" deleted");}
			else {PortsList=new LinkedList<PortComponent>();}
	  }
	  public void showParametersWindow(){
		new ControllerComponentWindow(300,300,null,this);
	  }
	  
	  
	  
	  public void exportXML(FileWriter fw) {
		  try {
			  fw.write("\t<Controller>\n");
			  fw.write("\t\t<Position>\n\t\t\t<X>"+this.getX()+"</X>\n\t\t\t<Y>"+this.getY()+"</Y>\n\t\t</Position>\n");
			  fw.write("\t\t<ComponentName>"+this.getComponentName()+"</ComponentName>\n");
			  fw.write((ip.length()>1)?"\t\t<IP>"+this.ip+"</IP>\n":"");
			  fw.write("\t\t<ListeningPort>"+this.listeningPort+"</ListeningPort>\n");
			  fw.write("\t</Controller>\n");
		  } catch (IOException e) {
				e.printStackTrace();
		  }
	  }
	
}
