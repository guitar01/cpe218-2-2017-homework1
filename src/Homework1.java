
public class Homework1 {

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
	String infix = inorder(n);
		System.out.println(infix.substring(1,infix.length()-1) + "=" + calculator(n));
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
}
