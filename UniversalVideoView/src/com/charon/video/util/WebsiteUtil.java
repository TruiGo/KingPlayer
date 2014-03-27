
package com.charon.video.util;

import com.charon.video.R;
import com.charon.video.domain.WebSite;

import java.util.List;

public class WebsiteUtil {
    public static List<WebSite> addWebsite(List<WebSite> webSites) {
        if (webSites == null) {
            return null;
        }
        webSites.clear();
        webSites.add(new WebSite("搜狐视频",
                "http://m.tv.sohu.com", R.drawable.logo_sohu));
        webSites.add(new WebSite("优酷视频",
                "http://3g.youku.com", R.drawable.logo_youku));
        webSites.add(new WebSite("乐视TV",
                "http://m.letv.com", R.drawable.logo_letv));
        webSites.add(new WebSite("腾讯视频",
                "http://3g.v.qq.com/", R.drawable.logo_qq));
        webSites.add(new WebSite("爱奇艺",
                "http://m.iqiyi.com", R.drawable.logo_iqiyi));
        webSites.add(new WebSite("PPTV",
                "http://m.pptv.com/", R.drawable.logo_pptv));
        webSites.add(new WebSite("新浪视频",
                "http://video.sina.cn/", R.drawable.logo_sina));
        webSites.add(new WebSite("百度视频",
                "http://m.baidu.com/video", R.drawable.logo_baidu));
        webSites.add(new WebSite("土豆视频",
                "http://m.tudou.com", R.drawable.logo_tudou));
        webSites.add(new WebSite("56.com",
                "http://m.56.com/", R.drawable.logo_56));
        webSites.add(new WebSite("人人影视",
                "http://m.yyets.com", R.drawable.logo_renren));
        webSites.add(new WebSite("酷6",
                "http://m.ku6.com", R.drawable.logo_ku6));
        webSites.add(new WebSite("PPS",
                "http://m.pps.tv", R.drawable.logo_pps));
        webSites.add(new WebSite("豆瓣电影",
                "http://movie.douban.com", R.drawable.logo_douban));
        webSites.add(new WebSite("风云直播",
                "http://m.fengyunzhibo.com", R.drawable.logo_fengyun));
        webSites.add(new WebSite("凤凰视频",
                "http://v.ifeng.com", R.drawable.logo_ifeng));
        return webSites;
    }

    public static List<WebSite> addLivesite(List<WebSite> webSites) {
        if (webSites == null) {
            return null;
        }
        webSites.add(new WebSite(
                "韩KBS高清",
                "http://news24kbs-2.gscdn.com/news24_300/_definst_/news24_300.stream/playlist.m3u8",
                R.drawable.logo_kbstv));

        webSites.add(new WebSite("韩WOWNet",
                "http://wowmlive.shinbnstar.com:1935/live2/wowtv/playlist.m3u8",
                R.drawable.logo_wownettv));
        return webSites;
    }

}
