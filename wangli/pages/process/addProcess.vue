<template>
	<view class="container">
		<view class="c-list">
			<view class="c-row b-b">
				<text class="tit">区域</text>
				<view class="con">
					<pickerAddress @change="change">
						<text class="tit">{{process.areaText}}</text>
					</pickerAddress>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">类型</text>
				<view class="con">
					<pickerProductType @change="changeType">
						<text class="tit">{{process.typeText}}</text>
					</pickerProductType>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">流程头像</text>
				<view class="con">
					<uni-file-picker limit="1" ref="tagFile" file-extname="png,jpg,jpeg" :auto-upload="false" @select="selectTagSuccess"></uni-file-picker>
				</view>
			</view>
			<view class="c-row b-b">
				<view class="tit">名称</view>
				<view class="con">
					<uni-easyinput v-model="process.name" placeholder="请输入流程名称" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">描述</text>
				<view class="con">
					<uni-easyinput v-model="process.description" type="textarea" placeholder="请输入该流程的描述" />
				</view>
			</view>
		</view>
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goBack" >返回</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goNext">下一步</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data() {
			return {
				process: {
					name: "",
					description: "",
					province: "",
					city: "",
					county: "",
					url: "",
					areaText: "请选择",
					typeText: "请选择",
					backgroundColor: "#ffffff",
					tagColor:"",
					contentColor:"",
					background: ""
				},
				tagImg: undefined
			};
		},
		methods:{
			selectTagSuccess(e){
				if(e.tempFiles.length > 0){
					this.tagImg = e.tempFiles[0];
				}
			},
			async goNext(){
				uni.navigateTo({
					url: '/pages/process/addProcessDetail'
				})
			},
			change(data) {
				let dataTmp = [];
				if(data.index[0] !=0){
					dataTmp.push(data.data[0]);
					this.process.province = data.index[0];
				}else{
					this.process.province = '';
				}
				if(data.index[1] !=0){
					dataTmp.push(data.data[1]);
					this.process.city = data.index[1];
				}else{
					this.process.city = '';
				}
				if(data.index[2] !=0){
					dataTmp.push(data.data[2]);
					this.process.county = data.index[2];
				}else{
					this.process.county = '';
				}
				if(dataTmp.length == 0){
					dataTmp.push("全国");
				}
				this.txt = dataTmp.join('---');
				this.process.areaText = this.txt;
			},
			changeType(data) {
				let dataTmp = [];
				if(data.index[0] !=0){
					dataTmp.push(data.data[0]);
					this.process.firstLevel = data.index[0];
				}else{
					this.process.firstLevel = '';
				}
				if(data.index[1] !=0){
					dataTmp.push(data.data[1]);
					this.process.secondLevel = data.index[1];
				}else{
					this.process.secondLevel = '';
				}
				if(dataTmp.length == 0){
					dataTmp.push("请选择");
				}
				this.process.typeText = dataTmp.join('-->')
				console.log(this.process)
			},
			goBack(){
				uni.navigateBack();
			}
		},
	}
</script>

<style lang='scss'>
	page{
		background: $page-color-base;
		padding-bottom: 160upx;
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
			.desc-text{
				color:$uni-color-primary;
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
