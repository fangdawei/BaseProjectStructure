# Android项目基础结构
基于MVP模式，使用了Dagger2和DataBinding。下面是项目的java代码的目录结构

![image](http://on08mbjyn.bkt.clouddn.com/Jietu20171128-201147.jpg)

根据包名应该就能清楚每个目录下面的代码的功能。其中，di下面是一些使用Dagger2实现依赖注入的代码；mvp下面是构成MVP模式的代码，分为contract、model和presenter，而V由于在项目中占有比较重要的位置，单独放在了ui这个包下面。

这里，对Activity以及Fragment进行了抽象和封装

![image](http://on08mbjyn.bkt.clouddn.com/Jietu20171128-201209.jpg)

IActivity以接口的方式抽象了Activity初始化的一些通用的方法(可能还可以加入一些其他的方法，根据需要添加即可)
BaseBindingActivity对DataBing进行了一些封装，提供的最大便利就是再也不用大量地使findViewById来获取View对象了。
对Fragment的封装与Activity的类似。

