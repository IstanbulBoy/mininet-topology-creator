
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


public class HostComponent extends DraggableComponent{

	private static final long serialVersionUID = 1L;

	private String ip;
	private String mac;
	private String mask;
	
	public HostComponent(String componentName, int componentX,int componentY, int componentWidth, int componentHeight,JPanel componentWindow,JPanel componentOriginWindow,boolean componentOut,int componentIndex,boolean isMenu) {
		super(componentName, componentX, componentY, componentWidth, componentHeight,componentWindow,componentOriginWindow,componentOut,componentIndex,isMenu);
    	this.ComponentType="Host";
		setImage(false);
    	this.ip="10.0.0."+(Window.countCompTypeDropped("Host")+1);
    	this.mask="255.255.255.0";
    	this.mac=Integer.toHexString(componentIndex+1);
    	int length=this.mac.length();
    	for (int i=12;i>length;i--){this.mac="0"+this.mac;}
    	this.mac=mac.substring(0,2)+":"+mac.substring(2,4)+":"+mac.substring(4,6)+":"+mac.substring(6,8)+":"+mac.substring(8,10)+":"+mac.substring(10,12);
       	PortsList = new LinkedList<PortComponent>();
    	
    	this.createInformations();
    	if(!this.isInMenu){
    	Font font = new Font("Arial",Font.BOLD,11);
    	JLabel componentNetworkLabelName = new JLabel();
    	componentNetworkLabelName.setBounds(22,42, 60, 10);
    	componentNetworkLabelName.setFont(font);
    	componentNetworkLabelName.setText(this.ComponentName);
    	this.add(componentNetworkLabelName);
    	JLabel componentNetworkLabelIP = new JLabel();
    	componentNetworkLabelIP.setBounds(5,52, 60, 15);
    	componentNetworkLabelIP.setFont(font);
    	componentNetworkLabelIP.setText(ip);
    	this.add(componentNetworkLabelIP);}
	}
	
	  public String getMac(){return mac;}
	public void setMac(String mac) {this.mac = mac;}
	public String getMask() {return mask;}
	public void setMask(String mask) {this.mask = mask;}
	public LinkedList<PortComponent> getPortsList() {return PortsList;}
	public void setPortsList(LinkedList<PortComponent> portsList) {PortsList = portsList;}

	//The paint function which is automatically called
	  @Override protected void paintComponent(Graphics g){
		  super.paintComponent(g);
		  if(isInMenu){g.drawImage(ComponentImage, 0, 0,40, 40,null);}
		  else{g.drawImage(ComponentImage, 10, 0,40, 40,null);}
	  }   
	 @Override protected void setImage(boolean clicked){
		 String image;
		 if(clicked){image="host_clicked.png";}
		 else{image="host_not_clicked.png";}
		 try{ComponentImage = ImageIO.read(new File(image));}
		 catch (IOException e) { e.printStackTrace();} 
		 this.repaint();
	}
	public String getIP(){
		return this.ip;
	}
	public void setIP(String componentIp){
		this.ip=componentIp;
	}

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
    	componentTextIP.setBounds(50,yPos, 450, 15);
    	componentTextIP.setText(this.getIP());
    	this.ComponentInformations.add(componentLabelIP);
    	this.ComponentInformations.add(componentTextIP);
    	
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
    	
    	//Information listener
    	componentTextName.getDocument().addDocumentListener(new DocumentListener() {
    	    @Override
    	    public void changedUpdate(DocumentEvent e) {}
    	    @Override
    	    public void insertUpdate(DocumentEvent e) {}
    	    @Override
    	    public void removeUpdate(DocumentEvent e)  {}	 
    	});
    	//Information listener
    	componentTextIP.getDocument().addDocumentListener(new DocumentListener() {
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
				|| Window.componentNamed(((JTextField)this.ComponentInformations.getComponent(3)).getText())!=null && !((JTextField)this.ComponentInformations.getComponent(3)).getText().equals(this.getComponentName())){
			  
			new JOptionPane();;
			JOptionPane.showMessageDialog(null,
					"Error!" + ((((JTextField)this.ComponentInformations.getComponent(3)).getText().length()<1 && !((JTextField)this.ComponentInformations.getComponent(3)).getText().equals(this.getComponentName()))?"\nIncorrect name!":"")
					+ ((Window.componentNamed(((JTextField)this.ComponentInformations.getComponent(3)).getText())!=null)?"Name already used!":"")
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
				if (((JComboBox<String>)ComponentInformations.getComponent(7)).getSelectedIndex()==this.getPortsList().size()){
				((JLabel)ComponentInformations.getComponent(8)).setText("Port " + this.getPortsList().get(((JComboBox<String>)ComponentInformations.getComponent(7)).getSelectedIndex()-1).getPortNumber() + 
						" linked to "+ PortsList.get(((JComboBox<?>)ComponentInformations.getComponent(7)).getSelectedIndex()-1).getAttachement().ComponentName);
			}
			else {
				((JLabel)ComponentInformations.getComponent(8)).setText("Port " + this.getPortsList().get(((JComboBox<String>)ComponentInformations.getComponent(7)).getSelectedIndex()).getPortNumber() + 
						" linked to "+ PortsList.get(((JComboBox<String>)ComponentInformations.getComponent(7)).getSelectedIndex()).getAttachement().ComponentName);
			}
			int j=((JComboBox<String>)ComponentInformations.getComponent(7)).getSelectedIndex();
			for(int i=0;i<this.getPortsList().size();i++){
				((JComboBox<String>)ComponentInformations.getComponent(7)).addItem("Port " + this.getPortsList().get(i).getPortNumber());
				((JComboBox<String>)ComponentInformations.getComponent(7)).removeItemAt(0);
			}
				((JComboBox<String>)ComponentInformations.getComponent(7)).setSelectedIndex(j);
				((JComboBox<String>)ComponentInformations.getComponent(7)).repaint();
				((JLabel)ComponentInformations.getComponent(8)).repaint();
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
	  }
	  @Override protected int requestPort(){
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
			if (PortsList.size()>=1){PortsList.remove(index);System.out.println("port "+index +" deleted");}
			else {PortsList=new LinkedList<PortComponent>();}
	  }
	  public void showParametersWindow(){
		  new HostComponentWindow(300,300,null,this);

	  }
	  public void exportXML(FileWriter fw) {
		  try {
			  fw.write("\t<Host>\n");
			  fw.write("\t\t<Position>\n\t\t\t<X>"+this.getX()+"</X>\n\t\t\t<Y>"+this.getY()+"</Y>\n\t\t</Position>\n");
			  fw.write("\t\t<ComponentName>"+this.getComponentName()+"</ComponentName>\n");
			  fw.write((ip.length()>1)?"\t\t<IP>"+this.ip+"</IP>\n":"");
			  fw.write((mask.length()>1)?"\t\t<Mask>"+this.mask+"</Mask>\n":"");
			  fw.write((mac.length()>1)?"\t\t<Mac>"+this.mac+"</Mac>\n":"");
			  fw.write("\t</Host>\n");
		  } catch (IOException e) {
				e.printStackTrace();
		  }
	  }
}
