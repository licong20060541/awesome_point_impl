#include <iostream>
#include <exception>
using namespace std;

struct MyException : public exception
{
    const char *what() const throw()
    {
        return "C++ Exception";
    }
};

int main()
{
    try
    {
        throw MyException();
    }
    catch (MyException &e)
    {
        std::cout << "MyException caught" << std::endl;
        std::cout << e.what() << std::endl;
    }
    catch (std::exception &e)
    {
        //其他的错误
    }
}

// const throw() 不是函数，这个东西叫异常规格说明，表示 what 函数可以抛出异常的类型，
// 类型说明放到 () 里，这里面没有类型，就是声明这个函数不抛出异常，通常函数不写后面的就表示函数可以抛出任何类型的异常。
// 异常规格说明
// 1、异常规格说明的目的是为了让函数使用者知道该函数可能抛出的异常有哪些。 可以在函数的声明中列出这个函数可能抛掷的所有异常类型。例如：
// void fun() throw(A，B，C，D);
// 2、若无异常接口声明，则此函数可以抛掷任何类型的异常。
