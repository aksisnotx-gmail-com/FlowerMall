import UniShare from 'uni_modules/uni-share/js_sdk/uni-share.js';
const uniShare = new UniShare();
import config from '@/config'
const uniUrl = config.uniUrl
const miniProgramId = config.miniProgramId

export function shareInfo(item,shareData) {
	console.log("share-item:",item)
	console.log("share-shareData:",shareData)
	console.log("share-miniProgramId:",miniProgramId)
	uniShare.show({
		content: { //公共的分享参数配置  类型（type）、链接（herf）、标题（title）、summary（描述）、imageUrl（缩略图）
			type: 0,
			href: shareData.webUrl,
			title: item.name,
			summary: item.description,
			imageUrl: item.tagUrl
		},
		menus: [{
			"img": "/static/share/wechatfriend.png",
			"text": "微信好友",
			"share": { //当前项的分享参数配置。可覆盖公共的配置如下：分享到微信小程序，配置了type=5
					"provider": "weixin",
					"scene": "WXSceneSession"
				}
			},
			{
			"img": "/static/share/wechatmoments.png",
			"text": "微信朋友圈",
			"share": {
					"provider": "weixin",
					"scene": "WXSceneTimeline"
				}
			},
	        {
	            "img": "/static/share/miniProgram.png",
	            "text": "微信小程序",
	            "share": {
	                provider: "weixin",
	                scene: "WXSceneSession",
	                type: 5,
	                miniProgram: {
	                    id: miniProgramId,
	                    path: shareData.miniPath,
	                    webUrl: shareData.webUrl,
	                    type: 0
	                },
	            }
	        }],
			cancelText: "取消分享",
		}, e => { //callback
			console.log(uniShare.isShow);
			console.log(e);
		})			
}

export function hidenShare() {
  uniShare.hide()
}
