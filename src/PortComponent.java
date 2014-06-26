
public class PortComponent {

	private boolean used;
	private int portNumber;
	//private int portIndex;
	private Component attachement;
	private DrawedLinkComponent link;
	
	public PortComponent(int portNumber){
		this.used=false;
		this.portNumber=0;
		this.attachement=null;
		this.link=null;
		this.portNumber=portNumber;
	}
	public void setUsed(boolean utilization){
		this.used=utilization;
	}
	public boolean isUsed(){
		return this.used;
	}
	public void setAttachement(Component p){
		this.attachement=p;
	}
	public Component getAttachement(){
		return this.attachement;
	}
	public DrawedLinkComponent getLink(){
		return this.link;
	}
	public void setLink(DrawedLinkComponent l){
		this.link=l;
	}
	public int getPortNumber() {
		return this.portNumber;
	}
	public void setPortNumber(int portNb){
		this.portNumber=portNb;
	}
}
