c++ const 关键字小结

const 在C++中是用来修饰内置类型变量，自定义对象，成员函数，返回值，函数参数

1. const修饰普通类型的变量
const int a = 7;
a = 8;       // it's wrong,
特殊：参考ex01.cpp

2. const 修饰指针变量有以下三种情况。

A:const 修饰指针指向的内容，则内容为不可变量。

B:const 修饰指针，则指针为不可变量。

C:const 修饰指针和指针指向的内容，则指针和指针指向的内容都为不可变量。

int a = 8;
const int * const  p = &a;

3. const参数传递和函数返回值

4. const修饰类成员函数
const 修饰类成员函数，其目的是防止成员函数修改被调用对象的值，
如果我们不想修改一个调用对象的值，所有的成员函数都应当声明为const成员函数。
注意：const关键字不能与static关键字同时使用，因为static关键字修饰静态成员函数，
静态成员函数不含有this指针，即不能实例化，const成员函数必须具体到某一实例。

参考ex02.cpp