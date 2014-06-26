import java.awt.Point;
import java.util.EventListener;

   public interface MenuComponentListener extends EventListener{
	    void drop(Component d,Point dropPosition);
	    void changeColor(Boolean canDrop);
	    void printInformations(Component p);
	    void changeIndication(String newIndication,int priority);
	    void actualize();
	    void unselectAllNonDraggableComponent();
	    void createLinkComponent();
	    void exportPY();
	    void exportXML();
	    void importXML();
	    void deleteComponent();
	    void createDroppedComponent(DraggableComponent newComponent);
	    void traceLinkComponent(Component ca,Component cb);
	}
   
