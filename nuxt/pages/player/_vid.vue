<template>
  <div>
    <link rel="stylesheet" href="https://g.alicdn.com/apsara-media-box/imp-web-player/2.18.1/skins/default/aliplayer-min.css" />
    <script type="text/javascript" charset="utf-8" src="https://g.alicdn.com/apsara-media-box/imp-web-player/2.18.1/aliplayer-min.js"></script>
    <div class="prism-player" id="player-con"></div>
  </div>
</template>
<script type="text/javascript" charset="utf-8" src="https://g.alicdn.com/apsara-media-box/imp-web-player/2.18.1/aliplayer-min.js" />
<script>
import vod from '@/api/vod'
export default {
  layout: 'video',
  asyncData({ params, error }) {
    return vod.getPlayAuth(params.vid).then(response => {
      console.log(response.data.data)
      return {
        vid: params.vid,
        playAuth: response.data.data.playAuth
      }
    })
  },
  mounted() {
    new Aliplayer({
      id: "player-con",
      vid: this.vid, // 视频id
      playauth: this.playAuth, // 播放凭证
      encryptType:'1',
      width: '100%',
      height: '500px',
      autoplay: true,
      isLive: false,
      rePlay: false,
      playsinline: true,
      preload: true,
      controlBarVisibility: "hover",
      useH5Prism: true
    }, function (player) {
      console.log("The player is created");
    })
  }
}
</script>
