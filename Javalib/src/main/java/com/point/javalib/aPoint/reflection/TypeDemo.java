package com.point.javalib.aPoint.reflection;

import com.point.javalib.zPoint.Print;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by licong12 on 2019/1/7.
 *
 * java的泛型只存在于程序的源码中，在编译后的class文件中不存在，这个过程就是--泛型"擦除"；
 * 所以，对于new ArrayList<String> 和 new ArrayList<Integer> 来说，两个对象在编译之后两者是一样的
 *
 * but, 通过TypeReference接口，是可以获取到对象中的泛型的
 *
 * json -- new TypeReference<Map<String, StudentEntity>>(){}:匿名的内部类，而编译器在编译的时候，
 * 会将此匿名内部类单独生成一个class文件，命名规则如下：主类＋$＋(1,2,3....)，并且包含了泛型信息！！
 * 如： class Test$1 extends TypeReference<Map<String, StudentEntity>> {}
 * 构造函数调用：
 * getGenericSuperclass()返回的是此对象带“泛型”的父类，
 * getActualTypeArguments()返回的是此父类中实际类型参数的Type 对象数组，说白了就是TypeReference<>中的泛型
 *
 *
 * Type是Java 编程语言中所有类型的公共高级接口（官方解释），也就是Java中所有类型的“爹”
 * Type体系中类型的包括5个：
 * 参数化类型(ParameterizedType)、数组类型(GenericArrayType)、类型变量(TypeVariable)、WildcardType、基本类型(Class);
 * class：
 *      int,float,double，枚举、数组、注解等
 * ParameterizedType：表示一种参数化的类型，比如List、Map
 *      Type[] getActualTypeArguments()获得<>中实际类型，Map<K,V>，所以有多个
 *      Type getRawType()获得<>前面实际类型
 *      getOwnerType: 获取内部类的“父类"，内部类的“拥有者”
 * GenericArrayType：带有泛型的数组，即T[]
 *      getGenericComponentType：获得这个数组元素类型,返回泛型数组中元素的Type类型，即List<String>[] 中
 *                         的 List<String>（ParameterizedTypeImpl）、T[] 中的T（TypeVariableImpl);
 *                         无论几维数组，只会脱去最右边的[]，返回剩下的值
 * TypeVariable：泛型的类型变量，指的是List<T>、Map<K,V>中的T，K，V等值，实际的Java类型
 *               是TypeVariableImpl（TypeVariable的子类）；
 *               此外，还可以对类型变量加上extend限定，这样会有类型变量对应的上限
 *               在这需要强调的是，TypeVariable代表着泛型中的变量，而ParameterizedType则代表整个泛型
 *      getGenericDeclaration：获取声明该类型变量的实体（类、方法、构造器）
 * WildcardType：
 *      泛型通配符
 */

public class TypeDemo<T, K extends Number & Serializable & Comparable> {

    private List<T> list = null;
    private Set<T> set = null;
    private Map<String, Integer> map = null;
    private Map.Entry<String, Integer> mapEntry = null;

    private T[][] t;
    private List<String>[] listArray;

    private T t1;
    private K k1;

    private TypeDemo typeDemo;

    private List<? extends Number> listNum;
    private List<? super String> listStr;


