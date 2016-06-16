
class TreeStar extends TreeIntNode {
	private TreeNode sub;
	
	public TreeStar(TreeNode sub) throws IllegalArgumentException {
		setSub(sub);
	}
	
	public TreeNode getSub() {
		return sub;
	}
	
	public TreeStar setSub(TreeNode sub) throws IllegalArgumentException {
		if(sub == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.sub = sub;
		
		return this;
	}
}
