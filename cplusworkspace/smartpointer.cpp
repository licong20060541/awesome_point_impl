#include <iostream>
#include <vector>
using namespace std;

// 类成员访问运算符（ -> ）可以被重载，但它较为麻烦。它被定义用于为一个类赋予"指针"行为。
// 运算符 -> 必须是一个成员函数。如果使用了 -> 运算符，返回类型必须是指针或者是类的对象。
// 运算符 -> 通常与指针引用运算符 * 结合使用，用于实现"智能指针"的功能。
// 这些指针是行为与正常指针相似的对象，唯一不同的是，当您通过指针访问对象时，
// 它们会执行其他的任务。比如，当指针销毁时，或者当指针指向另一个对象时，会自动删除对象。
// 间接引用运算符 -> 可被定义为一个一元后缀运算符。也就是说，给出一个类：
// class Ptr{
//    //...
//    X * operator->();
// };
// 类 Ptr 的对象可用于访问类 X 的成员，使用方式与指针的用法十分相似。例如：
// void f(Ptr p )
// {
//    p->m = 10 ; // (p.operator->())->m = 10
// }
// 语句 p->m 被解释为 (p.operator->())->m。同样地，下面的实例演示了如何重载类成员访问运算符 ->。

// TODO: 如何自动释放资源

class Obj
{
    static int i, j;

  public:
    void f() const
    {
        cout << i++ << endl;
    }
    void g() const
    {
        cout << j++ << endl;
    }
};

// 静态成员定义
int Obj::i = 10;
int Obj::j = 20;

// 为上面的类实现一个容器
class ObjContainer
{
    vector<Obj *> a;

  public:
    void add(Obj *obj)
    {
        a.push_back(obj); // 调用向量的标准方法
    }
    friend class SmartPointer;
};

// 实现智能指针，用于访问类 Obj 的成员
class SmartPointer
{
    ObjContainer oc;
    int index;

  public:
    SmartPointer(ObjContainer &objc)
    {
        oc = objc;
        index = 0;
    }
    // 返回值表示列表结束
    bool operator++() // 前缀版本
    {
        if (index >= oc.a.size() - 1)
            return false;
        if (oc.a[++index] == 0)
            return false;
        return true;
    }
    bool operator++(int) // 后缀版本
    {
        return operator++();
    }
    // 重载运算符 ->
    Obj *operator->() const
    {
        if (!oc.a[index])
        {
            cout << "Zero value";
            return (Obj *)0;
        }
        return oc.a[index];
    }
};

int main()
{
    const int sz = 10;
    Obj o[sz];
    ObjContainer oc;
    for (int i = 0; i < sz; i++)
    {
        oc.add(&o[i]);
    }
    SmartPointer sp(oc); // 创建一个迭代器
    do
    {
        sp->f(); // 智能指针调用
        sp->g();
    } while (sp++);
    return 0;
}
