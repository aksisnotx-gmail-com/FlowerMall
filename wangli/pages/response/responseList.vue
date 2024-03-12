<template>
	<view class="container">
		<view class="cart-list">
			<block v-for="(item, index) in cartList" :key="item.id">
				<view
					class="cart-item" 
					:class="{'b-b': index!==cartList.length-1}"
				>
					<view class="image-wrapper">
						<image :src="item.tagUrl" 
							:class="[item.loaded]"
							mode="aspectFill" 
							lazy-load 
							@click="toResponseDetail(item.id,item.requestId,item.status)"
							@load="onImageLoad('cartList', index)" 
							@error="onImageError('cartList', index)"
						></image> 
					</view>
					<view class="item-right c-list">
						<view class="c-row b-b">
							<text class="con">{{item.name}}</text>
						</view>
						<view class="c-row b-b">
							<view class="tit">回复时间：</view>
							<text class="con">{{formatDate(item.createTime)}}</text>
						</view>
						<view class="c-row b-b">
							<view class="tit">点赞数：</view>
							<text class="con">{{item.agreeCount}}</text>
						</view>
						<view class="c-row b-b">
							<view class="tit">状态：</view>
							<text class="con primary" v-if="item.status == '1' ">待审核</text>
							<text class="con success" v-if="item.status == '2' ">需求发起方审核成功</text>
							<text class="con warning" v-if="item.status == '3' ">需求发起方审核失败</text>
							<text class="con success" v-if="item.status == '4' ">平台审核成功</text>
							<text class="con warning" v-if="item.status == '5' ">平台审核失败</text>
						</view>
						<view class="c-row b-b">
							<uni-icons type="trash" size="30" color="#a6e7f1" @click="deleteResponse(item.id)"></uni-icons>
						</view>
					</view>
					
				</view>
			</block>
		</view>
	</view>
</template>

<script>
	import {getResponseInfo,deleteBusinessResponseInfo} from "@/api/response/responseList.js"
	export default {
		data() {
			return {
				total: 0, //总价格
				allChecked: false, //全选状态  true|false
				empty: false, //空白页现实  true|false
				cartList: [],
				checkedList: [],
			};
		},
		onLoad(){
			this.loadData();
		},
		watch:{
			//显示空白页
			cartList(e){
				let empty = e.length === 0 ? true: false;
				if(this.empty !== empty){
					this.empty = empty;
				}
			}
		},
		methods: {
			formatDate(dateString) {
				const date = new Date(dateString);
				const year = date.getFullYear();
				const month = String(date.getMonth() + 1).padStart(2, '0');
				const day = String(date.getDate()).padStart(2, '0');
				return `${year}-${month}-${day}`;
			},
			toResponseDetail(id,requestId,status){
				uni.navigateTo({
					url: `/pages/response/responseViewDetail?id=${id}`
				})
			},
			switchChange(e){
				
			},
			//请求数据
			async loadData(){
				this.cartList = [];
				await getResponseInfo().then(response => {
					this.cartList = response.data;
				});
				
			},
			//监听image加载完成
			onImageLoad(key, index) {
				this.$set(this[key][index], 'loaded', 'loaded');
			},
			//监听image加载失败
			onImageError(key, index) {
				this[key][index].image = '/static/logo.png';
			},
			deleteResponse(id){
				deleteBusinessResponseInfo(id).then(response => {
					this.$api.msg(response.msg);
					this.loadData();
				});
			},
			backRequest(){
				uni.switchTab({
					url: '/pages/work/index'
				})
			}
		}
	}
</script>

<style lang='scss'>
	.container{
		padding-bottom: 134upx;
		/* 空白页 */
		.empty{
			position:fixed;
			left: 0;
			top:0;
			width: 100%;
			height: 100vh;
			padding-bottom:100upx;
			display:flex;
			justify-content: center;
			flex-direction: column;
			align-items:center;
			background: #fff;
			image{
				width: 240upx;
				height: 160upx;
				margin-bottom:30upx;
			}
			.empty-tips{
				display:flex;
				font-size: $font-sm+2upx;
				color: $font-color-disabled;
				.navigator{
					color: $uni-color-primary;
					margin-left: 16upx;
				}
			}
		}
	}
	.cart-item{
		display:flex;
		position:relative;
		padding:10upx 10upx;
		.image-wrapper{
			width: 230upx;
			height: 240upx;
			flex-shrink: 0;
			position:relative;
			image{
				border-radius:8upx;
			}
		}
		.item-right{
			display:flex;
			flex-direction: column;
			flex: 1;
			overflow: hidden;
			position:relative;
			padding-left: 30upx;
			.title,.price{
				font-size:$font-base + 2upx;
				color: $font-color-dark;
				height: 40upx;
				line-height: 40upx;
			}
		}
		.del-btn{
			padding:4upx 5upx;
			font-size:34upx; 
			height: 50upx;
			color: $uni-color-primary;
		}
		.c-list{
			font-size: $font-sm + 2upx;
			color: $font-color-base;
			background: #fff;
			.c-row{
				display:flex;
				align-items:center;
				padding: 10upx 10upx;
				position:relative;
				height: 50upx;
			}
			.tit{
				width: 200upx;
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
			.red{
				color: $uni-color-primary;
			}
		}
	}
</style>
