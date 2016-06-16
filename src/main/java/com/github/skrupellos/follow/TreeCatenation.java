class TreeCatenation extends TreeIntNode {
	private TreeNode subA;
	private TreeNode subB;
	
	public TreeCatenation(TreeNode subA, TreeNode subB) throws IllegalArgumentException {
		setSubA(subA);
		setSubB(subB);
	}
	
	public TreeNode getSubA() {
		return subA;
	}
	
	public TreeNode getSubB() {
		return subB;
	}
	
	public TreeCatenation setSubA(TreeNode subA) throws IllegalArgumentException {
		if(subA == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.subA = subA;
		
		return this;
	}
	
	public TreeCatenation setSubB(TreeNode subB) throws IllegalArgumentException {
		if(subB == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.subB = subB;
		
		return this;
	}
}
