<template>
	<view class="container" style="width: 70%;margin-left: 15%;">
		<view style="background-color: #f2d7d3;display: flex;height: 50px;flex-direction:row-reverse">
			<view style="display: flex;justify-content: center;align-items: center;padding: 20px;">
				<text style="padding: 5px;font-size: 14px;" @click="login" v-if="hasLogin == '0'">登录</text>
				<text style="padding: 5px;font-size: 14px;" @click="regist" v-if="hasLogin == '0'">注册</text>
				<text style="padding: 5px;font-size: 14px;" v-if="hasLogin == '1'">欢迎： {{user.nickName}}</text>
				<text style="padding: 10px;font-size: 14px;">|</text>
				
				<view style="display: flex;align-items: center;" @click="openGoodCar">
					<image src="/static/temp/goodCars.png" style="width: 25px; height: 25px;margin: 5px;" mode="aspectFill" ></image>
					<text style="font-size: 14px;">我的购物车</text>
				</view>
				
				<text style="padding-left: 10px;font-size: 14px;" v-if="hasLogin == '1'" @click="logout">退出</text>
			</view>
		</view>
		<view style="background-color: #f2dfdc;display: flex;flex-direction:row;flex-wrap:wrap;align-content:stretch;justify-content:space-between;align-items:center;">
			<view style="display: flex;align-items: center;">
				<image src="/static/temp/logo.png" style="width: 150px; height: 80px;"></image>
			</view>
			<view style="height: 30px;width: 40%;border-radius:50px">
				<uni-easyinput suffixIcon="search" style="border-radius:50px" @iconClick="queryProduct"></uni-easyinput>
			</view>
		</view>
		<view class="scroll-menu-view">
			<scroll-view scroll-x="true">
				<view class="scroll-menu-topic-view" v-for="item in tabs" :key="(item.id)" @click="swichMenu(item.id)">
					<view>
						<text :class="type==item.id ? 'scroll-menu-topic-text' : 'scroll-menu-topic-text1'">{{item.name}}</text>
					</view>
				</view>
			</scroll-view>
		</view>
		<view style="background-image: url('/static/temp/userBackground.jpg');background-size: cover;margin-top: 20px;" v-if="showType == '0'">
			<view style="padding: 20px;width: 40%;margin-left: 60%;">
				<view class="c-list user-style">
					<view class="c-row" style="justify-content: center;">
						<text>用户注册</text>
					</view>
					<view class="c-row">
						<text class="tit">姓名</text>
						<uni-easyinput placeholder="请输入您的昵称" placeholderStyle="color:#f7c0ba" v-model="user.nickname"></uni-easyinput>
					</view>
					<view class="c-row">
						<text class="tit">账号</text>
						<uni-easyinput placeholder="请输入您的登录账号" placeholderStyle="color:#f7c0ba" v-model="user.username"></uni-easyinput>
					</view>
					<view class="c-row">
						<text class="tit">密码</text>
						<uni-easyinput placeholder="请输入您的密码" placeholderStyle="color:#f7c0ba" v-model="user.password"></uni-easyinput>
					</view>
					<view class="c-row">
						<text class="tit">确认密码</text>
						<uni-easyinput placeholder="请输入您的密码" placeholderStyle="color:#f7c0ba" v-model="user.password1"></uni-easyinput>
					</view>
					<view class="c-row">
						<button type="primary" class=" action-btn no-border" @click="registUser">注册</button>
						<button type="primary" class=" action-btn no-border" @click="cancel">取消</button>
					</view>
				</view>
			</view>
		</view>
		<view style="background-image: url('/static/temp/userBackground.jpg');background-size: cover;margin-top: 20px;" v-else-if="showType == '1'">
			<view style="padding: 20px;width: 40%;margin-left: 60%;">
				<view class="c-list user-style">
					<view class="c-row" style="justify-content: center;">
						<text>用户登录</text>
					</view>
					<view class="c-row">
						<text class="tit">账号</text>
						<uni-easyinput placeholder="请输入您的登录账号" placeholderStyle="color:#f7c0ba" v-model="user.username"></uni-easyinput>
					</view>
					<view class="c-row">
						<text class="tit">密码</text>
						<uni-easyinput placeholder="请输入您的密码" placeholderStyle="color:#f7c0ba" v-model="user.password"></uni-easyinput>
					</view>
					<view class="c-row">
						<button type="primary" class=" action-btn no-border" @click="userLogin">登录</button>
						<button type="primary" class=" action-btn no-border" @click="cancel">取消</button>
					</view>
				</view>
			</view>
		</view>
		<view style="background-image: url('/static/temp/userBackground.jpg');background-size: cover;margin-top: 20px;" v-else-if="showType == '2'">
			<view class="seckill-section">
				<view class="s-header">
					<text class="tip">购物车</text>
				</view>
				<uni-table>
					<uni-tr>
						<uni-th width="150" align="center"></uni-th>
						<uni-th width="150" align="center">商品信息</uni-th>
						<uni-th width="150" align="center">单价</uni-th>
						<uni-th width="150" align="center">数量</uni-th>
						<uni-th width="150" align="center">小计</uni-th>
						<uni-th width="150" align="center"></uni-th>
					</uni-tr>
					<uni-tr v-for="(item, index) in goodList" :key="index">
						<uni-td align="center"><image :src="item.tagUrl" mode="aspectFit" style="width: 150px;height: 150px;"></image></uni-td>
						<uni-td align="center">{{item.name}}</uni-td>
						<uni-td align="center">{{item.price}}</uni-td>
						<uni-td align="center">
							<uni-number-box v-model="item.number" ></uni-number-box>
						</uni-td>
						<uni-td align="center">{{item.price * item.number}}</uni-td>
						<uni-td align="center">
							<text @click="removeGood(item)" style="text-decoration: underline;">删除</text>
						</uni-td>
					</uni-tr>
				</uni-table>
				<view class="s-header">
					<button type="primary" style="background:darkgrey;width: 20%;" @click="cancel">返回</button>
					<button type="primary" style="background: hotpink;width: 20%" @click="goToGoodCar">结算</button>
				</view>
			</view>
		</view>
		<view style="background-image: url('/static/temp/userBackground.jpg');background-size: cover;margin-top: 20px;" v-else-if="showType == '3'">
			<view style="padding: 20px;width: 40%;margin-left: 60%;">
				<view class="c-list user-style">
					<view class="c-row" style="justify-content: center;">
						<text>结算页面</text>
					</view>
					<view class="c-row">
						<text>总价：￥{{totalPrice}}</text>
					</view>
						<image src="../../static/temp/erweima.png"></image>
					<view class="c-row">
						<button type="primary" class=" action-btn no-border" @click="cancel">返回</button>
					</view>
				</view>
			</view>
		</view>
		<view v-else>
			<view v-if="type == '0'">
				<!-- 头部轮播 -->
				<view class="carousel-section">
					<!-- 标题栏和状态栏占位符 -->
					<view class="titleNview-placing"></view>
					<!-- 背景色区域 -->
					<view class="titleNview-background" :style="{backgroundColor:titleNViewBackground}"></view>
					<swiper class="carousel" circular @change="swiperChange">
						<swiper-item v-for="(item, index) in carouselList" :key="index" class="carousel-item" @click="navToDetailPage({title: '轮播广告'})">
							<image :src="item.src" />
						</swiper-item>
					</swiper>
					<!-- 自定义swiper指示器 -->
					<view class="swiper-dots">
						<text class="num">{{swiperCurrent+1}}</text>
						<text class="sign">/</text>
						<text class="num">{{swiperLength}}</text>
					</view>
				</view>
				<view style="display: flex;padding-top: 100px;background: #f7ebeb;">
					<view style="width:60%;padding-left: 10px;direction:rtl">
						<image src="/static/temp/1.jpg" mode="aspectFit" style="width: 200px;height: 204px;"></image>
					</view>
					<view style="display: inline-block;margin-left: -10px;direction:ltr;width: 50%;margin-left: 5%;">
						<image src="/static/temp/2.jpg" mode="aspectFit" style="width: 150px;height: 150px;"></image>
						<view>
							<text style="margin-top:-30px;margin-left: 20px;">立即购买</text>
							<image src="/static/temp/right.png" mode="aspectFit" style="width: 10px;height: 10px;"></image>
						</view>
					</view>
				</view>
				
				<view class="seckill-section">
					<view class="s-header">
						<text class="tip">热卖单品推荐</text>
					</view>
					
					<view class="scoll-wrapper">
						<view 
							v-for="(item, index) in hotProductList" :key="index"
							class="floor-item"
							@click="navToDetailPage(item.id)"
						>
							<image :src="item.tagUrl" mode="aspectFit" style="width: 150px;height: 150px;"></image>
							<text class="title clamp">{{item.name}}</text>
							<text class="price">￥{{item.price}}</text>
						</view>
					</view>
				</view>
				
				<view class="seckill-section">
					<view class="s-header">
						<text class="tip">网红款推荐</text>
					</view>
					<view class="scoll-wrapper">
						<view 
							v-for="(item, index) in netHotProductList" :key="index"
							class="floor-item"
							@click="navToDetailPage(item.id)"
						>
							<image :src="item.tagUrl" mode="aspectFit" style="width: 150px;height: 150px;"></image>
							<text class="title clamp">{{item.name}}</text>
							<text class="price">￥{{item.price}}</text>
						</view>
					</view>
				</view>
				
				<view class="seckill-section">
					<view class="s-header">
						<text class="tip">特惠鲜花推荐</text>
					</view>
					<view class="scoll-wrapper">
						<view 
							v-for="(item, index) in specialProductList" :key="index"
							class="floor-item"
							@click="navToDetailPage(item.id)"
						>
							<image :src="item.tagUrl" mode="aspectFit" style="width: 150px;height: 150px;"></image>
							<text class="title clamp">{{item.name}}</text>
							<text class="price">￥{{item.price}}</text>
						</view>
					</view>
				</view>
				
				<view class="seckill-section">
					<view class="s-header">
						<text class="tip">生活鲜花推荐</text>
					</view>
					<view class="scoll-wrapper">
						<view 
							v-for="(item, index) in lifeProductList" :key="index"
							class="floor-item"
							@click="navToDetailPage(item.id)"
						>
							<image :src="item.tagUrl" mode="aspectFit" style="width: 150px;height: 150px;"></image>
							<text class="title clamp">{{item.name}}</text>
							<text class="price">￥{{item.price}}</text>
						</view>
					</view>
				</view>
				
				<view class="seckill-section">
					<view class="s-header">
						<text class="tip">新品推荐</text>
					</view>
					<scroll-view class="floor-list" scroll-x="true">
						<view class="scoll-wrapper1">
							<view 
								v-for="(item, index) in newProductList" :key="index"
								class="floor-item"
								@click="navToDetailPage(item.id)"
							>
								<image :src="item.tagUrl" mode="aspectFit" style="width: 150px;height: 150px;"></image>
								<text class="title clamp">{{item.name}}</text>
								<text class="price">￥{{item.price}}</text>
							</view>
						</view>
					</scroll-view>
				</view>
			</view>
			<view v-else>
				<view class="c-list" style="margin: 5%;background: white;" v-if="type == '1'">
					<view class="c-row">
						<text class="tit">已选条件</text>
						<view style="display: flex;" class="con">
							<text v-if="selectUseText" class="select-type">{{selectUseText}}</text>
							<text v-if="selectMaterialText" class="select-type">{{selectMaterialText}}</text>
							<text v-if="selectUseToText" class="select-type">{{selectUseToText}}</text>
							<text v-if="selectPriceText" class="select-type">{{selectPriceText}}</text>
						</view>
					</view>
					<view class="c-row" style="display: flex;">
						<text class="tit">用途</text>
						<view class="con">
							<uni-data-checkbox selectedColor="#f7c0ba" mode="tag" v-model="selectUseType" 
							:localdata="useType.map(item => ({text:item.dictLabel,value:item.dictValue}))" 
							@change="changUseType"></uni-data-checkbox>
						</view>
					</view>
					<view class="c-row" style="display: flex;">
						<text class="tit">花材</text>
						<view class="con">
							<uni-data-checkbox selectedColor="#f7c0ba" mode="tag" v-model="selectMaterialType" 
							:localdata="materialList.map(item => ({text:item.dictLabel,value:item.dictValue}))" 
							@change="changMaterialType"></uni-data-checkbox>
						</view>
					</view>
					<view class="c-row" style="display: flex;">
						<text class="tit">对象</text>
						<view class="con">
							<uni-data-checkbox selectedColor="#f7c0ba" mode="tag" v-model="selectUseToType" 
							:localdata="useToType.map(item => ({text:item.dictLabel,value:item.dictValue}))" 
							@change="changUseToType"></uni-data-checkbox>
						</view>
					</view>
					<view class="c-row" style="display: flex;">
						<text class="tit">价格</text>
						<view class="con">
							<uni-data-checkbox selectedColor="#f7c0ba" mode="tag" v-model="selectPriceType" 
							:localdata="priceType.map(item => ({text:item.dictLabel,value:item.dictValue}))" 
							@change="changPriceType"></uni-data-checkbox>
						</view>
					</view>
				</view>
				<view style="margin: 5%;">
					<view class="seckill-section">
						<view class="scoll-wrapper">
							<view 
								v-for="(item, index) in productList" :key="index"
								class="floor-item"
								@click="navToDetailPage(item.id)"
							>
								<image :src="item.tagUrl" mode="aspectFit" style="width: 150px;height: 150px;"></image>
								<text class="title clamp">{{item.name}}</text>
								<text class="price">￥{{item.price}}</text>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		
		<view class="c-list">
			<view class="c-row" style="display: flex;background: #f7c0ba;">
				<view class="buttom-item">
					<text class="buttom-title">高档花材</text>
					<text class="buttom-desc">100%品质保证</text>
				</view>
				<view class="buttom-item">
					<text class="buttom-title">同城配送</text>
					<text class="buttom-desc">1-3小时送达</text>
				</view>
				<view class="buttom-item">
					<text class="buttom-title">特色服务</text>
					<text class="buttom-desc">线下diy手工预约</text>
				</view>
				<view class="buttom-item">
					<text class="buttom-title">原创花艺</text>
					<text class="buttom-desc">资深花艺师设计</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import uniDataCheckbox from '@/components/uni-data-checkbox/components/uni-data-checkbox/uni-data-checkbox.vue'
	import {registUser} from "@/api/system/user.js"
	import {login,getInfo} from "@/api/login.js"
	
	import {getProductInfoList,getProductList,getProductType} from "@/api/index/index.js"
	
	import config from '@/config'
	const goodListKey = config.goodListKey
	
	export default {
		components: {
			uniDataCheckbox
		},
		data() {
			
			return {
				user:{
					username:"",
					nickname:"",
					email:"",
					phonenumber:"",
					sex:"",
					password:"",
					password1:""
				},
				titleNViewBackground: '',
				swiperCurrent: 0,
				swiperLength: 0,
				carouselList:[],
				hotProductList:[],
				netHotProductList:[],
				specialProductList:[],
				lifeProductList:[],
				foreverProductList:[],
				newProductList:[],
				productList:[],
				tabs: [{
						id: 0,
						name: '首页'
					},{
						id: 1,
						name: '鲜花分类'
					},
					{
						id: 2,
						name: '永生花'
					},
					{
						id: 3,
						name: '生活鲜花'
					},
					{
						id: 4,
						name: '包花束教程'
					},
					{
						id: 5,
						name: '线上预约diy'
					},
					{
						id: 6,
						name: '留言板'
					}
				],
				type:0,
				showType:"4",
				totalPrice:0,
				hasLogin: '0',
				currentTab: 0,
				useType:[{
					text: '全部',
					value: ""
				}, {
					text: '爱情鲜花',
					value: "001"
				}, {
					text: '生日鲜花',
					value: "002"
				}, {
					text: '问候长辈',
					value: "003"
				}, {
					text: '周年纪念',
					value: "004"
				}, {
					text: '求婚鲜花',
					value: "005"
				}, {
					text: '友情鲜花',
					value: "006"
				}, {
					text: '道歉鲜花',
					value: "007"
				}, {
					text: '祝福鲜花',
					value: "008"
				}, {
					text: '探病鲜花',
					value: "009"
				}, {
					text: '婚庆鲜花',
					value: "010"
				}, {
					text: '哀思鲜花',
					value: "011"
				}, {
					text: '感谢恩师',
					value: "012"
				}
				],
				materialList:[{
					text: '全部',
					value: ''
				},{
					text: '玫瑰',
					value: '001'
				},{
					text: '红玫瑰',
					value: '002'
				},{
					text: '粉玫瑰',
					value: '003'
				},{
					text: '香槟玫瑰',
					value: '004'
				},{
					text: '白玫瑰',
					value: '005'
				},{
					text: '蓝玫瑰',
					value: '006'
				}],
				useToType:[{
					text: '全部',
					value: '0'
				},{
					text: '恋人',
					value: '1'
				},{
					text: '朋友',
					value: '2'
				},{
					text: '同事',
					value: '3'
				},{
					text: '父母',
					value: '4'
				},{
					text: '长辈',
					value: '5'
				},{
					text: '老师',
					value: '6'
				},{
					text: '领导',
					value: '7'
				},{
					text: '客户',
					value: '8'
				}],
				priceType:[{
					text: '0-99',
					value: '0'
				},{
					text: '100-199',
					value: '1'
				},{
					text: '200-299',
					value: '2'
				},{
					text: '300-399',
					value: '3'
				},{
					text: '400-499',
					value: '4'
				},{
					text: '500-599',
					value: '5'
				},{
					text: '600以上',
					value: '6'
				}],
				selectUseType:"",
				selectUseText:'',
				selectMaterialType:"",
				selectMaterialText:'',
				selectUseToType:"",
				selectUseToText:'',
				selectPriceType:"",
				selectPriceText:'',
				goodList:[],
				goodListTmp:[]
			};
		},

		onLoad() {
			this.loadData();
		},
		methods: {
			goToGoodCar(){
				this.showType = "3";
				this.goodListTmp = this.goodList;
				for(let item of this.goodListTmp){
					this.totalPrice = item.price * item.number + this.totalPrice;
				}
			},
			removeGood(item){
				let that = this;
				uni.showModal({
					title: '',
					content: '确定将此商品从购物车中移除？',
					success: function (res) {
						if (res.confirm) {
						that.goodList.forEach((good, index) => {
							if (item.id === good.id) {
								that.goodList.splice(index, 1);
							}
						});
						uni.setStorageSync(goodListKey,that.goodList);
						} else if (res.cancel) {
						}
					}
				});
			},
			openGoodCar(){
				this.goodList =  uni.getStorageSync(goodListKey);
				this.showType = '2'
				console.log(this.goodList)
			},
			logout(){
				let that = this;
				uni.showModal({
					title: '提示',
					content: '确定退出？',
					success: function (res) {
						if (res.confirm) {
							that.user = {username:"",
								nickname:"",
								email:"",
								phonenumber:"",
								sex:"",
								password:"",
								password1:""};
							that.showType = '2';
							that.hasLogin = '0';
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
			},
			cancel(){
				this.showType = '4';
				this.user = {username:"",
					nickname:"",
					email:"",
					phonenumber:"",
					sex:"",
					password:"",
					password1:""};
					console.log(this.user)
			},
			regist(){
				this.showType = '0'
			},
			login(){
				this.showType = '1'
			},
			registUser(){
				if(this.user.password != this.user.password1){
					this.$modal.msgError("密码不相同")
					return;
				}
				console.log(this.user)
				registUser(this.user).then(res => {
					this.$modal.msgSuccess("注册成功")
					this.showType = '2'
					this.userLogin();
				})
			},
			userLogin(){
				console.log(this.user)
				login(this.user.username,this.user.password,'','').then(res => {
					console.log(res)
					this.showType = '2'
					this.hasLogin = '1';
					this.user = res.user;
					console.log("this.user",this.user)
				})
			},
			changUseType(e){
				this.selectUseText = '用途：' + e.detail.data.text;
			},
			changMaterialType(e){
				this.selectMaterialText = '花材：' +e.detail.data.text;
			},
			changUseToType(e){
				this.selectUseToText = '对象：'+e.detail.data.text;
			},
			changPriceType(e){
				this.selectPriceText = '价格：'+e.detail.data.text;
			},
			swichMenu(id) {
				this.type = id;
				// this.loadData();
				this.selectMaterialType = "";
				this.selectUseType = "";
				this.selectUseToType = "";
				this.selectPriceType = "";
				let data = {"materialType":this.selectMaterialType,"useType":this.selectUseType,"useToType":this.selectUseToType,"priceType":this.selectPriceType}
				if(id == '1'){
					getProductType("sys_fanhua_use_type").then(response => {
						this.useType = response.data;
					});
					getProductType("sys_fanhua_material").then(response => {
						this.materialList = response.data;
					});
					getProductType("sys_fanhua_use_to").then(response => {
						this.useToType = response.data;
					});
					getProductType("sys_fanhua_price").then(response => {
						this.priceType = response.data;
					});
				}else if(id == '2'){
					let data = {"type":"005"}
					getProductInfoList(data).then(response => {
						this.productList = response.data;
					});
				}else if(id == '3'){
					let data = {"type":"004"}
					getProductInfoList(data).then(response => {
						this.productList = response.data;
					});
				}else{
					getProductInfoList(data).then(response => {
						this.productList = response.data;
					});
				}
			},
			queryProduct(){
				let data = {"materialType":this.selectMaterialType,"useType":this.selectUseType,"useToType":this.selectUseToType,"priceType":this.selectPriceType}
				getProductInfoList(data).then(response => {
					this.productList = response.data;
				});
			},
			/**
			 * 请求静态数据只是为了代码不那么乱
			 * 分次请求未作整合
			 */
			async loadData() {
				let data = {};
				getProductList(data).then(response => {
					this.hotProductList = response.hotProduct;
					this.netHotProductList = response.netHotProduct;
					this.specialProductList = response.specialProduct;
					this.lifeProductList = response.lifeProduct;
					this.foreverProductList = response.foreverProduct;
					this.newProductList = response.newProduct;
				});
				
				let carouselList = await this.$api.json('carouselList');
				this.titleNViewBackground = carouselList[0].background;
				this.swiperLength = carouselList.length;
				this.carouselList = carouselList;
				
			// 	let goodsList = await this.$api.json('goodsList');
			// 	this.goodsList = goodsList || [];
			// 	this.hotGoodsList = [];
			// this.netHotGoodsList = this.hotGoodsList;
		},
			//轮播图切换修改背景色
			swiperChange(e) {
				const index = e.detail.current;
				this.swiperCurrent = index;
				this.titleNViewBackground = this.carouselList[index].background;
			},
			//详情页
			navToDetailPage(id) {
				//测试数据没有写id，用title代替
				uni.navigateTo({
					url: `/pages/product/product?id=${id}`
				})
			},
		},
		// #ifndef MP
		// 标题栏input搜索框点击
		onNavigationBarSearchInputClicked: async function(e) {
			this.$api.msg('点击了搜索框');
		},
		//点击导航栏 buttons 时触发
		onNavigationBarButtonTap(e) {
			const index = e.index;
			if (index === 0) {
				this.$api.msg('点击了扫描');
			} else if (index === 1) {
				// #ifdef APP-PLUS
				const pages = getCurrentPages();
				const page = pages[pages.length - 1];
				const currentWebview = page.$getAppWebview();
				currentWebview.hideTitleNViewButtonRedDot({
					index
				});
				// #endif
				uni.navigateTo({
					url: '/pages/notice/notice'
				})
			}
		}
		// #endif
	}
</script>

<style lang="scss">
	page {
		background: #f2dfdc;
	}
	.m-t{
		margin-top: 16upx;
	}
	/* 头部 轮播图 */
	.carousel-section {
		position: relative;
		// padding-top: 10px;

		.titleNview-placing {
			height: var(--status-bar-height);
			// padding-top: 44px;
			box-sizing: content-box;
		}

		.titleNview-background {
			position: absolute;
			// top: 50px;
			left: 0;
			width: 100%;
			// height: 230px;
			// transition: .4s;
		}
	}
	.carousel {
		width: 100%;
		height: 230px;

		.carousel-item {
			width: 100%;
			height: 100%;
			// padding: 0 28upx;
			overflow: hidden;
		}

		image {
			width: 100%;
			height: 100%;
			border-radius: 10upx;
		}
	}
	.swiper-dots {
		display: flex;
		position: absolute;
		left: 60upx;
		bottom: 15upx;
		width: 72upx;
		height: 36upx;
		background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAABkCAYAAADDhn8LAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTMyIDc5LjE1OTI4NCwgMjAxNi8wNC8xOS0xMzoxMzo0MCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OTk4MzlBNjE0NjU1MTFFOUExNjRFQ0I3RTQ0NEExQjMiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OTk4MzlBNjA0NjU1MTFFOUExNjRFQ0I3RTQ0NEExQjMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKFdpbmRvd3MpIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6Q0E3RUNERkE0NjExMTFFOTg5NzI4MTM2Rjg0OUQwOEUiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6Q0E3RUNERkI0NjExMTFFOTg5NzI4MTM2Rjg0OUQwOEUiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4Gh5BPAAACTUlEQVR42uzcQW7jQAwFUdN306l1uWwNww5kqdsmm6/2MwtVCp8CosQtP9vg/2+/gY+DRAMBgqnjIp2PaCxCLLldpPARRIiFj1yBbMV+cHZh9PURRLQNhY8kgWyL/WDtwujjI8hoE8rKLqb5CDJaRMJHokC6yKgSCR9JAukmokIknCQJpLOIrJFwMsBJELFcKHwM9BFkLBMKFxNcBCHlQ+FhoocgpVwwnv0Xn30QBJGMC0QcaBVJiAMiec/dcwKuL4j1QMsVCXFAJE4s4NQA3K/8Y6DzO4g40P7UcmIBJxbEesCKWBDg8wWxHrAiFgT4fEGsB/CwIhYE+AeBAAdPLOcV8HRmWRDAiQVcO7GcV8CLM8uCAE4sQCDAlHcQ7x+ABQEEAggEEAggEEAggEAAgQACASAQQCCAQACBAAIBBAIIBBAIIBBAIABe4e9iAe/xd7EAJxYgEGDeO4j3EODp/cOCAE4sYMyJ5cwCHs4rCwI4sYBxJ5YzC84rCwKcXxArAuthQYDzC2JF0H49LAhwYUGsCFqvx5EF2T07dMaJBetx4cRyaqFtHJ8EIhK0i8OJBQxcECuCVutxJhCRoE0cZwMRyRcFefa/ffZBVPogePihhyCnbBhcfMFFEFM+DD4m+ghSlgmDkwlOgpAl4+BkkJMgZdk4+EgaSCcpVX7bmY9kgXQQU+1TgE0c+QJZUUz1b2T4SBbIKmJW+3iMj2SBVBWz+leVfCQLpIqYbp8b85EskIxyfIOfK5Sf+wiCRJEsllQ+oqEkQfBxmD8BBgA5hVjXyrBNUQAAAABJRU5ErkJggg==);
		background-size: 100% 100%;

		.num {
			width: 36upx;
			height: 36upx;
			border-radius: 50px;
			font-size: 24upx;
			color: #fff;
			text-align: center;
			line-height: 36upx;
		}

		.sign {
			position: absolute;
			top: 0;
			left: 50%;
			line-height: 36upx;
			font-size: 12upx;
			color: #fff;
			transform: translateX(-50%);
		}
	}
	/* 分类 */
	.cate-section {
		display: flex;
		justify-content: space-around;
		align-items: center;
		flex-wrap:wrap;
		padding: 30upx 22upx; 
		background: #fff;
		.cate-item {
			display: flex;
			flex-direction: column;
			align-items: center;
			font-size: $font-sm + 2upx;
			color: $font-color-dark;
		}
		/* 原图标颜色太深,不想改图了,所以加了透明度 */
		image {
			width: 88upx;
			height: 88upx;
			margin-bottom: 14upx;
			border-radius: 50%;
			opacity: .7;
			box-shadow: 4upx 4upx 20upx rgba(250, 67, 106, 0.3);
		}
	}
	.ad-1{
		width: 100%;
		height: 210upx;
		padding: 10upx 0;
		background: #fff;
		image{
			width:100%;
			height: 100%; 
		}
	}
	/* 秒杀专区 */
	.seckill-section{
		padding: 20px;
		// background: #f2dfdc;
		background: #f7ebeb;
		.s-header{
			display:flex;
			align-items:center;
			justify-content: center;
			height: 92upx;
			line-height: 1;
			margin: 20px;
			.s-img{
				width: 140upx;
				height: 30upx;
			}
			.tip{
				font-size: 28px;
				color: #000;
			}
			.timer{
				display:inline-block;
				width: 40upx;
				height: 36upx;
				text-align:center;
				line-height: 36upx;
				margin-right: 14upx;
				font-size: $font-sm+2upx;
				color: #fff;
				border-radius: 2px;
				background: rgba(0,0,0,.8);
			}
			.icon-you{
				font-size: $font-lg;
				color: $font-color-light;
				flex: 1;
				text-align: right;
			}
		}
		.floor-list{
			white-space: nowrap;
		}
		.car-scoll-wrapper{
			display:flex;
			align-items: flex-start;
			flex-wrap: wrap;
			flex-direction:row;
			align-content:stretch;
			justify-content:flex-start;
		}
		.scoll-wrapper{
			display:flex;
			align-items: flex-start;
			flex-wrap: wrap;
			flex-direction:row-reverse;
			align-content:stretch;
			justify-content:center;
		}
		.scoll-wrapper1{
			display:flex;
			align-items: flex-start;
			padding: 10%;
		}
		.floor-item{
			width: 200px;
			margin-right: 5px;
			margin-left: 5px;
			font-size: $font-sm+2upx;
			color: $font-color-dark;
			line-height: 1.8;
			justify-items: center;
			display: grid;
			padding: 20px;
			image{
				width: 150upx;
				height: 150upx;
				border-radius: 6upx;
			}
			.price{
				color: $uni-color-primary;
			}
		}
	}
	
	.f-header{
		display:flex;
		align-items:center;
		height: 140upx;
		padding: 6upx 30upx 8upx;
		background: #fff;
		image{
			flex-shrink: 0;
			width: 80upx;
			height: 80upx;
			margin-right: 20upx;
		}
		.tit-box{
			flex: 1;
			display: flex;
			flex-direction: column;
		}
		.tit{
			font-size: $font-lg +2upx;
			color: #font-color-dark;
			line-height: 1.3;
		}
		.tit2{
			font-size: $font-sm;
			color: $font-color-light;
		}
		.icon-you{
			font-size: $font-lg +2upx;
			color: $font-color-light;
		}
	}
	/* 团购楼层 */
	.group-section{
		background: #fff;
		.g-swiper{
			height: 650upx;
			padding-bottom: 30upx;
		}
		.g-swiper-item{
			width: 100%;
			padding: 0 30upx;
			display:flex;
		}
		image{
			width: 100%;
			height: 460upx;
			border-radius: 4px;
		}
		.g-item{
			display:flex;
			flex-direction: column;
			overflow:hidden;
		}
		.left{
			flex: 1.2;
			margin-right: 24upx;
			.t-box{
				padding-top: 20upx;
			}
		}
		.right{
			flex: 0.8;
			flex-direction: column-reverse;
			.t-box{
				padding-bottom: 20upx;
			}
		}
		.t-box{
			height: 160upx;
			font-size: $font-base+2upx;
			color: $font-color-dark;
			line-height: 1.6;
		}
		.price{
			color:$uni-color-primary;
		}
		.m-price{
			font-size: $font-sm+2upx;
			text-decoration: line-through;
			color: $font-color-light;
			margin-left: 8upx;
		}
		.pro-box{
			display:flex;
			align-items:center;
			margin-top: 10upx;
			font-size: $font-sm;
			color: $font-base;
			padding-right: 10upx;
		}
		.progress-box{
			flex: 1;
			border-radius: 10px;
			overflow: hidden;
			margin-right: 8upx;
		}
	}
	/* 分类推荐楼层 */
	.hot-floor{
		width: 100%;
		overflow: hidden;
		margin-bottom: 20upx;
		.floor-img-box{
			width: 100%;
			height:320upx;
			position:relative;
			&:after{
				content: '';
				position:absolute;
				left: 0;
				top: 0;
				width: 100%;
				height: 100%;
				background: linear-gradient(rgba(255,255,255,.06) 30%, #f8f8f8);
			}
		}
		.floor-img{
			width: 100%;
			height: 100%;
		}
		.floor-list{
			white-space: nowrap;
			padding: 20upx;
			padding-right: 50upx;
			border-radius: 6upx;
			margin-top:-140upx;
			margin-left: 30upx;
			background: #fff;
			box-shadow: 1px 1px 5px rgba(0,0,0,.2);
			position: relative;
			z-index: 1;
		}
		.scoll-wrapper{
			display:flex;
			align-items: flex-start;
			flex-direction:row-reverse;
			flex-wrap: wrap;
			padding: 10px;
			justify-content: center;
		}
		.floor-item{
			width: 180upx;
			margin-right: 20upx;
			font-size: $font-sm+2upx;
			color: $font-color-dark;
			line-height: 1.8;
			image{
				width: 180upx;
				height: 180upx;
				border-radius: 6upx;
			}
			.price{
				color: $uni-color-primary;
			}
		}
		.more{
			display:flex;
			align-items: center;
			justify-content: center;
			flex-direction: column;
			flex-shrink: 0;
			width: 180upx;
			height: 180upx;
			border-radius: 6upx;
			background: #f3f3f3;
			font-size: $font-base;
			color: $font-color-light;
			text:first-child{
				margin-bottom: 4upx;
			}
		}
	}
	/* 猜你喜欢 */
	.guess-section{
		display:flex;
		flex-wrap:wrap;
		padding: 0 30upx;
		background: #fff;
		.guess-item{
			display:flex;
			flex-direction: column;
			width: 48%;
			padding-bottom: 40upx;
			&:nth-child(2n+1){
				margin-right: 4%;
			}
		}
		.image-wrapper{
			width: 100%;
			height: 330upx;
			border-radius: 3px;
			overflow: hidden;
			image{
				width: 100%;
				height: 100%;
				opacity: 1;
			}
		}
		.title{
			font-size: $font-lg;
			color: $font-color-dark;
			line-height: 80upx;
			padding: 5px;
		}
		.price{
			font-size: $font-lg;
			color: $uni-color-primary;
			line-height: 1;
		}
	}
	.scroll-menu-view {
		display:flex;
		white-space: nowrap;
		line-height: 50px;
		margin-top: 10px;
		align-items: center;
		position: relative;
		background-color: #f7c0ba;
		// padding: 6upx 30upx 8upx;
	
		.scroll-menu-topic-view {
			display: inline-block;
			white-space: nowrap;
			height: 100%;
			position: relative;
	
			.scroll-menu-topic-text {
				font-size: 22px;
				color: $scroll-menu-topic-text;
				padding: 10rpx 40rpx;
			}
			.scroll-menu-topic-text1 {
				font-size: 20px;
				color: $scroll-menu-topic-text1;
				padding: 10rpx 40rpx;
			}
		}
	}
	.buttom-item{
		width: 25%;
		display:inline-grid;
		align-items: center;
		justify-content: center;
		place-items:center;
	}
	.buttom-title{
		font-size:16px
	}
	.buttom-desc{
		font-size:12px
	}
	.c-list{
		font-size: $font-sm + 2upx;
		color: $font-color-base;
		// background: #fff;
		padding-top:2%;
		.c-row{
			display:flex;
			align-items:center;
			padding: 10upx 10upx;
			position:relative;
			height: 50px;
		}
		.tit{
			width: 130upx;
		}
		.tit1{
			width: 78upx;
			font-size: 14px;
			color: $main-action-btn-background-color;
		}
		.tit2{
			width: 110upx;
		}
		.con{
			flex: 1;
			color: $font-color-dark;
			.selected-text{
				margin-right: 10upx;
			}
		}
		.bz-list{
			height: 40upx;
			font-size: $font-sm+2upx;
			color: $font-color-dark;
			text{
				display: inline-block;
				margin-right: 30upx;
			}
		}
		.con-list{
			flex: 1;
			display:flex;
			flex-direction: column;
			color: $font-color-dark;
			line-height: 40upx;
		}
	}
	.select-type{
		margin-left: 10px;
		background: #f7c0ba; 
		border-radius: 4px;
		height: 30px;
		align-items: center;
		justify-content: center;
		display: flex;
		padding: 5px;
	}
	.img-style{
		width: 150px;
		height: 150px;
	}
	.user-style{
		background-color: white;
		margin: 20px;
		border-radius: 10px;
		padding: 5%;
	}
	.action-btn{
		display:flex;
		align-items: center;
		justify-content: center;
		width: 180upx;
		height: 100%;
		font-size: $font-base ;
		padding: 0;
		border-radius: 10px;
		background:$main-action-btn-background-color;
		color: $buttom-background-color;
	}
</style>
