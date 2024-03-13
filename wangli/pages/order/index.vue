
<script>
</script>

<template>
	<view>
		<view class="order_main">
			<image src="../../static/hot/1.jpg" mode="aspectFit"></image>
			<view class="order_conetent">
				<view class="order_info">
					<view>
						<text>数量: 1间</text>
						<text style="margin-left: 24px;">大床房</text>
					</view>
					<view>
						<text>下单时间: </text>
						<text style="margin-left: 24px;">2024/03/11</text>
					</view>
					<view>
						<text>总价: </text>
						<text class="order_price">¥ 100</text>
					</view>
				</view>
				<text style="margin-left: 240; font-size: 24px; color: #ccc;">已付款</text>
			</view>
		</view>
	</view>
</template>

<style scoped>
	
	.order_main {
		display: flex;
	}
	
	.order_conetent {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-left: 40px;
		width: 400px;
	}
	
	.order_info {
		display: flex; 
		flex-direction: column; 
		gap: 30px; 
		width: 300px;
	}
	
	.order_price {
		font-weight: 700;
		font-size: 20px;
		color: red;
		margin-left: 20px;
	}
</style>