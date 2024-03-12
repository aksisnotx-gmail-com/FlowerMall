<template>
	<view class="container">
		<!-- 区划选择 -->
		<view class="f-area m-t">
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
			<input class="ser-input" :value="name" placeholder="输入关键字搜索" style="border: none;" @input="inputSearchValue"/>
			<button class="ser-button" type="primary" size="mini" @click="searchValue">
				搜索
			</button>
		</view>
		<!-- 最新发布 -->
		<view class="f-new m-t f-new-request" v-if="latestRequestList.length > 0">
			<view class="s-header">
				<uni-icons type="fire-filled" size="30" color="#ff0000"></uni-icons>
			</view>
			<view class="tit-box">
				<text class="tit">最新发布</text>
			</view>
		</view>
		<view class="f-new-content f-new-request-content" v-if="latestRequestList.length > 0">
			<scroll-view class="f-new-content-list" scroll-x style="height: 80px;top: 4px; position: relative">
				<view class="f-new-content-scoll-wrapper">
					<view 
						v-for="(item, index) in latestRequestList" :key="index"
						@click="navToDetailPage(item)"
					>
						<view class="f-new-content-scoll-item" style="width: 70px;">
							<view>
								<image class="image" :src="item.tagUrl" mode="aspectFit" style="width: 118rpx; height: 85rpx; display: inline-block; box-sizing: border-box; left: 0rpx; top: 0rpx"></image>
							</view>
							<view>
								<text class="text clamp" style="font-size: 20rpx;">{{item.name}}</text>
							</view>
						</view>
						
					</view>
				</view>
			</scroll-view>
		</view>
		<!-- 需求列表 -->
		<scroll-view scroll-y="true" class="scoll-wrapper" @scrolltolower="onReachBottomLoad">
			<view class="guess-section">
				<view 
					v-for="(item, index) in requestList" :key="index"
					class="guess-item"
					style="height: 100%;"
					@click="navToDetailPage(item)"
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
	import {getLatesInfo,getRequestInfoList} from '@/api/public/request/index.js'
	export default {
		components:{
			pickerAddress
		},
		data() {
			return {
				name: '',
				type: 0,
				province: '',
				city: '',
				county: '',
				titleNViewBackground: '',
				swiperCurrent: 0,
				swiperLength: 0,
				latestRequestList: [],
				requestList: [],
				pageIndex: 0,
				hasNext: true,
				txt: '请选择',
				title: 'Hello',
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
				currentTab: 0,
			};	
		},
		onLoad() {
			this.loadData();
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
				getRequestInfoList(data).then(response => {
					for(let data of response.data){
						this.requestList.push(data);
					}
					if(response.data.length > 0){
						this.pageIndex += 1;
						this.hasNext = true;
					}else{
						this.hasNext = false;
					}
				});
			},
			inputSearchValue(event){
				this.name = event.detail.value
			},
			searchValue(){
				this.queryRequestInfoList();
			},
			swichMenu(id) {
				this.type = id;
				this.queryRequestInfoList();
			},
			async loadData() {
				this.queryRequestInfoList();
				this.getLatestRequestInfo();
			},
			getLatestRequestInfo(){
				getLatesInfo().then(response => {
					this.latestRequestList = response.data;
				});
			},
			queryRequestInfoList(){
				this.requestList = [];
				this.pageIndex = 0;
				let firstLevel = '';
				if(this.type != 0){
					firstLevel = this.type;
				}
				let data = {'name':this.name,'firstLevel':firstLevel, 'province':this.province,'city':this.city,'county':this.county,'pageIndex':this.pageIndex};
				getRequestInfoList(data).then(response => {
					for(let data of response.data){
						this.requestList.push(data);
					}
					if(response.data.length > 0){
						this.pageIndex += 1;
						this.hasNext = true;
					}else{
						this.hasNext = false;
					}
				});
			},
			//详情页
			navToDetailPage(item) {
				uni.navigateTo({
					url: `/pages/public/request/requestInfoDetail?id=${item.id}`
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
				this.queryRequestInfoList();
				
				this.txt = dataTmp.join('-->')
			}
		},
		onNavigationBarSearchInputChanged(e){
			this.name = e.text;
		},
		//点击导航栏 buttons 时触发
		onNavigationBarButtonTap(e) {
			this.queryRequestInfoList();
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
		top: 390px;
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
	.f-new-request{
		top:120px;
		display:flex;
		position:relative;
		align-items: center;
		padding: 6upx 30upx 8upx;
		background: $uni-bg-color-main;
	}
	.f-new-request-content{
		top:130px;
		display: flex;
		position: relative;
		padding: 6upx 30upx 8upx;
		background: $uni-bg-color-main;
	}
</style>
