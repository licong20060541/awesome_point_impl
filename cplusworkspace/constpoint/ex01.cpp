#include <iostream>
using namespace std;

int main(void)
{
    const int a = 7;
    int *p = (int *)&a;
    *p = 8;
    cout << *p << endl; // 8
    cout << a << endl; // 7
    // 调试窗口看到a的值被改变为8，但是输出的结果仍然是7

    volatile const int b = 7;
    int *q = (int *)&b;
    *q = 8;
    cout << *q << endl; // 8
    cout << b << endl; // 8
    // Volatile关键字跟const对应相反, 所以不会被编译器优化，编译器也就不会改变对b变量的操作
    // 因此b的值被修改为了8
}