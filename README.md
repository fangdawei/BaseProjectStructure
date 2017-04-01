# Android项目基础结构
基于MVP模式，使用了Dagger2和DataBinding。下面是项目的java代码的目录结构

![image](http://on08mbjyn.bkt.clouddn.com/Snip20170331_4.png)

根据包名应该就能清楚每个目录下面的代码的功能。其中，di下面是一些使用Dagger2实现依赖注入的代码；mvp下面是构成MVP模式的代码，分为contract、model和presenter，而V由于在项目中占有比较重要的位置，单独放在了ui这个包下面。

这里，对Activity以及Fragment进行了抽象和封装，

![image](http://on08mbjyn.bkt.clouddn.com/Snip20170331_5.png)

IActivity以接口的方式抽象了Activity初始化的一些通用的方法(可能还可以加入一些其他的方法，根据需要添加即可)

``` java
public interface IActivity {
  void initView();
  void initListener();
  void initData();
}
```
BaseActivity是项目中几乎所有的Activity的父类，它的功能主要是封装了ActionBar，使得继承自它的Activity可以很方便地创建包含或者不包含ActionBar的Activity(这里ActionBar是使用ToolBar实现的)。同时为了适应Android6.0之后的动态权限，封装了权限的动态申请功能。这里有一个例外，就是SplashActivity，它并没有继承BaseActivity，而是直接继承自AppCompatActivity。

```java
public abstract class BaseActivity
    extends AppCompatActivity implements IActivity {

  private ActivityBaseBinding baseVDB;
  private Map<Integer, PermissionListener> permissionListenerMap = new HashMap<>();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /** 开始加载布局 **/
    baseVDB = DataBindingUtil.setContentView(this, R.layout.activity_base);
    View toolBarView = createToolBar(savedInstanceState);
    if (toolBarView != null) {
      baseVDB.contentRoot.addView(toolBarView,
          ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    View contentView = createContentView(savedInstanceState);
    if (contentView != null) {
      baseVDB.contentRoot.addView(contentView,
          ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    /** 加载布局结束 **/
    preInit(savedInstanceState);
    initView();
    initListener();
    initData();
  }

  protected void preInit(Bundle savedInstanceState) {

  }

  protected void requestPermission(String permisson,
      PermissionListener listener, int requestCode) {
    if (PackageManager.PERMISSION_GRANTED ==
        ContextCompat.checkSelfPermission(this, permisson)) {
      if (listener != null) {
        listener.onGranted();
      }
    } else {
      boolean hasDenited = ActivityCompat
          .shouldShowRequestPermissionRationale(this, permisson);
      if (!hasDenited) {
        permissionListenerMap.put(requestCode, listener);
        ActivityCompat.requestPermissions(this,
            new String[] { permisson }, requestCode);
      } else {
        if (listener != null) {
          listener.onNotAsk();
        }
      }
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode,
      @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    int result = grantResults[0];
    PermissionListener listener = permissionListenerMap.get(requestCode);
    if (PackageManager.PERMISSION_GRANTED == result) {
      if (listener != null) {
        listener.onGranted();
      }
    } else {
      if (listener != null) {
        listener.onDenited();
      }
    }
  }

  protected abstract View createToolBar(Bundle savedInstanceState);

  protected abstract View createContentView(Bundle savedInstanceState);

  interface PermissionListener {
    void onGranted();//权限请求被允许
    void onDenited();//权限请求被拒绝
    void onNotAsk();//不再请求
  }
}
```
BaseBindingActivity对DataBing进行了一些封装，提供的最大便利就是再也不用大量地使用findViewById来获取View对象了。

```java
public abstract class BaseBindingActivity<T extends ViewDataBinding>
    extends BaseActivity {

  protected T mVDB;

  protected abstract T createViewDataBinding(Bundle savedInstanceState);

  @Override protected void preInit(Bundle savedInstanceState) {

  }

  @Override protected View createToolBar(Bundle savedInstanceState) {
    return null;
  }

  @Override protected View createContentView(Bundle savedInstanceState) {
    mVDB = createViewDataBinding(savedInstanceState);
    if (mVDB == null) {
      return null;
    } else {
      return mVDB.getRoot();
    }
  }

  @Override protected void onDestroy() {
    if (mVDB != null) {
      mVDB.unbind();
    }

    super.onDestroy();
  }
}
```
对Fragment的封装与Activity的类似。

