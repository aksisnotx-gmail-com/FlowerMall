/* 用户 */
const userInfo = {
	status: 1,
	data: {
		id: 1,
		mobile: 18888888888,
		nickname: 'Leo yo',
		portrait: '/static/user-bg.jpg'
	},
	msg: '提示'
}
const hotGoodsList = [{
		src: "/static/hot/1.jpg",
		title: "11支碎冰蓝玫瑰",
		price: 169,
	},
	{
		src: "/static/hot/2.jpg",
		title: "11支卡布奇洛玫瑰",
		price: 230,
	},
	{
		src: "/static/hot/3.jpg",
		title: "11支粉玫瑰花束",
		price: 159,
	},
	{
		src: "/static/hot/4.jpg",
		title: "19支红玫瑰",
		price: 278,
	},
	{
		src: "/static/hot/5.jpg",
		title: "33支红玫瑰",
		price: 230,
	},
	{
		src: "/static/hot/6.jpg",
		title: "11支白玫瑰",
		price: 159,
	},
	{
		src: "/static/hot/7.jpg",
		title: "11支香槟玫瑰",
		price: 230,
	},
	{
		src: "/static/hot/8.jpg",
		title: "33支戴安娜玫瑰花束",
		price: 369,
	}
]
/* 首页轮播图 */
const carouselList = [{
		src: "/static/temp/banner3.jpg",
		background: "rgb(203, 87, 60)",
	},
	{
		src: "/static/temp/banner2.jpg",
		background: "rgb(205, 215, 218)",
	},
	{
		src: "/static/temp/banner1.jpg",
		background: "rgb(183, 73, 69)",
	}
]
/* 需求轮播图 */
const businesscarouselList = [{
		src: "/static/temp/business/banner1.webp",
		background: "rgb(203, 202, 194)",
	},
	{
		src: "/static/temp/business/banner2.webp",
		background: "rgb(218, 215, 208)",
	},
	{
		src: "/static/temp/business/banner3.webp",
		background: "rgb(92, 183, 153)",
	},
	{
		src: "/static/temp/business/banner4.webp",
		background: "rgb(159, 183, 160)",
	},
	{
		src: "/static/temp/business/banner5.webp",
		background: "rgb(183, 73, 69)",
	}
]
/* 商品列表 */
const goodsList = [{
		image: "/static/user-bg.jpg",
		image2: "/static/user-bg.jpg",
		image3: "/static/user-bg.jpg",
		title: "古黛妃 短袖t恤女夏装2019新款韩版宽松",
		price: 179,
		sales: 61,
	},
	{
		image: "/static/user-bg.jpg",
		image2: "/static/user-bg.jpg",
		image3: "/static/user-bg.jpg",
		title: "潘歌针织连衣裙",
		price: 78,
		sales: 16,
	},
	{
		image: "/static/user-bg.jpg",
		image2: "/static/user-bg.jpg",
		image3: "/static/user-bg.jpg",
		title: "巧谷2019春夏季新品新款女装",
		price: 108.8,
		sales: 5,
	}, {
		image: "/static/user-bg.jpg",
		image2: "/static/user-bg.jpg",
		image3: "/static/user-bg.jpg",
		title: "私萱连衣裙",
		price: 265,
		sales: 88,
	}, {
		image: "/static/user-bg.jpg",
		image2: "/static/user-bg.jpg",
		image3: "/static/user-bg.jpg",
		title: "娇诗茹 ulzzang原宿风学生潮韩版春夏短",
		price: 422,
		sales: 137,
	}, {
		image: "/static/user-bg.jpg",
		image2: "/static/user-bg.jpg",
		image3: "/static/user-bg.jpg",
		title: "古黛妃 短袖t恤女夏装2019新款韩版宽松",
		price: 179,
		sales: 95,
	},
]

