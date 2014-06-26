import java.awt.Color;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Window extends JFrame implements MenuComponentListener{

	private static final long serialVersionUID = 1L;
	private WindowPanel rightMenu;
	public static DrawedLinkSpace leftMenu;
	private WindowPanel bottomMenu;
	private WindowPanel cont;
	private WindowPanel dragZone;
	private JLabel informationLabel;
	private WindowPanel informationPanel;
	public static LinkedList<Component> droppedComponentList;
	private int currentIndex;
	private String xmlFilePath=null;
	private String pyFilePath=null;
	private JFileChooser chooser = new JFileChooser();
	private FileNameExtensionFilter xmlFilter;
	private FileNameExtensionFilter pyFilter;
	
	public Window(int x, int y) {
		
	    super("Mininet topology creator");
		final Point windowSize = new Point();
		windowSize.x=x;
		windowSize.y=y;
	    setSize(windowSize.x, windowSize.y);
	    this.setLocationRelativeTo(null);               
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLayout(null); 
	    droppedComponentList = new LinkedList<Component>();
	    
	    //Contents
	    cont = new WindowPanel(0,0,800,800,Color.black,null);
	    this.setContentPane(cont);
	    //Right menu
	    rightMenu = new WindowPanel(this.getWidth()-60,0, this.getWidth()-50, this.getHeight()-200,rgbToHsl(220,220,220),null);
	    cont.add(rightMenu,0);
	    //Left menu
	    leftMenu = new DrawedLinkSpace(0,0,this.getWidth()-60, this.getHeight()-200,new Color(248,248,248),null);
	    leftMenu.setBorder(BorderFactory.createLineBorder(Color.black));
	    cont.add(leftMenu,1);
	    leftMenu.notDraggableListener(this);	    
	    //Drag zone
	    dragZone = new WindowPanel(0,0,this.getWidth(), this.getHeight()-200,null,null);
	    cont.add(dragZone,2);	    
	    //Bottom menu
	    bottomMenu = new WindowPanel(0,this.getHeight()-200,this.getWidth(), 200,Color.getHSBColor(0.6f,0.2f,0.7f),null);
	    cont.add(bottomMenu,3);
	    //Informations panel
	    informationPanel=new WindowPanel(0,0,this.getWidth(), 140,rgbToHsl(220,220,220),null);
	    bottomMenu.add(informationPanel);	    
	    //Console 
	    informationLabel=new JLabel();
	    informationLabel.setBounds(0,150,this.getWidth(), 15);
	    bottomMenu.add(informationLabel);
	    //Components
	    SwitchComponent compMenuSwitch = new SwitchComponent("switch",this.getWidth()-10 -40,10,40,40,dragZone,rightMenu,true,0,true);
	    cont.add(compMenuSwitch);
	    compMenuSwitch.draggableListener(this);
	    HostComponent compMenuHost = new HostComponent("host",this.getWidth()-10 -40,60,40,40,dragZone,rightMenu,true,0,true);
	    cont.add(compMenuHost);
	    compMenuHost.draggableListener(this);
	    PhysicalInterfaceComponent compMenuInterface = new PhysicalInterfaceComponent("physical interface",this.getWidth()-10 -40,110,40,40,dragZone,rightMenu,true,0,true);
	    cont.add(compMenuInterface);
	    compMenuInterface.draggableListener(this);
	    ControllerComponent compMenuController = new ControllerComponent("controller",this.getWidth()-10 -40,160,40,40,dragZone,rightMenu,true,0,true);
	    cont.add(compMenuController);
	    compMenuController.draggableListener(this);
	    LinkComponent compMenuLink = new LinkComponent("link",this.getWidth()-10 -40,210,40,40,true,0,true);
	    cont.add(compMenuLink);
	    compMenuLink.notDraggableListener(this);
	    RandomTopologyComponent compMenuRandom = new RandomTopologyComponent("network",this.getWidth()-10 -40,260,40,40,true,0,true);
	    cont.add(compMenuRandom);
	    compMenuRandom.notDraggableListener(this);
	    DeleteComponent compMenuDelete = new DeleteComponent("delete",this.getWidth()-10 -40,310,40,40,true,0,true);
	    cont.add(compMenuDelete);
	    compMenuDelete.notDraggableListener(this);
	    ExportComponent compMenuExport = new ExportComponent("export",this.getWidth()-10 -40,360,40,40,true,0,true);
	    cont.add(compMenuExport);
	    compMenuExport.notDraggableListener(this);
	    ExportXMLComponent compMenuExportXML = new ExportXMLComponent("export",this.getWidth()-10 -40,410,40,40,true,0,true);
	    cont.add(compMenuExportXML);
	    compMenuExportXML.notDraggableListener(this);
	    ImportComponent compMenuImport = new ImportComponent("import",this.getWidth()-10 -40,460,40,40,true,0,true);
	    cont.add(compMenuImport);
	    compMenuImport.notDraggableListener(this);
	    //Z-index
	    cont.setComponentZOrder(compMenuSwitch,droppedComponentList.size());
	    cont.setComponentZOrder(compMenuHost,droppedComponentList.size());
	    cont.setComponentZOrder(compMenuInterface,droppedComponentList.size());
	    cont.setComponentZOrder(compMenuController,droppedComponentList.size());
	    cont.setComponentZOrder(compMenuLink,droppedComponentList.size());
	    cont.setComponentZOrder(compMenuRandom,droppedComponentList.size());
	    cont.setComponentZOrder(compMenuDelete,droppedComponentList.size());
	    cont.setComponentZOrder(compMenuExport,droppedComponentList.size());
	    cont.setComponentZOrder(compMenuExportXML,droppedComponentList.size());
	    cont.setComponentZOrder(compMenuImport,droppedComponentList.size());
	    //this.listAllElements(cont);

	    xmlFilter = new FileNameExtensionFilter("Mininet projects (.xml)", "xml");
		pyFilter = new FileNameExtensionFilter("Mininet script (.py)", "py");
	    
	        
	    addKeyListener(new KeyListener() {
			@Override public void keyPressed(KeyEvent arg0) {}
			@Override public void keyReleased(KeyEvent arg0) {}
			@Override public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar()=='\u007F')
				{
					deleteComponent();
				}		
			}
	    });
	    this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                System.out.println("cont Resized: "+cont.getWidth()+","+cont.getHeight());
                updatePosition();
            }
	    });

	}
    private void updatePosition() {
    	System.out.println("Window Resized: "+this.getWidth()+","+this.getHeight());
    	cont.repaint();
	    

	}
        
    public void drop(Component d,Point dropPosition){
    	DraggableComponent compAdded = null;
    	int number=0;
    	
    	((JComponent) cont.getComponent(cont.getComponentCount()-3)).setBorder(BorderFactory.createLineBorder(Color.black));
    	    		
		if(d.getComponentType().equals("Switch")){
			while (componentNamed("s"+number) != null) number++;
    		compAdded = new SwitchComponent("s"+number,dropPosition.x,dropPosition.y,60,75,leftMenu,leftMenu,false,droppedComponentList.size(),false);
    	}
    	else if(d.getComponentType().equals("Host")){
    		while (componentNamed("h"+number) != null) number++;
    		compAdded = new HostComponent("h"+number,dropPosition.x,dropPosition.y,60,75,leftMenu,leftMenu,false,droppedComponentList.size(),false);
    	}
    	else if(d.getComponentType().equals("PhysicalInterface")){
    		while (componentNamed("eth"+number) != null) number++;
    		compAdded = new PhysicalInterfaceComponent("eth"+number,dropPosition.x,dropPosition.y,60,75,leftMenu,leftMenu,false,droppedComponentList.size(),false);
    	}
    	else if(d.getComponentType().equals("Controller")){
    		while (componentNamed("c"+number) != null) number++;
    		compAdded = new ControllerComponent("c"+number,dropPosition.x,dropPosition.y,60,75,leftMenu,leftMenu,false,droppedComponentList.size(),false);
    	}
    	
    	createDroppedComponent(compAdded);
    }
    public void createDroppedComponent(DraggableComponent newComponent){
    	leftMenu.add(newComponent);
    	droppedComponentList.add(newComponent);
    	newComponent.setDropped(true);
    	newComponent.draggableListener(this);
    	newComponent.componentListener(this);
    	this.changeIndication(newComponent.getName() + " added to the network",0);
    	this.repaint();
    	this.printInformations(newComponent);
    }
    public void createLinkComponent(){
    	informationPanel.removeAll();
    	if(droppedComponentList.size()>currentIndex){
    	droppedComponentList.get(currentIndex).setImage(false);
    	droppedComponentList.get(currentIndex).setClicked(false);}
    	this.changeIndication("Please select the first component to establish a link",1);
    	this.repaint();
    }
    public void changeColor(Boolean canDrop){
    	if(canDrop){
    	((JComponent) cont.getComponent(cont.getComponentCount()-3)).setBorder(BorderFactory.createLineBorder(Color.green));}
    	else{((JComponent) cont.getComponent(cont.getComponentCount()-3)).setBorder(BorderFactory.createLineBorder(Color.black));}
    }
    public void printInformations(Component d){
    	informationPanel.removeAll();
    	if(droppedComponentList.size()>currentIndex){
    	droppedComponentList.get(currentIndex).setImage(false);
    	droppedComponentList.get(currentIndex).setClicked(false);}
    	d.setImage(true);
    	d.setClicked(true);
	    currentIndex=d.getIndex();
	    //Don't print information if we try to make a link between two components but start to make a link
    	if(!((Component)cont.getComponent(5)).getClicked()){
    		informationPanel.add(d.getComponentInformations());}
    	else{this.drawLinkComponent(d);
    	}	
    	this.repaint();
    }
    public void drawLinkComponent(Component d){
		if(((LinkComponent)cont.getComponent(5)).getAttachedComponent(0)==null){
        	this.changeIndication("Please select the second component to establish a link",1);
    		((LinkComponent)cont.getComponent(5)).setAttachedComponent(d, 0);}
    		else if(((LinkComponent)cont.getComponent(5)).getAttachedComponent(1)==null){
    		((LinkComponent)cont.getComponent(5)).setAttachedComponent(d, 1);
    		traceLinkComponent(((LinkComponent)cont.getComponent(5)).getAttachedComponent(0),((LinkComponent)cont.getComponent(5)).getAttachedComponent(1));
    		}
    }
    public void traceLinkComponent(Component ca, Component cb){
    	if (verify(ca, cb)) {
	    	DrawedLinkComponent linkAdded = new DrawedLinkComponent("l"+countCompTypeDropped("Link"),ca,cb,droppedComponentList.size());
	    	droppedComponentList.add(linkAdded);
	    	leftMenu.addLink(linkAdded);
	    	connectTwoComponents(linkAdded);
    	}
    	((LinkComponent)cont.getComponent(5)).setAttachedComponent(null, 0);
    	((LinkComponent)cont.getComponent(5)).setAttachedComponent(null, 1);
		informationPanel.removeAll();
    	droppedComponentList.get(currentIndex).setImage(false);
    	droppedComponentList.get(currentIndex).setClicked(false);
    	this.changeIndication("Please select the first component to establish another link",1);
    }
    public void connectTwoComponents(DrawedLinkComponent link){
    	Component c0=link.getAttachedComponent(0) 
    			, c1=link.getAttachedComponent(1);
    	
    	((DraggableComponent)c0).setPort(((DraggableComponent)c0).requestPort(), c1, link);
    	((DraggableComponent)c1).setPort(((DraggableComponent)c1).requestPort(), c0 ,link);
    	repaint();
    	
    }
    public void changeIndication(String newIndication,int priority){
    	if(((Component)cont.getComponent(0)).getClicked()){
    		if(priority==1){informationLabel.setText(newIndication);}}
    	else{informationLabel.setText(newIndication);}
    	this.repaint();
    }
    public static int countCompTypeDropped(String type){
    	int j=0;
    	for(int i=0;i<droppedComponentList.size();i++){
    		if(droppedComponentList.get(i).getComponentType().equals(type)){j++;}
    	}
    	return j;
    }
    public void actualize(){
    	repaint();
    	leftMenu.repaint();
    }
    public void unselectAllNonDraggableComponent(){
    	((Component)cont.getComponent(2)).setClicked(false);
    	((Component)cont.getComponent(2)).setImage(false);
    }
    public void makeImport(){
        	importXML();
    }    
	public void listAllElements(JPanel cont){
    	for(int i=0;i<cont.getComponentCount();i++){
    		if(cont.getComponent(i) instanceof JLabel){
    			System.out.print("\n"+ i +"::::"+cont.getComponent(i).getClass());
    		}
    		else{
    			System.out.print("\n"+ i +"::"+((Component)cont.getComponent(i)).getIndex()+"::"+((Component)cont.getComponent(i)).getClass());
    		}
    	}
    }
    public void listAllElements(LinkedList<Component> cont){
    	System.out.print("\nLISTING TABLE");
    	for(int i=0;i<cont.size();i++){

    			System.out.print("\n"+ i +"::"+((Component)cont.get(i)).getIndex()+"::"+((Component)cont.get(i)).getClass());
    		
    	}
    }
    public void decreaseIndex(LinkedList<Component> cont, int index){
    	for(int i=index;i<cont.size();i++){

    		((Component)cont.get(i)).setIndex(i);

    	}
    }
    public void deleteComponent(){
    	//listAllElements(droppedComponentList);
		//*
    	if(droppedComponentList.size()>currentIndex)
		{
			if(!droppedComponentList.get(currentIndex).getComponentType().equals("Link")){
				
				System.out.println("Deleting component "+droppedComponentList.get(currentIndex).getComponentName());
				
				//System.out.println(droppedComponentList.get(currentIndex).getComponentName()+" : "+ droppedComponentList.get(currentIndex).getPortsList().size());
				if(droppedComponentList.get(currentIndex).getPortsList().size()>0){
					
					System.out.println("\tUpdating ports ("+droppedComponentList.get(currentIndex).getPortsList().size()+" ports)");
					
					for(int i=droppedComponentList.get(currentIndex).getPortsList().size()-1;i>=0;i--){
						
						//INDEX OF DRAWED LINK
						int index=droppedComponentList.get(currentIndex).getPortsList().get(i).getLink().getIndex();
						
						//INDEX OF 2ND COMPONENT ATTACHED
						int index2=((DrawedLinkComponent)droppedComponentList.get(index)).getAttachedComponent(0).getIndex();
						if(index2==currentIndex){
							index2=((DrawedLinkComponent)droppedComponentList.get(index)).getAttachedComponent(1).getIndex();
						}
						
						//UPDATE PORTS OF 2ND COMPONENT ATTACHED
						int portNb=0;
						for (int j=0;j<droppedComponentList.get(index2).getPortsList().size();j++){
							System.out.println(j);
							if (droppedComponentList.get(index2).getPortsList().get(j).getAttachement().getIndex()==currentIndex)
							{ portNb=j;
							break;}
							
						}((DraggableComponent)droppedComponentList.get(index2)).updatePort(portNb);
						System.out.println("\tPort "+portNb+" updated on "+droppedComponentList.get(index2).getComponentName());
						
						//REMOVE DRAWED LINK
						//((DrawedLinkComponent)droppedComponentList.get(index)).setComponentName(null);
						System.out.println("\tRemoving "+droppedComponentList.get(index).getComponentName());
						leftMenu.removeLink(((DrawedLinkComponent)droppedComponentList.get(index)));
						droppedComponentList.get(index).finalize();
						droppedComponentList.remove(index);
						
						if (index<currentIndex){System.out.println("coucou");currentIndex--;}
						
						decreaseIndex(droppedComponentList,index);	
						
						
					}
				}
				
				System.out.println("\tStarting "+droppedComponentList.get(currentIndex).getComponentName()+" deletion");
				droppedComponentList.get(currentIndex).setComponentName(null);
				((DraggableComponent)droppedComponentList.get(currentIndex)).removeComponent();
				droppedComponentList.get(currentIndex).finalize();
				droppedComponentList.remove(currentIndex);
				leftMenu.remove(currentIndex);
				decreaseIndex(droppedComponentList,currentIndex);
				System.out.println("\tComponent deleted");
				
				

				
			}
			else{
				
				System.out.println("Deleting link "+droppedComponentList.get(currentIndex).getComponentName());
				
				//INDEX OF COMPONENTS ATTACHED
				int index=((DrawedLinkComponent)droppedComponentList.get(currentIndex)).getAttachedComponent(0).getIndex();
				int index2=((DrawedLinkComponent)droppedComponentList.get(currentIndex)).getAttachedComponent(1).getIndex();
				
				//UPDATE PORTS OF COMPONENTS ATTACHED
				int portNb=0;
				System.out.println("\tsize: "+droppedComponentList.get(index).getPortsList().size());
				for (int j=0;j<droppedComponentList.get(index).getPortsList().size();j++){	
					System.out.println("\t"+index2+ " = "+droppedComponentList.get(index).getPortsList().get(j).getAttachement().getIndex()+ "?");
					if (droppedComponentList.get(index).getPortsList().get(j).getAttachement().getIndex()==index2)
					{ portNb=j;
					((DraggableComponent)droppedComponentList.get(index)).updatePort(portNb);
					System.out.println("\tPort "+portNb+" updated on "+droppedComponentList.get(index).getComponentName());
					break;}
				}
				
				
				System.out.println("\tsize: "+droppedComponentList.get(index2).getPortsList().size());
				for (int j=0;j<droppedComponentList.get(index2).getPortsList().size();j++){					
					System.out.println("\t"+(index)+ " = "+droppedComponentList.get(index2).getPortsList().get(j).getAttachement().getIndex()+ "?");
					if (droppedComponentList.get(index2).getPortsList().get(j).getAttachement().getIndex()==index)
					{ portNb=j;
					((DraggableComponent)droppedComponentList.get(index2)).updatePort(portNb);
					System.out.println("\tPort "+portNb+" updated on "+droppedComponentList.get(index2).getComponentName());
					break;}					
				}
				
				
								
				//REMOVE DRAWED LINK
				((DrawedLinkComponent)droppedComponentList.get(currentIndex)).setComponentName(null);
				leftMenu.removeLink(((DrawedLinkComponent)droppedComponentList.get(currentIndex)));
				droppedComponentList.get(currentIndex).finalize();
				droppedComponentList.remove(currentIndex);
				decreaseIndex(droppedComponentList,currentIndex);
				
				System.out.println("\tComponent deleted");
				
			}
			informationPanel.removeAll();
			//SELECT NOTHING
			currentIndex=droppedComponentList.size()+10000;
			repaint();
		}//*/
    }
    public Color rgbToHsl(double r, double g, double b){
        r = r/255;
        g = g/255;
        b = b/255;
        double max = Math.max(r, Math.max(g, b));
        double min = Math.min(r, Math.min(g, b));
        double h=(max+min)/2;
        double s=(max+min)/2;
        double l=(max+min)/2;
        if(max==min){
            h=0;
            s=0; // achromatic
        }
        else{
        	double d=max-min;
            if(l>0.5){s=d/(2-max-min);}
            else{s=d/(max+min);}
            if(max==r){
            	if(g<b){h=(g-b)/d+6;}
            	else{h=(g-b)/d;}
            }
            else if(max==g){h=(b-r)/d+2;}
            else{h=(r-g)/d+4;}
            h=h/6;
        }
        return Color.getHSBColor((float)h,(float)s,(float)l);
    }
    public void exportPY() {
    	
    	chooser.resetChoosableFileFilters();
        chooser.setFileFilter(pyFilter);
        int returnVal = chooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	System.out.println("You chose to save the project into this file: " +
        		chooser.getSelectedFile().getName());
        	pyFilePath=chooser.getSelectedFile().getAbsolutePath();
        }
        if (pyFilePath!=null){
        	
        	if (!pyFilePath.substring(pyFilePath.length()-3).equals(".py") || pyFilePath.length()<3){
        		pyFilePath+=".py";
        	}
        	        	    	
	        PrintWriter out = null; 
	        LinkedList<Component> lSwitch = new LinkedList<Component>();
	        LinkedList<Component> lHost = new LinkedList<Component>();
	        LinkedList<Component> lController = new LinkedList<Component>();
	        LinkedList<Component> lPhysicalInterface = new LinkedList<Component>();
	        LinkedList<Component> lLink = new LinkedList<Component>();
	        int error=0;
	        
	        try {
	        	
	        	
	
	        	for (Component component: droppedComponentList) {
	
	        		if (component instanceof SwitchComponent) {
	        			lSwitch.add(component);
	        		}
	        		else if(component instanceof HostComponent){
	        			lHost.add(component);					
	        	   	}
	        		else if(component instanceof DrawedLinkComponent){
	        			lLink.add(component);
	        		}
	        		else if(component instanceof ControllerComponent){
	        			lController.add(component);
	        		}
	        		else if(component instanceof PhysicalInterfaceComponent){
	        			lPhysicalInterface.add(component);
	        		}	
	        	}
	        	
	        	out = new PrintWriter(pyFilePath);
	        	out.println("#!/usr/bin/python\n");
	        	out.println("import re, sys");
	
	        	out.println("from mininet.topo import Topo");
	        	out.println("from mininet.net import Mininet");
	        	out.println("from mininet.cli import CLI");
	        	out.println("from mininet.util import dumpNodeConnections, quietRun");
	        	out.println("from mininet.log import setLogLevel, info, error");
	        	out.println("from mininet.node import Controller, RemoteController, OVSKernelSwitch");
	        	out.println("from mininet.link import TCLink, Intf, Link");
	        	
	        	CopyMyCLI(out);
	        	
	        	if (lPhysicalInterface.size()>0){
	        	out.println("\n\ndef checkIntf( intf ):");
	        	out.println("\t#Make sure intf exists and is not configured.");
	        	out.println("\tif ( ' %s:' % intf ) not in quietRun( 'ip link show' ):");
	        	out.println("\t\terror( 'Error:', intf, 'does not exist!\\n' )");
	        	out.println("\t\texit( 1 )");
	        	out.println("\tips = re.findall( r'\\d+\\.\\d+\\.\\d+\\.\\d+', quietRun( 'ifconfig ' + intf ) )");
	        	out.println("\tif ips:");
	        	out.println("\t\terror( 'Error:', intf, 'has an IP address, and is probably in use!' )");
	        	out.println("\t\texit( 1 )");
	        	}
	
				out.println("\n\nif __name__ == '__main__':");			    
				out.println("\tsetLogLevel( 'info' )\n");
				
				out.println("\tinfo( '*** Creating network ***\\n' )");
				
				
				out.println("\n\tinfo( '\\n*** Creating Controlers ***\\n' )");
				out.println("\tnet = Mininet( controller=RemoteController, link=TCLink, switch=OVSKernelSwitch )");
	
	        	ListIterator<Component> iterator = lController.listIterator();
	        	while (iterator.hasNext()) {
	        		Component obj=iterator.next();
	        		if (obj.getComponentName().length()>=1){
	        			out.println("\t" + obj.getComponentName()
	        				+ " = net.addController('" + obj.getComponentName()
	        				+ ((((ControllerComponent)obj).getIP().length()>=1)?("', RemoteController, ip='" + ((ControllerComponent)obj).getIP()+"',"):"")
	        				+ " port="+((ControllerComponent)obj).getListeningPort()
	        				+ " )");
	        		}
	        		else {
	        			error++;
	        			out.println("#### CONTROLLER NAME ERROR ####");
	        			new JOptionPane();;
	        			JOptionPane.showMessageDialog(null, "Error during the export !\nEvery switch must have a name!", "Export", JOptionPane.WARNING_MESSAGE);
	        		}
	        	}
	        	
	        	
				out.println("\n\tinfo( '\\n*** Creating Switches ***\\n' )"); 	
				
				iterator = lSwitch.listIterator();
	        	while (iterator.hasNext()) {
	        		Component obj=iterator.next();
	        		if (obj.getComponentName().length()>=1){
		        		out.println("\t"+obj.getComponentName() 
	        				+ " = net.addSwitch( '" + obj.getComponentName()
	        				+ ((((SwitchComponent)obj).getMac().length()>=1)?"' , mac='" + ((SwitchComponent)obj).getMac():"")
	        				+ "' , port='" + ((SwitchComponent)obj).getListeningPort()
	        				+ "' , switch='" + ((SwitchComponent)obj).getSwitchType() 
	        				+ "' )");
	        		}
	        		else {
	        			error++;
	        			out.println("#### SWITCH NAME ERROR ####");
	        			new JOptionPane();;
	        			JOptionPane.showMessageDialog(null, "Error during the export !\nEvery switch must have a name!", "Export", JOptionPane.WARNING_MESSAGE);
	        		}
	        	}
	        	
	        	
	        	out.println("\n\tinfo( '\\n*** Creating Hosts ***\\n' )");
	        	
	        	iterator = lHost.listIterator();
	        	while (iterator.hasNext()) {
	        		Component obj=iterator.next();
	        		int mask=24;
	        		if (((HostComponent)obj).getMask().length()>=1){
	        			if (((HostComponent)obj).getMask().equals("128.0.0.0")) 	   		mask=1;
	        			else if (((HostComponent)obj).getMask().equals("192.0.0.0")) 		mask=2;
	        			else if (((HostComponent)obj).getMask().equals("224.0.0.0")) 		mask=3;
	        			else if (((HostComponent)obj).getMask().equals("240.0.0.0")) 		mask=4;
	        			else if (((HostComponent)obj).getMask().equals("248.0.0.0")) 		mask=5;
	        			else if (((HostComponent)obj).getMask().equals("252.0.0.0")) 		mask=6;
	        			else if (((HostComponent)obj).getMask().equals("254.0.0.0")) 		mask=7;
	        			else if (((HostComponent)obj).getMask().equals("255.0.0.0")) 		mask=8;
	        			else if (((HostComponent)obj).getMask().equals("255.128.0.0")) 		mask=9;
	        			else if (((HostComponent)obj).getMask().equals("255.192.0.0")) 		mask=10;
	        			else if (((HostComponent)obj).getMask().equals("255.224.0.0")) 		mask=11;
	        			else if (((HostComponent)obj).getMask().equals("255.240.0.0")) 		mask=12;
	        			else if (((HostComponent)obj).getMask().equals("255.248.0.0")) 		mask=13;
	        			else if (((HostComponent)obj).getMask().equals("255.252.0.0")) 		mask=14;
	        			else if (((HostComponent)obj).getMask().equals("255.254.0.0")) 		mask=15;
	        			else if (((HostComponent)obj).getMask().equals("255.255.0.0")) 		mask=16;
	        			else if (((HostComponent)obj).getMask().equals("255.255.128.0")) 	mask=17;
	        			else if (((HostComponent)obj).getMask().equals("255.255.192.0")) 	mask=18;
	        			else if (((HostComponent)obj).getMask().equals("255.255.224.0")) 	mask=19;
	        			else if (((HostComponent)obj).getMask().equals("255.255.240.0")) 	mask=20;
	        			else if (((HostComponent)obj).getMask().equals("255.255.248.0")) 	mask=21;
	        			else if (((HostComponent)obj).getMask().equals("255.255.252.0")) 	mask=22;
	        			else if (((HostComponent)obj).getMask().equals("255.255.254.0")) 	mask=23;
	        			else if (((HostComponent)obj).getMask().equals("255.255.255.0")) 	mask=24;
	        			else if (((HostComponent)obj).getMask().equals("255.255.255.128")) 	mask=25;
	        			else if (((HostComponent)obj).getMask().equals("255.255.255.192")) 	mask=26;
	        			else if (((HostComponent)obj).getMask().equals("255.255.255.224")) 	mask=27;
	        			else if (((HostComponent)obj).getMask().equals("255.255.255.240")) 	mask=28;
	        			else if (((HostComponent)obj).getMask().equals("255.255.255.248")) 	mask=29;
	        			else if (((HostComponent)obj).getMask().equals("255.255.255.252")) 	mask=30;
	        			else if (((HostComponent)obj).getMask().equals("255.255.255.254")) 	mask=31;
	        			else if (((HostComponent)obj).getMask().equals("255.255.255.255")) 	mask=32;
	        			
	        		}
	        		if (obj.getComponentName().length()>=1){
	        			out.println("\t"+obj.getComponentName()        		
	        				+ " = net.addHost( '" + obj.getComponentName()
	        				+ ((((HostComponent)obj).getIP().length()>=1 && (((HostComponent)obj).getMask().length()>=1))?("' , ip='" + ((HostComponent)obj).getIP()
	        				+ "/" + mask):"")
	        				+ ((((HostComponent)obj).getMac().length()>=1)?"' , mac='" + ((HostComponent)obj).getMac():"" )
	        				+ "' )");
	        			if (((HostComponent)obj).getIP().length()>=1 && !(((HostComponent)obj).getMask().length()>=1)){
	        				error++;
	            			out.println("#### HOST IP ERROR ABOVE ####");
	            			new JOptionPane();;
	            			JOptionPane.showMessageDialog(null, "Error during the export !\nIP adress has bees specified, but not the mask! ("+obj.getComponentName()+")", "Export", JOptionPane.WARNING_MESSAGE);
	        			}
	        		}
	        		else {
	        			error++;
	        			out.println("#### HOST NAME ERROR ####");
	        			new JOptionPane();;
	        			JOptionPane.showMessageDialog(null, "Error during the export !\nEvery Host must have a name!", "Export", JOptionPane.WARNING_MESSAGE);
	        		}
	        	}
	
	        	
	        	out.println("\n\tinfo( '\\n*** Creating Physical Interfaces ***\\n' )");
	
	        	iterator = lPhysicalInterface.listIterator();
	        	while (iterator.hasNext()) {
	        		Component obj=iterator.next();
	        		try{
	        			if (obj.getComponentName().length()>=1){
			        		out.println("\tinfo( '\t*** Checking', '"+ obj.getComponentName() +"', '\\n' )");
			            	out.println("\tcheckIntf( '"+ obj.getComponentName() +"' )");        		
			        		out.println("\t" + obj.getComponentName()
			        				+ " = Intf( '" + obj.getComponentName()
			        				+ "' , node=" + ((PortComponent)((PhysicalInterfaceComponent)obj).getPortsList().get(0)).getAttachement().getComponentName()
			        				+ " )");
	        			}
	        			else {
	            			error++;
	            			out.println("#### PHYSICAL INTERFACE NAME ERROR ####");
	            			new JOptionPane();;
	            			JOptionPane.showMessageDialog(null, "Error during the export !\nEvery physical interface must have a name!", "Export", JOptionPane.WARNING_MESSAGE);
	            		}
	        		}catch(IndexOutOfBoundsException e){
	        			error++;
	        			out.println("#### PHYSICAL INTERFACE LINK ERROR ####");
	        			new JOptionPane();;
	        			JOptionPane.showMessageDialog(null, "Error during the export !\nPhysical interfaces must be linked to a Switch ("+obj.getComponentName()+")", "Export", JOptionPane.WARNING_MESSAGE);
	        			e.printStackTrace();
	        			
	        		}
	        		finally{}
	        	}
	        	
	        	
	        	out.println("\n\tinfo( '\\n*** Creating Links ***\\n' )");
	        	
	        	iterator = lLink.listIterator();
	        	while (iterator.hasNext()) {
	        		Component obj=iterator.next();
	        		Component component0 = (((DrawedLinkComponent)obj).getAttachedComponent(0));
	        		Component component1 = (((DrawedLinkComponent)obj).getAttachedComponent(1));
	        		if (	   component0 instanceof PhysicalInterfaceComponent 
	        				|| component1 instanceof PhysicalInterfaceComponent 
	        				|| component0 instanceof ControllerComponent 
	        				|| component1 instanceof ControllerComponent){}
	        		else{
	        		
	        			int port1=-1, port2=-1;
	        			for (int i=0; i<component0.getPortsList().size();i++){
	        				if (component0.getPortsList().get(i).getAttachement()==component1){
	        					port1=component0.getPortsList().get(i).getPortNumber();
	        				}
	        			}
	        			for (int i=0; i<component1.getPortsList().size();i++){
	        				if (component1.getPortsList().get(i).getAttachement()==component0){
	        					port2=component1.getPortsList().get(i).getPortNumber();
	        				}
	        			}
		        		out.println("\tnet.addLink(" + component0.getComponentName()
		        				+ " , " + component1.getComponentName()
		        				+ " , bw=" + ((DrawedLinkComponent)obj).getBandwidth()
		        				+ " , delay='" + ((DrawedLinkComponent)obj).getDelay()
		        				+ "ms' , loss=" + ((DrawedLinkComponent)obj).getLoss()
		        				+ " , max_queue_size=" + ((DrawedLinkComponent)obj).getMaxqueuesize()
		        				+ " , intfName1='" + component0.getComponentName() + "-" + component1.getComponentName()
		        				+ "' , intfName2='" + component1.getComponentName() + "-" + component0.getComponentName()
		        				//+ "' , port1=" + port1
		        				//+ " , port2=" + port2
		        				+ "' )");
	        		}
	        	}
	        	
				
				out.println("\n\tnet.start()");
				for(Component obj:lLink){
					if (((DrawedLinkComponent) obj).getAttachedComponent(0).getComponentName().length()<1 || ((DrawedLinkComponent) obj).getAttachedComponent(1).getComponentName().length()<1){
						error++;
	        			out.println("#### SWITCH OR CONTROLLER NAME ERROR ####");
					}
					else if (((DrawedLinkComponent) obj).getAttachedComponent(0) instanceof ControllerComponent){
						out.println("\t"+((SwitchComponent) ((DrawedLinkComponent) obj).getAttachedComponent(1)).getComponentName()
								+".start( ["
								+ ((ControllerComponent) ((DrawedLinkComponent) obj).getAttachedComponent(0)).getComponentName()
								+"] )");
					}
					else if (((DrawedLinkComponent) obj).getAttachedComponent(1) instanceof ControllerComponent){
						out.println("\t"+((SwitchComponent) ((DrawedLinkComponent) obj).getAttachedComponent(0)).getComponentName()
								+".start( ["
								+ ((ControllerComponent) ((DrawedLinkComponent) obj).getAttachedComponent(1)).getComponentName()
								+"] )");
					}
					
				}
				
				out.println("\tMyCLI( net )\n\tnet.stop()"); //Change MyCLI to CLI if you're not using the new functions
	        	
				new JOptionPane();;
				if (error==0)JOptionPane.showMessageDialog(null, "Topology succefully exported in "+pyFilePath+" !", "Export", JOptionPane.INFORMATION_MESSAGE);
				else if (error==1) JOptionPane.showMessageDialog(null, "Topology exported in "+pyFilePath+" with 1 error", "Export", JOptionPane.WARNING_MESSAGE);
				else JOptionPane.showMessageDialog(null, "Topology exported in "+pyFilePath+" with "+error+" errors", "Export", JOptionPane.WARNING_MESSAGE);
	
	        } catch (FileNotFoundException e) {
	        	new JOptionPane();;
				JOptionPane.showMessageDialog(null, "Error during the export !", "Export", JOptionPane.WARNING_MESSAGE);
	           e.printStackTrace();
	        } finally {
	           if (out != null) out.close();
	        }
        
        pyFilePath=null;
        }
    }
    private void CopyMyCLI(PrintWriter out) {
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/MyCLI.txt")));
    		String line;
    		line=reader.readLine();
    		out.println("\n\n");
			while (line!=null) {	
				out.println(line);
				line=reader.readLine();
			}
			reader.close();
			
    	}catch (IOException e) {
			e.printStackTrace();
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Error during the copy of MyCLI !"
					, "Export", JOptionPane.WARNING_MESSAGE);
		} 
		
	}
	public boolean verify(Component c0, Component c1){
	 
    	boolean result=false;
    	
    	boolean nbPorts=false, compatibility=false, unicity=true;
    	boolean c0isHost=false, c0isSwitch=false, c0isController=false, c0isPI=false;
    	boolean c1isHost=false, c1isSwitch=false, c1isController=false, c1isPI=false;
    	int c0nbPorts=10, c1nbPorts=10,nbController=0;
    	    	
    	if (c0 instanceof SwitchComponent){
    		c0isSwitch=true;
    	}
    	else if (c0 instanceof HostComponent){
    		c0isHost=true;
    		c0nbPorts=((HostComponent) c0).getPortsList().size();
    	}
    	else if (c0 instanceof ControllerComponent){
    		
    		c0isController=true;
    		
    		if (c1 instanceof SwitchComponent){
	    		for (PortComponent portComponent:((SwitchComponent) c1).getPortsList()){
	    			if (portComponent.getAttachement() instanceof ControllerComponent){		
	    	    				nbController++;
	    			}
	    		}
        	}    		
    	}
    	else if (c0 instanceof PhysicalInterfaceComponent){
    		c0isPI=true;
    		c0nbPorts=((PhysicalInterfaceComponent) c0).getPortsList().size();
    	}
    	
    	
    	
    	if (c1 instanceof SwitchComponent){
    		c1isSwitch=true;
    	}
    	else if (c1 instanceof HostComponent){
    		c1isHost=true;
    		c1nbPorts=((HostComponent) c1).getPortsList().size();
    	}
    	else if (c1 instanceof ControllerComponent){
    		
    		c1isController=true;
    		if (c0 instanceof SwitchComponent){
	    		for (PortComponent portComponent:((SwitchComponent) c0).getPortsList()){
	    			if (portComponent.getAttachement() instanceof ControllerComponent){		
	    	    				nbController++;
	    			}
	    		}
        	}
    		
    	}
    	else if (c1 instanceof PhysicalInterfaceComponent){
    		c1isPI=true;
    		c1nbPorts=((PhysicalInterfaceComponent) c1).getPortsList().size();
    	}

    	
    	
    	if ( 	   ((c0isHost || c0isPI) && c0nbPorts >= 1) 
    			|| ((c1isHost || c1isPI) && c1nbPorts >= 1) 
    			|| ( c0isSwitch && c1isController && nbController >= 1 ) 
    			|| ( c1isSwitch && c0isController && nbController >= 1 ) 
    	   ) {nbPorts=false;}
    	else {nbPorts=true;}
    	
    	if (	   (c0isHost && c1isSwitch) 
    			|| (c1isHost && c0isSwitch) 
    			|| (c0isSwitch && c1isSwitch )
    			|| (c0isPI && c1isSwitch || (c1isPI && c0isSwitch))
    			|| (c0isController && c1isSwitch) || (c1isController && c0isSwitch)
    	   ) {compatibility=true;}
    	else {compatibility=false;}

    	
    	for(Component component:droppedComponentList){
    		String c02Name=null, c12Name=null,
    				c01Name=c0.getComponentName(),
    				c11Name=c1.getComponentName();
    		if (component instanceof DrawedLinkComponent){
    			c02Name=((DrawedLinkComponent)component).getAttachedComponent(0).getComponentName();
    			c12Name=((DrawedLinkComponent)component).getAttachedComponent(1).getComponentName();

    			if (   (c01Name.equals(c02Name)&&c11Name.equals(c12Name) ) 
    				|| (c11Name.equals(c02Name)&&c01Name.equals(c12Name) ) )
    				{unicity=false;break;}
    		}
    	}
    		
    	
    	
    	
    	if (nbPorts && compatibility && unicity){
    		result= true;
    	}
    	else {
    		new JOptionPane();;
			JOptionPane.showMessageDialog(null, "Unable to link this two components!"
					+((nbPorts)?"":("\nToo much "+((nbController>=1)?"controller ":"")+"connections"))
					+((compatibility)?"":"\nComponent not compatible, you can't link a "+c0.getComponentType() +" and a "+c1.getComponentType())
					+((unicity)?"":"\nLink already created between " + c0.getComponentName() +" and "+ c1.getComponentName())
					, "Export", JOptionPane.WARNING_MESSAGE);
    	}
    	
    	return result;
    }
    public void exportXML() {
    	
    	chooser.resetChoosableFileFilters();
        chooser.setFileFilter(xmlFilter);
        int returnVal = chooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	System.out.println("You chose to save the project into this file: " +
        		chooser.getSelectedFile().getName());
        	xmlFilePath=chooser.getSelectedFile().getAbsolutePath();
        }
        if (xmlFilePath!=null){
        	
        	if (!xmlFilePath.substring(xmlFilePath.length()-4).equals(".xml") || xmlFilePath.length()<4){
        		xmlFilePath+=".xml";
        	}
    	
	    	FileWriter fw = null;
			try {
				fw = new FileWriter(xmlFilePath);  		
		        try {
		        	fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		        	fw.write("<Component>\n");
		            for (Component obj:droppedComponentList)
		            	obj.exportXML(fw);
		            fw.write("</Component>\n");
		            	
		        } catch (IOException e) {
					e.printStackTrace();
				} finally {
	
		        }
	        } catch (IOException e1) {
				e1.printStackTrace();
			} finally{
				try {
					fw.close();
					new JOptionPane();;
					JOptionPane.showMessageDialog(null, "Project succefully exported in "+xmlFilePath+" !"
							, "Export", JOptionPane.INFORMATION_MESSAGE);
								
				} catch (IOException e) {
					e.printStackTrace();
					new JOptionPane();;
					JOptionPane.showMessageDialog(null, "Error during the export in "+xmlFilePath+" !"
							, "Export", JOptionPane.WARNING_MESSAGE);
				}
	        }
			
			xmlFilePath=null;
        }
        
	}
    public void importXML() {
    	clearAllComponents();
    	//*
    	chooser.resetChoosableFileFilters();
        chooser.setFileFilter(xmlFilter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	System.out.println("You chose to save the project into this file: " +
        		chooser.getSelectedFile().getName());
        	xmlFilePath=chooser.getSelectedFile().getAbsolutePath();
        }
        
	    if (xmlFilePath!=null){
	        	
			try {
				Scanner scanner = new Scanner(new FileReader(xmlFilePath));
				
				//Importing the components without the links
				while (scanner.hasNextLine()) {	
					
					int X=-10,Y=-10;
					String componentName=null, ip=null, mac=null;
					String[] datas=null;
					String[] componentDatas=null;
	
					datas=getNextLineDatas(scanner);
									
					if (datas[0].equals("Switch")){										//If we found a switch, we store the important data and create a new object
						
						int listeningPort=0;
						String switchType=null;
						
						componentDatas=getNextLineDatas(scanner);						//Reading the position line
						componentDatas=getNextLineDatas(scanner);						//reading the X line and affecting
						X=Integer.parseInt(componentDatas[1]);	
						componentDatas=getNextLineDatas(scanner);						//reading the Y line and affecting
						Y=Integer.parseInt(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);
						componentDatas=getNextLineDatas(scanner);						//Reading the componentName
						componentName=(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading the IP or the Mac or the ListeningPort (depends if the informations have been given)
						if (componentDatas[0].equals("IP")){
							ip=componentDatas[1];
							componentDatas=getNextLineDatas(scanner);
						}
						if (componentDatas[0].equals("Mac")){
							mac=componentDatas[1];
							componentDatas=getNextLineDatas(scanner);
						}
						if (componentDatas[0].equals("ListeningPort")){
							listeningPort=Integer.parseInt(componentDatas[1]);
							componentDatas=getNextLineDatas(scanner);
						}
						switchType=componentDatas[1];
						componentDatas=getNextLineDatas(scanner);						//Reading the /Switch
						
						System.out.println("****SWITCH****X: "+X+"\nY: "+Y+"\ncomponentName: "+componentName+"\nip: "+ip+"\nmac: "+mac+"\nListeningPort: "+listeningPort+"\nswitchType: "+switchType);
						
						//Creating the Switch
						SwitchComponent newSwitch = new SwitchComponent(componentName, X, Y, 60,75,leftMenu,leftMenu,false,droppedComponentList.size(),false);
						newSwitch.setIP(ip);newSwitch.setMac(mac);newSwitch.setListeningPort(listeningPort);newSwitch.setSwitchType(switchType);
						createDroppedComponent(newSwitch);
					} 
					
					
					if (datas[0].equals("Host")){										//If we found a switch, we store the important data and create a new object
						
						String mask=null;
						
						componentDatas=getNextLineDatas(scanner);						//Reading the position line
						componentDatas=getNextLineDatas(scanner);						//reading the X line and affecting
						X=Integer.parseInt(componentDatas[1]);	
						componentDatas=getNextLineDatas(scanner);						//reading the Y line and affecting
						Y=Integer.parseInt(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);
						componentDatas=getNextLineDatas(scanner);						//Reading the componentName
						componentName=(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading the IP or the Mask or the Mac (depends if the informations have been given)
						if (componentDatas[0].equals("IP")){
							ip=componentDatas[1];
							componentDatas=getNextLineDatas(scanner);
						}
						if (componentDatas[0].equals("Mask")){
							mask=componentDatas[1];
							componentDatas=getNextLineDatas(scanner);
						}
						if (componentDatas[0].equals("Mac")){
							mac=componentDatas[1];
							componentDatas=getNextLineDatas(scanner);						//Reading the /Host
						}
										
						System.out.println("****HOST****\nX: "+X+"\nY: "+Y+"\ncomponentName: "+componentName+"\nip: "+ip+"\nmask: "+mask+"\nmac: "+mac);
						
						//Creating the Host
						HostComponent newHost = new HostComponent(componentName, X, Y, 60,75,leftMenu,leftMenu,false,droppedComponentList.size(),false);
						newHost.setIP(ip);newHost.setMac(mac);newHost.setMask(mask);
						createDroppedComponent(newHost);
					}
					
					if (datas[0].equals("PhysicalInterface")){										//If we found a PhysicalInterface, we store the important data and create a new object
											
						componentDatas=getNextLineDatas(scanner);						//Reading the position line
						componentDatas=getNextLineDatas(scanner);						//reading the X line and affecting
						X=Integer.parseInt(componentDatas[1]);	
						componentDatas=getNextLineDatas(scanner);						//reading the Y line and affecting
						Y=Integer.parseInt(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);
						componentDatas=getNextLineDatas(scanner);						//Reading the componentName
						componentName=(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading the /PhysicalInterface
						
										
						System.out.println("****PHYSICAL INTERFACE****\nX: "+X+"\nY: "+Y+"\ncomponentName: "+componentName);
						
						//Creating the Host
						PhysicalInterfaceComponent newPhysicalInterface = new PhysicalInterfaceComponent(componentName, X, Y, 60,75,leftMenu,leftMenu,false,droppedComponentList.size(),false);
						createDroppedComponent(newPhysicalInterface);
					}		
					
					if (datas[0].equals("Controller")){										//If we found a PhysicalInterface, we store the important data and create a new object
						
						int listeningPort=0;
						
						componentDatas=getNextLineDatas(scanner);						//Reading the position line
						componentDatas=getNextLineDatas(scanner);						//reading the X line and affecting
						X=Integer.parseInt(componentDatas[1]);	
						componentDatas=getNextLineDatas(scanner);						//reading the Y line and affecting
						Y=Integer.parseInt(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);
						componentDatas=getNextLineDatas(scanner);						//Reading the componentName
						componentName=(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading the IP or the ListeningPort (depends if the informations have been given)
						if (componentDatas[0].equals("IP")){
							ip=componentDatas[1];
							componentDatas=getNextLineDatas(scanner);
						}
						if (componentDatas[0].equals("ListeningPort")){
							listeningPort=Integer.parseInt(componentDatas[1]);
							componentDatas=getNextLineDatas(scanner);
						}
						
										
						System.out.println("****CONTROLLER****\nX: "+X+"\nY: "+Y+"\ncomponentName: "+componentName);
						
						//Creating the Controller
						ControllerComponent newController = new ControllerComponent(componentName, X, Y, 60,75,leftMenu,leftMenu,false,droppedComponentList.size(),false);
						newController.setIP(ip);newController.setListeningPort(listeningPort);
						createDroppedComponent(newController);
					}	
	
				}
				
				scanner = new Scanner(new FileReader(xmlFilePath));
				
				//Importing the links between Components
				while (scanner.hasNextLine()) {	
					
					String[] datas=null;
					String[] componentDatas=null;
					
					datas=getNextLineDatas(scanner);
					
					if (datas[0].equals("Link")){
						
						String componentName=null, name1=null, name2=null;
						int port1=-1, port2=-1, bandwidth=0, loss=0, delay=0, maxQueueSize=0;
						
						componentDatas=getNextLineDatas(scanner);						//Reading the componentName
						componentName=(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading name1
						name1=(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading port1
						port1=Integer.parseInt(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading name2
						name2=(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading port2
						port2=Integer.parseInt(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading bandwidth
						bandwidth=Integer.parseInt(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading delay
						delay=Integer.parseInt(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading loss
						loss=Integer.parseInt(componentDatas[1]);
						componentDatas=getNextLineDatas(scanner);						//Reading maxQueueSize
						maxQueueSize=Integer.parseInt(componentDatas[1]);					
										
						System.out.println("****LINK****\ncomponentName: "+componentName+"\nname1: "+name1+"\nname2: "+name2+"\nbandwidth: "+bandwidth+"\ndelay: "+delay+"\nloss: "+loss+"\nmqs:"+maxQueueSize);
					
						traceLinkComponent(componentNamed(name1),componentNamed(name2));
						DrawedLinkComponent newLink=((DrawedLinkComponent)droppedComponentList.get(droppedComponentList.size()-1));
						newLink.setComponentName(componentName);
						newLink.setBandwidth(bandwidth);
						newLink.setLoss(loss);
						newLink.setDelay(delay);
						newLink.setMaxqueuesize(maxQueueSize);
						newLink.updateInformations();
						newLink.updateSmallInformations();
						for (int i=0;i<newLink.getAttachedComponent(0).getPortsList().size();i++){
							  if (newLink.getAttachedComponent(0).getPortsList().get(i).getAttachement()==newLink.getAttachedComponent(1))
								  newLink.getAttachedComponent(0).getPortsList().get(i).setPortNumber(port1);
						  }
						  for (int i=0;i<newLink.getAttachedComponent(1).getPortsList().size();i++){
							  if (newLink.getAttachedComponent(1).getPortsList().get(i).getAttachement()==newLink.getAttachedComponent(0))
								  newLink.getAttachedComponent(1).getPortsList().get(i).setPortNumber(port2);
						  }
					}
					
				} 
				
				scanner.close();
				new JOptionPane();;
				JOptionPane.showMessageDialog(null, "Project succefully imported from "+xmlFilePath+" !"
						, "Export", JOptionPane.INFORMATION_MESSAGE);
							
			} catch (IOException e) {
				e.printStackTrace();
				new JOptionPane();;
				JOptionPane.showMessageDialog(null, "Error during the export from  "+xmlFilePath+" !"
						, "Export", JOptionPane.WARNING_MESSAGE);
			} 
			
		xmlFilePath=null;
	    }//*/
    }
    private String[] getNextLineDatas(Scanner scanner){
    	
    	String line=scanner.nextLine();
		StringTokenizer tokenizer = new StringTokenizer(line, "\t\n<>");	//Get the line
		int numberOfTokens = tokenizer.countTokens();					
		String[] datas = new String[numberOfTokens];			
		for (int i=0;i<numberOfTokens;i++){									//Get the datas
			datas[i]= tokenizer.nextToken();
		}
    	
    	return datas;
    	
    }
    
    static public Component componentNamed(String name){
		for (Component obj: droppedComponentList){
			if (obj.getComponentName().equals(name)) {System.out.println(name+" find");return obj;}
		}
		System.out.println(name+" not find");
    	return null;	  	
    }
    public void clearAllComponents(){
       	//*
    	if (droppedComponentList.size()>0) do{
    		currentIndex=droppedComponentList.size()-1;
    		System.out.println("Index: "+currentIndex);
    		deleteComponent();
       	}while (droppedComponentList.size()>0);
       	//*/
    	/*
    	for (Iterator<Component> iter = droppedComponentList.listIterator(); iter.hasNext(); ) {
    	    Component a = iter.next();
    	        deleteComponent();
    	    repaint();
    	}
    	//*/
    }
}
