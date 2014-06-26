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


public class ModifyPortsWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private DraggableComponent component;
	private int portNbToChange;
	private JPanel cont;
	
	public ModifyPortsWindow(int width, int height, LayoutManager panLayout, DraggableComponent givenComponent, int portNbToChange, JComboBox<String> componentPortsList, JLabel componentLabelPortsName){
			this.setSize(width, height);
			this.component=givenComponent;
			this.portNbToChange=portNbToChange;
			this.setLocationRelativeTo(null);               
			this.setTitle(component.getComponentName()+ " port modifications");
			this.setLayout(null); 
			cont = new JPanel();
			cont.setLayout(null);
			this.setContentPane(cont);
			this.createInformations(componentPortsList, componentLabelPortsName);
			this.setVisible(true);
	}

	public void createInformations(final JComboBox<String> componentPortsList, final JLabel componentLabelPortsName){
		
		JLabel componentLabelType = new JLabel();
		componentLabelType.setBounds(0,0, 125, 15);
		componentLabelType.setText("Port to change :");
		JLabel componentTextType = new JLabel();
    	componentTextType.setBounds(componentLabelType.getWidth(), 0, this.getWidth()-componentLabelType.getWidth()-componentTextType.getLocation().x-10, 15);
    	componentTextType.setText(String.valueOf(portNbToChange));
    	cont.add(componentLabelType);
    	cont.add(componentTextType);
    	
    	//Component port modification
    	JLabel componentLabelNewPort = new JLabel();
    	componentLabelNewPort.setBounds(0,25, 125, 15);
    	componentLabelNewPort.setText("Change port to :");
    	RegexFormatter portmask = new RegexFormatter("\\d{0,5}");
    	portmask.setOverwriteMode(false);
    	final JFormattedTextField componentTextListeningPort = new JFormattedTextField(portmask);
    	componentTextListeningPort.setBounds(componentLabelNewPort.getWidth(),25, this.getWidth()-componentLabelNewPort.getWidth()-componentTextListeningPort.getLocation().x-10, 15);
    	componentTextListeningPort.setText(String.valueOf(0));
    	cont.add(componentLabelNewPort);
    	cont.add(componentTextListeningPort);
    	
    	//apply button
    	JButton applyButton = new JButton();
    	applyButton.setBounds(this.getWidth()-120,50, 100, 30);
    	applyButton.setText("Apply");
    	cont.add(applyButton);
    	
    	applyButton.addActionListener(new ActionListener(){
		  	public void actionPerformed(ActionEvent e) {
	    		if (!errors()){
	    			component.changePort(portNbToChange,Integer.parseInt(((JFormattedTextField)cont.getComponent(3)).getText()));    			
	    			for(int i=0;i<component.getPortsList().size();i++){
	    		    	componentPortsList.addItem("Port " + component.getPortsList().get(i).getPortNumber());
	    		    	componentPortsList.removeItemAt(0);
	    		    }

	    			componentPortsList.setSelectedIndex(0);
	    		  	componentPortsList.repaint();
	    		  	componentLabelPortsName.repaint();//*/
	    		  	((DraggableComponent)component).updatePortInformations();

	    			dispose();
	    		}
	    	}	
    	});
	}
	
	
	
	private boolean errors() {
		if (((JFormattedTextField)cont.getComponent(3)).getText().length()<1){
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Error!\nPort number invalid!", "Port", JOptionPane.WARNING_MESSAGE);
			return true;
		}
		else if (component.portUsed(Integer.parseInt(((JFormattedTextField)cont.getComponent(3)).getText())) && Integer.parseInt(((JFormattedTextField)cont.getComponent(3)).getText())!=portNbToChange){
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Error!\nPort already used!", "Port", JOptionPane.WARNING_MESSAGE);
			return true;
		}
		
		return false;
	}
	
	
}