    public static void main(String[] args) throws Exception {

        // Type是实际类型时，toString即可打印类名，如果getClass().getName()则为class
        // Type是虚类型时，如包含T，getClass().getName()可获取TypeVariable等类型名，toString输出T

        // ----- test ParameterizedType -----

        // test type
        Field listField = TypeDemo.class.getDeclaredField("list");
        // 获取泛型类型
        Type typeList = listField.getGenericType();
        // 下面输出：sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
        Print.println(typeList.getClass().getName()); // 对比！！！
        Print.println(typeList.toString()); // java.util.List<T>

        // 1. test getActualTypeArguments
        Field mapField = TypeDemo.class.getDeclaredField("map");
        ParameterizedType parameterizedType = (ParameterizedType) mapField.getGenericType();
        // 获取实际泛型参数
        Type[] types = parameterizedType.getActualTypeArguments();
        Print.println(types[0].toString()); // class java.lang.String
        Print.println(types[1].toString()); // class java.lang.Integer
        Print.println(types[0].getClass().getName()); // java.lang.Class // 对比！！！

        // 2. test getRawType
        Print.println(parameterizedType.getRawType().toString()); // interface java.util.Map

        // 3. test getOwnerType
        Field mapEntryField = TypeDemo.class.getDeclaredField("mapEntry");
        parameterizedType = (ParameterizedType) mapEntryField.getGenericType();
        Print.println(parameterizedType.getOwnerType().toString()); // interface java.util.Map


        // ----- test GenericArrayType -----
        Print.println("");
        // test type
        Field listArrayFiled = TypeDemo.class.getDeclaredField("listArray");
        Type listArrayType = listArrayFiled.getGenericType();
        // sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl
        Print.println(listArrayType.getClass().getName());

        // 1. test getGenericComponentType []
        GenericArrayType genericArrayType = (GenericArrayType) listArrayType;
        // java.util.List<java.lang.String>
        Print.println(genericArrayType.getGenericComponentType().toString());

        // 2. test getGenericComponentType [][]
        Field tArrayField = TypeDemo.class.getDeclaredField("t");
        Type tArrayType = tArrayField.getGenericType();
        // sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl
        Print.println(tArrayType.getClass().getName());
        genericArrayType = (GenericArrayType) tArrayType;
        Print.println(genericArrayType.getGenericComponentType().toString()); // T[]


        // ----- test TypeVariable -----
        Print.println("");
        // test type
        listField = TypeDemo.class.getDeclaredField("list");
        parameterizedType = (ParameterizedType) listField.getGenericType();
        // 获取泛型中的实际类型
        Type[] type = parameterizedType.getActualTypeArguments();
        // sun.reflect.generics.reflectiveObjects.TypeVariableImpl
        Print.println(type[0].getClass().getName());
        Print.println(type[0].toString()); // T // 对比！！！

        // 1. test getBounds
        // 获得该类型变量的上限，也就是泛型中extend右边的值；例如 List<T extends Number> ，Number就是类型
        // 变量T的上限；如果我们只是简单的声明了List<T>（无显式定义extends），那么默认为Object
        // t1
        Print.println("t1");
        Field t1Field = TypeDemo.class.getDeclaredField("t1");
        TypeVariable typeVariable = (TypeVariable) t1Field.getGenericType();
        Type[] types1 = typeVariable.getBounds();
        for (Type t : types1) { // class java.lang.Object
            Print.println(t.toString());
        }
        // k1
        Print.println("k1"); // 多类型中，& 后必须为接口
        Field k1Field = TypeDemo.class.getDeclaredField("k1");
        typeVariable = (TypeVariable) k1Field.getGenericType();
        types1 = typeVariable.getBounds();
        // class java.lang.Number
        // interface java.io.Serializable
        // interface java.lang.Comparable
        for (Type t : types1) {
            Print.println(t.toString());
        }

        // 2. test getGenericDeclaration
        // 获取声明该类型变量实体，也就是TypeDemo<T>中的TypeDemo
        Print.println("test getGenericDeclaration");
        listField = TypeDemo.class.getDeclaredField("list");
        parameterizedType = (ParameterizedType) listField.getGenericType();
        // 获取泛型中的实际类型
        type = parameterizedType.getActualTypeArguments();
        typeVariable = (TypeVariable) type[0];
        // class com.point.javalib.aPoint.reflection.TypeDemo
        Print.println(typeVariable.getGenericDeclaration().toString());

        // 3. test getName
        // 获取类型变量在源码中定义的名称
        t1Field = TypeDemo.class.getDeclaredField("t1");
        typeVariable = (TypeVariable) t1Field.getGenericType();
        Print.println(typeVariable.getName()); // T


        // ----- test Class ----- Type接口的实现类
        Print.println("");
        Field classField = TypeDemo.class.getDeclaredField("typeDemo");
        // class com.point.javalib.aPoint.reflection.TypeDemo
        Print.println(classField.getGenericType().toString());
        // 没有声明泛型的时候，我们普通的对象就是一个Class类型，是Type中的一种


        // ----- test WildcardType -----
        Print.println("");
        Field listNum = TypeDemo.class.getDeclaredField("listNum");
        parameterizedType = (ParameterizedType) listNum.getGenericType();
        // class sun.reflect.generics.reflectiveObjects.WildcardTypeImpl
        Print.println(parameterizedType.getActualTypeArguments()[0].getClass().toString());

        // 1. test getUpperBounds 获取泛型变量的上边界（extends）
        WildcardType wildcardType = (WildcardType) parameterizedType.getActualTypeArguments()[0];
        Print.println(wildcardType.getUpperBounds()[0].toString()); // class java.lang.Number
        Print.println(wildcardType.getUpperBounds()[0].getClass().toString()); // class java.lang.Class

        // 2. test getLowerBounds 获取泛型变量的下边界（super）
        Field listStr = TypeDemo.class.getDeclaredField("listStr");
        parameterizedType = (ParameterizedType) listStr.getGenericType();
        wildcardType = (WildcardType) parameterizedType.getActualTypeArguments()[0];
        Print.println(wildcardType.getLowerBounds()[0].toString()); // class java.lang.String



        // 补充：GenericDeclaration
        // 声明类型变量的所有实体的公共接口；也就是说该接口定义了哪些地方可以定义类型变量（泛型）
        // GenericDeclaration下有三个子类，分别为Class、Method、Constructor；
        // 也就是说，我们定义泛型只能在一个类中这3个地方自定义泛型
        // class上定义如当前类。


    }


}
