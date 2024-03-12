<template>
	<view class="container">
		<!-- 区划选择 -->
		<view class="f-area m-t" >
			<view class="s-header">
				<uni-icons type="location" size="30" color="#ffffff"></uni-icons>
			</view>
			<view class="s-header">
				<pickerAddress @change="change">
					<text class="tit1">{{txt}}</text>
				</pickerAddress>
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
		
		<view class="mp-search-box">
			<input class="ser-input" :value="name" placeholder="输入关键字搜索" @input="inputSearchValue"/>
			<button class="ser-button" type="primary" size="mini" @click="searchValue">
				搜索
			</button>
		</view>
		
		<!-- 商户列表 -->
		<scroll-view scroll-y="true" class="scoll-wrapper" @scrolltolower="onReachBottomLoad">
			<view class="guess-section">
				<view 
					v-for="(item, index) in merchantList" :key="index"
					class="guess-item"
					style="height: 100%;"
					@click="navToMerchant(item.id)"
				>
					<view class="image-wrapper">
						<image :src="item.tagUrl" mode="aspectFill"></image>
					</view>
					<text class="title clamp">{{item.name}}</text>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import pickerAddress from '@/components/pickerAddress/pickerAddress.vue'
	import {getMerchantInfoList} from '@/api/public/merchant/index.js'
	export default {
		components:{
			pickerAddress
		},
		data() {
			return {
				name: '',
				type: 0,
				titleNViewBackground: '',
				swiperCurrent: 0,
				swiperLength: 0,
				businesscarouselList: [],
				latestRequestList: [],
				merchantList: [],
				pageIndex: 0,
				hasNext: true,
				txt: '请选择',
				title: 'Hello',
				province: '',
				city: '',
				county: '',
				tabs: [{
						id: 0,
						name: '全部'
					},{
						id: 1,
						name: '服装'
					},
					{
						id: 2,
						name: '鞋子'
					}
				],
			};
		},

		onLoad() {
			this.loadData();
		},
		onReachBottom(){
			if(!this.hasNext){
				return;
			}
			let firstLevel = '';
			if(this.type != 0){
				firstLevel = this.type;
			}
			let data = {'name':this.name,'firstLevel':firstLevel, 'province':this.province,'city':this.city,'county':this.county,'pageIndex':this.pageIndex};
			getMerchantInfoList(data).then(response => {
				for(let data of response.data){
					this.merchantList.push(data);
				}
				if(response.data.length > 0){
					this.pageIndex += 1;
					this.hasNext = true;
				}else{
					this.hasNext = false;
				}
			});
		},
		methods: {
			onReachBottomLoad(){
				if(!this.hasNext){
					return;
				}
				let firstLevel = '';
				if(this.type != 0){
					firstLevel = this.type;
				}
				let data = {'name':this.name,'firstLevel':firstLevel, 'province':this.province,'city':this.city,'county':this.county,'pageIndex':this.pageIndex};
				getMerchantInfoList(data).then(response => {
					for(let data of response.data){
						this.merchantList.push(data);
					}
					if(response.data.length > 0){
						this.pageIndex += 1;
						this.hasNext = true;
					}else{
						this.hasNext = false;
					}
				});
			},
			searchValue(){
				this.loadData();
			},
			inputSearchValue(event){
				this.name = event.detail.value
			},
			swichMenu(id) {
				this.type = id;
				this.loadData();
			},
			async loadData() {
				this.merchantList = [];
				this.pageIndex = 0;
				let firstLevel = '';
				if(this.type != 0){
					firstLevel = this.type;
				}
				let data = {'name':this.name,'firstLevel':firstLevel, 'province':this.province,'city':this.city,'county':this.county,'pageIndex':this.pageIndex};
				getMerchantInfoList(data).then(response => {
					for(let data of response.data){
						this.merchantList.push(data);
					}
					if(response.data.length > 0){
						this.pageIndex += 1;
						this.hasNext = true;
					}else{
						this.hasNext = false;
					}
				});
			},
			//轮播图切换修改背景色
			swiperChange(e) {
				const index = e.detail.current;
				this.swiperCurrent = index;
				this.titleNViewBackground = this.businesscarouselList[index].background;
			},
			//详情页
			navToMerchant(id) {
				//测试数据没有写id，用title代替
				uni.navigateTo({
					url: `/pages/public/merchant/merchantViewDetail?id=${id}`
				})
			},
			change(data) {
				let dataTmp = [];
				if(data.index[0] !=0){
					dataTmp.push(data.data[0]);
					this.province = data.index[0];
				}else{
					this.province = '';
				}
				if(data.index[1] !=0){
					dataTmp.push(data.data[1]);
					this.city = data.index[1];
				}else{
					this.city = '';
				}
				if(data.index[2] !=0){
					dataTmp.push(data.data[2]);
					this.county = data.index[2];
				}else{
					this.county = '';
				}
				if(dataTmp.length == 0){
					dataTmp.push("全国");
				}
				this.txt = dataTmp.join('-->')
				this.loadData();
			}
		},
		// 标题栏input搜索框点击
		onNavigationBarSearchInputChanged(e){
			this.name = e.text;
		},
		//点击导航栏 buttons 时触发
		onNavigationBarButtonTap(e) {
			this.loadData();
		}
	}
</script>

<style lang="scss">
	page {
		background: url("@/static/logo/background.png");
	}
	.m-t{
		margin-top: 10upx;
	}
	.f-area{
		display:flex;
		align-items: center;
		position: relative;
		top: 90px;
		padding: 6upx 30upx 8upx;
	}
	.scroll-menu-view {
		display:flex;
		white-space: nowrap;
		line-height: 50rpx;
		margin-top: 5px;
		align-items: center;
		position: relative;
		top: 100px;
		padding: 6upx 30upx 8upx;
	
		.scroll-menu-topic-view {
			display: inline-block;
			white-space: nowrap;
			height: 100%;
			position: relative;
	
			.scroll-menu-topic-text {
				font-size: 30rpx;
				color: $scroll-menu-topic-text;
				padding: 10rpx 40rpx;
			}
			.scroll-menu-topic-text1 {
				font-size: 30rpx;
				color: $scroll-menu-topic-text1;
				padding: 10rpx 40rpx;
			}
		}
	}
	.mp-search-box{
		display:flex;
		align-items: center;
		position: relative;
		top: 110px;
		padding: 6upx 30upx 8upx;
		
		.ser-input{
			flex:1;
			text-align: center;
			border-radius: 100px;
			background: rgba(255, 255, 255, 0.6);
			width: 80%; 
			margin-left: 10%;
			height: 30px;
		}
		.ser-button{
			border-radius: 100px;
			height: 30px;
			background:$buttom-background;
			color: $buttom-background-color;
		}
	}
	.scoll-wrapper{
		position: fixed; 
		bottom: 0; 
		background: rgb(209, 206, 206); 
		top: 235px;
	}
	.guess-section{
		display:flex;
		flex-wrap:wrap;
		padding: 0 50upx;
		position: relative;
		top: 25rpx;
		.guess-item{
			display:flex;
			flex-direction: column;
			width: 48%;
			height: 100%;
			background: $uni-bg-color-main;
			&:nth-child(2n+1){
				margin-right: 4%;
				margin-bottom: 4%;
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
			font-size: $font-base;
			color: $font-color-dark;
			line-height: 80upx;
		}
		.price{
			font-size: $font-lg;
			color: $uni-color-primary;
			line-height: 1;
		}
	}
</style>
