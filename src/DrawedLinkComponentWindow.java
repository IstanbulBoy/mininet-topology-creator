import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DrawedLinkComponentWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private DrawedLinkComponent component;
	private JPanel cont;
	
	public DrawedLinkComponentWindow(int width, int height, LayoutManager panLayout, DrawedLinkComponent givenComponent){
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
    	//Component bandwidth
    	JLabel componentLabelBandwidth = new JLabel();
    	componentLabelBandwidth.setBounds(0,yPos, 150, 15);
    	componentLabelBandwidth.setText("Bandwidth (MB/s) :");
    	RegexFormatter bandwidthmask = new RegexFormatter("\\d{0,8}");
    	bandwidthmask.setOverwriteMode(false);
    	JFormattedTextField componentTextBandwidth = new JFormattedTextField(bandwidthmask);
    	componentTextBandwidth.setBounds(componentLabelBandwidth.getWidth(),yPos, this.getWidth()-componentLabelBandwidth.getWidth()-componentTextBandwidth.getLocation().x-10, 15);
    	componentTextBandwidth.setText(String.valueOf(component.getBandwidth()));
    	cont.add(componentLabelBandwidth);
    	cont.add(componentTextBandwidth);
    	
    	yPos+=SpaceHeight;
    	//Component delay
    	JLabel componentLabelDelay = new JLabel();
    	componentLabelDelay.setBounds(0,yPos, 150, 15);
    	componentLabelDelay.setText("Delay (ms) :");
    	RegexFormatter delaymask = new RegexFormatter("\\d{0,8}");
    	delaymask.setOverwriteMode(false);
    	JFormattedTextField componentTextDelay = new JFormattedTextField(delaymask);
    	componentTextDelay.setBounds(componentLabelDelay.getWidth(),yPos, this.getWidth()-componentLabelDelay.getWidth()-componentTextDelay.getLocation().x-10, 15);
    	componentTextDelay.setText(String.valueOf(component.getDelay()));
    	cont.add(componentLabelDelay);
    	cont.add(componentTextDelay);
    	
    	yPos+=SpaceHeight;
    	//Component loss
    	JLabel componentLabelLoss = new JLabel();
    	componentLabelLoss.setBounds(0,yPos, 150, 15);
    	componentLabelLoss.setText("Loss (%) :");
    	RegexFormatter lossmask = new RegexFormatter("^[1-9]?[0-9]{1}$|^100$");
    	lossmask.setOverwriteMode(false);
    	JFormattedTextField componentTextLoss = new JFormattedTextField(lossmask);
    	componentTextLoss.setBounds(componentLabelLoss.getWidth(),yPos, this.getWidth()-componentLabelLoss.getWidth()-componentTextLoss.getLocation().x-10, 15);
    	componentTextLoss.setText(String.valueOf(component.getLoss()));
    	cont.add(componentLabelLoss);
    	cont.add(componentTextLoss);
    	
    	yPos+=SpaceHeight;
    	//Component Queue
    	JLabel componentLabelMaxQueueSize = new JLabel();
    	componentLabelMaxQueueSize.setBounds(0,yPos, 200, 15);
    	componentLabelMaxQueueSize.setText("Max queue size (pckts) :");
    	RegexFormatter maxqueuesizemask = new RegexFormatter("\\d{0,10}");
    	maxqueuesizemask.setOverwriteMode(false);
    	JFormattedTextField componentTextMaxQueueSize = new JFormattedTextField(maxqueuesizemask);
    	componentTextMaxQueueSize.setBounds(componentLabelMaxQueueSize.getWidth(),yPos, this.getWidth()-componentLabelMaxQueueSize.getWidth()-componentTextMaxQueueSize.getLocation().x-10, 15);
    	componentTextMaxQueueSize.setText(String.valueOf(component.getMaxqueuesize()));
    	cont.add(componentLabelMaxQueueSize);
    	cont.add(componentTextMaxQueueSize);
    	
    	yPos+=SpaceHeight;
    	//Attachment description
    	JLabel componentLabelAttachment = new JLabel();
    	componentLabelAttachment.setBounds(0,yPos, 250, 15);
    	componentLabelAttachment.setText("Link between "+component.getAttachedComponent(0).getComponentName()+" and "+component.getAttachedComponent(1).getComponentName());
    	cont.add(componentLabelAttachment);
 
    	
    	yPos+=SpaceHeight;
    	//apply button
    	JButton applyButton = new JButton();
    	applyButton.setBounds(this.getWidth()-120,yPos+20, 100, 30);
    	applyButton.setText("Apply");
    	cont.add(applyButton);
    	
		
    	//Information listener for button information
    	applyButton.addActionListener(new ActionListener(){
  		  public void actionPerformed(ActionEvent e) {
  			if ( ( (Window.componentNamed(componentTextName.getText())!=null) && !componentTextName.getText().equals(component.getComponentName()) ) ){
  		  		new JOptionPane();;
  				JOptionPane.showMessageDialog(null, "Error!\n"
						+ ((Window.componentNamed(componentTextName.getText())!=null && !componentTextName.getText().equals(component.getComponentName()) )?"Name already used!":"")
						, "Export", JOptionPane.WARNING_MESSAGE);
		  		}
  			else {updateInformations();dispose();}
  		    }
    	});
	}
	private void updateInformations(){
		component.setComponentName(((JTextField)cont.getComponent(3)).getText());
		component.setBandwidth(Integer.parseInt(((JFormattedTextField)cont.getComponent(5)).getText()));
		component.setDelay(Integer.parseInt(((JFormattedTextField)cont.getComponent(7)).getText()));
		component.setLoss(Integer.parseInt(((JFormattedTextField)cont.getComponent(9)).getText()));
		component.setMaxqueuesize(Integer.parseInt(((JFormattedTextField)cont.getComponent(11)).getText()));
		component.updateInformations();
		component.updateSmallInformations();
	}
}
