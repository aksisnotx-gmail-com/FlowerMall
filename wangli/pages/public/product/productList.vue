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
								@click="toProductDetail(item.id)"
								@load="onImageLoad('cartList', index)" 
								@error="onImageError('cartList', index)"
							></image> 
							<view
								class="yticon icon-xuanzhong2 checkbox"
								:class="{checked: item.checked}"
								@click="check(index)"
							></view>
						</view>
						<view class="item-right c-list">
							<view class="c-row">
								<text class="con text-center">{{item.name}}</text>
							</view>
							<view class="c-row b-b">
								<view class="tit">状态：</view>
								<text class="con primary" v-if="item.status == '0' ">待审核</text>
								<text class="con success" v-if="item.status == '1' ">审核通过</text>
								<text class="con warning" v-if="item.status == '2' ">审核失败</text>
							</view>
							<view class="c-row b-b">
								<view class="tit" v-if="item.status == '1' ">上架：</view>
								<view class="tit" v-if="item.status != '1' ">自动上架：</view>
								<view class="con">
									<switch :checked="item.publish" :disabled="true"></switch>
								</view>
							</view>
							<view class="c-row b-b">
								<view class="tit">浏览量：</view>
								<text class="con">100</text>
								<view class="tit">销售量：</view>
								<text class="con">50</text>
							</view>
						</view>
					</view>
				</block>
			</view>
		<view>
			<uni-popup ref="message" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000"></uni-popup-message>
			</uni-popup>
		</view>
		
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goBack">返回</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="requestConfirm">确定</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {getProductInfoList} from "@/api/public/product/productList.js"
	export default {
		data() {
			return {
				total: 0, //总价格
				allChecked: false, //全选状态  true|false
				empty: false, //空白页现实  true|false
				cartList: [],
				checkedList: [],
				publishDesc:"",
				messageText:"",
				msgType:"success",
				count: 0
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
			requestConfirm(){
				if(this.count > 3){
					this.msgType = "error"
					this.messageText = `最多添加三个商品`
					this.$refs.message.open()
					return;
				}else{
					let selectRequest = [];
					let pages = getCurrentPages();
					let prePage = pages[pages.length - 2];
					prePage.$vm.product = [];
					for(let data of this.cartList){
						if(data.checked){
							prePage.$vm.product.push(data)
						}
					}
				}
				uni.navigateBack();
			},
			check(index){
				let countTmp = this.count;
				if(!this.cartList[index].checked){
					countTmp += 1;
				}else{
					countTmp -= 1;
				}
				if(countTmp > 3){
					this.msgType = "error"
					this.messageText = `最多选择三个商品`
					this.$refs.message.open()
					return;
				}else{
					this.count = countTmp;
					this.cartList[index].checked = !this.cartList[index].checked;
				}
			},
			switchChange(id){
				updatePublish(id);
			},
			//请求数据
			async loadData(){
				this.cartList = [];
				let pages = getCurrentPages();
				let prePage = pages[pages.length - 2];
				let productList = prePage.$vm.product;
				let products = [];
				for(let product of productList){
					products.push(product.id);
				}
				await getProductInfoList().then(response => {
					for(let data of response.data){
						if(products.includes(data.id)){
							Object.defineProperty(data,"checked",{
								value: true,
								writable : true,
								enumerable : true,
								configurable : true
							});
							this.count += 1;
						}else{
							Object.defineProperty(data,"checked",{
								value: false,
								writable : true,
								enumerable : true,
								configurable : true
							});
						}
					}
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
			deleteProduct(id){
				deleteProductInfo(id).then(response => {
					this.$api.msg(response.msg);
					this.loadData();
				});
			},
			toProductDetail(id){
				uni.navigateTo({
					url: `/pages/public/product/productViewDetail?id=${id}`
				})
			},
			goBack(){
				uni.navigateBack();
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
	.checkbox{
		position:absolute;
		left:-16upx;
		top: -16upx;
		z-index: 8;
		font-size: 44upx;
		line-height: 1;
		padding: 4upx;
		color: $font-color-disabled;
		background:#fff;
		border-radius: 50px;
	}
	/* 复选框选中状态 */
	.action-section .checkbox.checked,
	.cart-item .checkbox.checked{
		color: $uni-color-primary;
	}
	/* 底部操作菜单 */
	.page-bottom{
		position:fixed;
		bottom:0upx;
		z-index: 95;
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 100upx;
		background: rgba(255,255,255,.9);
		box-shadow: 0 0 20upx 0 rgba(0,0,0,.5);
		border-radius: 16upx;
		
		.action-btn-group{
			display: flex;
			height: 76upx;
			border-radius: 100px;
			overflow: hidden;
			box-shadow: 0 20upx 40upx -16upx #fa436a;
			box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
			background: linear-gradient(to right, #ffac30,#fa436a,#F56C6C);
			margin-left: 20upx;
			position:relative;
			.action-btn{
				display:flex;
				align-items: center;
				justify-content: center;
				width: 180upx;
				height: 100%;
				font-size: $font-base ;
				padding: 0;
				border-radius: 0;
				background: transparent;
				background:$buttom-background;
				color: $buttom-background-color;
			}
		}
	}
</style>
