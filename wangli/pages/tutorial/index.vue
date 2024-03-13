
<script>
	export default {
		data() {
			return {
				content: '《叶圣陶散文》为“名家经典珍藏”丛书之一，收录了叶圣陶先生的散文精品数十篇。这些作品内容丰富，题材各异，构思精巧，文笔精巧、语言幽默、内蕴深厚、风格恬淡，充分显示了叶圣陶先生的文学功底及丰富的人生阅历，从一个侧面反映了作者的思想感情及创作风格，非常值得一读。叶圣陶是20世纪中国一位杰出的作家、教育家和出版家，又是中国现代儿童文学创作的先行者。作为散文家，他早期和周作人、朱自清共同成为文学研究会散文创作的中坚，后来又成为开明派散文的代表，其散文被一九三五年出版的《中国新文学大系》选录的篇数仅次于周作人、鲁迅和朱自清。'
			}
		}
	}
</script>

<template>
	<view class="tutorial">
		<view class="card">
		  <video src="@/static/videos/demo.mp4" controls></video>
		  <p>
			 fuck
		  </p>
		</view>
		<view class="card">
		  <video src="@/static/videos/demo.mp4" controls></video>
		  <p>
			 fuck
		  </p>
		</view>
		<view class="card">
		  <video src="@/static/videos/demo.mp4" controls></video>
		  <p>
			 fuck
		  </p>
		</view>
		<view class="card">
		  <video src="@/static/videos/demo.mp4" controls></video>
		  <p>
			 fuck
		  </p>
		</view>
		<view class="card">
		  <video src="@/static/videos/demo.mp4" controls></video>
		  <p>
			 fuck
		  </p>
		</view>
		<view class="card">
		  <video src="@/static/videos/demo.mp4" controls></video>
		  <p>{{content}}</p>
		</view>
	</view>
</template>

<style scoped>
	.tutorial {
		display: flex;
		gap: 100px;
		flex-wrap: wrap;
		justify-content: space-around;
	}
	
	.card {
		max-width: 342px;
		padding: 20px;
	  box-sizing: border-box;
	  background: rgba(217, 217, 217, 0.58);
	  border: 1px solid white;
	  box-shadow: 12px 17px 51px rgba(0, 0, 0, 0.22);
	  backdrop-filter: blur(6px);
	  border-radius: 17px;
	  text-align: center;
	  cursor: pointer;
	  transition: all 0.5s;
	  display: flex;
	  flex-direction: column;
	  align-items: center;
	  justify-content: center;
	  user-select: none;
	  font-weight: bolder;
	  color: black;
	}
	
	.card:hover {
	  border: 1px solid black;
	  transform: scale(1.05);
	}
	
	.card:active {
	  transform: scale(0.95) rotateZ(1.7deg);
	}
	
	.card video {
		border-radius: 20px;
	}
	
	.card p {
		margin-top: 20px;
	  /* 设置最大高度为300px */
	  max-height: 500px;
	  /* 隐藏超出的内容 */
	  overflow: hidden;
	  /* 显示省略号来表示被截断的文本，需要设置文本的装饰方式 */
	  display: -webkit-box;
	  /* -webkit-line-clamp: 13; /* 用来限制在一个块元素显示的文本的行数 */
	  /* -webkit-box-orient: vertical; */
	  text-overflow: ellipsis;
	  /* 需要设置这个才能使得省略号生效 */
	  white-space: normal;
	}
</style>