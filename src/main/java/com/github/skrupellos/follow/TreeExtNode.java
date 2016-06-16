import java.util.Collections;
import java.util.List;

abstract class TreeExtNode extends TreeNode {
	public List<TreeNode> getChildren() {
		return Collections.<TreeNode>emptyList();
	}
}
