<template>
	<view class="container">
		<view style="padding: 5%;">
			<view class="c-list">
				<view class="c-row" style="display: flex;">
					<view>
						<image :src="product.tagUrl"></image>
					</view>
					<view class="c-list">
						<view class="c-row">
							<text>{{product.name}}</text>
						</view>
						<view class="c-row">
							<text>￥{{product.price}}</text>
						</view>
						<view class="c-row">
							<text class="tit">材料</text>
							<text class="con">{{product.material}}</text>
						</view>
						<view class="c-row">
							<text class="tit">包装</text>
							<text class="con">{{product.packageInfo}}</text>
						</view>
						<view class="c-row">
							<text class="tit">花语</text>
							<text class="con">{{product.flowerLanguage}}</text>
						</view>
						<view class="c-row">
							<text class="tit">附赠</text>
							<text class="con">{{product.bundled}}</text>
						</view>
						<view class="c-row">
							<text class="tit">配送</text>
							<text class="con">{{product.delivery}}</text>
						</view>
						<view class="c-row">
							<text class="tit">数量</text>
							<uni-number-box
								class="step"
								:min="1" 
								:max="100"
								:value="productNum"
							></uni-number-box>
						</view>
						<view class="c-row">
							<button type="primary" style="background: black;"  @click="buy">立即购买</button>
							<button type="primary" style="background: hotpink;" @click="addToGoodCar">加入购物车</button>
						</view>
					</view>
				</view>
			</view>
		</view>
		
		<view class="detail-desc">
			<view class="d-header">
				<text>商品详情</text>
			</view>
			<view v-for="(item, index) in imgList" :key="index">
				<img style="width:100%;display:block;" 
					:src="item" />
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
	import uniNumberBox from '@/components/uni-number-box/components/uni-number-box/uni-number-box.vue'
	import share from '@/components/share';
	import config from '@/config'
	
	import {getProductInfo} from "@/api/index/index.js"
	
	const goodListKey = config.goodListKey
	export default{
		components: {
			share,
			uniNumberBox
		},
		data() {
			return {
				specClass: 'none',
				specSelected:[],
				product:{
					'id':'1',
					'name':'11支白玫瑰',
					'material':'材料材料材料材料材料材料材料材料',
					'packageInfo':'包装包装包装包装包装包装包装',
					'flowerLanguage':'花语花语花语花语花语花语花语花语花语',
					'bundled':'附赠附赠附赠附赠附赠附赠附赠附赠附赠',
					'delivery':'配送配送配送配送配送配送配送配送配送',
					'description':'说明说明说明说明说明说明说明说明说明',
					'productNum':'10',
					'price':'100',
					'tagUrl':'https://gd3.alicdn.com/imgextra/i3/0/O1CN01IiyFQI1UGShoFKt1O_!!0-item_pic.jpg_400x400.jpg',
					'url':'https://gd3.alicdn.com/imgextra/i3/0/O1CN01IiyFQI1UGShoFKt1O_!!0-item_pic.jpg_400x400.jpg;https://gd3.alicdn.com/imgextra/i3/0/O1CN01IiyFQI1UGShoFKt1O_!!0-item_pic.jpg_400x400.jpg'
				},
				productNum:0,
				favorite: true,
				shareList: [],
				imgList: [
					{
						src: 'https://gd3.alicdn.com/imgextra/i3/0/O1CN01IiyFQI1UGShoFKt1O_!!0-item_pic.jpg_400x400.jpg'
					},
					{
						src: 'https://gd3.alicdn.com/imgextra/i3/TB1RPFPPFXXXXcNXpXXXXXXXXXX_!!0-item_pic.jpg_400x400.jpg'
					},
					{
						src: 'https://gd2.alicdn.com/imgextra/i2/38832490/O1CN01IYq7gu1UGShvbEFnd_!!38832490.jpg_400x400.jpg'
					}
				],
				desc: `
					<div style="width:100%">
						<img style="width:100%;display:block;" src="https://gd3.alicdn.com/imgextra/i4/479184430/O1CN01nCpuLc1iaz4bcSN17_!!479184430.jpg_400x400.jpg" />
						<img style="width:100%;display:block;" src="https://gd2.alicdn.com/imgextra/i2/479184430/O1CN01gwbN931iaz4TzqzmG_!!479184430.jpg_400x400.jpg" />
						<img style="width:100%;display:block;" src="https://gd3.alicdn.com/imgextra/i3/479184430/O1CN018wVjQh1iaz4aupv1A_!!479184430.jpg_400x400.jpg" />
						<img style="width:100%;display:block;" src="https://gd4.alicdn.com/imgextra/i4/479184430/O1CN01tWg4Us1iaz4auqelt_!!479184430.jpg_400x400.jpg" />
						<img style="width:100%;display:block;" src="https://gd1.alicdn.com/imgextra/i1/479184430/O1CN01Tnm1rU1iaz4aVKcwP_!!479184430.jpg_400x400.jpg" />
					</div>
				`,
				specList: [
					{
						id: 1,
						name: '尺寸',
					},
					{	
						id: 2,
						name: '颜色',
					},
				],
				specChildList: [
					{
						id: 1,
						pid: 1,
						name: 'XS',
					},
					{
						id: 2,
						pid: 1,
						name: 'S',
					},
					{
						id: 3,
						pid: 1,
						name: 'M',
					},
					{
						id: 4,
						pid: 1,
						name: 'L',
					},
					{
						id: 5,
						pid: 1,
						name: 'XL',
					},
					{
						id: 6,
						pid: 1,
						name: 'XXL',
					},
					{
						id: 7,
						pid: 2,
						name: '白色',
					},
					{
						id: 8,
						pid: 2,
						name: '珊瑚粉',
					},
					{
						id: 9,
						pid: 2,
						name: '草木绿',
					},
				]
			};
		},
		async onLoad(options){
			
			//接收传值,id里面放的是标题，因为测试数据并没写id 
			let id = options.id;
			if(id){
				// this.$api.msg(`点击了${id}`);
			}
			
			
			//规格 默认选中第一条
			this.specList.forEach(item=>{
				for(let cItem of this.specChildList){
					if(cItem.pid === item.id){
						this.$set(cItem, 'selected', true);
						this.specSelected.push(cItem);
						break; //forEach不能使用break
					}
				}
			})
			this.shareList = await this.$api.json('shareList');
			this.imgList = this.product.url.split(';');
			
			getProductInfo(options.id).then(response => {
					this.product = response.data;
					console.log('product',this.product)
					if(this.product.url){
						this.imgList = [];
						let imgListTmp = this.product.url.split(';');
						for(let item of imgListTmp){
							if(item.length > 5){
								this.imgList.push(item)
							}
						}
						console.log('imglist',this.imgList)
					}
				});
			
		},
		methods:{
			addToGoodCar(){
				let goodList =  uni.getStorageSync(goodListKey);
				if(!goodList){
					goodList = [];
				}
				this.product.number = 1;
				goodList.push(this.product);
				
				uni.setStorageSync(goodListKey,goodList);
				
				this.$modal.msgSuccess("添加成功")
			},
			//规格弹窗开关
			toggleSpec() {
				if(this.specClass === 'show'){
					this.specClass = 'hide';
					setTimeout(() => {
						this.specClass = 'none';
					}, 250);
				}else if(this.specClass === 'none'){
					this.specClass = 'show';
				}
			},
			//选择规格
			selectSpec(index, pid){
				let list = this.specChildList;
				list.forEach(item=>{
					if(item.pid === pid){
						this.$set(item, 'selected', false);
					}
				})

				this.$set(list[index], 'selected', true);
				//存储已选择
				/**
				 * 修复选择规格存储错误
				 * 将这几行代码替换即可
				 * 选择的规格存放在specSelected中
				 */
				this.specSelected = []; 
				list.forEach(item=>{ 
					if(item.selected === true){ 
						this.specSelected.push(item); 
					} 
				})
				
			},
			//分享
			share(){
				this.$refs.share.toggleMask();	
			},
			//收藏
			toFavorite(){
				this.favorite = !this.favorite;
			},
			buy(){
				uni.navigateTo({
					url: `/pages/order/createOrder`
				})
			},
			stopPrevent(){}
		},

	}
