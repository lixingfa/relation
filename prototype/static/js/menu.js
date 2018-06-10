var SystemMenu = [{
	title: '系统管理',
	icon: '&#xe63f;',
	isCurrent: true,
	menu: [{
		   title: '信息总览',
		icon: '&#xe611;',
		href: 'workbench.html',
		isCurrent: true,
		children: [
		/***权限，平台角色**/
		{
			title: '信息总览',
			isCurrent: true,
			href: 'workbench.html'
		}
		/***权限结束**/
		/***权限，采购商角色**/
		,{
			title: '信息总览',
			//isCurrent: true,
			href: 'workbenchcg.html'
		}
		/***权限结束**/
		/***权限，供应商角色**/
		,{
			title: '信息总览',
			//isCurrent: true,
			href: 'workbenchgy.html'
		}
		/***权限结束**/
		]
	   }
	   /***权限，平台角色**/
	   ,{
		title: '注册审核',
		icon: '&#xe620;',
		href: 'index.html',
		children: []
	},{
		title: '投诉处理',
		icon: '&#xe620;',
		children: [{
			title: '投诉处理',
			href: 'index.html'
		},{
			title: '状态恢复',
			href: 'process.html'
		}]
	},{
		title: '合同信息',
		icon: '&#xe620;',
		href: 'index.html',
		children: []
	},{
		title: '采购商管理',
		icon: '&#xe620;',
		href: 'index.html',
		children: []
	},{
		title: '供应商管理',
		icon: '&#xe620;',
		href: 'index.html',
		children: []
	}
	/***权限结束**/
	/***权限，采购商角色**/
	,{
		title: '招标管理',
		icon: '&#xe620;',
		children: [{
			title: '竞标合同',
			href: 'index.html'
		},{
			title: '验收合同',
			href: 'process.html'
		},{
			title: '合同草稿',
			href: 'providers.html'
		}]
	},{
		title: '投诉处理',
		icon: '&#xe625;',
		href: 'basic_info.html',
		children: []
	}
	/***权限结束**/
	/***权限，供应商角色**/
	,{
		title: '竞标管理',
		icon: '&#xe647;',
		children: [{
			title: '竞标合同',
			href: 'providers.html'
		},{
			title: '发货管理',
			href: 'providers1.html'
		}]
	},{
		title: '投诉处理',
		icon: '&#xe611;',
		href: 'providers.html',
		children: []
	}
	/***权限结束**/
	]
},{
	title: '角色权限',
	icon: '&#xe60d;',
	menu: [{
		title: '系统设置',
		icon: '&#xe611;',
		children: [{
			title: '权限设置',
			href: 'process.html'
		},{
			title: '角色设置',
			href: 'process.html'
		},{
			title: '职务设置',
			href: 'process.html'
		},{
			title: '采购组',
			href: 'process.html'
		}]
	},{
		title: '专家库',
		icon: '&#xe62f;',
		children: [{
			title: '专家管理',
			href: 'process.html'/**列表，增删改查**/
		},{
			title: '审核专家',
			href: 'process.html'/**用户可以自己申请成为专家，审核通过即有评审的权限**/
		}]
	}]
}
/*,{
	title: '主数据',
	icon: '&#xe61e;',
	menu: [{
		title: '数据信息',
		icon: '&#xe647;',
		isCurrent: true,
		children: [{
			title: '数据管理',
			href: 'process.html',
			isCurrent: true
		},{
			title: '企业荣誉',
			href: 'index.html'
		},{
			title: '组织架构',
			href: 'index.html'
		},{
			title: '自定义',
			href: 'index.html'
		}]
	},{
		title: '数据表',
		icon: '&#xe611;',
		href: 'basic_info.html',
		children: []
	}]
},{
	title: '供应商管理',
	icon: '&#xe620;',
	menu: [{
		title: '供应商列表',
		icon: '&#xe647;',
		children: [{
			title: '供应链条',
			href: 'providers.html',
			isCurrent: true
		},{
			title: '供应组织',
			href: 'index.html'
		},{
			title: '运输渠道',
			href: 'index.html'
		},{
			title: '自定义',
			href: 'index.html'
		}]
	},{
		title: '供应客户',
		icon: '&#xe611;',
		href: 'basic_info.html',
		children: []
	}]
},{
	title: '采购商开发',
	icon: '&#xe625;',
	menu: [{
		title: '采购商主页',
		icon: '&#xe647;',
		children: [{
			title: '采购管理',
			href: 'providers1.html',
			isCurrent: true
		},{
			title: '采购列表',
			href: 'index.html'
		}]
	},{
		title: '数据表',
		icon: '&#xe611;',
		href: 'basic_info.html',
		children: []
	}]
},{
	title: '采购寻源',
	icon: '&#xe64b;',
	menu: [{
		title: '寻源管理',
		icon: '&#xe647;',
		isCurrent: true,
		children: [{
			title: '自定义',
			href: 'basic_info.html',
			isCurrent: true
		},{
			title: '采购分析',
			href: 'index.html'
		}]
	},{
		title: '统计图',
		icon: '&#xe611;',
		href: 'basic_info.html',
		children: []
	}]
},{
	title: '合同管理',
	icon: '&#xe64c;',
	menu: [{
		title: '合同归档',
		icon: '&#xe647;',
		isCurrent: true,
		children: [{
			title: '合同发布',
			href: 'basic_info.html',
			isCurrent: true
		},{
			title: '合同制度管理',
			href: 'index.html'
		}]
	},{
		title: '合同信息',
		icon: '&#xe611;',
		href: 'basic_info.html',
		children: []
	}]
}*/];