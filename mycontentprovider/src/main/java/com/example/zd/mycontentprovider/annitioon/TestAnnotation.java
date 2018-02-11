package com.example.zd.mycontentprovider.annitioon;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * use to do test Annotation
 *
 * @author zhangdong on 2018/2/8 0008.
 * @version 1.0
 *          ----------------------------------------------------------------------------------------
 * @Retention注解: 指定注解的作用时间，类似生命周期
 * RetentionPolicy.SOURCE           编译器会在编译时舍弃，eg：@Override注解，仅仅检查格式
 * RetentionPolicy.CLASS            自定义注解的默认值,编译器会把这种策略的注释保存在class文件中，运行时丢弃。
 * RetentionPolicy.RUNTIME          运行时注解，编译器会把该策略的注释保存到class文件中,程序可以通过反射等方式来获取
 * <p>
 * -------------------------------------------------------------------------------------------------
 * @Target注解: 修饰一个自定义的Annotation注解, 用于指定自定义注解可以修饰哪些程序元素。
 * ElementType.PACKAGE              注解作用于包
 * ElementType.TYPE                 注解作用于类型（类，接口，注解，枚举）
 * ElementType.ANNOTATION_TYPE      注解作用于注解
 * ElementType.CONSTRUCTOR          注解作用于构造方法
 * ElementType.METHOD               注解作用于方法
 * ElementType.PARAMETER            注解作用于方法参数
 * ElementType.FIELD                注解作用于属性
 * ElementType.LOCAL_VARIABLE       注解作用于局部变量
 * <p>
 * -------------------------------------------------------------------------------------------------
 * @Documented注解： 该注解修饰的自定义注解可以使用javac命令提取成API文档。
 * @Inherited注解： 该注解修饰的自定义具有继承性。eg:父类使用了@Inherited修饰的自定义注解,则子类也具有该自定义注解描述的特性。
 */

@Documented                                             //可用javac命令生成文档
@Retention(RetentionPolicy.RUNTIME)                      //指定注解的作用域
@Target({ElementType.PARAMETER,
        ElementType.METHOD,
        ElementType.FIELD})                             //该注解可修饰的目标类型（参数，方法,属性）
public @interface TestAnnotation {
    int tag() default 0;

//    String name() default "this is default name";
//
//    int value() default 110;
}
