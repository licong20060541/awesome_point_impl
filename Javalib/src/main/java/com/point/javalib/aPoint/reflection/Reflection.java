package com.point.javalib.aPoint.reflection;

import com.point.javalib.zPoint.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 参考：https://www.jianshu.com/p/607ff4e79a13
 * <p>
 * class关键字: 声明java类<br/>
 * Class：JDK提供的一个类 java.lang.Class<br/>
 * public final class Class<T> implements<br/>
 * 对于某个类，无论创建多少个实例对象，在JVM中都对应同一个Class对象
 * eg: 如下类Reflection，JVM会创建一个对应的Class类的Class实例，
 * 该实例保存了Reflection类的类型信息，包括属性，方法，构造方法等，
 * 通过这个Class实例可以在运行时访问Reflection对象的属性和方法等。
 * 反射不支持自动封箱，传入参数时要小心（自动封箱是在编译期间的，而反射在运行期间）
 */
public class Reflection {

    public static void main(String[] args) {
        Utils.print("-------begin-------\n");

//        new Reflection().testReflection();
//        new Reflection().getClassMethods();
//        new Reflection().testDemo();
//        new Reflection().testField();
//        new Reflection().testFieldDemo();
        new Reflection().testMethod();


        Utils.newLine();
        Utils.print("-------end-------");
    }

    /********************** 反射创建对象 ************************/

