import java.util.List;

abstract class TreeNode {
	private TreeNode parent;
	
	public abstract List<TreeNode> getChildren();
	
	public TreeNode getParent() {
		return parent;
	}
	
	public TreeNode setParent(TreeIntNode parent) {
		if(parent != null) {
			parent._removeChild(this);
		}
		parent._addChild(this);
		
		this.parent = parent;
		return this;
	}
	
	/// @TODO Direct access to package private parent attribute?
	/*package*/ void _setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	public TreeNode getRoot() {
		TreeNode parent = getParent();
		
		if(parent == null) {
			return this;
		}
		else {
			return parent.getRoot();
		}
	}
}
