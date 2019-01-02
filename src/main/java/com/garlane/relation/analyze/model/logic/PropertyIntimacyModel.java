/**
 * 
 */
package com.garlane.relation.analyze.model.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixingfa
 * @date 2018年7月9日下午5:48:30
 * 
 */
public class PropertyIntimacyModel {

	/**属性列表*/
	private List<String> propertys = new ArrayList<String>();
	/**亲密度*/
	private int[][] intimacy = new int[1][1];
	/**存疑的属性*/
	private List<String> impeachs = new ArrayList<String>();
	
	/**双重检验的单例模式*/
	private static volatile PropertyIntimacyModel propertyIntimacyModel;
	/**私有化构建函数*/
    private PropertyIntimacyModel() {}
    /**双重检查的单例*/
    public static PropertyIntimacyModel getInstance() {
        if (propertyIntimacyModel == null) {
            synchronized (PropertyIntimacyModel.class) {
                if (propertyIntimacyModel == null) {
                	propertyIntimacyModel = new PropertyIntimacyModel();
                }
            }
        }
        return propertyIntimacyModel;
    }

	
	/**
	 * addImpeachs:(设定一组属性的亲密度)
	 * @author lixingfa
	 * @date 2018年7月9日下午6:25:53
	 * @param sub 属性
	 */
	public void addImpeachs(List<String> sub){
		//属性在列表中的索引
		int[] index = new int[sub.size()];//TODO 存疑的怎么办？
		for (int i = 0; i < sub.size(); i++) {
			if (!propertys.contains(sub.get(i))) {
				propertys.add(sub.get(i));
				//扩展intimacy
				int[][] inti = new int[intimacy.length + 1][intimacy.length + 1];
				for (int j = 0; j < intimacy.length; j++) {
					for (int k = 0; k < intimacy.length; k++) {
						inti[j][k] = intimacy[j][k];
					}
				}
				intimacy = inti;
			}
			index[i] = propertys.indexOf(sub.get(i));
		}
		//循环+1
		for (int i = 0; i < index.length; i++) {
			for (int j = 0; j < index.length; j++) {
				if (i != j) {
					intimacy[index[i]][index[j]]++;
				}
			}
		}
	}
	
	
	public List<String> getPropertys() {
		return propertys;
	}
	public void setPropertys(List<String> propertys) {
		this.propertys = propertys;
	}
	public int[][] getIntimacy() {
		return intimacy;
	}
	public void setIntimacy(int[][] intimacy) {
		this.intimacy = intimacy;
	}
}