    private void testReflection() {
        try {
            Class c = Animal.class;
            Constructor constructor = c.getConstructor(String.class, int.class);
            Animal animal = (Animal) constructor.newInstance("hello", 22);
            Utils.print(animal.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /********************** 获取class ************************/

    enum E {
        A, B
    }

    private void getClassMethods() {

        // 1.
        // Returns the Class for String
        Class c = "foo".getClass();
        Utils.print(c.toString()); // class java.lang.String

        // Returns the Class corresponding to the enumeration type E.
        c = E.A.getClass();
        Utils.print(c.toString()); // class com.point.javalib.aPoint.reflection.Reflection$E

        // Returns the Class corresponding to an array with component type byte.
        byte[] bytes = new byte[1024];
        c = bytes.getClass();
        Utils.print(c.toString()); // class [B

        // Returns the Class corresponding to java.util.HashSet.
        Set<String> set = new HashSet<>();
        set.add("aa");
        c = set.getClass();
        Utils.print(c.toString()); // class java.util.HashSet


        // 2.
        // !!! 基本数据类型不可getClass
//        boolean b;
//        Class c = b.getClass();   // compile-time error

        //The `.class` syntax returns the Class corresponding to the type `boolean`.
        c = boolean.class;
        Utils.print(c.toString()); // boolean

        //Returns the Class for String
        c = String.class;
        Utils.print(c.toString()); // class java.lang.String


        // 3.
        try {
            c = Class.forName("java.lang.String");
            Utils.print(c.toString()); // class java.lang.String

            c = Class.forName("[D");    // 相当于double[].class -- smali
            Utils.print(c.toString()); // class [D

            c = Class.forName("[[Ljava.lang.String;");   // 相当于String[][].class
            Utils.print(c.toString()); // class [[Ljava.lang.String;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        // 4. 基本类型和void 类型的包装类可以使用TYPE字段获取
        c = Double.TYPE;   //等价于 double.class.
        Utils.print(c.toString()); // double

        c = Void.TYPE;
        Utils.print(c.toString()); // void


        // 5.
//        Class.getSuperclass() // 给定类的父类Class
//        Class.getClasses()
//        Class.getDeclaredClasses()
//        Class.getDeclaringClass()
//        Class.getEnclosingClass()
//        java.lang.reflect.Field.getDeclaringClass()
//        java.lang.reflect.Method.getDeclaringClass()
//        java.lang.reflect.Constructor.getDeclaringClass()

    }

    /********************** demo ************************/

    private void testDemo() {
        Class<?> c = HashMap.class;

        Utils.print("获取类名：Class : " + c.getCanonicalName());

        Utils.print("获取类限定符 Modifiers : " + Modifier.toString(c.getModifiers()));

        // 获取类泛型信息
        TypeVariable[] tv = c.getTypeParameters();
        if (tv.length != 0) {
            StringBuilder sb = new StringBuilder("泛型信息：");
            for (TypeVariable t : tv) {
                sb.append(t.getName()).append(", ");
            }
            Utils.print(sb.toString());
        } else {
            Utils.print("无泛型信息");
        }

        // 获取类实现的所有接口
        Type[] types = c.getGenericInterfaces();
        if (types.length != 0) {
            StringBuilder sb = new StringBuilder("接口信息：");
            for (Type type : types) {
                sb.append(type.toString()).append(", ");
            }
            Utils.print(sb.toString());
        } else {
            Utils.print("无接口信息");
        }

        // 获取类继承数上的所有父类
        List<Class> l = new ArrayList<>();
        printAncestor(c, l);
        if (l.size() != 0) {
            StringBuilder sb = new StringBuilder("父类信息：");
            for (Class ancestor : l) {
                sb.append(ancestor.getCanonicalName()).append(", ");
            }
            Utils.print(sb.toString());
        } else {
            Utils.print("无父类");
        }

        // 获取类的注解(只能获取到 RUNTIME 类型的注解)
        Annotation[] annotations = c.getAnnotations();
        if (annotations.length != 0) {
            StringBuilder sb = new StringBuilder("注解信息：");
            for (Annotation a : annotations) {
                sb.append(a.toString()).append(", ");
            }
            Utils.print(sb.toString());
        } else {
            Utils.print("无注解");
        }

//        licong12: 获取类名：Class : java.util.HashMap
//        licong12: 获取类限定符 Modifiers : public
//        licong12: 泛型信息：K, V,
//        licong12: 接口信息：java.util.Map<K, V>, interface java.lang.Cloneable, interface java.io.Serializable,
//        licong12: 父类信息：java.util.AbstractMap, java.lang.Object,
//        licong12: 无注解


    }

    private void printAncestor(Class<?> c, List<Class> l) {
        Class<?> ancestor = c.getSuperclass();
        if (ancestor != null) {
            l.add(ancestor);
            printAncestor(ancestor, l);
        }
    }


    /**************************** Member接口 ************************

     3个实现类
     java.lang.reflect.Field ：对应类变量
     java.lang.reflect.Method ：对应类方法
     java.lang.reflect.Constructor ：对应类构造函数

     ************************************************************/

    /**************************** Field ******************************

     Class提供了4种方法获得给定类的Field:
     getDeclaredField(String name): 获取指定的变量（只要是声明的变量都能获得，包括private）
     getField(String name): 获取指定的变量（只能获得public的）
     getDeclaredFields(): 获取所有声明的变量（包括private）
     getFields(): 获取所有的public变量


     ************************************************************/

    private void testField() {

        Class c = Cat.class;
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {

            // 获取名称
            Utils.print("名称: " + field.getName());

            // 获取类型
            Utils.print("类型: " + field.getType().toString());

            // 获取修饰符
            Utils.print("修饰符: " + Modifier.toString(field.getModifiers()));

            // 获取注解
            Annotation[] annotations = field.getAnnotations();
            if (annotations.length != 0) {
                StringBuilder sb = new StringBuilder("注解信息：");
                for (Annotation a : annotations) {
                    sb.append(a.toString()).append(", ");
                }
                Utils.print(sb.toString());
            } else {
                Utils.print("无注解");
            }

        }

    }

//
//    licong12: 名称: name
//    licong12: 类型: class java.lang.String
//    licong12: 修饰符: private
//    licong12: 无注解

//    licong12: 名称: age
//    licong12: 类型: int
//    licong12: 修饰符: public
//    licong12: 注解信息：@java.lang.Deprecated(),


    private void testFieldDemo() {
        Cat cat = new Cat("Tom", 2);
        Class c = cat.getClass();
        try {
            Field nameField = c.getDeclaredField("name");
            Field ageField = c.getField("age");
            nameField.setAccessible(true); // !!!
            String name = (String) nameField.get(cat);
            int age = ageField.getInt(cat);
            Utils.print("name: " + name + ", age: " + age);

            nameField.set(cat, "aa");
            ageField.set(cat, 100);
            Utils.print(cat.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**************************** Method ******************************

     1. Class依然提供了4种方法获取Method:

     getDeclaredMethod(String name, Class<?>... parameterTypes)
     根据方法名获得指定的方法， 参数name为方法名，参数parameterTypes为方法的参数类型;
     对于参数是泛型的情况，泛型须当成Object处理（Object.class）

     getMethod(String name, Class<?>... parameterTypes):根据方法名获取指定的public方法

     getDeclaredMethods() : 获取所有声明的方法

     getMethods() : 获取所有的public方法

     1.1 方法返回类型: 反射中所有形如getGenericXXX()的方法规则都类似
     getReturnType() 获取目标方法返回类型对应的Class对象
     getGenericReturnType() 获取目标方法返回类型对应的Type对象
     区别：
     a) getReturnType()返回类型为Class，getGenericReturnType()返回类型为Type; Class实现Type。
     b) 返回值为普通简单类型如Object, int, String等，getGenericReturnType()和getReturnType()的返回值一样
     例如 public String function1() 均返回 class java.lang.String
     c) 泛型: 例如public T function2() 那么各自返回值为：
     getReturnType() : class java.lang.Object
     getGenericReturnType() : T
     d) 返回值为参数化类型 例如public Class<String> function3(), 那么各自返回值为：
     getReturnType() : class java.lang.Class
     getGenericReturnType() : java.lang.Class<java.lang.String>


     1.2 获取方法参数类型
     getParameterTypes() 获取目标方法各参数类型对应的Class对象
     getGenericParameterTypes() 获取目标方法各参数类型对应的Type对象
     返回值为数组，它俩区别同上 “方法返回类型的区别” 。

     1.3 获取方法声明抛出的异常的类型
     getExceptionTypes() 获取目标方法抛出的异常类型对应的Class对象
     getGenericExceptionTypes() 获取目标方法抛出的异常类型对应的Type对象
     返回值为数组，区别同上

     1.4 获取方法参数名称
     .class文件中默认不存储方法参数名称，如果想要获取方法参数名称，需要在编译的时候加上-parameters参数。
     (构造方法的参数获取方法同样)

     1.5
     method.getModifiers(); // 方法修饰符

     method.isVarArgs() // 判断方法参数是否是可变参数
     public Constructor<T> getConstructor(Class<?>... parameterTypes)  //返回true
     public Constructor<T> getConstructor(Class<?> [] parameterTypes)  //返回false

     method.isSynthetic()
     // 判断是否是复合方法，个人理解复合方法是编译期间编译器生成的方法，并不是源代码中有的方法

     method.isBridge()
     // 判断是否是桥接方法，桥接方法是 JDK 1.5 引入泛型后，为了使Java的泛型方法生成的字节码和 1.5 版本前的
     // 字节码相兼容，由编译器自动生成的方法。可以参考https://www.cnblogs.com/zsg88/p/7588929.html

     1.6
     invoke(Object obj, Object... args)
     方法为static的话，第一个参数为null


     ************************************************************/

    // 这里的m可以是普通方法Method，也可以是构造方法Constructor
    private void showParameters(Method m) {
        //获取方法所有参数
        Parameter[] params = m.getParameters();
        for (int i = 0; i < params.length; i++) {
            Parameter p = params[i];
            p.getType();  // 获取参数类型
            p.getName();  // 获取参数名称，如果编译时未加上`-parameters`，返回的名称形如`argX`, X为参数在方法声明中的位置，从0开始
            p.getModifiers(); // 获取参数修饰符
            p.isNamePresent();  // .class文件中是否保存参数名称, 编译时加上`-parameters`返回true,反之false
        }
    }

    /**
     * 被调用的方法本身所抛出的异常在反射中都会以InvocationTargetException抛出。
     * 换句话说，反射调用过程中如果异常InvocationTargetException抛出，
     * 说明反射调用本身是成功的，因为这个异常是目标方法本身所抛出的异常
     */
    private void testMethod() {
        Class<?> c = Cat.class;
        try {
            Constructor constructor = c.getConstructor(String.class, int.class);
            Object cat = constructor.newInstance( "Jack", 3);

            Method method = c.getDeclaredMethod("sleep");
            method.setAccessible(true);
            method.invoke(cat);

            method = c.getDeclaredMethod("eat", String.class);
            method.invoke(cat, "world");

            // 调用不定项参数方法, 不定项参数可以当成数组来处理
            Class[] argClass = new Class[] {String[].class}; // 这个可以？
//            method = c.getDeclaredMethod("eat", String[].class); // 这样也可以，并易理解
            method = c.getDeclaredMethod("eat", argClass);
            String[] params = new String[]{"hi", "world"};
            method.invoke(cat, (Object) params); // 强转！！！

            // 这样如何？
//            Class[] aa = new Class[]{};
//            method = c.getDeclaredMethod("eat", aa); // 报错


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /**************************** Constructor ******************************

     和Method一样，Class也为Constructor提供了4种方法获取
     getDeclaredConstructor(Class<?>... parameterTypes)：获取指定构造函数，参数parameterTypes为构造方法的参数类型
     getConstructor(Class<?>... parameterTypes)：获取指定public构造函数，参数parameterTypes为构造方法的参数类型
     getDeclaredConstructors()：获取所有声明的构造方法
     getConstructors()：获取所有的public构造方法

     通过反射有两种方法可以创建对象：
     1）java.lang.reflect.Constructor.newInstance()
     2）Class.newInstance()
     一般来讲，我们优先使用第一种方法；那么这两种方法有何异同呢？
     Class.newInstance()仅可用来调用无参的构造方法；
     Constructor.newInstance()可以调用任意参数的构造方法。
     Class.newInstance()会将构造方法中抛出的异常不作处理原样抛出;
     Constructor.newInstance()会将构造方法中抛出的异常都包装成InvocationTargetException抛出。
     Class.newInstance()需要拥有构造方法的访问权限;
     Constructor.newInstance()可以通过setAccessible(true)方法绕过访问权限访问private构造方法。

     ************************************************************/



    /**************************** 数组和枚举 **********************

     数组类型：数组本质是一个对象，所以它也有自己的类型。
     例如对于int[] intArray，数组类型为class [I。数组类型中的[个数代表数组的维度，
     例如[代表一维数组，[[代表二维数组；[后面的字母代表数组元素类型，I代表int，
     一般为类型的首字母大写(long类型例外，为J)。

     class [B    //byte类型一维数组
     class [S    //short类型一维数组
     class [I    //int类型一维数组
     class [C    //char类型一维数组
     class [J    //long类型一维数组，J代表long类型，因为L被引用对象类型占用了
     class [F    //float类型一维数组
     class [D    //double类型一维数组
     class [Lcom.dada.Season    //引用类型一维数组
     class [[Ljava.lang.String  //引用类型二维数组


     java反射额外提供了几个方法为枚举服务。
     Class.isEnum(): Indicates whether this class represents an enum type

     Class.getEnumConstants():
     Retrieves the list of enum constants defined by the enum in the order they're declared

     java.lang.reflect.Field.isEnumConstant():
     Indicates whether this field represents an element of an enumerated type


     ************************************************************/

    private void testArray(Field field) {
        // 1
        // 获取一个变量的类型
        Class<?> c = field.getType();
        // 判断该变量是否为数组
        if (c.isArray()) {
            // 获取数组的元素类型
            c.getComponentType();
        }

        // 2
        // 创建数组, 参数componentType为数组元素的类型，后面不定项参数的个数代表数组的维度，参数值为数组长度
        // Array.newInstance(Class<?> componentType, int... dimensions)
        Object array = Array.newInstance(int.class, 2);

        //设置数组值，array为数组对象，index为数组的下标，value为需要设置的值
        Array.setInt(array , 0, 1);
        Array.setInt(array , 1, 2); // int[] array = new int[]{1, 2}
        // 注意：反射支持对数据自动加宽，但不允许数据narrowing(变窄?真难翻译)。
        // 意思是对于上述set方法，你可以在int类型数组中 set short类型数据，
        // 但不可以set long类型数据，否则会报IllegalArgumentException。

        // 获取数组的值，array为数组对象，index为数组的下标
        // Array.get(Object array, int index)


        // 3 多维数组
        Object matrix = Array.newInstance(int.class, 2, 2);
        Object row0 = Array.get(matrix, 0);
        Object row1 = Array.get(matrix, 1);

        Array.setInt(row0, 0, 1);
        Array.setInt(row0, 1, 2);
        Array.setInt(row1, 0, 3);
        Array.setInt(row1, 1, 4);

//
//        Object matrix = Array.newInstance(int.class, 2);
//        Object row0 = Array.newInstance(int.class, 2);
//        Object row1 = Array.newInstance(int.class, 2);
//
//        Array.setInt(row0, 0, 1);
//        Array.setInt(row0, 1, 2);
//        Array.setInt(row1, 0, 3);
//        Array.setInt(row1, 1, 4);
//
//        Array.set(matrix, 0, row0);
//        Array.set(matrix, 1, row1);




    }


}
