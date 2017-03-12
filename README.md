
## 实现功能
* 使用AIDL服务实现：

    * 音乐播放、暂停、上一曲、下一曲、歌词功能
    * 音乐播放模式：顺序播放 、随机播放、单曲播放 

* 多个页面调用AIDL服务
    * 主页面底部音乐迷你控制器
    * 音乐详情页面
    * 程序后台启动的Notifycation播放器

* 使用到的库
    * xRecyclerView
    * retrofit2
    * glide
    * okhttp3
## 实现步骤
* 使用retrofit完成音乐API的封装
	http://blog.csdn.net/zuiaisha1/article/details/61202252
* 编写核心AIDL服务， 实现服务核心功能
    * 音乐播放、暂停、上一曲、下一曲
    * 音乐播放模式：顺序播放 、随机播放、单曲播放 
* 实现前台页面
    * 主页面和主页面底部音乐迷你控制器
    * 程序后台启动的Notifycation播放器
    * 音乐详情页面并加入歌词功能
    
## 效果图
    
   ![这里写图片描述](http://img.blog.csdn.net/20170310171821364?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvenVpYWlzaGEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
   
   ![这里写图片描述](http://img.blog.csdn.net/20170310171840817?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvenVpYWlzaGEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![这里写图片描述](http://img.blog.csdn.net/20170310171926233?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvenVpYWlzaGEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
	
   ![这里写图片描述](http://img.blog.csdn.net/20170310171936740?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvenVpYWlzaGEx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



## 博客地址
http://blog.csdn.net/zuiaisha1/article/details/61201146