</script>

<style lang='scss'>
	page {
		background: #f7c0ba;
	}
	.icon-you{
		font-size: $font-base + 2upx;
		color: #888;
	}
	.carousel {
		height: 722upx;
		position:relative;
		swiper{
			height: 100%;
		}
		.image-wrapper{
			width: 100%;
			height: 100%;
		}
		.swiper-item {
			display: flex;
			justify-content: center;
			align-content: center;
			height: 750upx;
			overflow: hidden;
			image {
				width: 100%;
				height: 100%;
			}
		}
		
	}
	
	/* 标题简介 */
	.introduce-section{
		background: #fff;
		padding: 20upx 30upx;
		
		.title{
			font-size: 32upx;
			color: $font-color-dark;
			height: 50upx;
			line-height: 50upx;
		}
		.price-box{
			display:flex;
			align-items:baseline;
			height: 64upx;
			padding: 10upx 0;
			font-size: 26upx;
			color:$uni-color-primary;
		}
		.price{
			font-size: $font-lg + 2upx;
		}
		.m-price{
			margin:0 12upx;
			color: $font-color-light;
			text-decoration: line-through;
		}
		.coupon-tip{
			align-items: center;
			padding: 4upx 10upx;
			background: $uni-color-primary;
			font-size: $font-sm;
			color: #fff;
			border-radius: 6upx;
			line-height: 1;
			transform: translateY(-4upx); 
		}
		.bot-row{
			display:flex;
			align-items:center;
			height: 50upx;
			font-size: $font-sm;
			color: $font-color-light;
			text{
				flex: 1;
			}
		}
	}
	/* 分享 */
	.share-section{
		display:flex;
		align-items:center;
		color: $font-color-base;
		background: linear-gradient(left, #fdf5f6, #fbebf6);
		padding: 12upx 30upx;
		.share-icon{
			display:flex;
			align-items:center;
			width: 70upx;
			height: 30upx;
			line-height: 1;
			border: 1px solid $uni-color-primary;
			border-radius: 4upx;
			position:relative;
			overflow: hidden;
			font-size: 22upx;
			color: $uni-color-primary;
			&:after{
				content: '';
				width: 50upx;
				height: 50upx;
				border-radius: 50%;
				left: -20upx;
				top: -12upx;
				position:absolute;
				background: $uni-color-primary;
			}
		}
		.icon-xingxing{
			position:relative;
			z-index: 1;
			font-size: 24upx;
			margin-left: 2upx;
			margin-right: 10upx;
			color: #fff;
			line-height: 1;
		}
		.tit{
			font-size: $font-base;
			margin-left:10upx;
		}
		.icon-bangzhu1{
			padding: 10upx;
			font-size: 30upx;
			line-height: 1;
		}
		.share-btn{
			flex: 1;
			text-align:right;
			font-size: $font-sm;
			color: $uni-color-primary;
		}
		.icon-you{
			font-size: $font-sm;
			margin-left: 4upx;
			color: $uni-color-primary;
		}
	}
	
	.c-list{
		font-size: $font-sm + 2upx;
		color: $font-color-base;
		background: #fff;
		.c-row{
			display:flex;
			align-items:center;
			padding: 20upx 30upx;
			position:relative;
		}
		.tit{
			width: 140upx;
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
	
	/* 评价 */
	.eva-section{
		display: flex;
		flex-direction: column;
		padding: 20upx 30upx;
		background: #fff;
		margin-top: 16upx;
		.e-header{
			display: flex;
			align-items: center;
			height: 70upx;
			font-size: $font-sm + 2upx;
			color: $font-color-light;
			.tit{
				font-size: $font-base + 2upx;
				color: $font-color-dark;
				margin-right: 4upx;
			}
			.tip{
				flex: 1;
				text-align: right;
			}
			.icon-you{
				margin-left: 10upx;
			}
		}
	}
	.eva-box{
		display: flex;
		padding: 20upx 0;
		.portrait{
			flex-shrink: 0;
			width: 80upx;
			height: 80upx;
			border-radius: 100px;
		}
		.right{
			flex: 1;
			display: flex;
			flex-direction: column;
			font-size: $font-base;
			color: $font-color-base;
			padding-left: 26upx;
			.con{
				font-size: $font-base;
				color: $font-color-dark;
				padding: 20upx 0;
			}
			.bot{
				display: flex;
				justify-content: space-between;
				font-size: $font-sm;
				color:$font-color-light;
			}
		}
	}
	/*  详情 */
	.detail-desc{
		background: #fff;
		margin-top: 16upx;
		padding: 5%;
		margin: 5%;
		.d-header{
			display: flex;
			justify-content: center;
			align-items: center;
			height: 80upx;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			position: relative;
			margin-bottom: 50px;
				
			text{
				padding: 0 20upx;
				background: #fff;
				position: relative;
				z-index: 1;
			}
			&:after{
				position: absolute;
				left: 50%;
				top: 50%;
				transform: translateX(-50%);
				width: 100%;
				height: 0;
				content: '';
				border-bottom: 1px solid #ccc; 
			}
		}
	}
	
	/* 规格选择弹窗 */
	.attr-content{
		padding: 10upx 30upx;
		.a-t{
			display: flex;
			image{
				width: 170upx;
				height: 170upx;
				flex-shrink: 0;
				margin-top: -40upx;
				border-radius: 8upx;;
			}
			.right{
				display: flex;
				flex-direction: column;
				padding-left: 24upx;
				font-size: $font-sm + 2upx;
				color: $font-color-base;
				line-height: 42upx;
				.price{
					font-size: $font-lg;
					color: $uni-color-primary;
					margin-bottom: 10upx;
				}
				.selected-text{
					margin-right: 10upx;
				}
			}
		}
		.attr-list{
			display: flex;
			flex-direction: column;
			font-size: $font-base + 2upx;
			color: $font-color-base;
			padding-top: 30upx;
			padding-left: 10upx;
		}
		.item-list{
			padding: 20upx 0 0;
			display: flex;
			flex-wrap: wrap;
			text{
				display: flex;
				align-items: center;
				justify-content: center;
				background: #eee;
				margin-right: 20upx;
				margin-bottom: 20upx;
				border-radius: 100upx;
				min-width: 60upx;
				height: 60upx;
				padding: 0 20upx;
				font-size: $font-base;
				color: $font-color-dark;
			}
			.selected{
				background: #fbebee;
				color: $uni-color-primary;
			}
		}
	}
	
	/*  弹出层 */
	.popup {
		position: fixed;
		left: 0;
		top: 0;
		right: 0;
		bottom: 0;
		z-index: 99;
		
		&.show {
			display: block;
			.mask{
				animation: showPopup 0.2s linear both;
			}
			.layer {
				animation: showLayer 0.2s linear both;
			}
		}
		&.hide {
			.mask{
				animation: hidePopup 0.2s linear both;
			}
			.layer {
				animation: hideLayer 0.2s linear both;
			}
		}
		&.none {
			display: none;
		}
		.mask{
			position: fixed;
			top: 0;
			width: 100%;
			height: 100%;
			z-index: 1;
			background-color: rgba(0, 0, 0, 0.4);
		}
		.layer {
			position: fixed;
			z-index: 99;
			bottom: 0;
			width: 100%;
			min-height: 40vh;
			border-radius: 10upx 10upx 0 0;
			background-color: #fff;
			.btn{
				height: 66upx;
				line-height: 66upx;
				border-radius: 100upx;
				background: $uni-color-primary;
				font-size: $font-base + 2upx;
				color: #fff;
				margin: 30upx auto 20upx;
			}
		}
		@keyframes showPopup {
			0% {
				opacity: 0;
			}
			100% {
				opacity: 1;
			}
		}
		@keyframes hidePopup {
			0% {
				opacity: 1;
			}
			100% {
				opacity: 0;
			}
		}
		@keyframes showLayer {
			0% {
				transform: translateY(120%);
			}
			100% {
				transform: translateY(0%);
			}
		}
		@keyframes hideLayer {
			0% {
				transform: translateY(0);
			}
			100% {
				transform: translateY(120%);
			}
		}
	}
	
	/* 底部操作菜单 */
	.page-bottom{
		position:fixed;
		left: 30upx;
		bottom:30upx;
		z-index: 95;
		display: flex;
		justify-content: center;
		align-items: center;
		width: 690upx;
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
			&:after{
				content: '';
				position:absolute;
				top: 50%;
				right: 50%;
				transform: translateY(-50%);
				height: 28upx;
				width: 0;
				border-right: 1px solid rgba(255,255,255,.5);
			}
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
</style>
