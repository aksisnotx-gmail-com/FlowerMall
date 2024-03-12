<template>
	<view class="container">
			<!-- 列表 -->
			<view class="cart-list">
				<block v-for="(item, index) in processList" :key="item.id">
					<view
						class="cart-item" 
						:class="{'b-b': index!==processList.length-1}"
					>
						<view class="image-wrapper">
							<image :src="item.tagUrl" 
								:class="[item.loaded]"
								mode="aspectFill" 
								lazy-load 
								@click="toProcessDetail(item.id,item.status)"
								@load="onImageLoad('processList', index)" 
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
								<view class="tit">浏览次数：</view>
								<text class="con">{{item.viewCount}}</text>
								<view class="tit">分享次数：</view>
								<text class="con">{{item.shareCount}}</text>
							</view>
							<view class="c-row b-b">
								<view class="con" v-if="item.status == '3' ">
									<uni-icons type="weixin" size="30" color="#a6e7f1" @click="shareProcess(item)"></uni-icons>
								</view>
								<view class="con">
									<uni-icons type="trash" size="30" color="#a6e7f1" @click="deleteProcess(item.id)"></uni-icons>
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
	const processShareWebPath = config.processShareWebPath
	
	import {getProcess,getProcessByRequestId,deleteProcess,initProcessSign} from "@/api/process/processList.js"
	import {shareInfo} from "@/api/share/appShare.js"
	export default {
		data() {
			return {
				total: 0, //总价格
				allChecked: false, //全选状态  true|false
				empty: false, //空白页现实  true|false
				processList: [],
				checkedList: [],
				publishDesc:"",
				messageText:"",
				msgType:"success",
				requestId: "",
				count: 0,
				showOperation: true
			};
		},
		onLoad(e){
			this.loadData(e.requestId);
		},
		watch:{
			//显示空白页
			processList(e){
				let empty = e.length === 0 ? true: false;
				if(this.empty !== empty){
					this.empty = empty;
				}
			}
		},
		methods: {
			shareProcess(item){
				let shareItem = {'name':item.name,'description':item.description,'tagUrl':item.tagUrl};
				initProcessSign(item.id).then(response => {
					let ticket = response.data.ticket;
					let webUrl = uniUrl + processShareWebPath+'?ticket='+ticket;
					let shareData = {'webUrl':webUrl};
					shareInfo(shareItem,shareData);
				});
			},
			//请求数据
			loadData(requestId){
				this.processList = [];
				getProcess().then(response => {
					for(let data of response.data){
						this.processList.push(data);
					}
				});
			},
			deleteProcess(id){
				deleteProcess(id).then(response => {
					this.$api.msg(response.msg);
					this.loadData();
				});
			},
			toProcessDetail(id,status){
				uni.navigateTo({
					url: `/pages/process/processView?id=${id}`
				})
			},
			//监听image加载完成
			onImageLoad(key, index) {
				this.$set(this[key][index], 'loaded', 'loaded');
			},
			//监听image加载失败
			onImageError(key, index) {
				this[key][index].image = '/static/logo.png';
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
				height: 60upx;
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
		left: 0upx;
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
		
		.p-b-btn{
			display:flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			font-size: $font-sm;
			color: $font-color-base;
			width: 96upx;
			height: 80upx;
			.yticon{
				font-size: 40upx;
				line-height: 48upx;
				color: $font-color-light;
			}
			&.active, &.active .yticon{
				color: $uni-color-primary;
			}
			.icon-fenxiang2{
				font-size: 42upx;
				transform: translateY(-2upx);
			}
			.icon-shoucang{
				font-size: 46upx;
			}
		}
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
