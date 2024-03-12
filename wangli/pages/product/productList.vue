<template>
	<view class="container">
			<!-- 列表 -->
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
								@click="toProductDetail(item.id,item.status)"
								@load="onImageLoad('cartList', index)" 
								@error="onImageError('cartList', index)"
							></image> 
						</view>
						<view class="item-right c-list">
							<view class="c-row">
								<text class="con text-center">{{item.name}}</text>
							</view>
							<view class="c-row b-b">
								<view class="tit">状态：</view>
								<text class="con primary" v-if="item.status == '1' ">待审核</text>
								<text class="con warning" v-if="item.status == '2' ">审核失败</text>
								<text class="con success" v-if="item.status == '3' ">审核通过</text>
							</view>
							<view class="c-row b-b">
								<view class="tit" v-if="item.status == '1' ">上架：</view>
								<view class="tit" v-if="item.status != '1' ">自动上架：</view>
								<view class="con">
									<switch :checked="item.publish == '1'" @change="switchChange(item)"></switch>
								</view>
							</view>
							<view class="c-row b-b">
								<view class="tit">浏览次数：</view>
								<text class="con">{{item.viewCount}}</text>
								<view class="tit">分享次数：</view>
								<text class="con">{{item.shareCount}}</text>
							</view>
							<view class="c-row b-b">
								<view class="con" v-if="item.status == '3'">
									<uni-icons type="weixin" size="30" color="#a6e7f1" @click="shareProduct(item)"></uni-icons>
								</view>
								<view class="con">
									<uni-icons type="trash" size="30" color="#a6e7f1" @click="deleteProduct(item.id)"></uni-icons>
								</view>
							</view>
						</view>
						
					</view>
				</block>
			</view>
	</view>
</template>

<script>
	import config from '@/config'
	const uniUrl = config.uniUrl
	const productShareWebPath = config.productShareWebPath
	
	import {getProductInfoList,deleteProductInfo,updatePublish,initProductSign} from "@/api/product/productList.js"
	import {shareInfo} from "@/api/share/appShare.js"
	export default {
		data() {
			return {
				total: 0, //总价格
				allChecked: false, //全选状态  true|false
				empty: false, //空白页现实  true|false
				cartList: [],
				checkedList: [],
				publishDesc:"",
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
			shareProduct(item){
				let shareItem = {'name':item.name,'description':item.description,'tagUrl':item.tagUrl};
				initProductSign(item.id).then(response => {
					let ticket = response.data.ticket;
					let webUrl = uniUrl + productShareWebPath+'?ticket='+ticket;
					let shareData = {'webUrl':webUrl};
					shareInfo(shareItem,shareData);
				});
			},
			switchChange(item){
				let publish = item.publish;
				if(publish == '1'){
					item.publish = '0';
				}else{
					item.publish = '1';
				}
				let data = {'id':item.id,'publish':item.publish}
				updatePublish(data);
			},
			//请求数据
			async loadData(){
				this.cartList = [];
				await getProductInfoList().then(response => {
					for(let data of response.data){
						this.cartList.push(data);
					}
				});
				console.log(this.cartList)
			},
			//监听image加载完成
			onImageLoad(key, index) {
				this.$set(this[key][index], 'loaded', 'loaded');
			},
			//监听image加载失败
			onImageError(key, index) {
				this[key][index].image = '/static/logo.png';
			},
			deleteProduct(id){
				deleteProductInfo(id).then(response => {
					this.$api.msg(response.msg);
					this.loadData();
				});
			},
			toProductDetail(id,status){
				uni.navigateTo({
					url: `/pages/product/productViewDetail?id=${id}`
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
	/* 购物车列表项 */
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
			padding-left: 10upx;
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
				width: 130upx;
			}
			.tit1{
				width: 78upx;
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
			.primary{
				color: $uni-color-warning;
			}
			.success{
				color: $uni-color-success;
			}
			.warning{
				color: $uni-color-error;
			}
		}
	}
</style>
