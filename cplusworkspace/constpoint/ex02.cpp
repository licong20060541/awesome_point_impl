#include <iostream>
using namespace std;

class Test
{
  public:
    Test() {}
    Test(int value) : cat(value) {}
    int getCat() const
    {
        // ++cat; // 不可修改，报错
        cat2++; // 可以修改
        ++dog; // 可以修改
        return cat;
    }

  private:
    int cat;
    static int cat2;
    mutable int dog;
};

void outCat(const Test &test)
{
    cout << test.getCat() << endl; // int getCat()后面不添加const，则此处报错
}

int main(void)
{
    Test t(8);
    outCat(t);
    // system("pause");
    return 0;
}