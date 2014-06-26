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


public class RandomTopologyComponentWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private RandomTopologyComponent component;
	private JPanel cont;
	
	public RandomTopologyComponentWindow(int width, int height, LayoutManager panLayout, RandomTopologyComponent givenComponent){
			this.setSize(width, height);
			this.component=givenComponent;
			this.setLocationRelativeTo(null);               
			this.setTitle(component.getComponentName()+ " generator");
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
    	//Topology type
    	JLabel componentLabelPredefinedType = new JLabel();
    	componentLabelPredefinedType.setBounds(0,yPos, 50, 15);
    	componentLabelPredefinedType.setText("Predefined :");
    	JComboBox<String> componentPredefinedTypeList = new JComboBox<String>();
    	componentPredefinedTypeList.setBounds(componentLabelPredefinedType.getWidth(),yPos,this.getWidth()-componentLabelPredefinedType.getWidth()-componentPredefinedTypeList.getLocation().x-10,20);
    	componentPredefinedTypeList.addItem("simple");
    	componentPredefinedTypeList.addItem("single");
    	componentPredefinedTypeList.addItem("linear");
    	componentPredefinedTypeList.addItem("tree");
    	componentPredefinedTypeList.setSelectedItem(component.getPredefined());
    	this.add(componentLabelPredefinedType);
    	this.add(componentPredefinedTypeList);
    	
    	yPos+=SpaceHeight;
    	//Component NbHosts
    	final JLabel componentLabelNbHosts = new JLabel();
    	componentLabelNbHosts.setBounds(0,yPos, 135, 15);
    	componentLabelNbHosts.setText("Number of Hosts :");
    	RegexFormatter nbHosts = new RegexFormatter("\\d{0,5}");
    	nbHosts.setOverwriteMode(false);
    	final JFormattedTextField componentTextNbHosts = new JFormattedTextField(nbHosts);
    	componentTextNbHosts.setBounds(componentLabelNbHosts.getWidth(),yPos,this.getWidth()-componentLabelNbHosts.getWidth()-10,15); 
    	//componentTextListeningPort.setText(String.valueOf(component.getListeningPort()));
    	cont.add(componentLabelNbHosts);
    	cont.add(componentTextNbHosts);
    	componentLabelNbHosts.setVisible(false);
		componentTextNbHosts.setVisible(false);
    	
		//Component NbSwitches
    	final JLabel componentLabelNbSwitches = new JLabel();
    	componentLabelNbSwitches.setBounds(0,yPos, 160, 15);
    	componentLabelNbSwitches.setText("Number of Switches :");
    	RegexFormatter nbSwitches = new RegexFormatter("\\d{0,5}");
    	nbSwitches.setOverwriteMode(false);
    	final JFormattedTextField componentTextNbSwitches = new JFormattedTextField(nbSwitches);
    	componentTextNbSwitches.setBounds(componentLabelNbSwitches.getWidth(),yPos,this.getWidth()-componentLabelNbSwitches.getWidth()-10,15);
    	//componentTextListeningPort.setText(String.valueOf(component.getListeningPort()));
    	cont.add(componentLabelNbSwitches);
    	cont.add(componentTextNbSwitches);
    	componentLabelNbSwitches.setVisible(false);
		componentTextNbSwitches.setVisible(false);
    			
		//Component depth
    	final JLabel componentLabelDepth = new JLabel();
    	componentLabelDepth.setBounds(0,yPos, 70, 15);
    	componentLabelDepth.setText("Depth :");
    	RegexFormatter depth = new RegexFormatter("\\d{0,5}");
    	depth.setOverwriteMode(false);
    	final JFormattedTextField componentTextDepth = new JFormattedTextField(depth);
    	componentTextDepth.setBounds(componentLabelDepth.getWidth(),yPos,this.getWidth()-componentLabelDepth.getWidth()-10,15); 
    	//componentTextListeningPort.setText(String.valueOf(component.getListeningPort()));
    	cont.add(componentLabelDepth);
    	cont.add(componentTextDepth);
    	componentLabelDepth.setVisible(false);
    	componentTextDepth.setVisible(false);
    	
    	yPos+=SpaceHeight;
    	//Component fanout
    	final JLabel componentLabelFanout = new JLabel();
    	componentLabelFanout.setBounds(0,yPos, 70, 15);
    	componentLabelFanout.setText("Fanout :");
    	RegexFormatter fanout = new RegexFormatter("\\d{0,2}");
    	fanout.setOverwriteMode(false);
    	final JFormattedTextField componentTextFanout = new JFormattedTextField(fanout);
    	componentTextFanout.setBounds(componentLabelFanout.getWidth(),yPos, this.getWidth()-componentLabelFanout.getWidth()-10, 15);
    	//componentTextListeningPort.setText(String.valueOf(component.getListeningPort()));
    	cont.add(componentLabelFanout);
    	cont.add(componentTextFanout);
    	componentLabelFanout.setVisible(false);
		componentTextFanout.setVisible(false);

    	
    	//apply button
    	JButton applyButton = new JButton();
    	applyButton.setBounds(this.getWidth()-120,8*SpaceHeight, 100, 30);
    	applyButton.setText("Apply");
    	cont.add(applyButton);
    			
    	//Information listener for button information
    	componentPredefinedTypeList.addActionListener(new ActionListener(){
  		  	public void actionPerformed(ActionEvent e) {
  		  		if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("simple")){
  		  			componentLabelNbSwitches.setVisible(false);
  		  			componentTextNbSwitches.setVisible(false);
  		  			componentLabelNbHosts.setVisible(false);
    				componentTextNbHosts.setVisible(false);
    				componentLabelDepth.setVisible(false);
    		    	componentTextDepth.setVisible(false);
    				componentLabelFanout.setVisible(false);
    				componentTextFanout.setVisible(false);
    				
  		  		}
  		  		else if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("single")){
		  			componentLabelNbSwitches.setVisible(false);
		  			componentTextNbSwitches.setVisible(false);
		  			componentLabelNbHosts.setVisible(true);
					componentTextNbHosts.setVisible(true);
					componentLabelDepth.setVisible(false);
			    	componentTextDepth.setVisible(false);
					componentLabelFanout.setVisible(false);
					componentTextFanout.setVisible(false);
			  	}
  		  		else if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("linear")){
		  			componentLabelNbSwitches.setVisible(true);
		  			componentTextNbSwitches.setVisible(true);
		  			componentLabelNbHosts.setVisible(false);
					componentTextNbHosts.setVisible(false);
					componentLabelDepth.setVisible(false);
			    	componentTextDepth.setVisible(false);
					componentLabelFanout.setVisible(false);
					componentTextFanout.setVisible(false);
  		  		}
  		  		else if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("tree")){
		  			componentLabelNbSwitches.setVisible(false);
		  			componentTextNbSwitches.setVisible(false);
		  			componentLabelNbHosts.setVisible(false);
					componentTextNbHosts.setVisible(false);
					componentLabelDepth.setVisible(true);
			    	componentTextDepth.setVisible(true);
					componentLabelFanout.setVisible(true);
					componentTextFanout.setVisible(true);
		  		}
  		  		else {
	  		  		componentLabelNbSwitches.setVisible(false);
			  		componentTextNbSwitches.setVisible(false);
			  		componentLabelNbHosts.setVisible(false);
					componentTextNbHosts.setVisible(false);
					componentLabelDepth.setVisible(false);
			    	componentTextDepth.setVisible(false);
					componentLabelFanout.setVisible(false);
					componentTextFanout.setVisible(false);
  		  		}
  		  	}
    	});
    	
    	//Information listener for Topology type
    	applyButton.addActionListener(new ActionListener(){
  		  	public void actionPerformed(ActionEvent e) {
  		  		if (!errors()){
  		  			updateInformations();
  		  			dispose();
  				}
  		    }

			
    	});
	}
	private boolean errors() {
		
		if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("single")
				&&  ((JFormattedTextField)cont.getComponent(5)).getText().length()<1){
			new JOptionPane();;
				JOptionPane.showMessageDialog(null, "Error!\n"
					+ ((((JFormattedTextField)cont.getComponent(5)).getText().length()<1)?"Invalid number of Hosts!\n":"")
					, "Export", JOptionPane.WARNING_MESSAGE);
	  		
			return true;
		}
		else if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("linear")
				&&  ((JFormattedTextField)cont.getComponent(7)).getText().length()<1){
			new JOptionPane();;
				JOptionPane.showMessageDialog(null, "Error!\n"
					+ ((((JFormattedTextField)cont.getComponent(7)).getText().length()<1)?"Invalid number of Switches!\n":"")
					, "Export", JOptionPane.WARNING_MESSAGE);
	  		
			return true;
		}
		else if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("tree")
				&& ( (((JFormattedTextField) cont.getComponent(9)).getText().length()<1)
				   || (((JFormattedTextField) cont.getComponent(11)).getText().length()<1) 
				   )
			){
			new JOptionPane();;
				JOptionPane.showMessageDialog(null, "Error!\n"
					+ ((((JFormattedTextField)cont.getComponent(9)).getText().length()<1)?"Invalid depth!\n":"")
					+ ((((JFormattedTextField)cont.getComponent(11)).getText().length()<1)?"Invalid fanout!":"")
					, "Export", JOptionPane.WARNING_MESSAGE);
	  		
			return true;
		}
		
		if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("tree")
				&& (int)Math.pow(Integer.valueOf((((JFormattedTextField)cont.getComponent(11)).getText())),Integer.valueOf((((JFormattedTextField)cont.getComponent(9)).getText()))+1)
						>100){
				     /*   ^-- Number max of component at the top of the tree   */
			
			int result = JOptionPane.showConfirmDialog((Component) null, "You are about to create a very big tree and that could be long.\nDo you want to continue?",
			        "alert", JOptionPane.OK_CANCEL_OPTION);
			System.out.println(result);
			
			return result!=0;
			
			
		}
		
		
		return false;
	}
	private void updateInformations(){
		component.setPredefined(String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()));
		if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("simple")){
			component.setPredefined("single");
			component.setArg1(2);
			component.setArg2(0);
		}
		else if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("single")){
			component.setArg1(Integer.parseInt(((JFormattedTextField)cont.getComponent(5)).getText()));
			component.setArg2(0);
		}
		else if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("linear")){
			component.setArg1(Integer.parseInt(((JFormattedTextField)cont.getComponent(7)).getText()));
			component.setArg2(0);
		}
		else if (String.valueOf(((JComboBox<?>)cont.getComponent(3)).getSelectedItem()).equals("tree")){
			component.setArg1(Integer.parseInt(((JFormattedTextField)cont.getComponent(9)).getText()));
			component.setArg2(Integer.parseInt(((JFormattedTextField)cont.getComponent(11)).getText()));
		}

		component.createTopology();
	}
}
