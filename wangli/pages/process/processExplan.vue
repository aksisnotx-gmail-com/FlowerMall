<template>
	<view class="container">
			<view class="c-list">
				<view class="c-row b-b">
					<text class="con" style="color: #00ff00;">1，流程用以体现您想展示的所有工作阶段</text>
				</view>
				<view class="c-row b-b">
					<text class="con" style="color: #a2c3ff;">2，流程中的每个阶段都可以关联已创建完成的任务</text>
				</view>
				<view class="c-row b-b">
					<text class="con" style="color: #ff55ff;">3，一个完整的流程，需以“开始”起始，以“结束”结尾。此两流程已默认创建。您只需要在这两个流程中新增你自己的工作阶段</text>
				</view>
				<view class="c-row b-b">
					<text class="con" style="color: #ff6b08;">4，为了更好体现您的流程设计，您可以在“开始”流程中列出您整个流程的简介，在“结束”流程中添加一些结尾语</text>
				</view>
				<view class="c-row b-b">
					<text class="con" style="color: #ff0b2f;">5，每个流程都可以添加“文本”，“附件”和“需求”。您可以通过“预览”功能查看您的流程设计是否满意</text>
				</view>
				<view class="c-row b-b">
					<text class="con" style="color: #aa00ff;">6，流程创建完成之后，可以通过流程维护功能对流程进行“任务关联”，“工作阶段”删除等操作</text>
				</view>
			</view>
		
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goBack">返回</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goAddProcess">开始</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		methods:{
			goAddProcess(){
				uni.navigateTo({
					url: `/pages/process/addProcess`
				})
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
