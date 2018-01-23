
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;


public class Homework1 extends JPanel implements TreeSelectionListener {
    private JEditorPane htmlPane = new JEditorPane();
    private JTree tree;

    public Homework1(Node n) {
        super(new GridLayout(1,0));

        tree = new JTree(createNodes(n));

        ImageIcon leafIcon = createImageIcon("images/middle.gif");
        if (leafIcon != null) {
            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            renderer.setOpenIcon(leafIcon);
            renderer.setClosedIcon(leafIcon);
            tree.setCellRenderer(renderer);
        }

        tree.putClientProperty("JTree.lineStyle", "None");

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        //Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(tree);

        JScrollPane htmlView = new JScrollPane(htmlPane);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);

        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100);
        splitPane.setPreferredSize(new Dimension(500, 300));

        //Add the split pane to this panel.
        add(splitPane);
    }

	public static void main(String[] args) {
		String input = "";
		// Begin of arguments input sample
		if (args.length > 0) {
		    input = args[0];
	}else System.exit(1);
		// End of arguments input sample
		
		// TODO: Implement your project here
	Node n = null;
		for(int i=0; i<input.length(); i++){
			Node tmp = new Node();
			tmp.setValue(String.valueOf(input.charAt(i)));
			tmp.setLeft(n);
			n = infix(tmp);
	}
        String text = inorder(n);
        if (n.getLeft() != null) text = text.substring(1,text.length()-1) + "=" +calculator(n);
        System.out.println(text);


        Node finalN = n;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(finalN);
            }
        });
    }

	public static Node infix(Node n){
		if (!n.getValue().matches("[0-9]")) {
			n.setRight(n.getLeft());
			if (n.getLeft().getValue().matches("[0-9]")) {
				n.setLeft(n.getRight().getLeft());
				n.getRight().setLeft(null);
			} else {
				n.setRight(n.getLeft());
				n.setLeft(n.getRight().getLeft().getLeft());
				n.getRight().getLeft().setLeft(null);
			}
		}
		return n;
	}

	public static String inorder(Node n) {
		String left = "";
		String right = "";
		if (n.getLeft() != null) {
			left = "(" + inorder(n.getLeft());
		}
		if (n.getRight() != null) {
			right = inorder(n.getRight()) + ")";
		}

		return (left + n.getValue() + right);
	}

	public static int calculator(Node n) {

		if (n.getValue().matches("[0-9]")) return Integer.valueOf(n.getValue());

		int result = 0;
		int left = calculator(n.getLeft());
		int right = calculator(n.getRight());

		switch (n.getValue()){

			case "+":
				result = left + right;
				break;
			case "-":
				result = left - right;
				break;
			case "*":
				result = left * right;
				break;
			case "/":
				result = left / right;
				break;
		}

		return result;
	}
	@Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        Node n =(Node) node.getUserObject();
        String text = inorder(n);
        if (n.getLeft() != null) text = text.substring(1,text.length()-1) + "=" +calculator(n);
        htmlPane.setText(text);
    }

    private static void createAndShowGUI(Node n) {

        //Create and set up the window.
        JFrame frame = new JFrame("Binary Tree Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new Homework1(n));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public DefaultMutableTreeNode createNodes(Node n) {
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(n);

        if (n.getLeft() != null) {
            t.add(createNodes(n.getLeft()));
            t.add(createNodes(n.getRight()));
        }
        return t;
    }

    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Homework1.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
