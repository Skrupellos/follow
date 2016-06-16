class TreeUnion extends TreeIntNode {
	private TreeNode subA;
	private TreeNode subB;
	
	public TreeUnion(TreeNode subA, TreeNode subB) throws IllegalArgumentException {
		setSubA(subA);
		setSubB(subB);
	}
	
	public TreeNode getSubA() {
		return subA;
	}
	
	public TreeNode getSubB() {
		return subB;
	}
	
	public TreeUnion setSubA(TreeNode subA) throws IllegalArgumentException {
		if(subA == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.subA = subA;
		
		return this;
	}
	
	public TreeUnion setSubB(TreeNode subB) throws IllegalArgumentException {
		if(subB == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.subB = subB;
		
		return this;
	}
}
