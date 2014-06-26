import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JPanel;


public class WindowPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public WindowPanel()
	{
		
	}
	public WindowPanel(int panX, int panY, int panDX, int panDY, Color panColor, LayoutManager panLayout)
	{
		this.setBounds(panX, panY, panDX, panDY);
		this.setBackground(panColor);
		this.setLayout(panLayout);
	}

	
}
