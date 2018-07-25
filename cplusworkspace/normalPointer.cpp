#include <iostream>
using namespace std;

int main()
{
    int *pointer = nullptr;
    pointer = new int;
    *pointer = 5;
    cout << *pointer << endl;
    delete pointer;

    pointer = new int[5];
    pointer[0] = 9;
    pointer[1] = 8;
    cout << pointer[1] << endl;
    delete[] pointer;


}
