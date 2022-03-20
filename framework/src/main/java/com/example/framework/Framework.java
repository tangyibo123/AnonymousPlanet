package com.example.framework;

/**
 * 单例模式：一个类在整个应用程序有且只有一个实例在运行
 * 特点：1. 私有构造函数，首先必须要先保证该类不能被实例化，否则会产生多个实例，也就违反了单例的原则
 *     2. 自行创建实例，提供一个方法给我们可以调用到这个唯一的实例，因为私有构造函数不能实例化，
 *        所以要提供一个入口给调用者能够获取到这个唯一的实例
 *
 * volatitle主要包含两个功能，一个是保证内存可见性，一个是禁止重排序（写操作在读操作之前）
 */
public class Framework {

    private volatile static Framework mFramework;

    private Framework(){}

    public static Framework getFramework(){
        if (mFramework == null) {
            synchronized (Framework.class) {
                if (mFramework == null) {
                    mFramework = new Framework();
                }
            }
        }
        return mFramework;
    }
}
