/**
 * 
 */
package com.garlane.relation.analyze.model.easyui;
import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author lixingfa
 * @date 2018年7月3日下午2:09:49
 * 
 */
public class GridModel extends TreeModel implements Serializable{
	private static final long serialVersionUID = 1L;

	/**标识字段，往往是数据表的主键*/
	private String idField;
	/**定义树节点字段*/
	private String treeField;
	/**单选，如果可以多选，需要留心传给后台的值如何处理*/
	private boolean singleSelect = false;
	/**是否分页，影响后台方法*/
	private boolean pagination = false;
	/**初始化页码*/
	//private int pageNumber = 1;
	/**初始化页面大小*/
	//private int pageSize = 10;
	
	/**分页形式的数据，rows、total*/
	private JSONObject dataObject = null;
	
	/**treeGrid areaSeq,areaName*/
	public GridModel(String idField,String treeField){
		this.idField = idField;
		this.treeField = treeField;
	}
	
	/**dataGrid，并不强制要求*/
	public GridModel(String idField){
		this.idField = idField;
	}
	public GridModel(){
		
	}
	/*********************************/
	public String getIdField() {
		return idField;
	}
	public void setIdField(String idField) {
		this.idField = idField;
	}
	public boolean isSingleSelect() {
		return singleSelect;
	}
	public void setSingleSelect(boolean singleSelect) {
		this.singleSelect = singleSelect;
	}
	public boolean isPagination() {
		return pagination;
	}
	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}
	public String getTreeField() {
		return treeField;
	}

	public JSONObject getDataObject() {
		return dataObject;
	}

	public void setDataObject(JSONObject dataObject) {
		this.dataObject = dataObject;
	}
	/*data:{
    "total":5,
    "rows":[
        {
            "shopName":"郎奇空调专卖店",
            "address":"福华路399号中海大厦一楼",
            "areaName":"福田区",
            "shopSeq":"e9d41307ac7341f0b1f45f467d8bfc95",
            "areaSeq":"0addd5bed4734a87b346a7df47452a3b",
            "staffName":"黄福华",
            "telephone":"0755-122356446",
            "staffSeq":"0d11de1d4487456d808596279d82f759"
        },
        {
            "shopName":"郎奇空调南山营业部",
            "address":"前海路0366号山水情商厦B2-8765号",
            "areaSeq":"a291cbd23c6942b1bd9b843b52f75066",
            "shopSeq":"cc46e27f91384eb6931eb3af90c5e8ee",
            "areaName":"南山区",
            "staffName":"周敏",
            "telephone":"0755-83657876",
            "staffSeq":"0a0e2c6b0cca41baa8456571f948fa82"
        },
        {
            "shopName":"克孜勒苏柯尔克孜郎奇空调专卖店",
            "address":"喀什市亚瓦格路10号",
            "areaName":"克孜勒苏柯尔克孜自治州",
            "areaSeq":"f9fff18ab3224d1f9f330effbc43842e",
            "shopSeq":"a16aaf5766e64638921228526898090b",
            "staffName":"王莉",
            "telephone":"0908-5211233477",
            "staffSeq":"bd8edd2a927448ce8941c2bf467fba92"
        },
        {
            "shopName":"郎奇空调",
            "address":"天山区健康路219号",
            "areaName":"乌鲁木齐市",
            "areaSeq":"cfdac1b3a2734626b604cf4edf97b1ef",
            "shopSeq":"17fe4dfe8d884362bd01f24224407592",
            "staffName":"古塔热力",
            "telephone":"0991-567845677",
            "staffSeq":"e582143ca579473fa8ca994665e42ad2"
        },
		{
            "shopName":"郎奇空调天猫旗舰店",
            "address":"https://www.tmall.com/?spm=a211oj.11498583.a2226mz.1.5589499axB3TzY",
            "areaName":"",
            "areaSeq":"",
            "shopSeq":"17fe4dfe8d884362bd01f24224407222",
            "staffName":"张维新",
            "telephone":"010-2231423445",
            "staffSeq":"e582143ca579473fa8ca994665e42222"
        }
    ]
},*/
	/*data:[{
"areaSeq":"8250b441b7c347d68049b8aa1a10f6a1",
"areaName": "广东省",
"state":"closed",
"parentAreaSeq":null,
"children": [{
	"areaName": "广州市",
	"areaSeq": "02e2198e33244c26a11c954710fc64b4",
	"jcAreaSeq":"440101000000",
	"parentAreaSeq":"8250b441b7c347d68049b8aa1a10f6a1"
}, {
	"areaName": "深圳市",
	"areaSeq": "f6932014926b4dc4a70537cf40ea6f61",
	"jcAreaSeq":"440301000000",
	"parentAreaSeq":"8250b441b7c347d68049b8aa1a10f6a1",
	"state":"closed",
	"children": [{
		"areaName": "福田区",
		"jcAreaSeq":"440304000000",
		"areaSeq": "0addd5bed4734a87b346a7df47452a3b",
		"parentAreaSeq":"f6932014926b4dc4a70537cf40ea6f61"
	}, {
		"areaSeq": "a291cbd23c6942b1bd9b843b52f75066",
		"jcAreaSeq":"440305000000",
		"areaName": "南山区",
		"parentAreaSeq":"f6932014926b4dc4a70537cf40ea6f61"
	}]
}]
}, {
"areaName": "新疆维吾尔族自治区",
"areaSeq": "5973477abe1f4659b85a164b8439e352",
"state":"closed",
"children": [{
	"areaName": "克孜勒苏柯尔克孜自治州",
	"areaSeq": "f9fff18ab3224d1f9f330effbc43842e",
	"jcAreaSeq":"653000000000",
	"parentAreaSeq":"f9fff18ab3224d1f9f330effbc43842e"
}, {
	"areaName": "乌鲁木齐市",
  	"areaSeq":"cfdac1b3a2734626b604cf4edf97b1ef",
	"jcAreaSeq":"650100000000",
	"parentAreaSeq":"f9fff18ab3224d1f9f330effbc43842e"
}]
}]*/
}
