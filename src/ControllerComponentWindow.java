import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ControllerComponentWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private ControllerComponent component;
	private JPanel cont;
	
	public ControllerComponentWindow(int width, int height, LayoutManager panLayout, ControllerComponent givenComponent){
			this.setSize(width, height);
			this.component=givenComponent;
			this.setLocationRelativeTo(null);               
			this.setTitle(component.getComponentName()+ " modifications");
			this.setLayout(null); 
			cont = new JPanel();
			cont.setLayout(null);
			this.setContentPane(cont);
			this.createInformations();
			this.setVisible(true);
	}
	
	
	
	public void createInformations(){
		int SpaceHeight=25;
		int yPos=0;
		//Component type
		JLabel componentLabelType = new JLabel();
		componentLabelType.setBounds(0,yPos, 50, 15);
		componentLabelType.setText("Type :");
		JLabel componentTextType = new JLabel();
    	componentTextType.setBounds(componentLabelType.getWidth(), yPos, this.getWidth()-componentLabelType.getWidth()-componentTextType.getLocation().x-10, 15);
    	componentTextType.setText(component.getComponentType());
    	cont.add(componentLabelType);
    	cont.add(componentTextType);
    	
    	yPos+=SpaceHeight;
		//Component name
		JLabel componentLabelName = new JLabel();
    	componentLabelName.setBounds(0,yPos, 50, 15);
    	componentLabelName.setText("Name :");
    	final JTextField componentTextName = new JTextField();
    	componentTextName.setBounds(componentLabelName.getWidth(), yPos, this.getWidth()-componentLabelName.getWidth()-componentTextName.getLocation().x-10, 15);
    	componentTextName.setText(component.getComponentName());
    	cont.add(componentLabelName);
    	cont.add(componentTextName);
    	
    	yPos+=SpaceHeight;
    	//Component IP
    	JLabel componentLabelIP = new JLabel();
    	componentLabelIP.setBounds(0,yPos, 50, 15);
    	componentLabelIP.setText("Ip :");
    	RegexFormatter ipmask = new RegexFormatter("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        ipmask.setOverwriteMode(false);
    	JFormattedTextField componentTextIP = new JFormattedTextField(ipmask);
    	componentTextIP.setBounds(componentLabelIP.getWidth(),yPos, this.getWidth()-componentLabelIP.getWidth()-componentTextIP.getLocation().x-10, 15);
    	componentTextIP.setText(component.getIP());
    	cont.add(componentLabelIP);
    	cont.add(componentTextIP);
    	
    	yPos+=SpaceHeight;
    	//Component listening port
    	JLabel componentLabelListeningPort = new JLabel();
    	componentLabelListeningPort.setBounds(0,yPos, 110, 15);
    	componentLabelListeningPort.setText("Listening port :");
    	RegexFormatter portmask = new RegexFormatter("\\d{0,5}");
    	portmask.setOverwriteMode(false);
    	final JFormattedTextField componentTextListeningPort = new JFormattedTextField(portmask);
    	componentTextListeningPort.setBounds(componentLabelListeningPort.getWidth(),yPos, this.getWidth()-componentLabelListeningPort.getWidth()-componentTextListeningPort.getLocation().x-10, 15);
    	componentTextListeningPort.setText(String.valueOf(component.getListeningPort()));
    	cont.add(componentLabelListeningPort);
    	cont.add(componentTextListeningPort);
    	
    	yPos+=SpaceHeight;
    	//Ports description
    	JLabel componentLabelPortsList = new JLabel();
    	componentLabelPortsList.setBounds(0,yPos, 50, 15);
    	componentLabelPortsList.setText("Ports :");
    	final JComboBox<String> componentPortsList = new JComboBox<String>();
    	componentPortsList.setBounds(componentLabelPortsList.getWidth(),yPos,this.getWidth()-componentLabelPortsList.getWidth()-componentPortsList.getLocation().x-10,20);
    	for(int i=0;i<component.getPortsList().size();i++){
    		componentPortsList.addItem("Port " + component.getPortsList().get(i).getPortNumber());
    	}
    	cont.add(componentLabelPortsList);
    	cont.add(componentPortsList);
    	
    	yPos+=SpaceHeight;
    	final JLabel componentLabelPortsName = new JLabel();
    	componentLabelPortsName.setBounds(0,yPos,this.getWidth()-componentLabelPortsName.getLocation().x-10,15);
    	if(component.getPortsList().size()>0){
    		componentLabelPortsName.setText("Port " + component.getPortsList().get(componentPortsList.getSelectedIndex()).getPortNumber() + " linked to "  + component.getPortsList().get(componentPortsList.getSelectedIndex()).getAttachement().ComponentName);
    	}
    	else{
    		componentLabelPortsName.setText("No port");
    	}
    	cont.add(componentLabelPortsName);


    	yPos+=SpaceHeight;
    	//apply button
    	JButton applyButton = new JButton();
    	applyButton.setBounds(this.getWidth()-120,yPos+20, 100, 30);
    	applyButton.setText("Apply");
    	cont.add(applyButton);
    	
    	//ModifyPorts button
    	JButton modifyPorts = new JButton();
    	modifyPorts.setBounds(this.getWidth()-110,5*SpaceHeight, 100, 15);
    	modifyPorts.setText("Modify");
    	cont.add(modifyPorts);
    	
		
    	//Information listener for button information
    	applyButton.addActionListener(new ActionListener(){
  		  	public void actionPerformed(ActionEvent e) {
  		  		if ( ( (Window.componentNamed(componentTextName.getText())!=null) && !componentTextName.getText().equals(component.getComponentName()) ) 
  		  				|| componentTextListeningPort.getText().length()<1){
	  		  		new JOptionPane();;
	  				JOptionPane.showMessageDialog(null, "Error!\n"
  						+ ((Window.componentNamed(componentTextName.getText())!=null && !componentTextName.getText().equals(component.getComponentName()) )?"Name already used!":"")
  						+ ((componentTextListeningPort.getText().length()<1)?"Port number invalid!":""), "Export", JOptionPane.WARNING_MESSAGE);
  		  		}
  		  		else {updateInformations();dispose();}
  		  	}
    	});
    	
    	
    	
    	modifyPorts.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			//Opening new window
    			if (component.getPortsList().size()>0)
    			 showPortParametersWindow(component.getPortsList().get(componentPortsList.getSelectedIndex()).getPortNumber(),componentPortsList,componentLabelPortsName);

    		}
    	});
    	
    	//Information listener for PORTS
    	componentPortsList.addActionListener(new ActionListener(){
    		  public void actionPerformed(ActionEvent e) {
    			  updatePortInformations();
    		    }
    	});
	}
	public void updatePortInformations(){
	 	if((component).getPortsList().size()>0){
	 		if (((JComboBox<?>)cont.getComponent(9)).getSelectedIndex()==component.getPortsList().size()) {
	 			((JLabel)cont.getComponent(10)).setText("Port " 
	 					+ component.getPortsList().get(((JComboBox<?>)cont.getComponent(9)).getSelectedIndex()-1).getPortNumber() 
	 					+" linked to "+ component.getPortsList().get(((JComboBox<?>)cont.getComponent(9)).getSelectedIndex()-1).getAttachement().getComponentName());}
	 		else {
	 			((JLabel)cont.getComponent(10)).setText("Port " 
	 					+ component.getPortsList().get(((JComboBox<?>)cont.getComponent(9)).getSelectedIndex()).getPortNumber() 	
	 					+" linked to "+ component.getPortsList().get(((JComboBox<?>)cont.getComponent(9)).getSelectedIndex()).getAttachement().getComponentName());
    	
	 		}
	 	}
	}
	public void showPortParametersWindow(int port, JComboBox<String> componentPortsList, JLabel componentLabelPortsName){
		new ModifyPortsWindow(250,115,null,(DraggableComponent)component,port,componentPortsList, componentLabelPortsName);
	}
	
	
	private void updateInformations(){
		component.setComponentName(((JTextField)cont.getComponent(3)).getText());
		component.setIP(((JFormattedTextField)cont.getComponent(5)).getText());
		component.setListeningPort(Integer.parseInt(((JFormattedTextField)cont.getComponent(7)).getText()));
		component.updateInformations();
		component.updateSmallInformations();
	}
}
