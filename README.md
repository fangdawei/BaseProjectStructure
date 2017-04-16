# Android项目基础结构
基于MVP模式，使用了Dagger2和DataBinding。下面是项目的java代码的目录结构

![image](http://on08mbjyn.bkt.clouddn.com/Snip20170331_4.png)

根据包名应该就能清楚每个目录下面的代码的功能。其中，di下面是一些使用Dagger2实现依赖注入的代码；mvp下面是构成MVP模式的代码，分为contract、model和presenter，而V由于在项目中占有比较重要的位置，单独放在了ui这个包下面。

这里，对Activity以及Fragment进行了抽象和封装，

![image](http://on08mbjyn.bkt.clouddn.com/Snip20170331_5.png)

IActivity以接口的方式抽象了Activity初始化的一些通用的方法(可能还可以加入一些其他的方法，根据需要添加即可)

``` java
public abstract class BaseActivity extends AppCompatActivity
    implements IActivity {

  private ActivityBaseBinding baseVDB;
  private Map<Integer, PermissionListener> permissionListenerMap =
      new HashMap<>();
  private AtomicInteger requestCodeNumber = new AtomicInteger(0);

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /** 开始加载布局 **/
    baseVDB = DataBindingUtil.setContentView(this, R.layout.activity_base);
    View toolBarView = createToolBar(savedInstanceState,
        baseVDB.toolbarContainer);
    if (toolBarView != null) {
      baseVDB.toolbarContainer.addView(toolBarView);
    }
    View contentView = createContentView(savedInstanceState,
        baseVDB.contentContainer);
    if (contentView != null) {
      baseVDB.contentContainer.addView(contentView);
    }
    /** 加载布局结束 **/
    preInit(savedInstanceState);
    initView();
    initListener();
    initData();
  }

  /**
   * 在执行init(initView、initListener、initData)之前执行
   */
  protected void preInit(Bundle savedInstanceState) {

  }

  /**
   * 创建ToolBar的View
   */
  protected abstract View createToolBar(Bundle savedInstanceState,
      ViewGroup container);

  /**
   * 创建页面主要内容View
   */
  protected abstract View createContentView(Bundle savedInstanceState,
      ViewGroup container);

  public void requestPermission(String permisson, PermissionListener listener) {
    if (PackageManager.PERMISSION_GRANTED ==
        ContextCompat.checkSelfPermission(this, permisson)) {
      if (listener != null) {
        listener.onGranted();
      }
    } else {
      boolean hasDenited =
          ActivityCompat.shouldShowRequestPermissionRationale(this, permisson);
      if (!hasDenited) {
        int requestCode = requestCodeNumber.getAndIncrement();
        permissionListenerMap.put(requestCode, listener);
        ActivityCompat.requestPermissions(this, new String[] { permisson }, 
            requestCode);
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
    permissionListenerMap.remove(requestCode);
  }

  /**
   * 权限申请结果监听器
   */
  public interface PermissionListener {
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

  protected abstract T createViewDataBinding(Bundle savedInstanceState,
      ViewGroup container);

  @Override protected void preInit(Bundle savedInstanceState) {

  }

  @Override protected View createToolBar(Bundle savedInstanceState,
      ViewGroup container) {
    return null;
  }

  @Override protected View createContentView(Bundle savedInstanceState,
      ViewGroup container) {
    mVDB = createViewDataBinding(savedInstanceState, container);
    if(mVDB == null){
      return  null;
    } else {
      return mVDB.getRoot();
    }
  }

  @Override protected void onDestroy() {
    if(mVDB != null){
      mVDB.unbind();
    }

    super.onDestroy();
  }
}
```
对Fragment的封装与Activity的类似。