/* 购物车 */
const cartList = [{
		id: 1,
		image: '/static/user-bg.jpg',
		attr_val: '春装款 L',
		stock: 15,
		title: 'OVBE 长袖风衣',
		price: 278.00,
		number: 1
	},
	{
		id: 3,
		image: '/static/user-bg.jpg',
		attr_val: '激光导航 扫拖一体',
		stock: 3,
		title: '科沃斯 Ecovacs 扫地机器人',
		price: 1348.00,
		number: 5
	},
	{
		id: 4,
		image: '/static/user-bg.jpg',
		attr_val: 'XL',
		stock: 55,
		title: '朵绒菲小西装',
		price: 175.88,
		number: 1
	},
	{
		id: 5,
		image: '/static/user-bg.jpg',
		attr_val: '520 #粉红色',
		stock: 15,
		title: '迪奥（Dior）烈艳唇膏',
		price: 1089.00,
		number: 1
	},
	{
		id: 6,
		image: '/static/user-bg.jpg',
		attr_val: '樱花味润手霜 30ml',
		stock: 15,
		title: "欧舒丹（L'OCCITANE）乳木果",
		price: 128,
		number: 1
	},
	{
		id: 7,
		image: '/static/user-bg.jpg',
		attr_val: '特级 12个',
		stock: 7,
		title: '新疆阿克苏苹果 特级',
		price: 58.8,
		number: 10
	},
	{
		id: 8,
		image: '/static/user-bg.jpg',
		attr_val: '激光导航 扫拖一体',
		stock: 15,
		title: '科沃斯 Ecovacs 扫地机器人',
		price: 1348.00,
		number: 1
	},
	{
		id: 9,
		image: '/static/user-bg.jpg',
		attr_val: 'XL',
		stock: 55,
		title: '朵绒菲小西装',
		price: 175.88,
		number: 1
	},
	{
		id: 10,
		image: '/static/user-bg.jpg',
		attr_val: '520 #粉红色',
		stock: 15,
		title: '迪奥（Dior）烈艳唇膏',
		price: 1089.00,
		number: 1
	},
	{
		id: 11,
		image: '/static/user-bg.jpg',
		attr_val: '樱花味润手霜 30ml',
		stock: 15,
		title: "欧舒丹（L'OCCITANE）乳木果",
		price: 128,
		number: 1
	},
	{
		id: 12,
		image: '/static/user-bg.jpg',
		attr_val: '特级 12个',
		stock: 7,
		title: '新疆阿克苏苹果 特级',
		price: 58.8,
		number: 10
	},
	{
		id: 13,
		image: '/static/user-bg.jpg',
		attr_val: '春装款/m',
		stock: 15,
		title: '女装2019春秋新款',
		price: 420.00,
		number: 1
	}
];
//详情展示页面
const detailData = {
	title: '纯种金毛幼犬活体有血统证书',
	title2: '拆家小能手 你值得拥有',
	favorite: true,
	imgList: [{
			src: '/static/user-bg.jpg'
		},
		{
			src: '/static/user-bg.jpg'
		},
		{
			src: '/static/user-bg.jpg'
		},
		{
			src: '/static/user-bg.jpg'
		},
	],
	episodeList: [
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24
	],
	guessList: [{
			src: '/static/user-bg.jpg',
			title: '猫眼指甲油',
			title2: '独树一帜的免照灯猫眼指甲'
		},
		{
			src: '/static/user-bg.jpg',
			title: '创意屋',
			title2: '创意屋形上下双层高低床'
		},
		{
			src: '/static/user-bg.jpg',
			title: 'MissCandy 指甲油',
			title2: '十分适合喜欢素净的妹纸，尽显淡雅的气质'
		},
		{
			src: '/static/user-bg.jpg',
			title: 'RMK 2017星空海蓝唇釉',
			title2: '唇釉质地，上唇后很滋润。少女也会心动的蓝色，透明液体形状。'
		}
	],
	evaList: [{
			src: '/static/user-bg.jpg',
			nickname: 'Ranth Allngal',
			time: '09-20 12:54',
			zan: '54',
			content: '评论不要太苛刻，不管什么产品都会有瑕疵，客服也说了可以退货并且商家承担运费，我觉得至少态度就可以给五星。'
		},
		{
			src: '/static/user-bg.jpg',
			nickname: 'Ranth Allngal',
			time: '09-20 12:54',
			zan: '54',
			content: '楼上说的好有道理。'
		}
	]
}
const shareList = [{
		type: 1,
		icon: '/static/temp/share_wechat.png',
		text: '微信好友'
	},
	{
		type: 2,
		icon: '/static/temp/share_moment.png',
		text: '朋友圈'
	},
	{
		type: 3,
		icon: '/static/temp/share_qq.png',
		text: 'QQ好友'
	},
	{
		type: 4,
		icon: '/static/temp/share_qqzone.png',
		text: 'QQ空间'
	}
]
const lazyLoadList = [{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
	{
		src: '/static/user-bg.jpg'
	},
]

const orderList = [{
		time: '2019-04-06 11:37',
		state: 1,
		goodsList: [{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			}
		]
	},
	{
		time: '2019-04-06 11:37',
		state: 9,
		goodsList: [{
			title: '古黛妃 短袖t恤女 春夏装2019新款韩版宽松',
			price: 179.5,
			image: '/static/user-bg.jpg',
			number: 1,
			attr: '珊瑚粉 M'
		}]
	},
	{
		time: '2019-04-06 11:37',
		state: 1,
		goodsList: [{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			},
		]
	},
	{
		time: '2019-04-06 11:37',
		state: 1,
		goodsList: [{
			title: '回力女鞋高帮帆布鞋女学生韩版鞋子女2019潮鞋女鞋新款春季板鞋女',
			price: 69,
			image: '/static/user-bg.jpg',
			number: 1,
			attr: '白色-高帮 39'
		}]
	},
	{
		time: '2019-04-06 11:37',
		state: 1,
		goodsList: [{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			},
		]
	},
	{
		time: '2019-04-06 11:37',
		state: 1,
		goodsList: [{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			},
			{
				image: '/static/user-bg.jpg',
			},
		]
	}

]
const cateList = [{
		id: 1,
		name: '手机数码'
	},
	{
		id: 2,
		name: '礼品鲜花'
	},
	{
		id: 3,
		name: '男装女装'
	},
	{
		id: 4,
		name: '母婴用品'
	},
	{
		id: 5,
		pid: 1,
		name: '手机通讯'
	},
	{
		id: 6,
		pid: 1,
		name: '运营商'
	},
	{
		id: 8,
		pid: 5,
		name: '全面屏手机',
		picture: '/static/temp/cate2.jpg'
	},
	{
		id: 9,
		pid: 5,
		name: '游戏手机',
		picture: '/static/temp/cate3.jpg'
	},
	{
		id: 10,
		pid: 5,
		name: '老人机',
		picture: '/static/temp/cate1.jpg'
	},
	{
		id: 11,
		pid: 5,
		name: '拍照手机',
		picture: '/static/temp/cate4.jpg'
	},
	{
		id: 12,
		pid: 5,
		name: '女性手机',
		picture: '/static/temp/cate5.jpg'
	},
	{
		id: 14,
		pid: 6,
		name: '合约机',
		picture: '/static/temp/cate1.jpg'
	},
	{
		id: 15,
		pid: 6,
		name: '选好卡',
		picture: '/static/temp/cate4.jpg'
	},
	{
		id: 16,
		pid: 6,
		name: '办套餐',
		picture: '/static/temp/cate5.jpg'
	},
	{
		id: 17,
		pid: 2,
		name: '礼品',
	},
	{
		id: 18,
		pid: 2,
		name: '鲜花',
	},
	{
		id: 19,
		pid: 17,
		name: '公益摆件',
		picture: '/static/temp/cate7.jpg'
	},
	{
		id: 20,
		pid: 17,
		name: '创意礼品',
		picture: '/static/temp/cate8.jpg'
	},
	{
		id: 21,
		pid: 18,
		name: '鲜花',
		picture: '/static/temp/cate9.jpg'
	},
	{
		id: 22,
		pid: 18,
		name: '每周一花',
		picture: '/static/temp/cate10.jpg'
	},
	{
		id: 23,
		pid: 18,
		name: '卡通花束',
		picture: '/static/temp/cate11.jpg'
	},
	{
		id: 24,
		pid: 18,
		name: '永生花',
		picture: '/static/temp/cate12.jpg'
	},
	{
		id: 25,
		pid: 3,
		name: '男装'
	},
	{
		id: 26,
		pid: 3,
		name: '女装'
	},
	{
		id: 27,
		pid: 25,
		name: '男士T恤',
		picture: '/static/temp/cate13.jpg'
	},
	{
		id: 28,
		pid: 25,
		name: '男士外套',
		picture: '/static/temp/cate14.jpg'
	},
	{
		id: 29,
		pid: 26,
		name: '裙装',
		picture: '/static/temp/cate15.jpg'
	},
	{
		id: 30,
		pid: 26,
		name: 'T恤',
		picture: '/static/temp/cate16.jpg'
	},
	{
		id: 31,
		pid: 26,
		name: '上装',
		picture: '/static/temp/cate15.jpg'
	},
	{
		id: 32,
		pid: 26,
		name: '下装',
		picture: '/static/temp/cate16.jpg'
	},
	{
		id: 33,
		pid: 4,
		name: '奶粉',
	},
	{
		id: 34,
		pid: 4,
		name: '营养辅食',
	},
	{
		id: 35,
		pid: 4,
		name: '童装',
	},
	{
		id: 39,
		pid: 4,
		name: '喂养用品',
	},
	{
		id: 36,
		pid: 33,
		name: '有机奶粉',
		picture: '/static/temp/cate17.jpg'
	},
	{
		id: 37,
		pid: 34,
		name: '果泥/果汁',
		picture: '/static/temp/cate18.jpg'
	},
	{
		id: 39,
		pid: 34,
		name: '面条/粥',
		picture: '/static/temp/cate20.jpg'
	},
	{
		id: 42,
		pid: 35,
		name: '婴童衣橱',
		picture: '/static/temp/cate19.jpg'
	},
	{
		id: 43,
		pid: 39,
		name: '吸奶器',
		picture: '/static/temp/cate21.jpg'
	},
	{
		id: 44,
		pid: 39,
		name: '儿童餐具',
		picture: '/static/temp/cate22.jpg'
	},
	{
		id: 45,
		pid: 39,
		name: '牙胶安抚',
		picture: '/static/temp/cate23.jpg'
	},
	{
		id: 46,
		pid: 39,
		name: '围兜',
		picture: '/static/temp/cate24.jpg'
	},
]

export default {
	carouselList,
	businesscarouselList,
	cartList,
	detailData,
	lazyLoadList,
	userInfo,
	shareList,
	goodsList,
	orderList,
	cateList
}
