package com.garlane.relation.common.model.tree;

public abstract class CallBack<T extends TreeModel<T>> {
	protected Node<T> node;
	
	/**
	 * 用前需先初始化
	 * 
	 * @param node
	 * @author zhouwx
	 * @date 2017年11月12日 下午9:27:56
	 */
	final public void init(Node<T> node){
		this.node = node;
	}
	
	/**
	 * 处理Tree部分
	 * 
	 * @param t
	 * @author zhouwx
	 * @date 2017年11月10日 下午4:49:10
	 */
	public abstract void execute(T t);
	
	/**
	 * 对当前Node判断，自定义条件，是否放弃遍历本次节点
	 * 
	 * @param v
	 * @return
	 * @author zhouwx
	 * @date 2017年11月10日 下午4:49:23
	 */
	public boolean abandon(){
		return false;
	}

}


/*
class Test{
	public static void main(String[] args) {
		Node<TreeModel> n= new Node<TreeModel>();
		TreeModel t = new TreeModel();
		CallBack<TreeModel> c = new CallBack<TreeModel>() {
			@Override
			public void execute(TreeModel t) {
			}
		};
	}
}
	*/