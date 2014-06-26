import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class DrawedLinkComponent extends Component{


	private static final long serialVersionUID = 1L;
	private Component compA;
	private Component compB;
	private int bandwidth;
	private int delay;
	private int loss;
	private int maxQueueSize;
	
	private Line2D line;
	private int thickness;
	private JLabel description;
	
	public DrawedLinkComponent(String componentName,Component linkedComponentA, Component linkedComponentB,int componentIndex){
		super(componentName,componentIndex);
		this.compA=linkedComponentA;
		this.compB=linkedComponentB;
		this.ComponentType="Link";
		this.bandwidth=100;
		this.maxQueueSize=1000;
		line = new Line2D.Double(compA.getLocation().x+30,compA.getLocation().y+30,compB.getLocation().x+30,compB.getLocation().y+30);
		this.createInformations();
		
		description = new JLabel();
		description.setBounds(compA.getLocation().x+20+(int)((compB.getLocation().x+20-compA.getLocation().x)/2), compB.getLocation().y+(int)((compA.getLocation().y+40-compB.getLocation().y)/2)-20, 40,40 );
		description.setText(this.getComponentName());
	}
	
	public int getBandwidth() {return bandwidth;}
	public void setBandwidth(int bandwidth) {this.bandwidth = bandwidth;}
	public int getDelay() {return delay;}
	public void setDelay(int delay) {this.delay = delay;}
	public int getLoss() {return loss;}
	public void setLoss(int loss) {this.loss = loss;}
	public int getMaxqueuesize() {return maxQueueSize;}
	public void setMaxqueuesize(int maxqueuesize) {this.maxQueueSize = maxqueuesize;}
	
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
		//Component attachment
		JLabel componentLabelAttachement = new JLabel();
		componentLabelAttachement.setBounds(0,yPos, 500, 15);
		componentLabelAttachement.setText("Link between "+this.getAttachedComponent(0).getComponentName() +" and "+this.getAttachedComponent(1).getComponentName());
    	this.ComponentInformations.add(componentLabelAttachement);
    	
    	yPos+=SpaceHeight;
    	//Button informations
    	JButton modifyInformations = new JButton();
    	modifyInformations.setBounds(0,5*SpaceHeight-5,200,15);
    	modifyInformations.setText("More informations");
    	this.ComponentInformations.add(modifyInformations);
    	
    	//Button apply
    	JButton apply = new JButton();
    	apply.setBounds(250,5*SpaceHeight-5,200,15);
    	apply.setText("Apply");
    	this.ComponentInformations.add(apply);
    	
    	//Information listener for NAME
    	componentTextName.getDocument().addDocumentListener(new DocumentListener() {
    	    @Override
    	    public void changedUpdate(DocumentEvent e) {}
    	    @Override
    	    public void insertUpdate(DocumentEvent e) {
    	    	//updateSmallInformations();
    	    	}
    	    @Override
    	    public void removeUpdate(DocumentEvent e)  {
    	    	//updateSmallInformations();
    	    	}	 
    	});
    	//Information listener for button information
    	modifyInformations.addActionListener(new ActionListener(){
  		  public void actionPerformed(ActionEvent e) {
  			 showParametersWindow();

  		    }
    	});
    	
    	apply.addActionListener(new ActionListener(){
  		  	public void actionPerformed(ActionEvent e) {
  	    		if (!errors()){updateSmallInformations();}
  	    		updateInformations();
  		  		}
    	});

	}
	
	public void updateInformations(){
		  this.ComponentInformations.removeAll();
		  this.createInformations();
	  }
	  public void updateSmallInformations(){
		  this.setComponentName(((JTextField)this.ComponentInformations.getComponent(3)).getText());
		  description.setText(((JTextField)this.ComponentInformations.getComponent(3)).getText());
	  }
	
	@Override
	public void paint(Graphics g) {
		
	    
	}
	public void updateLineCoordinates(){
		line = new Line2D.Double(compA.getLocation().x+30,compA.getLocation().y+30,compB.getLocation().x+30,compB.getLocation().y+30);
		description.setBounds(compA.getLocation().x+20+(int)((compB.getLocation().x+20-compA.getLocation().x)/2), compB.getLocation().y+(int)((compA.getLocation().y+40-compB.getLocation().y)/2), 40,40 );
	}

	  public Component getAttachedComponent(int index){
		  if(index==0){return compA;}
		  else{return compB;}
	  }
	  public Line2D getLine(){
		  return this.line;
	  }
	  public void setThickness(int thick){
		  this.thickness=thick;
		  
	  }
	  public int getThickness(){
		  return this.thickness;
	  }
	  @Override protected void setImage(boolean b){
		  if(b){this.thickness=2;}
		  else{this.thickness=1;}
	  }
	  public JLabel getDescription(){
		  return this.description;
	  }
	  public void showParametersWindow(){
		  new DrawedLinkComponentWindow(300,300,null,this);

	  }
	  public void exportXML(FileWriter fw) {
		  try {
			  int port1=-1,port2=-1;
			  for (int i=0;i<this.getAttachedComponent(0).getPortsList().size();i++){
				  if (this.getAttachedComponent(0).getPortsList().get(i).getAttachement()==this.getAttachedComponent(1))
					  port1=this.getAttachedComponent(0).getPortsList().get(i).getPortNumber();
			  }
			  for (int i=0;i<this.getAttachedComponent(1).getPortsList().size();i++){
				  if (this.getAttachedComponent(1).getPortsList().get(i).getAttachement()==this.getAttachedComponent(0))
					  port2=this.getAttachedComponent(1).getPortsList().get(i).getPortNumber();
			  }
			  fw.write("\t<Link>\n");
			  fw.write("\t\t<ComponentName>"+this.getComponentName()+"</ComponentName>\n");
			  fw.write("\t\t<LinkedComponent1Name>"+this.getAttachedComponent(0).getComponentName()+"</LinkedComponent1Name>\n");
			  fw.write("\t\t<PortComponent1>"+port1+"</PortComponent1>\n");
			  fw.write("\t\t<LinkedComponent2Name>"+this.getAttachedComponent(1).getComponentName()+"</LinkedComponent2Name>\n");
			  fw.write("\t\t<PortComponent2>"+port2+"</PortComponent2>\n");
			  fw.write("\t\t<Bandwidth>"+bandwidth+"</Bandwidth>\n");
			  fw.write("\t\t<Delay>"+delay+"</Delay>\n");
			  fw.write("\t\t<Loss>"+loss+"</Loss>\n");
			  fw.write("\t\t<MaxqueueSize>"+maxQueueSize+"</MaxqueueSize>\n");
			  fw.write("\t</Link>\n");
		  } catch (IOException e) {
				e.printStackTrace();
		  }
	  }
	  protected boolean errors() {
			if((((JTextField)this.ComponentInformations.getComponent(3)).getText().length()<1)
					|| Window.componentNamed(((JTextField)this.ComponentInformations.getComponent(3)).getText())!=null && !((JTextField)this.ComponentInformations.getComponent(3)).getText().equals(this.getComponentName())){
				  
				new JOptionPane();;
				JOptionPane.showMessageDialog(null,
						"Error!" + ((((JTextField)this.ComponentInformations.getComponent(3)).getText().length()<1)?"\nIncorrect name!":"")
						+ ((Window.componentNamed(((JTextField)this.ComponentInformations.getComponent(3)).getText())!=null && !((JTextField)this.ComponentInformations.getComponent(3)).getText().equals(this.getComponentName()))?"Name already used!":"")
						, "Export", JOptionPane.WARNING_MESSAGE);
				return true;
			}
			else {return false;}
	  }

}
