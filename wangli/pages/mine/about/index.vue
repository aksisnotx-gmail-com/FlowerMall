<template>
  <view class="about-container">
    <view class="header-section text-center">
      <image style="width: 150rpx;height: 150rpx;" src="/static/logo200.png" mode="widthFix">
      </image>
      <uni-title type="h2" title="旭侠客"></uni-title>
    </view>

    <view class="content-section">
      <view class="menu-list">
        <view class="list-cell">
          <view class="menu-item-box">
            <view>版本信息</view>
            <view class="text-right">v{{version}}</view>
          </view>
        </view>
        <view class="list-cell">
          <view class="menu-item-box">
            <view>官方邮箱</view>
            <view class="text-right">3562617983@qq.com</view>
          </view>
        </view>
        <view class="list-cell">
          <view class="menu-item-box">
            <view>服务热线</view>
            <view class="text-right">19150026052</view>
          </view>
        </view>
        <view class="list-cell">
          <view class="menu-item-box">
            <view>公司网站</view>
            <view class="text-right">{{url}}</view>
          </view>
        </view>
      </view>
    </view>

    <view class="copyright">
      <view>Copyright © 2023 By 成都旭侠客电子商务有限责任公司</view>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        url: "www.xuxiake666.com",
        version: "1.0.0"
      }
    }
  }
</script>

<style lang="scss">
  page {
    background-color: #f8f8f8;
  }

  .copyright {
    margin-top: 50rpx;
    text-align: center;
    line-height: 60rpx;
    color: #999;
  }

  .header-section {
    display: flex;
    padding: 30rpx 0 0;
    flex-direction: column;
    align-items: center;
  }
</style>
